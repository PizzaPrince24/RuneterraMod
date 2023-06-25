package com.pizzaprince.runeterramod.world.biome;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {

	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, RuneterraMod.MOD_ID);
	
	public static final ResourceKey<Biome> SHURIMAN_DESERT = createBiome("shuriman_desert");

	public static final ResourceKey<Biome> SHURIMAN_WASTELAND = createBiome("shuriman_wasteland");
	
	private static ResourceKey<Biome> createBiome(String name) {
		return ResourceKey.create(Registries.BIOME, new ResourceLocation(RuneterraMod.MOD_ID, name));
	}
	
	
	public static RegistryObject<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier) {
        return BIOMES.register(key.location().getPath(), biomeSupplier);
    }

	private static void register(BootstapContext<Biome> context, ResourceKey<Biome> key, Biome biome) {
		context.register(key, biome);
	}


	public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }

	public static void bootstrap(BootstapContext<Biome> context) {
		HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);
		HolderGetter<PlacedFeature> placedFeatureGetter = context.lookup(Registries.PLACED_FEATURE);

		register(context, SHURIMAN_DESERT, ModOverworldBiomes.shurimanDesert(placedFeatureGetter, carverGetter));

		register(context, SHURIMAN_WASTELAND, ModOverworldBiomes.shurimanWasteland(placedFeatureGetter, carverGetter));
	}
}
