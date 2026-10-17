package com.creeper.client;

import com.creeper.ExampleMod;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;

public final class CreeperWingsFeature extends FeatureRenderer<CreeperEntityRenderState, CreeperEntityModel> {
    private final CreeperWingsModel wings = new CreeperWingsModel();

    public CreeperWingsFeature(FeatureRendererContext<CreeperEntityRenderState, CreeperEntityModel> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                       CreeperEntityRenderState state, float limbAngle, float limbDistance) {
        render(wings, ExampleMod.id("textures/entity/creeper_wings.png"), matrices, queue,
                light, state, -1, 0);
    }
}
