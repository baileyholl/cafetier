package com.hollingsworth.cafetier.common.block;

import com.hollingsworth.cafetier.api.DeedData;
import com.hollingsworth.cafetier.common.item.DeedItem;
import com.hollingsworth.cafetier.common.util.ITickableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
            if(!heldStack.isEmpty() && heldStack.getItem() instanceof DeedItem) {
                var deedData = new DeedData(heldStack);
                if (deedData.getUuid() != null) {
                    tile.setCafe(deedData.getUuid());
                }
                return InteractionResult.SUCCESS;
            }else if(tile.getCafe() != null){
                tile.getCafe().startGame(tile, pPlayer);
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
