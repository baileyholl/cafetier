package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.api.CafeManager;
import com.hollingsworth.cafetier.api.game_events.FoodServedEvent;
import com.hollingsworth.cafetier.common.util.ITickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends SingleItemBlock implements ITickableBlock {

    public static final VoxelShape shape = Block.box(3, 0, 3, 13, 1, 13);
    public PlateBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlateEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return super.use(state, world, pos, player, handIn, hit);
    }

    @Override
    public void setStack(SingleItemTile tile, Player player) {
        super.setStack(tile, player);
        if(tile.getLevel() instanceof ServerLevel serverLevel) {
            CafeManager.postCafeEvent(new FoodServedEvent(tile.getBlockPos(), tile.stack.copy()), serverLevel, tile.getBlockPos());
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return shape;
    }

    @Override
    public @Nullable BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return BlockPathTypes.RAIL;
    }
}
