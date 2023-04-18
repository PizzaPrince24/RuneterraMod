package com.pizzaprince.runeterramod.world.feature;

import java.util.List;

import com.pizzaprince.runeterramod.RuneterraMod;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacedFeatures {
	
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, RuneterraMod.MOD_ID);
	
	public static final Holder<PlacedFeature> SHURIMAN_DUNE = createPlacedFeature("shuriman_dune", ModConfiguredFeatures.SHURIMAN_DUNE, CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
	
	public static void register(IEventBus eventBus) {
		PLACED_FEATURES.register(eventBus);
	}

	private static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String name, Holder<ConfiguredFeature<FC, ?>> configuredFeature, PlacementModifier... modifiers) {
		return PLACED_FEATURES.register(name, () -> new PlacedFeature(Holder.hackyErase(configuredFeature), List.of(modifiers))).getHolder().get();
	}

}