package com.example.examplemod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class SeatEntity extends Entity implements IEntityAdditionalSpawnData {

    public SeatEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        noPhysics = true;
    }

    public SeatEntity(Level pLevel, BlockPos pos) {
        super(CafeEntities.SEAT.get(), pLevel);
        noPhysics = true;
    }

    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        var bb = getBoundingBox();
        var diff = new Vec3(x, y, z).subtract(bb.getCenter());
        this.setBoundingBox(bb.move(diff));
    }

    @Override
    public void setDeltaMovement(Vec3 pMotion) {}

    @Override
    protected boolean canRide(Entity pVehicle) {
        return !(pVehicle instanceof FakePlayer);
    }

    @Override
    protected void removePassenger(Entity pPassenger) {
        super.removePassenger(pPassenger);
        if(pPassenger instanceof TamableAnimal tamableAnimal){
            tamableAnimal.setInSittingPose(false);
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pPassenger) {
        return super.getDismountLocationForPassenger(pPassenger).add(0, 0.5, 0);
    }

    @Override
    public void positionRider(Entity pEntity, MoveFunction pCallback) {
        if(!this.hasPassenger(pEntity)){
            return;
        }
        var d0 = this.getY() + this.getPassengersRidingOffset() + pEntity.getMyRidingOffset();
        pCallback.accept(pEntity, this.getX(), d0 + getCustomEntitySeatOffset(pEntity), this.getZ());
    }

    public double getCustomEntitySeatOffset(Entity entity) {
        if (entity instanceof Slime) return 0.25;
        if (entity instanceof Parrot) return (1 / 16f);
        if (entity instanceof Skeleton) return (1 / 8f);
        if (entity instanceof Creeper) return (1 / 8f);
        if (entity instanceof Cat) return (1 / 8f);
        return  (entity instanceof Wolf) ? (1 / 16f) : 0.0;
    }

    @Override
    public void positionRider(Entity pPassenger) {
        super.positionRider(pPassenger);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {}

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {}
}
