package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import com.hollingsworth.cafetier.common.item.CafeItems;
import com.hollingsworth.cafetier.common.item.Schematic;
import com.hollingsworth.cafetier.common.util.ComponentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class RequestSchematicServer implements Message{

    public BlockPos deskPos;

    public RequestSchematicServer(BlockPos deskPos) {
        this.deskPos = deskPos;
    }

    public RequestSchematicServer(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(this.deskPos.asLong());
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        this.deskPos = BlockPos.of(buf.readLong());
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        ServerLevel level = player.getLevel();
        ManagementDeskEntity desk = (ManagementDeskEntity) level.getBlockEntity(this.deskPos);
        Cafe cafe = null;
        if(desk != null){
            cafe = desk.getCafe();
        }
        if(cafe != null){
            ItemStack schematiStack = new ItemStack(CafeItems.SCHEMATIC);
            if(cafe.getBounds() != null){
                // Contract because bounds are written as the expanded to match the UI
                Schematic.writeAABB(schematiStack, cafe.getBounds().contract(1,1,1));
            }
            player.level.addFreshEntity(new ItemEntity(player.level, deskPos.getX() + 0.5, deskPos.getY() + 1, deskPos.getZ() + 0.5, schematiStack));
            player.sendSystemMessage(Component.translatable("cafetier.update_schematic").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
        }
    }
}
