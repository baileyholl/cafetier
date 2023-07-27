package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class CafeManager {

    public static boolean postCafeEvent(IStateEvent stateEvent, ServerLevel level, BlockPos pos){
        Cafe cafe = getCafeByPos(level, pos);
        if(cafe != null){
            cafe.postEvent(stateEvent);
            return true;
        }
        return false;
    }

    public static Cafe getCafeByPos(ServerLevel level, BlockPos pos){
        for(var cafe : CafeSavedData.from(level).getCafes()){
            if(level.isLoaded(pos) && level.getBlockEntity(cafe.deskPos) instanceof ManagementDeskEntity desk
                    && cafe.getBounds() != null && cafe.getBounds().contains(pos.getX(), pos.getY(), pos.getZ())
                    && desk.cafe != null && desk.cafe.cafeUUID.equals(cafe.cafeUUID)){
                return cafe;
            }
        }
        return null;
    }

}
