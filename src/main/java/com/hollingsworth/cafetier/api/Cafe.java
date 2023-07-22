package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import com.hollingsworth.cafetier.common.util.SerializeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.UUID;

public class Cafe {
    public String name;
    public UUID cafeUUID;
    public UUID ownerUUID;
    public BlockPos deskPos;
    private AABB area;
    private CafeGame game = null;

    private Cafe(UUID cafeUUID, String name, UUID ownerUUID) {
        this.cafeUUID = cafeUUID;
        this.ownerUUID = ownerUUID;
        this.name = name;
        this.deskPos = null;
    }

    public static Cafe create(UUID ownerUUID, String name){
        return new Cafe(UUID.randomUUID(), name, ownerUUID);
    }

    public Cafe(CompoundTag tag){
        this.cafeUUID = tag.getUUID("uuid");
        this.deskPos = BlockPos.of(tag.getLong("blockPos"));
        this.name = tag.getString("name");
        if(tag.contains("area")){
            CompoundTag areaTag = tag.getCompound("area");
            area = SerializeUtil.aabbFromTag(areaTag);
        }
    }

    public CompoundTag save(CompoundTag tag){
        tag.putUUID("uuid", cafeUUID);
        if(deskPos != null) {
            tag.putLong("blockPos", deskPos.asLong());
        }
        if(name != null){
            tag.putString("name", name);
        }
        if(area != null){
            tag.put("area", SerializeUtil.aabbToTag(area));
        }
        return tag;
    }

    public void startGame(ManagementDeskEntity desk, Player player){
        game = CafeGame.buildGame(this, desk);
        if(game == null){
            player.sendSystemMessage(Component.literal("Invalid game setup"));
            game = null;
            return;
        }
        GameManager.addGame(desk.getLevel(), game);
    }

    public CafeGame getGame(){
        return game;
    }

    public void postEvent(IStateEvent event){
        if(game != null){
            game.onGameEvent(event);
        }
    }

    public @Nullable AABB getBounds(){
        return area;
    }

    public void setBounds(AABB bounds){
        area = bounds;
    }
}
