package com.example.examplemod.block;

import com.example.examplemod.entity.SeatEntity;
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
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
    }
}
