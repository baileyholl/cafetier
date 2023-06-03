package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.util.RandUtil;
import com.mojang.math.Vector3d;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class PlateEntity extends SingleItemTile {

    public PlateEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.PLATE_ENTITY.get(), pos, state);
    }


    public void spawnEatingParticles(int numParticles){
        for(int i = 0; i < numParticles; ++i) {
            var vec31 = new Vector3d(this.getX() + .5, this.getY() + 0.3, this.getZ() + 0.5);
            if (this.level instanceof ServerLevel) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
                ((ServerLevel) this.level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, getStack()), vec31.x, vec31.y, vec31.z, 1, RandUtil.inRange(-0.25, 0.25), RandUtil.inRange(0.1, 0.2), RandUtil.inRange(-0.25, 0.25), 0.0D);
        }
    }
}
