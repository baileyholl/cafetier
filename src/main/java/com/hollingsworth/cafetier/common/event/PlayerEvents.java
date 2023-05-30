package com.hollingsworth.cafetier.common.event;

import com.hollingsworth.cafetier.Cafetier;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cafetier.MODID)
public class PlayerEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void interactSeatEvent(PlayerInteractEvent.RightClickBlock event) {

//        if(event.getLevel().isClientSide
//            && event.getLevel().getBlockState(event.getPos()).getBlock() instanceof SeatBlock
//            && ClientInfo.seatingEntityID > 0
//            && event.getLevel().getEntity(ClientInfo.seatingEntityID) instanceof Customer customer
//            && customer.getCanBeSeated()){
//            Networking.sendToServer(new ClientSeatEntity(event.getHitVec().getBlockPos(), ClientInfo.seatingEntityID));
////            event.setCanceled(true);
//        }

    }
}
