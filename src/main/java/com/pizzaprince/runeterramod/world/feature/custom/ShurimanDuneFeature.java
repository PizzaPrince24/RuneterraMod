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

	public ShurimanDuneFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		RandomSource rand = RandomSource.create(level.getSeed());
		ChunkAccess chunk = level.getChunk(origin);
		int startX = chunk.getPos().x * 16;
		int startZ = chunk.getPos().z * 16;

		SimplexNoise noise = new SimplexNoise(rand);

		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
		boolean[][] biomes = new boolean[48][48];
		boolean[][] heights = new boolean[48][48];
		boolean checkBiomes = outlineSurroundingChunks(chunk, level);
		if(checkBiomes){
			fillOutSurroundingArea(chunk, level, biomes);
			checkheights(chunk, level, heights);
		} else {
			checkheights(chunk, level, heights);
		}


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
				int y = (int)Mth.clampedLerp(height, ground, 1 - blend(x, z, biomes, heights, checkBiomes)) - 2;
				for(int y1 = y; y1 >= ground; y1--) {
					blockPos.setY(y1);
					if (level.isEmptyBlock(blockPos)) {
						if(y1 == y){
							chunk.setBlockState(blockPos, rand.nextDouble() < (1.0/9001.0) ? ModBlocks.SUN_STONE_ORE.get().defaultBlockState() : ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), false);
						} else {
							chunk.setBlockState(blockPos, ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), false);
						}
					}
				}
			}
		}
		return true;
	}

	private void checkheights(ChunkAccess chunk, WorldGenLevel level, boolean[][] heights) {
		int x = chunk.getPos().x*16;
		int z = chunk.getPos().z*16;
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z), z);
		for(int dx = -16; dx < 32; dx++){
			for(int dz = -16; dz < 32; dz++){
				pos.setX(x+dx);
				pos.setZ(z+dz);
				pos.setY(level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX(), pos.getZ()));
				heights[dx+16][dz+16] = level.getBlockState(pos).getFluidState().isEmpty();
			}
		}
	}

	private void fillOutSurroundingArea(ChunkAccess chunk, WorldGenLevel level, boolean[][] biomes) {
		int x = chunk.getPos().x*16;
		int z = chunk.getPos().z*16;
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z), z);
		for(int dx = -16; dx < 32; dx++){
			for(int dz = -16; dz < 32; dz++){
				pos.setX(x+dx);
				pos.setZ(z+dz);
				pos.setY(level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX(), pos.getZ()));
				biomes[dx+16][dz+16] = level.getBiome(pos).is(ModBiomes.SHURIMAN_DESERT);
			}
		}
	}

	private boolean outlineSurroundingChunks(ChunkAccess chunk, WorldGenLevel level) {
		int x = chunk.getPos().x*16;
		int z = chunk.getPos().z*16;
		for(int dx = -15; dx < 32; dx+=2){
			if(!level.getBiome(new BlockPos(x+dx, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x+dx, z-15), z-15)).is(ModBiomes.SHURIMAN_DESERT)){
				return true;
			}
		}
		for(int dx = -15; dx < 32; dx+=2){
			if(!level.getBiome(new BlockPos(x+dx, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x+dx, z+31), z+31)).is(ModBiomes.SHURIMAN_DESERT)){
				return true;
			}
		}
		for(int dz = -15; dz < 32; dz+=2){
			if(!level.getBiome(new BlockPos(x-15, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x-15, z+dz), z+dz)).is(ModBiomes.SHURIMAN_DESERT)){
				return true;
			}
		}
		for(int dz = -15; dz < 32; dz+=2){
			if(!level.getBiome(new BlockPos(x+31, level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x+31, z+dz), z+dz)).is(ModBiomes.SHURIMAN_DESERT)){
				return true;
			}
		}
		return false;

	}

	private double blend(int startX, int startZ, boolean[][] biomes, boolean [][] heights, boolean checkbiomes){
		double result = 1;
		for(int x = startX; x < startX+32; x++){
			for(int z = startZ; z < startZ+32; z++){
				if(checkbiomes && !biomes[x][z]){
					result = Math.min(result, Math.sqrt(Math.pow((double)(x-startX-16) / 16, 2) + Math.pow((double)(z-startZ-16) / 16, 2)));
				}
				if(!heights[x][z]){
					result = Math.min(result, Math.sqrt(Math.pow((double)(x-startX-16) / 16, 2) + Math.pow((double)(z-startZ-16) / 16, 2)));
				}
			}
		}
		return result;
	}

	private double blendLiquidOnly(int startX, int startZ, boolean[][] heights){
		double result = 1;
		for(int x = startX; x < startX+32; x++){
			for(int z = startZ; z < startZ+32; z++){
				if(!heights[x][z]){
					result = Math.min(result, Math.sqrt(Math.pow((double)(x-startX-16) / 16, 2) + Math.pow((double)(z-startZ-16) / 16, 2)));
				}
			}
		}
		return result;
	}
}
