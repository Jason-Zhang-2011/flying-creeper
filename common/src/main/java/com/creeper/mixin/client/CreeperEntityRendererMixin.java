package com.creeper.mixin.client;

import com.creeper.client.CreeperWingsFeature;
import net.minecraft.client.render.entity.CreeperEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntityRenderer.class)
public abstract class CreeperEntityRendererMixin extends MobEntityRenderer<CreeperEntity, CreeperEntityRenderState, CreeperEntityModel> {
    protected CreeperEntityRendererMixin(EntityRendererFactory.Context context, CreeperEntityModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void flyingCreeper$addWings(EntityRendererFactory.Context context, CallbackInfo ci) {
        addFeature(new CreeperWingsFeature(this));
    }
}
