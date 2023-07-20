package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeSavedData;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

public class CreateCafeServer implements Message{
    public String cafeName;
    public BlockPos cafePos;

    public CreateCafeServer(BlockPos cafePos, String cafeName) {
        this.cafeName = cafeName;
        this.cafePos = cafePos;
    }

    public CreateCafeServer(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(cafePos.asLong());
        buf.writeUtf(cafeName);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        cafePos = BlockPos.of(buf.readLong());
        cafeName = buf.readUtf();
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        UUID ownerUUID = player.getUUID();
        Cafe cafe = Cafe.create(ownerUUID, cafeName);
        CafeSavedData.from(player.getLevel()).addCafe(cafe);
        if(player.level.getBlockEntity(cafePos) instanceof ManagementDeskEntity managementDeskEntity){
            managementDeskEntity.setCafe(cafe.cafeUUID);
        }
    }
}
