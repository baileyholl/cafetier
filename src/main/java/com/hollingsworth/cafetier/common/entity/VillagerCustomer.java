package com.hollingsworth.cafetier.common.entity;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.common.util.RandUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class VillagerCustomer extends Customer implements VillagerDataHolder {
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
        return new VillagerData(VillagerType.DESERT, VillagerProfession.ARMORER, 1);
    }

    @Override
    public void setVillagerData(VillagerData pData) {}
}
