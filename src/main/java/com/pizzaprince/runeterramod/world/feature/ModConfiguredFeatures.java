package com.pizzaprince.runeterramod.world.feature;

import java.util.function.Supplier;

import com.pizzaprince.runeterramod.RuneterraMod;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModConfiguredFeatures {
	
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, RuneterraMod.MOD_ID);
	
	public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> SHURIMAN_DUNE = createConfiguredFeature("shuriman_dune", ModFeatures.SHURIMAN_DUNE_FEATURE, () -> NoneFeatureConfiguration.INSTANCE);
	
	public static void register(IEventBus eventBus) {
		CONFIGURED_FEATURES.register(eventBus);
	}
	
	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> createConfiguredFeature(String key, F feature, Supplier<FC> configurationSupplier)
    {
        return CONFIGURED_FEATURES.<ConfiguredFeature<FC, ?>>register(key, () -> new ConfiguredFeature<>(feature, configurationSupplier.get())).getHolder().get();
    }

}
