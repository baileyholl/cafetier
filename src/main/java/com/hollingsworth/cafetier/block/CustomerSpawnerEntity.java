package com.hollingsworth.cafetier.block;

import com.hollingsworth.cafetier.api.CustomerSpawners;
import com.hollingsworth.cafetier.util.ITickable;
import com.hollingsworth.cafetier.util.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CustomerSpawnerEntity extends ModdedTile implements ITickable {
    public CustomerSpawnerEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.CUSTOMER_SPAWNER_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            return;
        }
        if (level.getGameTime() % 20 == 0) {
            CustomerSpawners.addPosition(level, worldPosition);
        }

    }
}
