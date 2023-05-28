package com.example.examplemod.api;

import com.example.examplemod.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class Cafe {
    public UUID uuid;
    public BlockPos blockPos;

    public Cafe(UUID uuid) {
        this.uuid = uuid;
        this.blockPos = null;
    }

    public Cafe(CompoundTag tag){
        this.uuid = tag.getUUID("uuid");
        this.blockPos = BlockPos.of(tag.getLong("blockPos"));
    }

    public CompoundTag save(CompoundTag tag){
        tag.putUUID("uuid", uuid);
        if(blockPos != null) {
            tag.putLong("blockPos", blockPos.asLong());
        }
        return tag;
    }

    public void tick(ManagementDeskEntity desk){

    }
}
