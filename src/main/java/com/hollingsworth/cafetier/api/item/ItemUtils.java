package com.hollingsworth.cafetier.api.item;

import com.hollingsworth.cafetier.api.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemUtils {

    public static final Map<UUID, Long> CRAFT_TIMESTAMPS = new HashMap<>();
    public static final int STAMP_LEEWAY = 20 * 8;

    public static ItemStack assignFreshness(ServerLevel level, BlockPos pos, ItemStack stack) {
        if(GameManager.games.isEmpty() || !GameManager.hasGames(level)){
            return stack;
        }
        CafeGame game = GameManager.getGame(level, pos);
        if(game == null){
            return stack;
        }
        Cafe cafe = game.cafe;
        long currentGameTime = level.getGameTime();
        long previousStamp = CRAFT_TIMESTAMPS.computeIfAbsent(cafe.cafeUUID, uuid -> currentGameTime);
        boolean outdated = currentGameTime - previousStamp > STAMP_LEEWAY;
        if(outdated){
            CRAFT_TIMESTAMPS.put(cafe.cafeUUID, currentGameTime);
        }
        long stamp = outdated ? currentGameTime : previousStamp;
        return FreshnessData.assign(stack, stamp, game.uuid);
    }

    public static List<Component> collectFreshnessTooltips(ItemStack stack, List<Component> tooltip){
        if(stack.hasTag() && stack.getTag().contains(FreshnessData.TAG)) {
            FreshnessData freshnessData = new FreshnessData(stack);
            tooltip.add(Component.literal("Freshness: " + freshnessData.getCreatedStamp()));
        }
        return tooltip;
    }

    public static ItemStack removeFreshnessTag(ItemStack stack){
        if(stack.hasTag() && stack.getTag().contains(FreshnessData.TAG)) {
            stack.getTag().remove(FreshnessData.TAG);
            if(stack.getTag().isEmpty()){
                stack.setTag(null);
            }
        }
        return stack;
    }

    public static void removeFreshnessForPlayer(Player player, UUID gameUUID){
        Inventory inventory = player.getInventory();
        for(int i = 0; i < inventory.getContainerSize(); i++){
            ItemStack stack = inventory.getItem(i);
            if(stack.hasTag() && stack.getTag() != null && stack.getTag().contains(FreshnessData.TAG)){
                FreshnessData freshnessData = new FreshnessData(stack);
                if(freshnessData.getGameUUID().equals(gameUUID) || GameManager.getGame((ServerLevel) player.level, freshnessData.getGameUUID()) == null){
                    inventory.setItem(i, ItemUtils.removeFreshnessTag(stack));
                }
            }
        }
    }
}
