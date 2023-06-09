package com.hollingsworth.cafetier.common.util;

import com.hollingsworth.cafetier.api.item.ItemUtils;
import com.hollingsworth.cafetier.common.mixin.CraftingContainerAccessor;
import com.hollingsworth.cafetier.common.mixin.CraftingMenuAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class RecipeMixinUtils {
    public static ItemStack getAssembled(ItemStack original, Container pInv){
        ServerLevel level = null;
        BlockPos pos = null;
        if (pInv instanceof CraftingContainerAccessor craftingContainerAccessor
                && craftingContainerAccessor.getMenu() instanceof CraftingMenuAccessor craftingMenu) {
            level = (ServerLevel) craftingMenu.getPlayer().level;
            pos = craftingMenu.getPlayer().getOnPos();
        }else if(pInv instanceof AbstractFurnaceBlockEntity abstractFurnaceBlockEntity){
            level = (ServerLevel) abstractFurnaceBlockEntity.getLevel();
            pos = abstractFurnaceBlockEntity.getBlockPos();
        }
        if(level != null && pos != null) {
            return ItemUtils.assignFreshness(level, pos, original);
        }

        return original;
    }
}
