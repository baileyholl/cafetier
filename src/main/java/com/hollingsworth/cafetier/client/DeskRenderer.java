package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class DeskRenderer implements BlockEntityRenderer<ManagementDeskEntity> {

    public DeskRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    public void render(ManagementDeskEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.cafe == null){
            return;
        }
        AABB renderBB = pBlockEntity.cafe.getBounds();
        if(renderBB == null)
            return;
        BlockPos deskPos = pBlockEntity.getBlockPos();
        renderBB = renderBB.move(deskPos.multiply(-1));
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.lines());
        LevelRenderer.renderLineBox(pPoseStack, vertexconsumer, renderBB, 0.9F, 0.9F, 0.9F, 1.0f);

    }

    public boolean shouldRenderOffScreen(ManagementDeskEntity pBlockEntity) {
        return true;
    }

    public int getViewDistance() {
        return 96;
    }
}
