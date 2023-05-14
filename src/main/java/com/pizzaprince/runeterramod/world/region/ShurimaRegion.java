package com.pizzaprince.runeterramod.world.region;

import java.util.List;
import java.util.function.Consumer;

import org.checkerframework.common.returnsreceiver.qual.This;

import com.mojang.datafixers.util.Pair;
import com.pizzaprince.runeterramod.world.biome.ModBiomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ModifiedVanillaOverworldBuilder;
import terrablender.api.ParameterUtils.Continentalness;
import terrablender.api.ParameterUtils.Depth;
import terrablender.api.ParameterUtils.Erosion;
import terrablender.api.ParameterUtils.Humidity;
import terrablender.api.ParameterUtils.Temperature;
import terrablender.api.ParameterUtils.Weirdness;
import terrablender.api.Region;
import terrablender.api.RegionType;

import static terrablender.api.ParameterUtils.*;

public class ShurimaRegion extends Region{

	public ShurimaRegion(ResourceLocation name, int weight) {
		super(name, RegionType.OVERWORLD, weight);
	}
	
	@Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        /*
		ModifiedVanillaOverworldBuilder builder = new ModifiedVanillaOverworldBuilder();
        // Overlap Vanilla's parameters with our own for our COLD_BLUE biome.
        // The parameters for this biome are chosen arbitrarily.
        List<Climate.ParameterPoint> points = new ParameterPointListBuilder()
            .temperature(Temperature.span(Temperature.WARM, Temperature.HOT))
            .humidity(Humidity.span(Humidity.ARID, Humidity.DRY))
            .continentalness(Continentalness.INLAND)
            .erosion(Erosion.EROSION_0, Erosion.EROSION_1)
            .depth(Depth.SURFACE, Depth.FLOOR)
            .weirdness(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_NORMAL_DESCENDING)
            .build();
        */
        this.addBiome(mapper, Temperature.span(Temperature.WARM, Temperature.HOT), 
        		Humidity.span(Humidity.ARID, Humidity.DRY),
        		Continentalness.span(Continentalness.COAST, Continentalness.FAR_INLAND), 
        		Climate.Parameter.span(0.6f, 0.9f),
        		Weirdness.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING), 
        		Depth.span(Depth.SURFACE, Depth.UNDERGROUND), 
        		2, ModBiomes.SHURIMAN_DESERT);

		this.addBiome(mapper, Temperature.span(Temperature.WARM, Temperature.HOT),
				Humidity.span(Humidity.ARID, Humidity.DRY),
				Continentalness.span(Continentalness.COAST, Continentalness.FAR_INLAND),
				Climate.Parameter.span(0.7f, 1.0f),
				Weirdness.span(Weirdness.MID_SLICE_NORMAL_ASCENDING, Weirdness.MID_SLICE_VARIANT_ASCENDING),
				Depth.span(Depth.SURFACE, Depth.UNDERGROUND),
				2, ModBiomes.SHURIMAN_WASTELAND);

	}

}
