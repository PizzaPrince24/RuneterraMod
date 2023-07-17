package com.pizzaprince.runeterramod.block.custom;

import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.block.entity.custom.AbstractItemTransuferEntity;
import com.pizzaprince.runeterramod.block.entity.custom.SunDiskAltarEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractItemTransfuser extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public AbstractItemTransfuser(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof AbstractItemTransuferEntity) {
                ((AbstractItemTransuferEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()){
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof AbstractItemTransuferEntity transfuser){
                transfuser.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
                    if(pPlayer.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
                        for(int slot = itemHandler.getSlots()-1; slot >= 0; slot--){
                            if(!itemHandler.getStackInSlot(slot).isEmpty()){
                                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, itemHandler.getStackInSlot(slot));
                                itemHandler.extractItem(slot, 1, false);
                                break;
                            }
                        }

                    } else {
                        for(int slot = 0; slot < itemHandler.getSlots()-1; slot++){
                            if(itemHandler.getStackInSlot(slot).isEmpty()){
                                itemHandler.insertItem(slot, pPlayer.getItemInHand(InteractionHand.MAIN_HAND).copyWithCount(1), false);
                                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, pPlayer.getItemInHand(InteractionHand.MAIN_HAND)
                                        .copyWithCount(pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getCount()-1));
                                break;
                            }
                        }
                    }
                });
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}
