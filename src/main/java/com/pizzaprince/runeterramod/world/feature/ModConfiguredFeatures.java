package com.pizzaprince.runeterramod.world.feature;

import java.util.function.Supplier;

import com.pizzaprince.runeterramod.RuneterraMod;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> SHURIMAN_DUNE_KEY = registerKey("shuriman_dune");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SHURIMAN_CACTUS_KEY = registerKey("shuriman_cactus");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

		register(context, SHURIMAN_DUNE_KEY, ModFeatures.SHURIMAN_DUNE_FEATURE, NoneFeatureConfiguration.INSTANCE);

		register(context, SHURIMAN_CACTUS_KEY, ModFeatures.SHURIMAN_CACTUS_FEATURE, NoneFeatureConfiguration.INSTANCE);
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(RuneterraMod.MOD_ID, name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
																						  ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}
