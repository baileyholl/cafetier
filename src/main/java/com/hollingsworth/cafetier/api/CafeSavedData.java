package com.hollingsworth.cafetier.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class CafeSavedData extends SavedData {
    private Map<UUID, Cafe> cafeMap = new HashMap<UUID, Cafe>();

    public CafeSavedData(){

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

    public CafeSavedData(CompoundTag tag){
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

    public Collection<Cafe> getCafes(){
        return cafeMap.values();
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public static CafeSavedData from(ServerLevel level){
        return level.getServer()
                .overworld()
                .getDataStorage()
                .computeIfAbsent(CafeSavedData::new, CafeSavedData::new, "CAFE_DATA" );
    }
}
