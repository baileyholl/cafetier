package com.example.examplemod.block;

import com.example.examplemod.api.Cafe;
import com.example.examplemod.api.CafeData;
import com.example.examplemod.util.ITickable;
import com.example.examplemod.util.ITooltipProvider;
import com.example.examplemod.util.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
        if(cafe.blockPos != null && cafe.blockPos.equals(worldPosition)){
            cafe.tick(this);
        }else{
            uuid = null;
            updateBlock();
        }
    }

    public void setCafe(UUID uuid){
        this.uuid = uuid;
        var cafe = CafeData.from((ServerLevel) level).getCafe(uuid);
        if (cafe != null) {
            cafe.blockPos = worldPosition;
        }
        updateBlock();
    }

    public Cafe getCafe(){
        if(uuid != null && level instanceof ServerLevel serverLevel){
            return CafeData.from(serverLevel).getCafe(uuid);
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
        tooltip.add(Component.literal("UUID: $uuid"));
    }
}
