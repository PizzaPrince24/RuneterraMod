package com.pizzaprince.runeterramod.world.biome;

import com.pizzaprince.runeterramod.block.ModBlocks;

import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRuleData {

	private static final SurfaceRules.RuleSource SHURIMAN_SAND = makeStateRule(ModBlocks.SHURIMA_SAND.get());;
	
	public static SurfaceRules.RuleSource makeRules(){
		SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        
        return SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SHURIMAN_SAND)), 
        		SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SHURIMAN_SAND));
	}
	
	private static SurfaceRules.RuleSource makeStateRule(Block block){
        return SurfaceRules.state(block.defaultBlockState());
    }
}
