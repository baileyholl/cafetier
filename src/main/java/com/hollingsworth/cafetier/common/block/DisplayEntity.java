package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.util.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DisplayEntity extends ModdedTile {
    public DisplayEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.DISPLAY_ENTITY.get(), pos, state);
    }
}
