package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.AABB;

import java.util.UUID;

public class Cafe {
    public UUID uuid;
    public BlockPos deskPos;
    private CafeGame game = null;

    public Cafe(UUID uuid) {
        this.uuid = uuid;
        this.deskPos = null;
    }

    public Cafe(CompoundTag tag){
        this.uuid = tag.getUUID("uuid");
        this.deskPos = BlockPos.of(tag.getLong("blockPos"));
    }

    public CompoundTag save(CompoundTag tag){
        tag.putUUID("uuid", uuid);
        if(deskPos != null) {
            tag.putLong("blockPos", deskPos.asLong());
        }
        return tag;
    }

    public void tick(ManagementDeskEntity desk){
        if(game != null){
            game.tick();
        }
    }

    public void startGame(ManagementDeskEntity desk){
        game = new CafeGame(this, desk);
        if(game.spawnPos == null){
            System.out.println("null spawn pos");
            game = null;
        }
    }

    public void postEvent(IStateEvent event){
        if(game != null){
            game.onGameEvent(event);
        }
    }

    public AABB getBounds(){
        return new AABB(deskPos).inflate(5);
    }
}
