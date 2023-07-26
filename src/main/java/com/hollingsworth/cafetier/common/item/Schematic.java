package com.hollingsworth.cafetier.common.item;

import com.hollingsworth.cafetier.common.block.ManagementDesk;
import com.hollingsworth.cafetier.common.util.ComponentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

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
        if(pContext.getLevel().getBlockState(pContext.getClickedPos()).getBlock() instanceof ManagementDesk){
            return InteractionResult.PASS;
        }
        ItemStack heldStack = pContext.getItemInHand();
        CompoundTag tag = heldStack.getOrCreateTag();
        if(pContext.getPlayer().isShiftKeyDown()){
            tag.remove(POS1);
            tag.remove(POS2);
            pContext.getPlayer().sendSystemMessage(Component.translatable("cafetier.cleared_boundaries"));
        }
        else if(!tag.contains(POS1)){
            tag.putLong(POS1, pContext.getClickedPos().asLong());
            pContext.getPlayer().sendSystemMessage(Component.translatable("cafetier.first_boundary"));
        }else if(!tag.contains(POS2)){
            tag.putLong(POS2, pContext.getClickedPos().asLong());
            pContext.getPlayer().sendSystemMessage(Component.translatable("cafetier.second_boundary"));
        }
        return super.useOn(pContext);
    }

    public static void writeAABB(ItemStack stack, AABB aabb){
        CompoundTag tag = stack.getOrCreateTag();
        BlockPos start = new BlockPos(aabb.minX, aabb.minY, aabb.minZ);
        BlockPos end = new BlockPos(aabb.maxX, aabb.maxY, aabb.maxZ);
        tag.putLong(POS1, start.asLong());
        tag.putLong(POS2, end.asLong());
    }

    public static BlockPos getPos(ItemStack stack, String key){
        if(!stack.hasTag() || !stack.getTag().contains(key)){
            return null;
        }
        return BlockPos.of(stack.getTag().getLong(key));
    }

    public @Nullable AABB getAABB(ItemStack stack){
        CompoundTag tag = stack.getTag();
        if(tag != null && tag.contains(POS1) && tag.contains(POS2)){
            return new AABB(BlockPos.of(tag.getLong(POS1)), BlockPos.of(tag.getLong(POS2)));
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @org.jetbrains.annotations.Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        BlockPos startPos = getPos(pStack, POS1);
        BlockPos endPos = getPos(pStack, POS2);

        if(startPos != null){
            pTooltipComponents.add(Component.literal("Start: " + startPos.getX() + ", " + startPos.getY() + ", " + startPos.getZ()));
        }
        if(endPos != null){
            pTooltipComponents.add(Component.literal("End: " + endPos.getX() + ", " + endPos.getY() + ", " + endPos.getZ()));
        }else{
            pTooltipComponents.add(Component.literal("Right click on a block to set the end position").withStyle(ComponentUtil.TAKE_ACTION_STYLE));
        }
        CompoundTag tag = pStack.getTag();
        if(tag != null){
            String cafeName = tag.getString("cafeName");
            String description = tag.getString("description");
            if(!cafeName.isEmpty()) {
                pTooltipComponents.add(Component.literal(cafeName));
            }
            if(!description.isEmpty()) {
                pTooltipComponents.add(Component.literal(description));
            }
        }
    }
}
