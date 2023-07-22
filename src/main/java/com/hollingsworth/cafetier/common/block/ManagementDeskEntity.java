package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeSavedData;
import com.hollingsworth.cafetier.common.util.ITickable;
import com.hollingsworth.cafetier.common.util.ITooltipProvider;
import com.hollingsworth.cafetier.common.util.ModdedTile;
import com.hollingsworth.cafetier.common.util.SerializeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.UUID;

public class ManagementDeskEntity extends ModdedTile implements ITickable, ITooltipProvider {
    private UUID uuid = null;
    public AABB renderBB = null;
    public ManagementDeskEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.MANAGEMENT_DESK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {

    }

    public void setCafe(UUID uuid){
        this.uuid = uuid;
        var cafe = CafeSavedData.from((ServerLevel) level).getCafe(uuid);
        if (cafe != null) {
            cafe.deskPos = worldPosition;
        }
        updateBlock();
    }

    public void setBounds(AABB aabb){
        this.renderBB = null;
        Cafe cafe = getCafe();
        if(cafe != null){
            this.renderBB = aabb;
            cafe.setBounds(aabb);
            resyncCafe();
        }
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
        if(renderBB != null){
            pTag.put("area", SerializeUtil.aabbToTag(renderBB));
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        uuid = null;
        renderBB = null;
        if(pTag.contains("uuid")){
            uuid = pTag.getUUID("uuid");
        }
        if(pTag.contains("area")){
            CompoundTag areaTag = pTag.getCompound("area");
            renderBB = SerializeUtil.aabbFromTag(areaTag);
        }
    }

    public void resyncCafe(){
        if(uuid != null){
            var cafe = getCafe();
            if(cafe != null){
                renderBB = cafe.getBounds();
            }
        }
        updateBlock();
    }

    public void getTooltip(List<Component> tooltip) {
        tooltip.add(Component.literal("UUID: " + uuid));
    }

    @Override
    public AABB getRenderBoundingBox() {
        return super.getRenderBoundingBox().inflate(5);
    }
}
