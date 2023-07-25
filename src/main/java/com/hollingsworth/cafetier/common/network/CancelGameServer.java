package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.GameManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

public class CancelGameServer implements Message{
    public UUID gameUUID;

    public CancelGameServer(UUID gameUUID) {
        this.gameUUID = gameUUID;
    }

    public CancelGameServer(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(gameUUID);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        gameUUID = buf.readUUID();
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        CafeGame game = GameManager.getGame((ServerLevel) player.level, gameUUID);
        if(game != null){
            GameManager.endGame((ServerLevel) player.level, game.uuid);
            player.sendSystemMessage(Component.translatable("cafetier.game_ended"));
        }else{
            player.sendSystemMessage(Component.translatable("cafetier.already_ended"));
        }
    }
}
