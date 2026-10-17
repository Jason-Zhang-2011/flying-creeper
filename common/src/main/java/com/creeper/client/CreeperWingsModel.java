package com.creeper.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.util.math.MathHelper;

public final class CreeperWingsModel extends EntityModel<CreeperEntityRenderState> {
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public CreeperWingsModel() {
        super(createModel());
        leftWing = root.getChild("left_wing");
        rightWing = root.getChild("right_wing");
    }

    private static ModelPart createModel() {
        ModelData data = new ModelData();
        data.getRoot().addChild("left_wing", ModelPartBuilder.create().uv(0, 0)
                .cuboid(0, -2, 0, 10, 12, 1), ModelTransform.origin(2, 8, 2));
        data.getRoot().addChild("right_wing", ModelPartBuilder.create().uv(0, 0).mirrored()
                .cuboid(-10, -2, 0, 10, 12, 1), ModelTransform.origin(-2, 8, 2));
        return TexturedModelData.of(data, 32, 32).createModel();
    }

    @Override
    public void setAngles(CreeperEntityRenderState state) {
        super.setAngles(state);
        float flap = MathHelper.cos(state.age * 1.8F) * 0.65F;
        leftWing.yaw = 0.35F + flap;
        rightWing.yaw = -leftWing.yaw;
        leftWing.roll = -0.65F;
        rightWing.roll = 0.65F;
    }
}
