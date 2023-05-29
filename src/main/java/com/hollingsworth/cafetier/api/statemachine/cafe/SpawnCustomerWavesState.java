package com.hollingsworth.cafetier.api.statemachine.cafe;

import com.hollingsworth.cafetier.api.CafeGame;
import com.hollingsworth.cafetier.api.game_events.CustomerSpawnedEvent;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.entity.Customer;
import com.hollingsworth.cafetier.entity.VillagerCustomer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.jetbrains.annotations.Nullable;

public class SpawnCustomerWavesState implements IState {

    public CafeGame cafeGame;
    public int numSpawned;
    public int ticksToNextSpawn;
    public int maxSpawns;

    public SpawnCustomerWavesState(CafeGame cafeGame) {
        this.cafeGame = cafeGame;
        maxSpawns = 1;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd() {

    }

    @Nullable
    @Override
    public IState tick() {
        if(numSpawned >= maxSpawns){
            cafeGame.scoreManager.wavesCompleted++;
            if(cafeGame.scoreManager.wavesCompleted >= cafeGame.maxWave){
                return new GameTeardownState();
            }else{
                return new TakeBreakState(cafeGame);
            }
        }
        if(ticksToNextSpawn <= 0){
            spawnCustomer();
        }else{
            ticksToNextSpawn--;
        }
        return null;
    }

    public void spawnCustomer(){
        ticksToNextSpawn = getNextTickSpawn();
        numSpawned++;
        BlockPos spawnPos = cafeGame.findRandomSpawnPos(cafeGame.getLevel(), cafeGame.getCafePos(), 1, 1);
        if(spawnPos == null){
            System.out.println("no spawn found");
            return;
        }
        Customer customer = new VillagerCustomer(cafeGame.desk.getLevel(), spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, cafeGame.cafe);
        cafeGame.desk.getLevel().addFreshEntity(customer);
        customer.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 30));
        // print distance between desk and customer
        System.out.println("distance: " + Math.sqrt(cafeGame.getCafePos().distSqr(spawnPos)));
        System.out.println(customer.getOnPos());
        cafeGame.onGameEvent(new CustomerSpawnedEvent(customer));
    }

    public int getNextTickSpawn(){
        return (int) (Math.random() * 100) + 50;
    }

    @Nullable
    @Override
    public IState onEvent(IStateEvent event) {
        return null;
    }
}
