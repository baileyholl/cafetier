package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.common.item.Schematic;
import com.hollingsworth.cafetier.common.network.Networking;
import com.hollingsworth.cafetier.common.network.OpenCreateScreen;
import com.hollingsworth.cafetier.common.network.OpenViewScreen;
import com.hollingsworth.cafetier.common.util.ITickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ManagementDesk extends Block implements ITickableBlock {

    public ManagementDesk(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide){
            return InteractionResult.SUCCESS;
        }

        var heldStack = pPlayer.getItemInHand(pHand);
        var tile = (ManagementDeskEntity) pLevel.getBlockEntity(pPos);
        if(tile != null){
            if(heldStack.getItem() instanceof Schematic schematic){
                if(tile.getCafe() != null){
                    AABB aabb = schematic.getAABB(heldStack);
                    if(aabb != null){
                        // Expand by 1 block in each direction to include the walls
                        tile.setBounds(aabb.expandTowards(1,1,1));
                        pPlayer.sendSystemMessage(Component.translatable("cafetier.set_boundary"));
                        heldStack.shrink(1);
                    }
                }
            }else {
                if(tile.getCafe() == null) {
                    Networking.sendToPlayerClient(new OpenCreateScreen(pPos), (ServerPlayer) pPlayer);
                }else{
                    Networking.sendToPlayerClient(new OpenViewScreen(tile), (ServerPlayer) pPlayer);
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new ManagementDeskEntity(p_153215_, p_153216_);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return super.getRenderShape(pState);
    }
}
