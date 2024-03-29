package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import com.hollingsworth.cafetier.common.util.SerializeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.UUID;

public class Cafe {
    private String name;
    private String description;
    public UUID cafeUUID;
    public UUID ownerUUID;
    public BlockPos deskPos;
    private AABB area;
    private CafeGame game = null;

    private Cafe(UUID cafeUUID, String name, String description, UUID ownerUUID) {
        this.cafeUUID = cafeUUID;
        this.ownerUUID = ownerUUID;
        this.name = name;
        this.description = description;
        this.deskPos = null;
    }

    public static Cafe create(ServerLevel serverLevel, UUID ownerUUID, String name, String description){
        Cafe cafe = new Cafe(UUID.randomUUID(), name, description, ownerUUID);
        CafeSavedData.from(serverLevel).addCafe(cafe);
        return cafe;
    }

    public Cafe(CompoundTag tag){
        this.cafeUUID = tag.getUUID("uuid");
        this.deskPos = BlockPos.of(tag.getLong("blockPos"));
        this.name = tag.getString("name");
        this.description = tag.getString("description");
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
        if(description != null){
            tag.putString("description", description);
        }
        return tag;
    }

    public void startGame(ManagementDeskEntity desk, Player player){
        CafeGame game = CafeGame.buildGame(this, desk);
        if(game.spawnPositions.isEmpty()){
            player.sendSystemMessage(Component.translatable("cafetier.no_spawn_positions"));
        }
        if(game.menuStacks.size() < 3){
            player.sendSystemMessage(Component.translatable("cafetier.not_enough_menu_items"));
        }
        if(getBounds() == null){
            player.sendSystemMessage(Component.translatable("cafetier.no_bounds"));
        }
        if(!game.isValid()){
            return;
        }
        player.sendSystemMessage(Component.translatable("cafetier.game_starting"));
        this.game = game;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
