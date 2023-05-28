package com.hollingsworth.cafetier.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CafeData extends SavedData {
    private Map<UUID, Cafe> cafeMap = new HashMap<UUID, Cafe>();

    public CafeData(){

    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        var cafeList = new ListTag();
        for(var cafe : cafeMap.values()){
            cafeList.add(cafe.save(new CompoundTag()));
        }
        tag.put("CafeList", cafeList);
        return tag;
    }

    public CafeData(CompoundTag tag){
        var cafes = tag.getList("CafeList", 10);
        for(int i = 0; i < cafes.size(); i++){
            var cafe = new Cafe(cafes.getCompound(i));
            cafeMap.put(cafe.uuid, cafe);
        }
    }

    public void addCafe(Cafe cafe){
        cafeMap.put(cafe.uuid, cafe);
    }

    public Cafe getCafe(UUID uuid){
        return cafeMap.get(uuid);
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public static CafeData from(ServerLevel level){
        return level.getServer()
                .overworld()
                .getDataStorage()
                .computeIfAbsent(CafeData::new, CafeData::new, "CAFE_DATA" );
    }
}
