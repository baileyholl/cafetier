package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.item.Schematic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.hollingsworth.cafetier.client.RaycastHelper.rayTraceRange;
import static com.hollingsworth.cafetier.client.RaycastHelper.rayTraceUntil;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Cafetier.MODID)
@OnlyIn(Dist.CLIENT)
public class ClientEvents {

//    @SubscribeEvent
//    public static void renderLast(final RenderLevelStageEvent event) {
//        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY){
//            BlockPos playerPos = Minecraft.getInstance().player.getOnPos();
////            event.getPoseStack().translate(playerPos.getX(), playerPos.getY(), playerPos.getZ());
//            BlockPos blockpos = new BlockPos(0,6, 0);
//
//            int size = 20;
//            AABB aabb = new AABB(blockpos).inflate(size);
//            ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
//            if(!(stack.getItem() instanceof Schematic) || !stack.hasTag())
//                return;
//            BlockPos firstPos = BlockPos.of(stack.getTag().getLong(Schematic.POS1));
//            if(firstPos == null)
//                return;
//
//            BlockPos secondPos = stack.getTag().contains(Schematic.POS2) ? BlockPos.of(stack.getTag().getLong(Schematic.POS2)) : null;
//            if(secondPos == null){
//                HitResult hitresult = Minecraft.getInstance().player.pick(20, event.getPartialTick(), false);
//                if(hitresult == null || hitresult.getType() != HitResult.Type.BLOCK)
//                    return;
//                Vec3 hitVec = hitresult.getLocation();
//                secondPos = new BlockPos(hitVec);
//            }
//            PoseStack poseStack = event.getPoseStack();
//            poseStack.pushPose();
//            Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
//                    .getPosition();
//
//            AABB aabb1 = new AABB(Vec3.atLowerCornerOf(firstPos), Vec3.atLowerCornerOf(secondPos));
//            boolean cameraInside = aabb1.contains(camera);
//            float inflate = cameraInside ? -1 / 128f : 1 / 128f;
//
//            aabb1.move(camera.scale(-1));
//            aabb1 = aabb1.move(-camera.x, -camera.y, -camera.z);
//
//            VertexConsumer vertexconsumer = CafeRenders.bufferSource.getBuffer(RenderType.lines());
//            LevelRenderer.renderLineBox(poseStack, vertexconsumer, aabb1, 0.9F, 0.9F, 0.9F, 1.0f);
//            CafeRenders.bufferSource.endBatch();
//            poseStack.popPose();
//        }
//    }

    @SubscribeEvent
    public static void renderLast(final RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) {
            return;
        }
        ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
        if (!(stack.getItem() instanceof Schematic) || !stack.hasTag())
            return;
        BlockPos firstPos = stack.getTag().contains(Schematic.POS1) ? BlockPos.of(stack.getTag().getLong(Schematic.POS1)) : null;
        if (firstPos == null)
            return;
        BlockPos selectedPos = null;
        LocalPlayer player = Minecraft.getInstance().player;
        BlockHitResult trace = rayTraceRange(player.level, player, 75);
        if (trace != null && trace.getType() == HitResult.Type.BLOCK) {

            BlockPos hit = trace.getBlockPos();
            boolean replaceable = player.level.getBlockState(hit)
                    .canBeReplaced(new BlockPlaceContext(new UseOnContext(player, InteractionHand.MAIN_HAND, trace)));
            if (trace.getDirection()
                    .getAxis()
                    .isVertical() && !replaceable)
                hit = hit.relative(trace.getDirection());
            selectedPos = hit;
        } else
            selectedPos = null;
        BlockPos secondPos = stack.getTag().contains(Schematic.POS2) ? BlockPos.of(stack.getTag().getLong(Schematic.POS2)) : null;
        if (secondPos == null) {
            secondPos = selectedPos;
        }
        Direction selectedFace = null;
        if (secondPos != null) {
            AABB bb = new AABB(firstPos, secondPos).expandTowards(1, 1, 1)
                    .inflate(.45f);
            Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera()
                    .getPosition();
            boolean inside = bb.contains(projectedView);
            RaycastHelper.PredicateTraceResult result =
                    rayTraceUntil(player, 70, pos -> inside ^ bb.contains(VecHelper.getCenterOf(pos)));
            selectedFace = result.missed() ? null
                    : inside ? result.getFacing()
                    .getOpposite() : result.getFacing();
        }

        AABB currentSelectionBox = null;
        if (secondPos == null) {
            if (firstPos == null) {
                currentSelectionBox = selectedPos == null ? null : new AABB(selectedPos);
            }else{
                currentSelectionBox = selectedPos == null ? new AABB(firstPos) : new AABB(firstPos, selectedPos).expandTowards(1, 1, 1);
            }
        }else {
            currentSelectionBox = new AABB(firstPos, secondPos).expandTowards(1, 1, 1);
        }
//        System.out.println(currentSelectionBox);
        if(currentSelectionBox != null){
            Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
                    .getPosition();
            boolean cameraInside = currentSelectionBox.contains(camera);
            float inflate = cameraInside ? -1 / 128f : 1 / 128f;

            currentSelectionBox.move(camera.scale(-1));
            currentSelectionBox = currentSelectionBox.move(-camera.x, -camera.y, -camera.z);

            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            VertexConsumer vertexconsumer = CafeRenders.bufferSource.getBuffer(RenderType.lines());
            LevelRenderer.renderLineBox(poseStack, vertexconsumer, currentSelectionBox, 0.9F, 0.9F, 0.9F, 1.0f);
            CafeRenders.bufferSource.endBatch();
            poseStack.popPose();
        }
    }


}
