package com.hollingsworth.cafetier.common.event;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.item.ItemUtils;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cafetier.MODID)
public class ItemEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void craftedEvent(PlayerEvent.ItemCraftedEvent event) {

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void smeltedEvent(PlayerEvent.ItemSmeltedEvent event) {


    }

    @SubscribeEvent
    public static void onTooltip(final ItemTooltipEvent event) {
        ItemUtils.collectFreshnessTooltips(event.getItemStack(), event.getToolTip());
    }
}
