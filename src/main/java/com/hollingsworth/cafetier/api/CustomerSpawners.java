package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.block.CustomerSpawnerEntity;
import com.hollingsworth.cafetier.util.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerSpawners {

    public static final Map<String, Set<BlockPos>> CUSTOMER_SPAWNERS = new ConcurrentHashMap<>();

    public static void addPosition(Level world, BlockPos pos) {
        String key = world.dimension().location().toString();
        if (!CUSTOMER_SPAWNERS.containsKey(key))
            CUSTOMER_SPAWNERS.put(key, new HashSet<>());

        CUSTOMER_SPAWNERS.get(key).add(pos);
    }

    public static List<BlockPos> getSpawnersForCafe(Level level, Cafe cafe, double distance){
        List<BlockPos> stalePos = new ArrayList<>();
        Set<BlockPos> worldList = CUSTOMER_SPAWNERS.getOrDefault(level.dimension().location().toString(), new HashSet<>());
        List<BlockPos> spawners = new ArrayList<>();
        for(BlockPos p : worldList){
            if(!level.isLoaded(p))
                continue;
            BlockEntity entity = level.getBlockEntity(p);
            if(level.getBlockEntity(p) == null || !(entity instanceof CustomerSpawnerEntity)){
                stalePos.add(p);
                continue;
            }
            AABB cafeBounds = cafe.getBounds();
            AABB spawnerBounds = new AABB(entity.getBlockPos());

            if(!cafeBounds.intersects(spawnerBounds)
                    && BlockUtil.boxDistance(cafeBounds, spawnerBounds) >= distance){
                System.out.println(BlockUtil.boxDistance(cafeBounds, spawnerBounds));
                spawners.add(p);
            }
        }
        for(BlockPos p : stalePos){
            worldList.remove(p);
        }
        return spawners;
    }

}
