package com.hollingsworth.cafetier.common.mixin;

import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CampfireBlockEntity.class)
public class CampfireTileMixin {
//
//    @Inject(method= "cookTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;)V",
//            at=@At(value = "INVOKE"),
//            target = "Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;cookTick(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/CampfireBlockEntity;)V",
//    locals=LocalCapture.PRINT)
//    private static void outputStack(Level pLevel, BlockPos pPos, BlockState pState, CampfireBlockEntity pBlockEntity, CallbackInfo ci, boolean flag, int i, ItemStack itemStack, int j, Container container, ItemStack itemStack1) {
//        //ItemUtils.assignFreshness(level, pos, itemStack);
//    }
}
