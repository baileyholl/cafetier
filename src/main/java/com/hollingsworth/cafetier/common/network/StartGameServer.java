package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.GameManager;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class StartGameServer implements Message{

    public BlockPos cafePos;

    public StartGameServer(BlockPos cafePos) {
        this.cafePos = cafePos;
    }

    public StartGameServer(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(cafePos.asLong());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        cafePos = BlockPos.of(buf.readLong());
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        if(player.level.getBlockEntity(cafePos) instanceof ManagementDeskEntity managementDeskEntity){
            Cafe cafe = managementDeskEntity.getCafe();
            if(cafe != null){
                CafeGame game = GameManager.getGame((ServerLevel) player.level, cafe.cafeUUID);
                if(game == null){
                    cafe.startGame(managementDeskEntity, player);
                }
            }
        }
    }
}
