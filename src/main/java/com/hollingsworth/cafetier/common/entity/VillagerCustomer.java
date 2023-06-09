package com.hollingsworth.cafetier.common.entity;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.common.util.RandUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class VillagerCustomer extends Customer implements VillagerDataHolder {

    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(VillagerCustomer.class, EntityDataSerializers.VILLAGER_DATA);

    public VillagerCustomer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public VillagerCustomer(EntityType<? extends PathfinderMob> pEntityType, Level pLevel, double x, double y, double z, CafeGame cafe) {
        super(pEntityType, pLevel, x, y, z, cafe);
    }

    @Override
    public void acknowledgeServer(@Nullable LivingEntity entity) {
        this.playSound(SoundEvents.VILLAGER_YES, 1.0f + (float) RandUtil.inRange(-0.2, 0.2), 1.0f + (float) RandUtil.inRange(-0.2, 0.2));
    }

    public VillagerCustomer(Level level, CafeGame cafe){
        super(CafeEntities.VILLAGER.get(), level, cafe);
    }

    public VillagerCustomer(Level level, double x, double y, double z, CafeGame cafe) {
        super(CafeEntities.VILLAGER.get(), level, x, y, z, cafe);
    }

    @Override
    public void angryAnimate() {
        super.angryAnimate();
        this.playSound(SoundEvents.VILLAGER_NO, 1.0f + (float) RandUtil.inRange(-0.2, 0.2), 1.0f + (float) RandUtil.inRange(-0.2, 0.2));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_VILLAGER_DATA, getRandomData());
    }

    public VillagerData getRandomData(){
        var villagerTypes = new VillagerType[]{VillagerType.DESERT, VillagerType.JUNGLE, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.SWAMP, VillagerType.TAIGA};
        var professions = new VillagerProfession[]{ VillagerProfession.ARMORER, VillagerProfession.BUTCHER, VillagerProfession.CARTOGRAPHER, VillagerProfession.CLERIC, VillagerProfession.FARMER, VillagerProfession.FISHERMAN, VillagerProfession.FLETCHER, VillagerProfession.LEATHERWORKER, VillagerProfession.LIBRARIAN, VillagerProfession.MASON, VillagerProfession.NITWIT, VillagerProfession.NONE, VillagerProfession.SHEPHERD, VillagerProfession.TOOLSMITH, VillagerProfession.WEAPONSMITH};
        var levels = new int[]{1, 2, 3, 4, 5};
        return new VillagerData(villagerTypes[random.nextInt(villagerTypes.length)],
                professions[random.nextInt(0, professions.length)],
                levels[random.nextInt(0, levels.length)]);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VILLAGER_AMBIENT;
    }

    @Override
    public VillagerData getVillagerData() {
        return this.entityData.get(DATA_VILLAGER_DATA);
    }

    @Override
    public void setVillagerData(VillagerData pData) {}
}
