package com.hollingsworth.cafetier.api.statemachine.customer;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

public class InteractEvent implements IStateEvent {

    public Player player;
    public InteractionHand hand;

    public InteractEvent(Player player, InteractionHand hand) {
        this.player = player;
        this.hand = hand;
    }
}
