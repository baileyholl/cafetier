package com.hollingsworth.cafetier.item;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeSavedData;
import com.hollingsworth.cafetier.api.DeedData;
import com.hollingsworth.cafetier.util.ModItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class DeedItem extends ModItem {

    public DeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        var deedData = new DeedData(pPlayer.getItemInHand(pUsedHand));
        if(pLevel instanceof ServerLevel serverLevel && deedData.getUuid() != null){
            deedData.setUuid(UUID.randomUUID());
            CafeSavedData.from(serverLevel).addCafe(new Cafe(deedData.getUuid()));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
