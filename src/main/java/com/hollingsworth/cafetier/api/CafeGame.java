package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameSetupState;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameTeardownState;
import com.hollingsworth.cafetier.common.block.DisplayEntity;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of a cafe game.
 *
 */
public class CafeGame {
    public Cafe cafe;
    public ManagementDeskEntity desk;
    public int maxWave;

    public SimpleStateMachine gameSm;
    public CustomerManager customerManager = new CustomerManager();
    public ScoreManager scoreManager = new ScoreManager();
    public BlockPos spawnPos;
    public List<ItemStack> menuStacks = new ArrayList<>();

    public CafeGame(Cafe cafe, ManagementDeskEntity desk) {
        this.cafe = cafe;
        this.desk = desk;
        gameSm = new SimpleStateMachine(new GameSetupState(this));
        maxWave = 1;
        spawnPos = getValidSpawnPos((ServerLevel) desk.getLevel());
        menuStacks = getMenuItems((ServerLevel) desk.getLevel());
        System.out.println(menuStacks.size());
    }

    public void tick() {
        gameSm.tick();
        customerManager.tick();
    }

    public ServerLevel getLevel(){
        return (ServerLevel) desk.getLevel();
    }

    public BlockPos getCafePos(){
        return desk.getBlockPos();
    }

    public void onGameEvent(IStateEvent event){
        gameSm.onEvent(event);
        customerManager.onEvent(event);
        scoreManager.onEvent(event);
    }

    public BlockPos getValidSpawnPos(ServerLevel level) {
        List<BlockPos> validPositions = CustomerSpawners.getSpawnersForCafe(level, cafe, 10);
        if(validPositions.isEmpty())
            return null;
        return validPositions.get(level.random.nextInt(validPositions.size()));
    }

    public List<ItemStack> getMenuItems(ServerLevel level){
        AABB pAabb = cafe.getBounds();
        List<ItemStack> menuItems = new ArrayList<>();
        for(BlockPos pos : BlockPos.betweenClosed(Mth.floor(pAabb.minX), Mth.floor(pAabb.minY), Mth.floor(pAabb.minZ), Mth.floor(pAabb.maxX), Mth.floor(pAabb.maxY), Mth.floor(pAabb.maxZ))){
            if(level.getBlockEntity(pos) instanceof DisplayEntity displayEntity){
                if(displayEntity.getStack().isEmpty() || !displayEntity.getStack().isEdible())
                    continue;
                menuItems.add(displayEntity.getStack());
            }
        }
        return menuItems;
    }

    public boolean isDone(){
        return gameSm.getCurrentState() instanceof GameTeardownState;
    }
}
