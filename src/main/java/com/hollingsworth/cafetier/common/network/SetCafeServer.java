package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

public class SetCafeServer implements Message{
    public UUID cafeUUID;
    public BlockPos deskPos;

    public SetCafeServer(BlockPos deksPos, UUID cafeUUID) {
        this.cafeUUID = cafeUUID;
        this.deskPos = deksPos;
    }

    public SetCafeServer(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(deskPos.asLong());
        buf.writeUUID(cafeUUID);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        deskPos = BlockPos.of(buf.readLong());
        cafeUUID = buf.readUUID();
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        if(player.level.getBlockEntity(deskPos) instanceof ManagementDeskEntity managementDeskEntity){
            managementDeskEntity.setCafe(cafeUUID);
            player.sendSystemMessage(Component.translatable("cafetier.cafe_set"));
        }
    }
}
