package com.hollingsworth.cafetier.common.event;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.api.GameManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cafetier.MODID)
public class ItemEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void craftedEvent(PlayerEvent.ItemCraftedEvent event) {
        if(!((event.getEntity() instanceof FakePlayer))
                && !event.getEntity().level.isClientSide
                && event.getCrafting().isEdible()
                && GameManager.hasGames(event.getEntity().level)){
            System.out.println("attach");
            event.getCrafting().getOrCreateTag().putString("test", "hi");
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void smeltedEvent(PlayerEvent.ItemSmeltedEvent event) {


    }

    @SubscribeEvent
    public static void onTooltip(final ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack.hasTag() && stack.getTag().contains("test")) {
            event.getToolTip().add(Component.literal(stack.getTag().getString("test")));
        }
    }
}
