package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.block.CafeBlocks;
import com.hollingsworth.cafetier.common.entity.CafeEntities;
import com.hollingsworth.cafetier.common.util.GuiEntityInfoHUD;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Cafetier.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class CafeRenders {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CafeBlocks.MANAGEMENT_DESK_ENTITY.get(), DeskRenderer::new);

//
        event.registerEntityRenderer(CafeEntities.SEAT.get(), SeatRender::new);
        event.registerEntityRenderer(CafeEntities.VILLAGER.get(), VillRenderer::new);
    }
    @SubscribeEvent
    public static void registerOverlays(final RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "tooltip", GuiEntityInfoHUD.OVERLAY);
    }

}
