package com.hollingsworth.cafetier.common.entity.goal;

import com.mojang.math.Vector3d;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.function.Supplier;

public class LookAtPositionGoal extends Goal {
    public Mob mob;
    public Supplier<Vector3d> positionSupplier;

    public LookAtPositionGoal(Mob mob, Supplier<Vector3d> positionSupplier) {
        this.mob = mob;
        this.positionSupplier = positionSupplier;
    }

    @Override
    public void tick() {
        Vector3d lookAt = positionSupplier.get();
        if(lookAt == null){
            return;
        }
        this.mob.getLookControl().setLookAt(lookAt.x, lookAt.y, lookAt.z);
    }

    @Override
    public boolean canUse() {
        return positionSupplier.get() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return positionSupplier.get() != null;
    }
}
