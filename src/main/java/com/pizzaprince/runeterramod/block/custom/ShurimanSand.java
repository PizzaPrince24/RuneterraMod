package com.pizzaprince.runeterramod.block.custom;

import com.pizzaprince.runeterramod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.fml.common.Mod;

public class ShurimanSand extends FallingBlock {
    public ShurimanSand(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlant(world, pos).is(ModBlocks.SHURIMAN_CACTUS.get()) || plantable.getPlant(world, pos).is(ModBlocks.SHURIMAN_SAND.get());
        //return state.is(ModBlocks.SHURIMAN_CACTUS.get()) || state.is(ModBlocks.SHURIMAN_SAND.get());
    }
}
