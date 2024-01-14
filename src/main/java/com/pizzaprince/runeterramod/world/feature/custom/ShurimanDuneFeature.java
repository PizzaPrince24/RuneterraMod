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
		rand.

		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
		boolean checkBiomes = outlineSurroundingChunks(chunk, level);

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
				int y = (int)Mth.clampedLerp(height, ground, 1 - (checkBiomes ? blend(level, blockPos, chunk) : blendLiquidOnly(level, blockPos, chunk))) - 2;
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
		return true;
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

	private double blend(WorldGenLevel level, BlockPos pos, ChunkAccess chunk){
		double result = 1;
		for(int x = -16; x < 16; x++){
			for(int z = -16; z < 16; z++){
				if(!level.getBiome(pos.offset(x, 0, z)).is(ModBiomes.SHURIMAN_DESERT)){
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

	private double blendLiquidOnly(WorldGenLevel level, BlockPos pos, ChunkAccess chunk){
		double result = 1;
		for(int x = -16; x < 16; x++){
			for(int z = -16; z < 16; z++){
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
