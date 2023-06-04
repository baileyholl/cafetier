package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.Cafetier;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = Cafetier.MODID)
public class GameManager {
    public static Map<String, List<CafeGame>> games = new ConcurrentHashMap<>();

    public static void tick(Level level){
        String key = level.dimension().location().toString();
        if(games.containsKey(key)){
            List<CafeGame> toRemove = new ArrayList<>();
            for(CafeGame game : games.get(key)){
                if(!game.isValid()){
                    game.doTeardown();
                    toRemove.add(game);
                    continue;
                }
                game.tick();
            }
            for(CafeGame game : toRemove){
                games.get(key).remove(game);
            }
        }
    }

    public static void addGame(Level level, CafeGame game){
        String key = level.dimension().location().toString();
        if(!games.containsKey(key)){
            games.put(key, new ArrayList<>());
        }
        games.get(key).add(game);
    }


    @SubscribeEvent
    public static void serverTick(TickEvent.LevelTickEvent e) {

        if (e.phase != TickEvent.Phase.END || e.level.isClientSide)
            return;
        tick(e.level);
    }
}
