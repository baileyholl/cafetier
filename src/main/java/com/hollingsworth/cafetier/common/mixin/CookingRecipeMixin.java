package com.hollingsworth.cafetier.common.mixin;

import com.hollingsworth.cafetier.common.util.RecipeMixinUtils;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractCookingRecipe.class)
public class CookingRecipeMixin {

    @ModifyReturnValue(method = "assemble", at = @At("RETURN"))
    private ItemStack assemble(ItemStack original, Container pInv) {
        return RecipeMixinUtils.getAssembled(original, pInv);
    }
}
