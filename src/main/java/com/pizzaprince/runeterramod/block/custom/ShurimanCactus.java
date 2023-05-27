package com.pizzaprince.runeterramod.block.custom;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.world.plant.ModPlantTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.Nullable;

public class ShurimanCactus extends HorizontalDirectionalBlock implements IPlantable {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty POD_STATE = IntegerProperty.create("pod_state", 0, 2);

    protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
    protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
    public ShurimanCactus(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide() && pPlayer.getItemInHand(pHand).getItem() instanceof SwordItem && pState.getValue(POD_STATE) > 0){
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(ModItems.CACTUS_JUICE.get())));
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(ModItems.CACTUS_JUICE.get())));
            pLevel.setBlockAndUpdate(pPos, pState.setValue(POD_STATE, pState.getValue(POD_STATE) - 1));
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(POD_STATE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(POD_STATE, 0);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return OUTLINE_SHAPE;
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = pLevel.getBlockState(pPos.relative(direction));
            Material material = blockstate.getMaterial();
            if (material.isSolid() || pLevel.getFluidState(pPos.relative(direction)).is(FluidTags.LAVA)) {
                return false;
            }
        }

        BlockState blockstate1 = pLevel.getBlockState(pPos.below());
        return blockstate1.canSustainPlant(pLevel, pPos, Direction.UP, this) && !pLevel.getBlockState(pPos.above()).getMaterial().isLiquid();
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return ModPlantTypes.SHURIMAN;
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        pEntity.hurt(DamageSource.CACTUS, 1.0F);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int numPods = pState.getValue(POD_STATE);
        if(numPods != 2){
            pLevel.setBlockAndUpdate(pPos, pState.setValue(POD_STATE, numPods+1));
        }
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.canSurvive(pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.scheduleTick(pCurrentPos, this, 1);
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return state.is(ModBlocks.SHURIMAN_CACTUS.get()) || state.is(ModBlocks.SHURIMAN_SAND.get());
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return defaultBlockState();
    }
}
