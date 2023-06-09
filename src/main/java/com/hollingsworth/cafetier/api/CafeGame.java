package com.hollingsworth.cafetier.api;

import com.hollingsworth.cafetier.api.game_events.CustomerSpawnedEvent;
import com.hollingsworth.cafetier.api.game_events.GameInvalidatedEvent;
import com.hollingsworth.cafetier.api.item.ItemUtils;
import com.hollingsworth.cafetier.api.statemachine.IState;
import com.hollingsworth.cafetier.api.statemachine.IStateEvent;
import com.hollingsworth.cafetier.api.statemachine.SimpleStateMachine;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameSetupState;
import com.hollingsworth.cafetier.api.statemachine.cafe.GameTeardownState;
import com.hollingsworth.cafetier.client.CafeClientData;
import com.hollingsworth.cafetier.common.block.DisplayEntity;
import com.hollingsworth.cafetier.common.block.ManagementDeskEntity;
import com.hollingsworth.cafetier.common.block.SeatBlock;
import com.hollingsworth.cafetier.common.entity.Customer;
import com.hollingsworth.cafetier.common.network.Networking;
import com.hollingsworth.cafetier.common.network.SyncGameClient;
import com.hollingsworth.cafetier.data.BlockTagProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An instance of a cafe game.
 *
 */
public class CafeGame {
    public Cafe cafe;
    public ManagementDeskEntity desk;
    public int maxWave;
    public final UUID uuid;

    public SimpleStateMachine<IState<?>, IStateEvent> gameSm;
    public CustomerManager customerManager = new CustomerManager();
    public ScoreManager scoreManager = new ScoreManager();
    public List<ItemStack> menuStacks = new ArrayList<>();
    public List<BlockPos> waitingPositions = new ArrayList<>();
    public List<BlockPos> spawnPositions = new ArrayList<>();
    public int numSeats = 0;


    protected CafeGame(Cafe cafe, ManagementDeskEntity desk) {
        this.cafe = cafe;
        this.desk = desk;
        maxWave = 1;
        spawnPositions = CustomerSpawners.getSpawnersForCafe(desk.getLevel(), cafe, 10);
        gameSm = new SimpleStateMachine<IState<?>, IStateEvent>(new GameSetupState(this));
        uuid = UUID.randomUUID();
        initBoundary((ServerLevel) desk.getLevel());
    }

    public static CafeGame buildGame(Cafe cafe, ManagementDeskEntity desk){
        CafeGame game = new CafeGame(cafe, desk);
        if(game.spawnPositions.isEmpty() || game.menuStacks.isEmpty() || !game.isValid()){
            return null;
        }
        return game;
    }

    protected void initBoundary(ServerLevel serverLevel){
        AABB pAabb = cafe.getBounds();
        for(BlockPos pos : BlockPos.betweenClosed(Mth.floor(pAabb.minX), Mth.floor(pAabb.minY), Mth.floor(pAabb.minZ), Mth.floor(pAabb.maxX), Mth.floor(pAabb.maxY), Mth.floor(pAabb.maxZ))){
            BlockEntity be = serverLevel.getBlockEntity(pos);
            if(be instanceof DisplayEntity displayEntity){
                if(displayEntity.getStack().isEmpty() || !displayEntity.getStack().isEdible())
                    continue;
                menuStacks.add(displayEntity.getStack().copy());
            }
            if(serverLevel.getBlockState(pos).is(BlockTagProvider.WAITING_BLOCK)){
                waitingPositions.add(pos.immutable());
            }

            if(serverLevel.getBlockState(pos).getBlock() instanceof SeatBlock){
                numSeats++;
            }
        }
    }

    public void tick() {
        gameSm.tick();
        customerManager.tick();
        sendPacketToClients();
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

    public void spawnCustomer(Customer customer, BlockPos pos){
        customer.setPos(pos.getX(), pos.getY(), pos.getZ());
        customer.spawnPos = pos.immutable();
        getLevel().addFreshEntity(customer);
        onGameEvent(new CustomerSpawnedEvent(customer));
    }

    public boolean isDone(){
        return gameSm.getCurrentState() instanceof GameTeardownState;
    }

    public boolean isValid(){
        return cafe.deskPos != null && cafe.getBounds() != null && getLevel().getBlockEntity(cafe.deskPos) instanceof ManagementDeskEntity;
    }

    public void doTeardown(){
        ServerLevel level = (ServerLevel) desk.getLevel();
        for(Player player : level.players()){
            ItemUtils.removeFreshnessForPlayer(player, uuid);
        }
        onGameEvent(new GameInvalidatedEvent());
    }

    public void sendPacketToClients(){
        CafeClientData cafeClientData = new CafeClientData(scoreManager.getScore(), customerManager.trackedCustomers.size(), 100);
        ServerLevel level = (ServerLevel) desk.getLevel();
        for(ServerPlayer serverPlayer : level.getPlayers(p -> cafe.getBounds().contains(p.position()))){
            Networking.sendToPlayerClient(new SyncGameClient(cafeClientData), serverPlayer);
        }
    }
}
