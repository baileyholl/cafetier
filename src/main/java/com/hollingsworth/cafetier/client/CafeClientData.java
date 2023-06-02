package com.hollingsworth.cafetier.client;

import net.minecraft.nbt.CompoundTag;

public class CafeClientData {
    public int score;
    public int customersRemaining;
    public int cafeTime;

    public CafeClientData(CompoundTag tag){
        score = tag.getInt("score");
        customersRemaining = tag.getInt("customersRemaining");
        cafeTime = tag.getInt("cafeTime");
    }

    public CafeClientData(int score, int customersRemaining, int cafeTime){
        this.score = score;
        this.customersRemaining = customersRemaining;
        this.cafeTime = cafeTime;
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putInt("score", score);
        tag.putInt("customersRemaining", customersRemaining);
        tag.putInt("cafeTime", cafeTime);
        return tag;
    }
}
