package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.util.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PlateEntity extends ModdedTile {
    public PlateEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.PLATE_ENTITY.get(), pos, state);
    }
}
