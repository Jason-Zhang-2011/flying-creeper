package com.creeper.entity;

import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/** Uses the same landing-position check as bee navigation. */
public final class CreeperFlightNavigation extends BirdNavigation {
    public CreeperFlightNavigation(MobEntity entity, World world) {
        super(entity, world);
        setCanOpenDoors(false);
        setCanSwim(true);
    }

    @Override
    public boolean isValidPosition(BlockPos pos) {
        return !world.getBlockState(pos.down()).isAir();
    }
}
