package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.common.entity.Customer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class CustomerRenderer<T extends Customer, M extends EntityModel<T>> extends MobRenderer<T, M> {
    public CustomerRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Override
    protected boolean shouldShowName(T pEntity) {
        return true;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack matrixStack, MultiBufferSource buffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, matrixStack, buffer, pPackedLight);
//        double y = entityIn.getBbHeight() + (model.young ? 0.5f : 0.8f);
//
//        if(!entityIn.getDesiredItem().isEmpty() && entityIn.showDesiredItem()){
//            matrixStack.pushPose();
//            matrixStack.translate(0, y + 0.6, 0);
//            matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());
//            matrixStack.scale(0.6f, 0.6f, 0.6f);
//            Minecraft.getInstance().getItemRenderer().renderStatic(entityIn.getDesiredItem(), ItemTransforms.TransformType.FIXED,
//                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, matrixStack, buffer, 0);
//            matrixStack.popPose();
//        }
    }

    @Override
    protected void renderNameTag(T entityIn, Component pDisplayName, PoseStack matrixStack, MultiBufferSource buffer, int pPackedLight) {
        super.renderNameTag(entityIn, pDisplayName, matrixStack, buffer, pPackedLight);

        double distance = this.entityRenderDispatcher.distanceToSqr(entityIn.getX(), entityIn.getY(), entityIn.getZ());
        if(distance > 4096.0D)
            return;
        double y = entityIn.getBbHeight() + (model.young ? 0.5f : 0.8f);

        if(!entityIn.getDesiredItem().isEmpty() && entityIn.showDesiredItem()){
            matrixStack.pushPose();
            matrixStack.translate(0, y + 0.6, 0);
            matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());
            matrixStack.scale(0.55f, 0.55f, 0.55f);
            Minecraft.getInstance().getItemRenderer().renderStatic(entityIn.getDesiredItem(), ItemTransforms.TransformType.FIXED,
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, matrixStack, buffer, 0);
            matrixStack.popPose();
        }
        matrixStack.pushPose();
        matrixStack.translate(0, y, 0);
        matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());

        matrixStack.scale(-0.025F, -0.025F, 0.025F);

        final Matrix4f happinessPose = matrixStack.last().pose();

        VertexConsumer happinessVert = buffer.getBuffer(RenderTypes.worldEntityIcon(new ResourceLocation(entityIn.getHappinessIcon())));
        happinessVert.vertex(happinessPose,-9 , -12, 0).uv(0, 0).endVertex();
        happinessVert.vertex(happinessPose, -9, 12, 0).uv(0, 1).endVertex();
        happinessVert.vertex(happinessPose, 9,12, 0).uv(1, 1).endVertex();
        happinessVert.vertex(happinessPose, 9, -12, 0).uv(1, 0).endVertex();

        matrixStack.popPose();

        if(!entityIn.getDisplayIcon().isEmpty()) {
            matrixStack.pushPose();

            float scale = 0.03f;
            matrixStack.translate(0, y + 0.6, 0);
            matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());
            matrixStack.scale(-scale, -scale, -scale);
            final Matrix4f pose = matrixStack.last().pose();

            VertexConsumer r = buffer.getBuffer(RenderTypes.worldEntityIcon(new ResourceLocation(entityIn.getDisplayIcon())));
            r.vertex(pose, -8, -8, 0).uv(0, 0).endVertex();
            r.vertex(pose, -8, 8, 0).uv(0, 1).endVertex();
            r.vertex(pose, 8, 8, 0).uv(1, 1).endVertex();
            r.vertex(pose, 8, -8, 0).uv(1, 0).endVertex();

            matrixStack.popPose();
        }
    }

}
