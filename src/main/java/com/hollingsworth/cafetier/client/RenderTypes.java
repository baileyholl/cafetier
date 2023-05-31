package com.hollingsworth.cafetier.client;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

public class RenderTypes
{
    /**
     * Usable for rendering simple flat textures
     *
     * @param  resLoc texture location
     * @return        render type
     */
    public static RenderType worldEntityIcon(final ResourceLocation resLoc)
    {
        return InnerRenderTypes.WORLD_ENTITY_ICON.apply(resLoc);
    }

    public static final class InnerRenderTypes extends RenderType
    {
        private static final DepthTestStateShard ALWAYS_DEPTH_TEST = new AlwaysDepthTestStateShard();

        private InnerRenderTypes(final String nameIn,
                                 final VertexFormat formatIn,
                                 final VertexFormat.Mode drawModeIn,
                                 final int bufferSizeIn,
                                 final boolean useDelegateIn,
                                 final boolean needsSortingIn,
                                 final Runnable setupTaskIn,
                                 final Runnable clearTaskIn)
        {
            super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
            throw new IllegalStateException();
        }

        private static final Function<ResourceLocation, RenderType> WORLD_ENTITY_ICON = Util.memoize((p_173202_) -> {
            return create("cafetier_entity_icon",
                    DefaultVertexFormat.POSITION_TEX,
                    VertexFormat.Mode.QUADS,
                    1024,
                    false,
                    true,
                    RenderType.CompositeState.builder()
                            .setShaderState(POSITION_TEX_SHADER)
                            .setTextureState(new RenderStateShard.TextureStateShard(p_173202_, false, false))
                            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                            .setDepthTestState(ALWAYS_DEPTH_TEST)
                            .createCompositeState(false));
        });
    }

    private static class AlwaysDepthTestStateShard extends RenderStateShard.DepthTestStateShard
    {
        private AlwaysDepthTestStateShard()
        {
            super("true_always", -1);
            setupState = () -> {
                RenderSystem.enableDepthTest();
                RenderSystem.depthFunc(GL11.GL_ALWAYS);
            };
        }
    }
}