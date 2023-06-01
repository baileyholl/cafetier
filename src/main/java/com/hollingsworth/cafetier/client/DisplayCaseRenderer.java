package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.common.block.DisplayEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DisplayCaseRenderer implements BlockEntityRenderer<DisplayEntity> {
    private final EntityRenderDispatcher entityRenderer;

    public DisplayCaseRenderer(BlockEntityRendererProvider.Context pContext) {
        entityRenderer = pContext.getEntityRenderer();
    }

    @Override
    public void render(DisplayEntity tileEntityIn, float pPartialTick, PoseStack matrixStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        if (tileEntityIn.getStack() == null || tileEntityIn.getStack().isEmpty()) return;

        float yOffset = 0.5f;
        float xOffset = 0.5f;
        float zOffset = 0.5f;

        matrixStack.pushPose();

        if (tileEntityIn.getBlockState().hasProperty(BlockStateProperties.FACING)) {
            switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.FACING)) {
                case DOWN -> yOffset = 0.4f;
                case WEST -> xOffset = 0.45f;
                case EAST -> xOffset = 0.55f;
                case SOUTH -> zOffset = 0.55f;
                case NORTH -> zOffset = 0.45f;
                default -> yOffset = 0.6f;
            }
        } else {
            yOffset = 0.635f;
        }
        matrixStack.translate(xOffset, yOffset, zOffset);
        float scale = 0.45f;
        matrixStack.scale(scale, scale, scale);
        matrixStack.mulPose(Vector3f.YP.rotationDegrees((pPartialTick + (float) ClientInfo.ticksInGame) * 3f));
        Minecraft.getInstance().getItemRenderer().renderStatic(tileEntityIn.getStack(),
                ItemTransforms.TransformType.FIXED,
                pPackedLight,
                pPackedOverlay,
                matrixStack,
                pBufferSource,
                (int) tileEntityIn.getBlockPos().asLong());

        matrixStack.popPose();
    }
}
