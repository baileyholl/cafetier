package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.common.item.CafeItems;
import com.hollingsworth.cafetier.common.util.ComponentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class CreateCafeServer implements Message{
    public String cafeName;
    public String description;
    public BlockPos cafePos;

    public CreateCafeServer(BlockPos cafePos, String cafeName, String description) {
        this.cafeName = cafeName;
        this.cafePos = cafePos;
        this.description = description;
    }

    public CreateCafeServer(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(cafePos.asLong());
        buf.writeUtf(cafeName);
        buf.writeUtf(description);
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        cafePos = BlockPos.of(buf.readLong());
        cafeName = buf.readUtf();
        description = buf.readUtf();
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, ServerPlayer player, NetworkEvent.Context context) {
        ItemStack schematiStack = new ItemStack(CafeItems.SCHEMATIC);
        schematiStack.getOrCreateTag().putString("cafeName", cafeName);
        schematiStack.getOrCreateTag().putString("description", description);
        player.level.addFreshEntity(new ItemEntity(player.level, cafePos.getX() + 0.5, cafePos.getY() + 1, cafePos.getZ() + 0.5, schematiStack));
        player.sendSystemMessage(Component.translatable("cafetier.complete_cafe").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
    }
}
