package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.util.ITickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class DisplayBlock extends SingleItemBlock implements ITickableBlock {

    public static final VoxelShape shape = Stream.of(
            Block.box(3, 4, 3, 13, 6, 13),
            Block.box(3, 6, 3, 13, 14, 13),
            Block.box(5, 0, 5, 11, 2, 11),
            Block.box(6, 2, 6, 10, 4, 10),
            Block.box(7, 14, 7, 9, 15, 9),
            Block.box(6, 15, 6, 10, 16, 10),
            Block.box(6, 2, 6, 10, 4, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public DisplayBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DisplayEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return shape;
    }
}
