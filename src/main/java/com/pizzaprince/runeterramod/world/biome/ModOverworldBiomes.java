package com.pizzaprince.runeterramod.world.biome;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.world.feature.ModConfiguredFeatures;
import com.pizzaprince.runeterramod.world.feature.ModPlacedFeatures;

import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.synth.NoiseUtils;

public class ModOverworldBiomes {
	
	@Nullable
    private static final Music NORMAL_MUSIC = null;

	protected static int calculateSkyColor(float color)
    {
        float $$1 = color / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }
	
	private static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music)
    {
        return biome(precipitation, temperature, downfall, 4159204, 329011, spawnBuilder, biomeBuilder, music);
    }

    private static Biome biome(Biome.Precipitation precipitation, float temperature, float downfall, int waterColor, int waterFogColor, MobSpawnSettings.Builder spawnBuilder, BiomeGenerationSettings.Builder biomeBuilder, @Nullable Music music)
    {
        return (new Biome.BiomeBuilder()).precipitation(precipitation).temperature(temperature).downfall(downfall).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(12638463).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(music).build()).mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build();
    }
    
    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder)
    {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }
    
    public static Biome shurimanDesert() {
		MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
		BiomeDefaultFeatures.desertSpawns(spawnBuilder);
		
		BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        //BiomeDefaultFeatures.addFossilDecoration(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultFlowers(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultGrass(biomeBuilder);
        //BiomeDefaultFeatures.addDesertVegetation(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        //BiomeDefaultFeatures.addDesertExtraVegetation(biomeBuilder);
        //BiomeDefaultFeatures.addDesertExtraDecoration(biomeBuilder);

		biomeBuilder.addFeature(GenerationStep.Decoration.RAW_GENERATION, ModPlacedFeatures.SHURIMAN_DUNE);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.SHURIMAN_CACTUS);
        
        return biome(Biome.Precipitation.NONE, 2.0F, 0.0F, spawnBuilder, biomeBuilder, NORMAL_MUSIC);
	}

    public static Biome shurimanWasteland(){
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.desertSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDesertExtraDecoration(biomeBuilder);
        BiomeDefaultFeatures.addDesertVegetation(biomeBuilder);
        BiomeDefaultFeatures.addDesertExtraVegetation(biomeBuilder);

        return biome(Biome.Precipitation.NONE, 2.0f, 0.0f, spawnBuilder, biomeBuilder, NORMAL_MUSIC);
    }

}
