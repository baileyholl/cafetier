package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeSavedData;
import com.hollingsworth.cafetier.common.item.CafeItems;
import com.hollingsworth.cafetier.common.item.Schematic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;

public class RequestSchematicServer implements Message{

    public UUID cafeUUID;
    public BlockPos deskPos;

    public RequestSchematicServer(UUID cafeUUID, BlockPos deskPos) {
        this.cafeUUID = cafeUUID;
        this.deskPos = deskPos;
    }

    public RequestSchematicServer(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.cafeUUID);
        buf.writeLong(this.deskPos.asLong());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.cafeUUID = buf.readUUID();
        this.deskPos = BlockPos.of(buf.readLong());
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        Cafe cafe = CafeSavedData.from((ServerLevel) player.level).getCafe(this.cafeUUID);
        if(cafe != null){
            ItemStack schematiStack = new ItemStack(CafeItems.SCHEMATIC);
            if(cafe.getBounds() != null){
                Schematic.writeAABB(schematiStack, cafe.getBounds());
            }
            player.level.addFreshEntity(new ItemEntity(player.level, deskPos.getX() + 0.5, deskPos.getY() + 1, deskPos.getZ() + 0.5, schematiStack));
            player.sendSystemMessage(Component.translatable("cafetier.update_schematic"));
        }
    }
}
