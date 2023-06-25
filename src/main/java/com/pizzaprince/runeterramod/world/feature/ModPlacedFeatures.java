package com.pizzaprince.runeterramod.world.feature;

import java.util.List;

import com.pizzaprince.runeterramod.RuneterraMod;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacedFeatures {

	public static final ResourceKey<PlacedFeature> SHURIMAN_DUNE_PLACED_KEY = createKey("shuriman_dune_placed");

	public static final ResourceKey<PlacedFeature> SHURIMAN_CACTUS_PLACED_KEY = createKey("shuriman_cactus_placed");

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, SHURIMAN_DUNE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SHURIMAN_DUNE_KEY),
				CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, RarityFilter.onAverageOnceEvery(1));

		register(context, SHURIMAN_CACTUS_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.SHURIMAN_CACTUS_KEY),
				CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), RarityFilter.onAverageOnceEvery(5));
	}
	private static ResourceKey<PlacedFeature> createKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(RuneterraMod.MOD_ID, name));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
								 List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
								 PlacementModifier... modifiers) {
		register(context, key, configuration, List.of(modifiers));
	}

}
