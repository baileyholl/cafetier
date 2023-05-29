package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameSetupState;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameTeardownState;
import com.hollingsworth.cafetier.block.ManagementDeskEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;
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

    public CafeGame(Cafe cafe, ManagementDeskEntity desk) {
        this.cafe = cafe;
        this.desk = desk;
        gameSm = new SimpleStateMachine(new GameSetupState(this));
        maxWave = 2;
        spawnPos = getValidSpawnPos((ServerLevel) desk.getLevel(), desk.getBlockPos());
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

    public BlockPos getValidSpawnPos(ServerLevel level, BlockPos center) {
        for(int i = 0; i < 10; ++i) {
            BlockPos blockpos = this.findRandomSpawnPos(level, center, 1, 1);
            if (blockpos != null) {
                return blockpos;
            }
        }

        return null;
    }

    @Nullable
    public BlockPos findRandomSpawnPos(ServerLevel level, BlockPos center, int pOffsetMultiplier, int pMaxTry) {
        List<BlockPos> validPositions = CustomerSpawners.getSpawnersForCafe(level, cafe, 10);
        if(validPositions.isEmpty())
            return null;
        return validPositions.get(level.random.nextInt(validPositions.size()));
    }

    public boolean isValidCustomerSpawn(ServerLevel level, BlockPos pos){
        return true;
//        if(!level.getBlockState())
//        return level.getWorldBorder().isWithinBounds(pos) && ;
    }

    public boolean isDone(){
        return gameSm.getCurrentState() instanceof GameTeardownState;
    }
}
