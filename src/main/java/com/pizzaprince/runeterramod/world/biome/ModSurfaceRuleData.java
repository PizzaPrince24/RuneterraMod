package com.pizzaprince.runeterramod.world.biome;

import com.pizzaprince.runeterramod.block.ModBlocks;

import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class ModSurfaceRuleData {

	private static final SurfaceRules.RuleSource SHURIMAN_SAND = makeStateRule(ModBlocks.SHURIMAN_SAND.get());
	private static final SurfaceRules.RuleSource SHURIMAN_SANDSTONE = makeStateRule(ModBlocks.SHURIMAN_SANDSTONE.get());

	private static final SurfaceRules.ConditionSource SHURIMAN_DESERT = makeConditionSourceFromBiome(ModBiomes.SHURIMAN_DESERT);

	private static final SurfaceRules.ConditionSource SHURIMAN_WASTELAND = makeConditionSourceFromBiome(ModBiomes.SHURIMAN_WASTELAND);
	
	public static SurfaceRules.RuleSource makeRules(){
		SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

		return SurfaceRules.sequence(
				SurfaceRules.ifTrue(SHURIMAN_DESERT,
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SHURIMAN_SAND)
										)
								),
								SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SHURIMAN_SANDSTONE)
						)
				),
				SurfaceRules.ifTrue(SHURIMAN_WASTELAND,
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
										SurfaceRules.sequence(
												SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SHURIMAN_SAND)
										)
								),
								SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SHURIMAN_SANDSTONE)
						)
				)
		);
	}
	
	private static SurfaceRules.RuleSource makeStateRule(Block block){
        return SurfaceRules.state(block.defaultBlockState());
    }

	private static SurfaceRules.ConditionSource makeConditionSourceFromBiome(ResourceKey<Biome> biome){
		return SurfaceRules.isBiome(biome);
	}
}
