package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.item.Schematic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Cafetier.MODID)
@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void renderLast(final RenderLevelStageEvent event) {
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY){
            BlockPos playerPos = Minecraft.getInstance().player.getOnPos();
//            event.getPoseStack().translate(playerPos.getX(), playerPos.getY(), playerPos.getZ());
            BlockPos blockpos = new BlockPos(0,6, 0);

            int size = 20;
            AABB aabb = new AABB(blockpos).inflate(size);
            ItemStack stack = Minecraft.getInstance().player.getMainHandItem();
            if(!(stack.getItem() instanceof Schematic) || !stack.hasTag())
                return;
            BlockPos firstPos = BlockPos.of(stack.getTag().getLong(Schematic.POS1));
            if(firstPos == null)
                return;
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
                    .getPosition();
//            Transformation transformation = new Transformation()
//            poseStack.pushTransformation();
//            poseStack.translate(firstPos.getX() - camera.x, firstPos.getY() - camera.y, firstPos.getZ() - camera.z);

            BlockPos secondPos = stack.getTag().contains(Schematic.POS2) ? BlockPos.of(stack.getTag().getLong(Schematic.POS2)) : null;
            if(secondPos == null){
                secondPos = new BlockPos(camera);
            }
            AABB aabb1 = new AABB(firstPos, secondPos).inflate(1.0);
            aabb1.move(camera.scale(-1));

//            AABB aabb1 = new AABB(firstPos, secondPos).inflate(1.0);
            // Normalize AABB with respect to the camera
            aabb1 = aabb1.move(-camera.x, -camera.y, -camera.z);

            VertexConsumer vertexconsumer = CafeRenders.bufferSource.getBuffer(RenderType.lines());
            LevelRenderer.renderLineBox(poseStack, vertexconsumer, aabb1, 0.9F, 0.9F, 0.9F, 1.0f);
            CafeRenders.bufferSource.endBatch();
            poseStack.popPose();
        }
    }
}
