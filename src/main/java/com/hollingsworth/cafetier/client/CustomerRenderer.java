package com.hollingsworth.cafetier.client;

import com.hollingsworth.cafetier.Cafetier;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
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
    protected void renderNameTag(T entityIn, Component pDisplayName, PoseStack matrixStack, MultiBufferSource buffer, int pPackedLight) {
        super.renderNameTag(entityIn, pDisplayName, matrixStack, buffer, pPackedLight);
//
//        if (entityIn.getCitizenDataView() != null && entityIn.getCitizenDataView().hasVisibleInteractions())
//        {
            double distance = this.entityRenderDispatcher.distanceToSqr(entityIn.getX(), entityIn.getY(), entityIn.getZ());
            if (distance <= 4096.0D)
            {
                double y = entityIn.getBbHeight() + (model.young ? 0.5f : 0.8f);

                matrixStack.pushPose();
                matrixStack.translate(0, y, 0);
                matrixStack.mulPose(entityRenderDispatcher.cameraOrientation());
                //matrixStack.mulPose(Vector3f.ZP.rotationDegrees(90));

                matrixStack.scale(-0.025F, -0.025F, 0.025F);

                final Matrix4f pose = matrixStack.last().pose();

                VertexConsumer r = buffer.getBuffer(RenderTypes.worldEntityIcon(new ResourceLocation(Cafetier.MODID, "textures/icons/blocking.png")));
                r.vertex(pose, -5, 0, 0).uv(0, 0).endVertex();
                r.vertex(pose, -5, 10, 0).uv(0, 1).endVertex();
                r.vertex(pose, 5, 10, 0).uv(1, 1).endVertex();
                r.vertex(pose, 5, 0, 0).uv(1, 0).endVertex();

                matrixStack.popPose();
            }
//        }
    }
}
