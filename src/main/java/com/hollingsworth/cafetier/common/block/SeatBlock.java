package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.entity.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SeatBlock extends Block {
    VoxelShape SEAT = box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    // Static Block Shapes
    VoxelShape SEAT_COLLISION = box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0);
    public SeatBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void fallOn(Level p_152426_, BlockState p_152427_, BlockPos p_152428_, Entity p_152429_, float p_152430_) {
        super.fallOn(p_152426_, p_152427_, p_152428_, p_152429_, p_152430_ * 0.5f);
    }


    @Override
    public void updateEntityAfterFallOn(BlockGetter reader, Entity entity) {
        var pos = entity.blockPosition();
        if (entity instanceof Player
                || !(entity instanceof LivingEntity)
                || !canBePickedUp(entity)
                || isSeatOccupied(entity.level, pos)) {
            if (entity.isSuppressingBounce()) {
                super.updateEntityAfterFallOn(reader, entity);
                return;
            }
            var vec3 = entity.getDeltaMovement();
            if (vec3.y < 0.0) {
                var d0 = entity instanceof LivingEntity ? 1.0 : 0.8;
                entity.setDeltaMovement(vec3.x, -vec3.y * 0.66 * d0, vec3.z);
            }
            return;
        }
        if (reader.getBlockState(pos).getBlock() != this) {
            return;
        }
        sitDown(entity.level, pos, entity);
    }

    public void sitDown(Level world, BlockPos pos, Entity entity){
        if(world.isClientSide){
            return;
        }
        var seat = new SeatEntity(world, pos);
        seat.setPos(pos.getX() + .5, pos.getY(), pos.getZ() + .5);
        world.addFreshEntity(seat);
        entity.startRiding(seat, true);
        if (entity instanceof TamableAnimal tamableAnimal)
            tamableAnimal.setInSittingPose(true);
    }

    public boolean isSeatOccupied(Level world, BlockPos pos){
        return !world.getEntitiesOfClass(SeatEntity.class, new AABB(pos)).isEmpty();
    }

    public boolean canBePickedUp(Entity entity){
        if(entity instanceof Shulker || entity instanceof Player){
            return false;
        }
        return entity instanceof LivingEntity;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return BlockPathTypes.RAIL;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SEAT;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return SEAT_COLLISION;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pPlayer.isShiftKeyDown()){
            return InteractionResult.PASS;
        }
        var seats = pLevel.getEntitiesOfClass(SeatEntity.class, new AABB(pPos));
        if(!seats.isEmpty()){
            var seat = seats.get(0);
            var passengers = seat.getPassengers();
            if(!passengers.isEmpty() && passengers.get(0) instanceof Player){
                return InteractionResult.PASS;
            }
            if(!pLevel.isClientSide){
                seat.ejectPassengers();
                pPlayer.startRiding(seat);
            }
            return InteractionResult.SUCCESS;
        }
        if(pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }
        sitDown(pLevel, pPos, getLeashed(pPlayer).orElse(pPlayer));
        return InteractionResult.SUCCESS;
    }

    public Optional<Entity> getLeashed(Player player){
        var entities = player.level.getEntities(null, player.getBoundingBox().inflate(10));
        for(Entity entity : entities){
            if(entity instanceof Mob && ((Mob) entity).getLeashHolder() == player && canBePickedUp(entity)){
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }
}
