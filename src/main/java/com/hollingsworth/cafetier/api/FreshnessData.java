package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.common.util.ItemstackData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class FreshnessData extends ItemstackData {
    public static final String TAG = "cafetier_freshness";
    private long createdStamp;
    private UUID gameUUID;

    public FreshnessData(ItemStack stack) {
        super(stack);
        var itemTag = getItemTag(stack);
        if(itemTag != null){
            createdStamp = itemTag.getLong("createdStamp");
            if(itemTag.contains("gameUUID")){
                gameUUID = itemTag.getUUID("gameUUID");
            }
        }
    }

    public FreshnessData(ItemStack stack, long createdStamp, UUID gameUUID) {
        super(stack);
        this.createdStamp = createdStamp;
        this.gameUUID = gameUUID;
        writeItem();
    }

    public static ItemStack assign(ItemStack stack, long createdStamp, UUID gameUUID){
        return new FreshnessData(stack, createdStamp, gameUUID).stack;
    }

    public UUID getGameUUID() {
        return gameUUID;
    }

    public long getCreatedStamp() {
        return createdStamp;
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        tag.putLong("createdStamp", createdStamp);
        if(gameUUID != null){
            tag.putUUID("gameUUID", gameUUID);
        }
    }

    @Override
    public String getTagString() {
        return TAG;
    }
}
