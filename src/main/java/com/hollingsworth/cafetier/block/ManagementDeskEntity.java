package com.hollingsworth.cafetier.block;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeSavedData;
import com.hollingsworth.cafetier.util.ITickable;
import com.hollingsworth.cafetier.util.ITooltipProvider;
import com.hollingsworth.cafetier.util.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.UUID;

public class ManagementDeskEntity extends ModdedTile implements ITickable, ITooltipProvider {
    private UUID uuid;
    public ManagementDeskEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.MANAGEMENT_DESK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if(level.isClientSide()) {
            return;
        }
        var cafe = getCafe();
        if(cafe != null && cafe.blockPos != null && cafe.blockPos.equals(worldPosition)){
            cafe.tick(this);
        }else{
            uuid = null;
            updateBlock();
        }
    }

    public void setCafe(UUID uuid){
        this.uuid = uuid;
        var cafe = CafeSavedData.from((ServerLevel) level).getCafe(uuid);
        if (cafe != null) {
            cafe.blockPos = worldPosition;
        }
        updateBlock();
    }

    public Cafe getCafe(){
        if(uuid != null && level instanceof ServerLevel serverLevel){
            return CafeSavedData.from(serverLevel).getCafe(uuid);
        }
        return null;
    }

    public void  saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if(uuid != null){
            pTag.putUUID("uuid", uuid);
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("uuid")){
            uuid = pTag.getUUID("uuid");
        }
    }

    public void getTooltip(List<Component> tooltip) {
        tooltip.add(Component.literal("UUID: " + uuid));
    }
}
