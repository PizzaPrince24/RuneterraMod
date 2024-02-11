package com.pizzaprince.runeterramod.world.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class ModOverworldBiomeBuilder {

    private static final float VALLEY_SIZE = 0.05F;
    private static final float LOW_START = 0.26666668F;
    public static final float HIGH_START = 0.4F;
    private static final float HIGH_END = 0.93333334F;
    private static final float PEAK_SIZE = 0.1F;
    public static final float PEAK_START = 0.56666666F;
    private static final float PEAK_END = 0.7666667F;
    public static final float NEAR_INLAND_START = -0.11F;
    public static final float MID_INLAND_START = 0.03F;
    public static final float FAR_INLAND_START = 0.3F;
    public static final float EROSION_INDEX_1_START = -0.78F;
    public static final float EROSION_INDEX_2_START = -0.375F;
    private static final float EROSION_DEEP_DARK_DRYNESS_THRESHOLD = -0.225F;
    private static final float DEPTH_DEEP_DARK_DRYNESS_THRESHOLD = 0.9F;
    private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    private final Climate.Parameter[] temperatures = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.45F),
            Climate.Parameter.span(-0.45F, -0.15F),
            Climate.Parameter.span(-0.15F, 0.2F),
            Climate.Parameter.span(0.2F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private final Climate.Parameter[] humidities = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.35F),
            Climate.Parameter.span(-0.35F, -0.1F),
            Climate.Parameter.span(-0.1F, 0.1F),
            Climate.Parameter.span(0.1F, 0.3F),
            Climate.Parameter.span(0.3F, 1.0F)
    };
    private final Climate.Parameter[] erosions = new Climate.Parameter[]{
            Climate.Parameter.span(-1.0F, -0.78F),
            Climate.Parameter.span(-0.78F, -0.375F),
            Climate.Parameter.span(-0.375F, -0.2225F),
            Climate.Parameter.span(-0.2225F, 0.05F),
            Climate.Parameter.span(0.05F, 0.45F),
            Climate.Parameter.span(0.45F, 0.55F),
            Climate.Parameter.span(0.55F, 1.0F)
    };
    private final Climate.Parameter FROZEN_RANGE = this.temperatures[0];
    private final Climate.Parameter UNFROZEN_RANGE = Climate.Parameter.span(this.temperatures[1], this.temperatures[4]);
    private final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
    private final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
    private final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
    private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
    private final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
    private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
    private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
    private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);

    private final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
            {Biomes.DEEP_FROZEN_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
            {Biomes.FROZEN_OCEAN, Biomes.COLD_OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN}
    };
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
            {ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT},
            {ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT},
            {ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT},
            {ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT},
            {ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT}
    };
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
            {ModBiomes.SHURIMAN_DESERT, null, ModBiomes.SHURIMAN_DESERT, null, null},
            {null, null, null, null, ModBiomes.SHURIMAN_DESERT},
            {ModBiomes.SHURIMAN_DESERT, null, null, ModBiomes.SHURIMAN_DESERT, null},
            {null, null, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT, ModBiomes.SHURIMAN_DESERT},
            {null, null, null, null, null}};
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND}
    };
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
            {ModBiomes.SHURIMAN_WASTELAND, null, null, null, null},
            {null, null, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {null, null, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, null},
            {null, null, null, null, null},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, null, null, null}};
    private final ResourceKey<Biome>[][] SHATTERED_BIOMES = new ResourceKey[][]{
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND, ModBiomes.SHURIMAN_WASTELAND},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };


    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addOffCoastBiomes(registry, mapper);
        this.addInlandBiomes(registry, mapper);
        this.addUndergroundBiomes(registry, mapper);
    }

    private void addInlandBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addMidSlice(mapper, Climate.Parameter.span(-1.0F, -0.93333334F));
        this.addHighSlice(mapper, Climate.Parameter.span(-0.93333334F, -0.7666667F));
        this.addPeaks(mapper, Climate.Parameter.span(-0.7666667F, -0.56666666F));
        this.addHighSlice(mapper, Climate.Parameter.span(-0.56666666F, -0.4F));
        this.addMidSlice(mapper, Climate.Parameter.span(-0.4F, -0.26666668F));
        this.addLowSlice(mapper, Climate.Parameter.span(-0.26666668F, -0.05F));
        this.addValleys(mapper, Climate.Parameter.span(-0.05F, 0.05F));
        this.addLowSlice(mapper, Climate.Parameter.span(0.05F, 0.26666668F));
        this.addMidSlice(mapper, Climate.Parameter.span(0.26666668F, 0.4F));
        this.addHighSlice(mapper, Climate.Parameter.span(0.4F, 0.56666666F));
        this.addPeaks(mapper, Climate.Parameter.span(0.56666666F, 0.7666667F));
        this.addHighSlice(mapper, Climate.Parameter.span(0.7666667F, 0.93333334F));
        this.addMidSlice(mapper, Climate.Parameter.span(0.93333334F, 1.0F));
    }

    private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temperature = this.temperatures[i];

            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter humidity = this.humidities[j];


                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlands = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlandsOrSlope = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pParam);
                ResourceKey<Biome> plateauBiome = this.pickPlateauBiome(i, j, pParam);
                ResourceKey<Biome> shatteredBiome = this.pickShatteredBiome(i, j, pParam);
                ResourceKey<Biome> windsweptBiome = this.maybePickWindsweptSavannaBiome(i, j, pParam, shatteredBiome);
                ResourceKey<Biome> peakBiome = this.pickPeakBiome(i, j, pParam);

                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], pParam, 0.0F, peakBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[1], pParam, 0.0F, middleBiomeOrBadlandsOrSlope);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], pParam, 0.0F, peakBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], pParam, 0.0F, plateauBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.midInlandContinentalness, this.erosions[3], pParam, 0.0F, middleBiomeOrBadlands);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.farInlandContinentalness, this.erosions[3], pParam, 0.0F, plateauBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], pParam, 0.0F, windsweptBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pParam, 0.0F, shatteredBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, middleBiome);
            }
        }

    }

    private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temperature = this.temperatures[i];

            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter humidity = this.humidities[j];

                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlands = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlandsOrSlope = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pParam);
                ResourceKey<Biome> plateauBiome = this.pickPlateauBiome(i, j, pParam);
                ResourceKey<Biome> shatteredBiome = this.pickShatteredBiome(i, j, pParam);
                ResourceKey<Biome> windsweptBiome = this.maybePickWindsweptSavannaBiome(i, j, pParam, shatteredBiome);
                ResourceKey<Biome> slopeBiome = this.pickSlopeBiome(i, j, pParam);
                ResourceKey<Biome> peakBiome = this.pickPeakBiome(i, j, pParam);

                this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[0], pParam, 0.0F, slopeBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[0], pParam, 0.0F, peakBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[1], pParam, 0.0F, middleBiomeOrBadlandsOrSlope);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[1], pParam, 0.0F, slopeBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], pParam, 0.0F, plateauBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.midInlandContinentalness, this.erosions[3], pParam, 0.0F, middleBiomeOrBadlands);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.farInlandContinentalness, this.erosions[3], pParam, 0.0F, plateauBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[5], pParam, 0.0F, windsweptBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pParam, 0.0F, shatteredBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, middleBiome);
            }
        }

    }

    private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        this.addSurfaceBiome(pConsumer, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), pParam, 0.0F, Biomes.STONY_SHORE);
        this.addSurfaceBiome(pConsumer, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.SWAMP);
        this.addSurfaceBiome(pConsumer, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.MANGROVE_SWAMP);

        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temperature = this.temperatures[i];

            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter humidity = this.humidities[j];

                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlands = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlandsOrSlope = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pParam);
                ResourceKey<Biome> shatteredBiome = this.pickShatteredBiome(i, j, pParam);
                ResourceKey<Biome> plateauBiome = this.pickPlateauBiome(i, j, pParam);
                ResourceKey<Biome> beachBiome = this.pickBeachBiome(i, j);
                ResourceKey<Biome> windsweptBiome = this.maybePickWindsweptSavannaBiome(i, j, pParam, middleBiome);
                ResourceKey<Biome> shatteredCoastBiome = this.pickShatteredCoastBiome(i, j, pParam);
                ResourceKey<Biome> slopeBiome = this.pickSlopeBiome(i, j, pParam);

                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[0], pParam, 0.0F, slopeBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.erosions[1], pParam, 0.0F, middleBiomeOrBadlandsOrSlope);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.farInlandContinentalness, this.erosions[1], pParam, 0.0F, i == 0 ? shatteredCoastBiome : plateauBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[2], pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.midInlandContinentalness, this.erosions[2], pParam, 0.0F, middleBiomeOrBadlands);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.farInlandContinentalness, this.erosions[2], pParam, 0.0F, plateauBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[3], pParam, 0.0F, middleBiomeOrBadlands);
                if (pParam.max() < 0L) {
                    this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, this.erosions[4], pParam, 0.0F, beachBiome);
                    this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, middleBiome);
                } else {
                    this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, middleBiome);
                }

                this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, this.erosions[5], pParam, 0.0F, shatteredCoastBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[5], pParam, 0.0F, windsweptBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pParam, 0.0F, shatteredBiome);
                if (pParam.max() < 0L) {
                    this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, this.erosions[6], pParam, 0.0F, beachBiome);
                } else {
                    this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, this.erosions[6], pParam, 0.0F, middleBiome);
                }

                if (i == 0) {
                    this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, middleBiome);
                }
            }
        }

    }

    private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        this.addSurfaceBiome(pConsumer, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), pParam, 0.0F, Biomes.STONY_SHORE);
        this.addSurfaceBiome(pConsumer, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.SWAMP);
        this.addSurfaceBiome(pConsumer, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.MANGROVE_SWAMP);

        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temperature = this.temperatures[i];

            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter humidity = this.humidities[j];

                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlands = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pParam);
                ResourceKey<Biome> middleBiomeOrBadlandsOrSlope = this.pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(i, j, pParam);
                ResourceKey<Biome> beachBiome = this.pickBeachBiome(i, j);
                ResourceKey<Biome> windsweptBiome = this.maybePickWindsweptSavannaBiome(i, j, pParam, middleBiome);
                ResourceKey<Biome> shatteredCoastBiome = this.pickShatteredCoastBiome(i, j, pParam);

                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, middleBiomeOrBadlands);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, middleBiomeOrBadlandsOrSlope);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), pParam, 0.0F, middleBiomeOrBadlands);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[4]), pParam, 0.0F, beachBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, this.erosions[5], pParam, 0.0F, shatteredCoastBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.nearInlandContinentalness, this.erosions[5], pParam, 0.0F, windsweptBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], pParam, 0.0F, middleBiome);
                this.addSurfaceBiome(pConsumer, temperature, humidity, this.coastContinentalness, this.erosions[6], pParam, 0.0F, beachBiome);
                if (i == 0) {
                    this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, middleBiome);
                }
            }
        }

    }

    private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pParam) {
        this.addSurfaceBiome(pConsumer, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, pParam.max() < 0L ? Biomes.STONY_SHORE : Biomes.FROZEN_RIVER);
        this.addSurfaceBiome(pConsumer, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, pParam.max() < 0L ? Biomes.STONY_SHORE : Biomes.RIVER);
        this.addSurfaceBiome(pConsumer, this.FROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, Biomes.FROZEN_RIVER);
        this.addSurfaceBiome(pConsumer, this.UNFROZEN_RANGE, this.FULL_RANGE, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, Biomes.RIVER);
        this.addSurfaceBiome(pConsumer, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), pParam, 0.0F, Biomes.FROZEN_RIVER);
        this.addSurfaceBiome(pConsumer, this.UNFROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), pParam, 0.0F, Biomes.RIVER);
        this.addSurfaceBiome(pConsumer, this.FROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], pParam, 0.0F, Biomes.FROZEN_RIVER);
        this.addSurfaceBiome(pConsumer, this.UNFROZEN_RANGE, this.FULL_RANGE, this.coastContinentalness, this.erosions[6], pParam, 0.0F, Biomes.RIVER);
        this.addSurfaceBiome(pConsumer, Climate.Parameter.span(this.temperatures[1], this.temperatures[2]), this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.SWAMP);
        this.addSurfaceBiome(pConsumer, Climate.Parameter.span(this.temperatures[3], this.temperatures[4]), this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.MANGROVE_SWAMP);
        this.addSurfaceBiome(pConsumer, this.FROZEN_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.erosions[6], pParam, 0.0F, Biomes.FROZEN_RIVER);

        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter temperature = this.temperatures[i];

            for(int j = 0; j < this.humidities.length; ++j) {
                Climate.Parameter humidity = this.humidities[j];

                ResourceKey<Biome> middleBiomeOrBadlands = this.pickMiddleBiomeOrBadlandsIfHot(i, j, pParam);
                this.addSurfaceBiome(pConsumer, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[0], this.erosions[1]), pParam, 0.0F, middleBiomeOrBadlands);
            }
        }

    }

    private void addOffCoastBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addSurfaceBiome(mapper, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.MUSHROOM_FIELDS);

        for(int i = 0; i < this.temperatures.length; ++i) {
            Climate.Parameter climate$parameter = this.temperatures[i];
            this.addSurfaceBiome(mapper, climate$parameter, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[0][i]);
            this.addSurfaceBiome(mapper, climate$parameter, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[1][i]);
        }
    }

    private void addUndergroundBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addUndergroundBiome(mapper, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.DRIPSTONE_CAVES);
        this.addUndergroundBiome(mapper, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.LUSH_CAVES);
        this.addBottomBiome(mapper, this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(this.erosions[0], this.erosions[1]), this.FULL_RANGE, 0.0F, Biomes.DEEP_DARK);
    }

    private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pTemperature, Climate.Parameter pHumidity, Climate.Parameter pContinentalness, Climate.Parameter pErosion, Climate.Parameter pDepth, float pWeirdness, ResourceKey<Biome> pKey) {
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(0.0F), pDepth, pWeirdness), pKey));
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(1.0F), pDepth, pWeirdness), pKey));
    }

    private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pTemperature, Climate.Parameter pHumidity, Climate.Parameter pContinentalness, Climate.Parameter pErosion, Climate.Parameter pDepth, float pWeirdness, ResourceKey<Biome> pKey) {
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.span(0.2F, 0.9F), pDepth, pWeirdness), pKey));
    }

    private void addBottomBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsumer, Climate.Parameter pTemperature, Climate.Parameter pHumidity, Climate.Parameter pContinentalness, Climate.Parameter pErosion, Climate.Parameter pDepth, float pWeirdness, ResourceKey<Biome> pKey) {
        pConsumer.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(1.1F), pDepth, pWeirdness), pKey));
    }

    private ResourceKey<Biome> pickMiddleBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        if (pParam.max() < 0L) {
            return this.MIDDLE_BIOMES[pTemperature][pHumidity];
        } else {
            ResourceKey<net.minecraft.world.level.biome.Biome> resourcekey = this.MIDDLE_BIOMES_VARIANT[pTemperature][pHumidity];
            return resourcekey == null ? this.MIDDLE_BIOMES[pTemperature][pHumidity] : resourcekey;
        }
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        return pTemperature == 4 ? this.pickBadlandsBiome(pHumidity, pParam) : this.pickMiddleBiome(pTemperature, pHumidity, pParam);
    }

    private ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        return pTemperature == 0 ? this.pickSlopeBiome(pTemperature, pHumidity, pParam) : this.pickMiddleBiomeOrBadlandsIfHot(pTemperature, pHumidity, pParam);
    }

    private ResourceKey<Biome> maybePickWindsweptSavannaBiome(int pTemperature, int pHumidity, Climate.Parameter pParam, ResourceKey<Biome> pKey) {
        return pTemperature > 1 && pHumidity < 4 && pParam.max() >= 0L ? ModBiomes.SHURIMAN_WASTELAND : pKey;
    }

    private ResourceKey<Biome> pickShatteredCoastBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        ResourceKey<Biome> resourcekey = pParam.max() >= 0L ? this.pickMiddleBiome(pTemperature, pHumidity, pParam) : this.pickBeachBiome(pTemperature, pHumidity);
        return this.maybePickWindsweptSavannaBiome(pTemperature, pHumidity, pParam, resourcekey);
    }

    private ResourceKey<Biome> pickBeachBiome(int pTemperature, int pHumidity) {
        if (pTemperature == 0) {
            return ModBiomes.SHURIMAN_WASTELAND;
        } else {
            return pTemperature == 4 ? ModBiomes.SHURIMAN_DESERT : ModBiomes.SHURIMAN_DESERT;
        }
    }

    private ResourceKey<Biome> pickBadlandsBiome(int pHumidity, Climate.Parameter pParam) {
        if (pHumidity < 2) {
            return ModBiomes.SHURIMAN_DESERT;
        } else {
            return pHumidity < 4 ? ModBiomes.SHURIMAN_WASTELAND : ModBiomes.SHURIMAN_WASTELAND;
        }
    }

    private ResourceKey<Biome> pickPlateauBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        if (pParam.max() < 0L) {
            return this.PLATEAU_BIOMES[pTemperature][pHumidity];
        } else {
            ResourceKey<Biome> resourcekey = this.PLATEAU_BIOMES_VARIANT[pTemperature][pHumidity];
            return resourcekey == null ? this.PLATEAU_BIOMES[pTemperature][pHumidity] : resourcekey;
        }
    }

    private ResourceKey<Biome> pickPeakBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        if (pTemperature <= 2) {
            return pParam.max() < 0L ? ModBiomes.SHURIMAN_DESERT : ModBiomes.SHURIMAN_DESERT;
        } else {
            return pTemperature == 3 ? ModBiomes.SHURIMAN_DESERT : this.pickBadlandsBiome(pHumidity, pParam);
        }
    }

    private ResourceKey<Biome> pickSlopeBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        if (pTemperature >= 3) {
            return this.pickPlateauBiome(pTemperature, pHumidity, pParam);
        } else {
            return pHumidity <= 1 ? ModBiomes.SHURIMAN_WASTELAND : ModBiomes.SHURIMAN_DESERT;
        }
    }

    private ResourceKey<Biome> pickShatteredBiome(int pTemperature, int pHumidity, Climate.Parameter pParam) {
        ResourceKey<Biome> resourcekey = this.SHATTERED_BIOMES[pTemperature][pHumidity];
        return resourcekey == null ? this.pickMiddleBiome(pTemperature, pHumidity, pParam) : resourcekey;
    }

}
