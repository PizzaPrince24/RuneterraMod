package com.pizzaprince.runeterramod.world.biome;

import java.util.function.Supplier;

import com.pizzaprince.runeterramod.RuneterraMod;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.Biomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {
	
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, RuneterraMod.MOD_ID);
	
	public static final ResourceKey<Biome> SHURIMAN_DESERT = createBiome("shuriman_desert");

	public static final ResourceKey<Biome> SHURIMAN_WASTELAND = createBiome("shuriman_wasteland");
	
	public static void registerBiomes() {
		register(SHURIMAN_DESERT, ModOverworldBiomes::shurimanDesert);
		register(SHURIMAN_WASTELAND, ModOverworldBiomes::shurimanWasteland);
	}
	
	private static ResourceKey<Biome> createBiome(String name) {
		return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(RuneterraMod.MOD_ID, name));
	}
	
	
	public static RegistryObject<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier) {
        return BIOMES.register(key.location().getPath(), biomeSupplier);
    }


	public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }
}
