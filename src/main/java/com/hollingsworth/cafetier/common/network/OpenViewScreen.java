package com.hollingsworth.cafetier.common.network;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.GameManager;
import com.hollingsworth.cafetier.client.gui.ViewCafeScreen;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenViewScreen implements Message{
    public BlockPos deskPos;
    public List<ItemStack> menuItems = new ArrayList<>();
    public int numSeats;
    public int numSpawners;
    Cafe cafe;
    public UUID gameUUID = null;

    public OpenViewScreen(ManagementDeskEntity managementDeskEntity){
        this.deskPos = managementDeskEntity.getBlockPos();
        CafeGame cafeGame = CafeGame.buildGame(managementDeskEntity.getCafe(), managementDeskEntity);
        this.menuItems = cafeGame.menuStacks;
        this.numSeats = cafeGame.numSeats;
        this.numSpawners = cafeGame.spawnPositions.size();
        cafe = managementDeskEntity.getCafe();
        CafeGame runningGame = GameManager.getGame((ServerLevel) managementDeskEntity.getLevel(), managementDeskEntity.getBlockPos());
        gameUUID = runningGame == null ? null : runningGame.uuid;
    }

    public OpenViewScreen(FriendlyByteBuf buf) {
        this.decode(buf);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(deskPos.asLong());
        buf.writeInt(menuItems.size());
        for(ItemStack stack : menuItems){
            buf.writeItem(stack);
        }
        buf.writeInt(numSeats);
        buf.writeInt(numSpawners);
        buf.writeNbt(cafe.save(new CompoundTag()));
        buf.writeBoolean(gameUUID != null);
        if(gameUUID != null){
            buf.writeUUID(gameUUID);
        }
    }

    @Override
    public void decode(FriendlyByteBuf buf) {
        deskPos = BlockPos.of(buf.readLong());
        int menuSize = buf.readInt();
        for(int i = 0; i < menuSize; i++){
            menuItems.add(buf.readItem());
        }
        numSeats = buf.readInt();
        numSpawners = buf.readInt();
        cafe = new Cafe(buf.readNbt());
        if(buf.readBoolean()) {
            gameUUID = buf.readUUID();
        }
    }

    @Override
    public void onClientReceived(Minecraft minecraft, Player player, NetworkEvent.Context context) {
        Minecraft.getInstance().setScreen(new ViewCafeScreen(
                cafe,
                menuItems,
                numSeats,
                numSpawners,
                gameUUID
        ));
    }
}
