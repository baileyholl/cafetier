package com.hollingsworth.cafetier.block;

import com.hollingsworth.cafetier.util.ITickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CustomerSpawner extends Block implements ITickableBlock {
    public CustomerSpawner(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CustomerSpawnerEntity(pPos, pState);
    }
}
