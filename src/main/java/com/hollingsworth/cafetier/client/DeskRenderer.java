package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.block.ManagementDeskEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;

public class DeskRenderer implements BlockEntityRenderer<ManagementDeskEntity> {

    public DeskRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    public void render(ManagementDeskEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        BlockPos blockpos = new BlockPos(0,0,0);
        Vec3i vec3i = new Vec3i(5, 5, 5);
        if (vec3i.getX() >= 1 && vec3i.getY() >= 1 && vec3i.getZ() >= 1) {
            double d0 = (double)blockpos.getX();
            double d1 = (double)blockpos.getZ();
            double d5 = (double)blockpos.getY();
            double d8 = d5 + (double)vec3i.getY();
            double d2;
            double d3;
            d2 = (double)vec3i.getX();
            d3 = (double)vec3i.getZ();

            double d4;
            double d6;
            double d7;
            double d9;

            d4 = d2 < 0.0D ? d0 + 1.0D : d0;
            d6 = d3 < 0.0D ? d1 + 1.0D : d1;
            d7 = d4 + d2;
            d9 = d6 + d3;


            float f = 1.0F;
            float f1 = 0.9F;
            float f2 = 0.5F;
            VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.lines());
            if (true) {
                LevelRenderer.renderLineBox(pPoseStack, vertexconsumer, new AABB(new BlockPos(0,0,0)).inflate(5), 0.9F, 0.9F, 0.9F, 1.0f);
            }
        }

    }

    public boolean shouldRenderOffScreen(ManagementDeskEntity pBlockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 96;
    }
}
