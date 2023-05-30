package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.common.entity.Customer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientSeatEntity {
    BlockPos seatPos;
    int id;
    public ClientSeatEntity(BlockPos seatPos, int id){
        this.seatPos = seatPos;
        this.id = id;
    }

    public ClientSeatEntity(FriendlyByteBuf pb) {
        seatPos = pb.readBlockPos();
        id = pb.readInt();
    }

    public void toBytes(FriendlyByteBuf pb) {
        pb.writeBlockPos(seatPos);
        pb.writeInt(id);
    }

    @SuppressWarnings("Convert2Lambda")
    public static boolean onMessage(ClientSeatEntity message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer sender = ctx.get().getSender();
                if(sender.getLevel().getEntity(message.id) instanceof Customer customer){

                }
            });
        }
        ctx.get().setPacketHandled(true);
        return true;
    }
}
