package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.GameManager;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class CancelGameServer implements Message{
    public BlockPos pos;

    public CancelGameServer(BlockPos pos) {
        this.pos = pos;
    }

    public CancelGameServer(FriendlyByteBuf buf) {
        decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(pos.asLong());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        pos = BlockPos.of(buf.readLong());
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        if(player.level.getBlockEntity(pos) instanceof ManagementDeskEntity managementDeskEntity){
            Cafe cafe = managementDeskEntity.getCafe();
            if(cafe != null){
                CafeGame game = GameManager.getGame((ServerLevel) player.level, cafe.cafeUUID);
                if(game != null){
                    GameManager.endGame((ServerLevel) player.level, game.uuid);
                    player.sendSystemMessage(Component.translatable("cafetier.game_ended"));
                }else{
                    player.sendSystemMessage(Component.translatable("cafetier.already_ended"));
                }
            }
        }
    }
}
