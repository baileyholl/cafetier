package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.util.ITickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends Block implements ITickableBlock {
    public PlateBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlateEntity(pPos, pState);
    }
}
