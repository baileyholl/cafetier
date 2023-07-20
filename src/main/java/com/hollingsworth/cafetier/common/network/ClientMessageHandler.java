/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.hollingsworth.cafetier.common.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientMessageHandler {

    public static <T extends Message> void handleClient(T message, Supplier<NetworkEvent.Context> ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        message.onClientReceived(minecraft, minecraft.player, ctx.get());
    }
}
