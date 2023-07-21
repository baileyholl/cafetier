package com.hollingsworth.cafetier.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class Schematic extends Item {
    public static String POS1 = "pos1";
    public static String POS2 = "pos2";

    public Schematic(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide){
            return InteractionResult.SUCCESS;
        }

        ItemStack heldStack = pContext.getItemInHand();
        CompoundTag tag = heldStack.getOrCreateTag();
        if(tag.contains(POS1) && tag.contains(POS2)) {
        }
        else if(!tag.contains(POS1)){
            tag.putLong(POS1, pContext.getClickedPos().asLong());
        }else if(!tag.contains(POS2)){
            tag.putLong(POS2, pContext.getClickedPos().asLong());
        }
        return super.useOn(pContext);
    }
}
