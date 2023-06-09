package com.hollingsworth.cafetier.common.mixin;

import com.hollingsworth.cafetier.common.util.RecipeMixinUtils;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShapedRecipe.class)
public class ShapedRecipeMixin {

    @ModifyReturnValue(method = "assemble", at = @At("RETURN"))
    private ItemStack assemble(ItemStack original, CraftingContainer pInv) {
        return RecipeMixinUtils.getAssembled(original, pInv);
    }
}
