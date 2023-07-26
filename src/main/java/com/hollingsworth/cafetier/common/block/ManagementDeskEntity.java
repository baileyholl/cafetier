package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.api.Cafe;
import com.hollingsworth.cafetier.api.CafeSavedData;
import com.hollingsworth.cafetier.common.util.ITickable;
import com.hollingsworth.cafetier.common.util.ITooltipProvider;
import com.hollingsworth.cafetier.common.util.ModdedTile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ManagementDeskEntity extends ModdedTile implements ITickable, ITooltipProvider {
    private UUID uuid = null;

    // Used for client syncing.
    public Cafe cafe = null;

    public ManagementDeskEntity(BlockPos pos, BlockState state) {
        super(CafeBlocks.MANAGEMENT_DESK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if(level.isClientSide){
            return;
        }
        if(uuid != null && (cafe == null || cafe.cafeUUID.equals(uuid))){
            cafe = getCafe();
            updateBlock();
        }
    }

    public void setCafe(UUID uuid){
        if(level.isClientSide){
            return;
        }
        this.uuid = uuid;
        var cafe = CafeSavedData.from((ServerLevel) level).getCafe(uuid);
        if (cafe != null) {
            cafe.deskPos = worldPosition;
        }
        this.cafe = getCafe();
        updateBlock();
    }

    public void setBounds(AABB aabb){
        Cafe cafe = getCafe();
        if(cafe != null){
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

    public void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if(uuid != null) {
            pTag.putUUID("uuid", uuid);
        }
        if(cafe != null){
            pTag.put("cafe", cafe.save(new CompoundTag()));
        }
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        uuid = null;
        cafe = null;
        if(pTag.contains("uuid")){
            uuid = pTag.getUUID("uuid");
        }
        if(pTag.contains("cafe")){
            cafe = new Cafe(pTag.getCompound("cafe"));
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return super.getUpdatePacket();
    }

    public void resyncCafe(){
        if(uuid != null){
            var cafe = getCafe();
            this.cafe = cafe;
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
