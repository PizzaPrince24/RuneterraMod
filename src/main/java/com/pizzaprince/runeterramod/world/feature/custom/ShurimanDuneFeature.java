package com.pizzaprince.runeterramod.world.feature.custom;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.pizzaprince.runeterramod.block.ModBlocks;

import com.pizzaprince.runeterramod.world.biome.ModBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.*;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.common.Mod;
import oshi.util.tuples.Pair;

public class ShurimanDuneFeature extends Feature<NoneFeatureConfiguration>{
	private static SimplexNoise noise = null;

	public ShurimanDuneFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource rand = context.random();
		ChunkAccess chunk = level.getChunk(origin);
		int startX = chunk.getPos().x * 16;
		int startZ = chunk.getPos().z * 16;

		if(noise == null) {
			noise = new SimplexNoise(rand);
		}

		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

		for(int x = 0; x < 16; x++){
			for(int z = 0; z < 16; z++){
				blockPos.setX(startX + x);
				blockPos.setZ(startZ + z);
				int ground = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockPos.getX(), blockPos.getZ());
				blockPos.setY(ground);
				if(level.containsAnyLiquid(new AABB(blockPos))) {
					continue;
				}
				double noiseValue = 15 * (0.8 + noise.getValue(((double)blockPos.getX()) / 70, ((double)blockPos.getZ()) / 70));
				int height = (int) noiseValue + ground;
				int y = (int)Mth.clampedLerp(height, ground, 1 - blend(level, blockPos, chunk)) - 2;
				for(int y1 = y; y1 >= ground; y1--) {
					blockPos.setY(y1);
					if (level.isEmptyBlock(blockPos)) {
						if(y1 == y){
							chunk.setBlockState(blockPos, rand.nextDouble() < (1.0/9001.0) ? ModBlocks.SUN_STONE_ORE.get().defaultBlockState() : ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), false);
							//level.setBlock(blockPos, rand.nextDouble() < (1/9001) ? ModBlocks.SUN_STONE_ORE.get().defaultBlockState() : ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), 2);
						} else {
							chunk.setBlockState(blockPos, ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), false);
							//level.setBlock(blockPos, ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), 2);
						}
					}
				}
			}
		}
/*
		for(int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				blockPos.setX(startX + x);
				blockPos.setZ(startZ + z);
				int ground = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockPos.getX(), blockPos.getZ());
				blockPos.setY(ground);
				if(level.containsAnyLiquid(new AABB(blockPos))) {
					blendfromLiquid(level, blockPos.getX(), blockPos.getZ());
				}
			}
		}

 */

		return true;
	}
    /*
	private void blendfromLiquid(WorldGenLevel level, int originX, int originZ) {
		System.out.println("Liquid at " + originX + ", " + originZ);
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for(int dx = -8; dx <= 8; dx++){
			for(int dz = -8; dz <= 8; dz++){
				int x = originX+dx;
				int z = originZ+dz;
				int ground = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
				pos.set(x, ground, z);
				if(!level.containsAnyLiquid(new AABB(pos))){
					double noiseValue = 15 * (0.8 + noise.getValue(((double)x) / 70, ((double)z) / 70));
					int height = (int) noiseValue + ground;
					int y = (int)Mth.clampedLerp(height, ground, 1 - ((Math.sqrt(Math.pow(dx, 2) + Math.pow(dz, 2))) / 8)) - 2;
					if(y < ground) continue;
					for(int dy = y; dy < height; dy++){
						pos.setY(dy);
						level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
					}
				}
			}
		}
	}

	 */

	private double blend(WorldGenLevel level, BlockPos pos, ChunkAccess chunk){
		double result = 1;
		boolean checkBiomes;
		if(!level.getBiome(pos.offset(16, 0, 0)).is(ModBiomes.SHURIMAN_DESERT) || !level.getBiome(pos.offset(-16, 0, 0)).is(ModBiomes.SHURIMAN_DESERT)
			|| !level.getBiome(pos.offset(0, 0, 16)).is(ModBiomes.SHURIMAN_DESERT) || !level.getBiome(pos.offset(0, 0, -16)).is(ModBiomes.SHURIMAN_DESERT)
				|| !level.getBiome(pos.offset(16, 0, 16)).is(ModBiomes.SHURIMAN_DESERT) || !level.getBiome(pos.offset(16, 0, -16)).is(ModBiomes.SHURIMAN_DESERT)
				|| !level.getBiome(pos.offset(-16, 0, 16)).is(ModBiomes.SHURIMAN_DESERT) || !level.getBiome(pos.offset(-16, 0, -16)).is(ModBiomes.SHURIMAN_DESERT)){
			checkBiomes = true;
		} else {
			checkBiomes = false;
		}
		for(int x = -16; x < 16; x++){
			for(int z = -16; z < 16; z++){
				if(checkBiomes && !level.getBiome(pos.offset(x, 0, z)).is(ModBiomes.SHURIMAN_DESERT)){
					result = Math.min(result, Math.sqrt(Math.pow((double)x / 16, 2) + Math.pow((double)z / 16, 2)));
				}
				BlockState state = level.getBlockState(new BlockPos(pos.getX()+x,
						level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX()+x, pos.getZ()+z), pos.getZ()+z));
				if(!state.getFluidState().isEmpty()){
					result = Math.min(result, Math.sqrt(Math.pow((double)x / 16, 2) + Math.pow((double)z / 16, 2)));
				}
			}
		}
		return result;
	}
}
