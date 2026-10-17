package com.creeper.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import com.creeper.entity.CreeperFlightNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import net.minecraft.storage.ReadView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity implements Flutterer {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void flyingCreeper$flightControl(CallbackInfo ci) {
        moveControl = new FlightMoveControl(this, 20, true);
        setNoGravity(true);
    }

    // Old saves may contain NoGravity=false; wings must remain active after loading.
    @Inject(method = "readCustomData", at = @At("RETURN"))
    private void flyingCreeper$restoreFlight(ReadView view, CallbackInfo ci) {
        setNoGravity(true);
    }

    @Override
    public boolean isInAir() {
        return !isOnGround();
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new CreeperFlightNavigation(this, world);
    }

    @Inject(method = "createCreeperAttributes", at = @At("RETURN"))
    private static void flyingCreeper$flightSpeed(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(EntityAttributes.FLYING_SPEED, 0.35);
    }

    @Inject(method = "initGoals", at = @At("RETURN"))
    private void flyingCreeper$flyingWander(CallbackInfo ci) {
        goalSelector.clear(goal -> goal instanceof WanderAroundFarGoal);
        goalSelector.add(5, new FlyGoal(this, 1.0));
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    private void flyingCreeper$ignoreFalls(double distance, float multiplier, DamageSource source,
                                          CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target =
            "Lnet/minecraft/server/world/ServerWorld;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)V"), index = 4)
    private float flyingCreeper$airborneExplosion(float power) {
        return isOnGround() ? power : power * 0.5F;
    }
}
