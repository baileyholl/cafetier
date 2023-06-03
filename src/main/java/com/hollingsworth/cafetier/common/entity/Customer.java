package com.hollingsworth.cafetier.common.entity;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.game_events.CustomerDiedEvent;
import com.hollingsworth.cafetier.api.game_events.InteractEvent;
import com.hollingsworth.cafetier.api.statemachine.CustomerSM;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.customer.GoToCafeState;
import com.hollingsworth.cafetier.api.statemachine.customer.LeavingCafeState;
import com.hollingsworth.cafetier.common.entity.goal.LookAtPositionGoal;
import com.hollingsworth.cafetier.common.util.ITooltipProvider;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Customer extends PathfinderMob implements ITooltipProvider {
    public Cafe cafe;
    public static final EntityDataAccessor<Boolean> CAN_BE_SEATED = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAPPINESS_HIT_ZERO = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> HAPPINESS = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_HAPPINESS = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<ItemStack> DESIRED_ITEM = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.ITEM_STACK);
    public static final EntityDataAccessor<BlockPos> EATING_AT = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.BLOCK_POS);
    public static final EntityDataAccessor<Boolean> SHOW_DESIRED_ITEM = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.BOOLEAN);
    public int angryParticleCD = 0;
    public CustomerSM brain = null;
    public BlockPos spawnPos;
    public Vector3d forcedLookAt = null;

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Customer(EntityType<? extends PathfinderMob> entityType, Level level, Cafe cafe){
        this(entityType, level);
        this.cafe = cafe;
        brain = new CustomerSM(new GoToCafeState(this), this);
    }

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, double x, double y, double z, Cafe cafe){
        this(pEntityType, pLevel, cafe);
        this.setPos(x, y, z);
        spawnPos = new BlockPos(x, y, z);
    }

    public Customer(EntityType<? extends PathfinderMob> entityType, Level level, BlockPos spawnPos, Cafe cafe) {
        this(entityType, level, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, cafe);
    }

    public boolean desiresStack(ItemStack stack){
        return stack.sameItem(this.getDesiredItem());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LookAtPositionGoal(this, () -> forcedLookAt));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, LivingEntity.class, 8.0f));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_BE_SEATED, false);
        this.entityData.define(HAPPINESS, 100);
        this.entityData.define(MAX_HAPPINESS, 100);
        this.entityData.define(DESIRED_ITEM, ItemStack.EMPTY);
        this.entityData.define(EATING_AT, BlockPos.ZERO);
        this.entityData.define(HAPPINESS_HIT_ZERO, false);
        this.entityData.define(SHOW_DESIRED_ITEM, false);
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide) {
            if(brain != null) {
                brain.tick();
            }
            if(cafe == null || cafe.getGame() == null){
                this.remove(RemovalReason.DISCARDED);
            }
            if(angryParticleCD > 0){
                angryParticleCD--;
            }
        }
    }

    public void onHappinessZero(){
        if(!level.isClientSide){
            this.setHappinessHitZero(true);
            brain.setState(new LeavingCafeState(this));
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.loseHappiness(Math.max(1, (int)pAmount));
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        if(!level.isClientSide) {
            brain.onEvent(new CustomerDiedEvent(this));
        }
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        super.mobInteract(pPlayer, pHand);
        if(!level.isClientSide){
            InteractEvent event = new InteractEvent(pPlayer, pHand);
            onEvent(event);
        }
        return InteractionResult.SUCCESS;
    }

    public void addParticlesAroundSelf(ParticleOptions pParticleOption) {
        if(level instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 5; ++i) {
                double d0 = this.random.nextGaussian() * 0.52D;
                double d1 = this.random.nextGaussian() * 0.52D;
                double d2 = this.random.nextGaussian() * 0.52D;
                serverLevel.sendParticles(pParticleOption,  this.getRandomX(1.0D), this.getRandomY() + 1.0D, this.getRandomZ(1.0D), 1, d0, d1, d2, 0.2D);
            }
        }
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    @Override
    public void dropLeash(boolean pBroadcastPacket, boolean pDropLeash) {
        if (this.leashHolder != null) {
            this.leashHolder = null;
            this.leashInfoTag = null;
            if (!this.level.isClientSide && pBroadcastPacket && this.level instanceof ServerLevel) {
                ((ServerLevel)this.level).getChunkSource().broadcast(this, new ClientboundSetEntityLinkPacket(this, (Entity)null));
            }
        }
    }

    public void onEvent(IStateEvent gameEvent){
        if(!level.isClientSide) {
            brain.onEvent(gameEvent);
        }
    }

    public @Nullable BlockPos getSeatedPos(){
        if(this.isPassenger() && this.getVehicle() instanceof SeatEntity seat){
            return seat.blockPosition();
        }
        return null;
    }

    public int timeToOrder(){
        return 20 * 8;
    }

    public int timeToEat(){
        return 20 * 8;
    }

    public int maxWaitForSeat(){
        return 20 * 10;
    }

    public int maxWaitToOrder(){
        return 20 * 10;
    }

    public int maxWaitForFood(){
        return 20 * 30;
    }

    public int maxWaitToPay(){
        return 20 * 10;
    }

    public float scoreModifier(){
        return 1.0f;
    }

    public void loseHappiness(int amount){
        this.setHappiness(this.getHappiness() - amount);
        if(angryParticleCD == 0) {
            angryAnimate();
            angryParticleCD = 20;
        }
        if(this.getHappiness() <= 0){
            this.onHappinessZero();
        }
    }

    public void angryAnimate(){
        this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
    }

    public void setCanBeSeated(boolean canBeSeated){
        this.entityData.set(CAN_BE_SEATED, canBeSeated);
    }

    public boolean getCanBeSeated(){
        return this.entityData.get(CAN_BE_SEATED);
    }

    public void setHappiness(int patience){
        this.entityData.set(HAPPINESS, patience);
    }

    public int getHappiness(){
        return this.entityData.get(HAPPINESS);
    }

    public void setMaxHappiness(int maxPatience){
        this.entityData.set(MAX_HAPPINESS, maxPatience);
    }

    public int getMaxHappiness(){
        return this.entityData.get(MAX_HAPPINESS);
    }


    public void setDesiredItem(ItemStack desiredItem){
        this.entityData.set(DESIRED_ITEM, desiredItem);
    }

    public ItemStack getDesiredItem(){
        return this.entityData.get(DESIRED_ITEM);
    }

    public void setEatingAt(BlockPos eatingAt){
        this.entityData.set(EATING_AT, eatingAt);
    }

    public BlockPos getEatingAt(){
        return this.entityData.get(EATING_AT);
    }

    public void setHappinessHitZero(boolean happinessHitZero){
        this.entityData.set(HAPPINESS_HIT_ZERO, happinessHitZero);
    }

    public boolean didHappinessHitZero(){
        return this.entityData.get(HAPPINESS_HIT_ZERO);
    }

    public void setShowDesiredItem(boolean showDesiredItem){
        this.entityData.set(SHOW_DESIRED_ITEM, showDesiredItem);
    }

    public boolean showDesiredItem(){
        return this.entityData.get(SHOW_DESIRED_ITEM);
    }

    @Override
    public void getTooltip(List<Component> tooltip) {
        tooltip.add(Component.literal("Happiness: " + this.getHappiness()));
    }
}
