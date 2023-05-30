package com.hollingsworth.cafetier.common.entity;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.statemachine.EmptyState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.api.statemachine.customer.GoToCafeState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Customer extends PathfinderMob {
    public Cafe cafe;
    public static final EntityDataAccessor<Boolean> CAN_BE_SEATED = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> PATIENCE = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MAX_PATIENCE = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> SHOW_PATIENCE = SynchedEntityData.defineId(Customer.class, EntityDataSerializers.BOOLEAN);

    public SimpleStateMachine brain = new SimpleStateMachine(EmptyState.INSTANCE);

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Customer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, double x, double y, double z, Cafe cafe){
        this(pEntityType, pLevel);
        this.setPos(x, y, z);
        this.cafe = cafe;
        brain = new SimpleStateMachine(new GoToCafeState(this));
    }

    public Customer(EntityType<? extends PathfinderMob> entityType, Level level, BlockPos spawnPos, Cafe cafe) {
        this(entityType, level, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, cafe);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CAN_BE_SEATED, false);
        this.entityData.define(PATIENCE, 100);
        this.entityData.define(MAX_PATIENCE, 100);
        this.entityData.define(SHOW_PATIENCE, false);
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide) {
            brain.tick();
        }
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        super.mobInteract(pPlayer, pHand);
        if(getCanBeSeated()){
            this.setLeashedTo(pPlayer, true);
        }
        return InteractionResult.SUCCESS;
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
        brain.onEvent(gameEvent);
    }

    public void setCanBeSeated(boolean canBeSeated){
        this.entityData.set(CAN_BE_SEATED, canBeSeated);
    }

    public boolean getCanBeSeated(){
        return this.entityData.get(CAN_BE_SEATED);
    }

    public void setPatience(int patience){
        this.entityData.set(PATIENCE, patience);
    }

    public int getPatience(){
        return this.entityData.get(PATIENCE);
    }

    public void setMaxPatience(int maxPatience){
        this.entityData.set(MAX_PATIENCE, maxPatience);
    }

    public int getMaxPatience(){
        return this.entityData.get(MAX_PATIENCE);
    }

    public void setShowPatience(boolean showPatience){
        this.entityData.set(SHOW_PATIENCE, showPatience);
    }

    public boolean getShowPatience(){
        return this.entityData.get(SHOW_PATIENCE);
    }
}
