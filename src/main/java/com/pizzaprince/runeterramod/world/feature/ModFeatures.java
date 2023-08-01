package com.pizzaprince.runeterramod.world.feature;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.world.feature.custom.ShurimanCactusFeature;
import com.pizzaprince.runeterramod.world.feature.custom.ShurimanDuneFeature;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModFeatures {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, RuneterraMod.MOD_ID);
	
	public static final Feature<NoneFeatureConfiguration> SHURIMAN_DUNE_FEATURE = createFeature("shuriman_dune", new ShurimanDuneFeature(NoneFeatureConfiguration.CODEC));

	public static final Feature<NoneFeatureConfiguration> SHURIMAN_CACTUS_FEATURE = createFeature("shuriman_cactus", new ShurimanCactusFeature(NoneFeatureConfiguration.CODEC));
	public static void register(IEventBus eventBus) {
		FEATURES.register(eventBus);
	}
	
	private static <C extends FeatureConfiguration, F extends Feature<C>> F createFeature(String key, F value)
    {
        FEATURES.register(key, () -> value);
        return value;
    }
}
