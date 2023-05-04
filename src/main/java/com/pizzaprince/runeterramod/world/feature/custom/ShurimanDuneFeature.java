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
				if(level.isWaterAt(new BlockPos(startX+x, ground, startZ+z))) {
					continue;
				}
				double noiseValue = 15 * (0.8 + noise.getValue(((double)blockPos.getX()) / 70, ((double)blockPos.getZ()) / 70));
				int height = (int) noiseValue + ground;
				int y = (int)Mth.clampedLerp(height, ground, 1 - blend(level, blockPos, origin)) - 2;
				for(int y1 = y; y1 >= ground; y1--) {
					blockPos.setY(y1);
					if (level.isEmptyBlock(blockPos)) {
						level.setBlock(blockPos, ModBlocks.SHURIMAN_SAND.get().defaultBlockState(), 2);
						int updateX = blockPos.getX() & 15;
						int updateZ = blockPos.getZ() & 15;
						//chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG).update(updateX, blockPos.getY(), updateZ, ModBlocks.SHURIMAN_SAND.get().defaultBlockState());
						//chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG).update(updateX, blockPos.getY(), updateZ, ModBlocks.SHURIMAN_SAND.get().defaultBlockState());
					}
				}
			}
		}
		return true;
	}

	private double blend(WorldGenLevel level, BlockPos pos, BlockPos origin){
		double result = 1;
		for(int i = 1; i <= 8; i++){
			if(level.containsAnyLiquid(new AABB(pos).inflate(i))){
				result = 0.125 * i;
				break;
			}
		}
		for(int x = -16; x < 16; x++){
			for(int z = -16; z < 16; z++){
				if(!level.getBiome(pos.offset(x, 0, z)).is(ModBiomes.SHURIMAN_DESERT)){
					result = Math.min(result, Math.sqrt(Math.pow((double)x / 16, 2) + Math.pow((double)z / 16, 2)));
				}
			}
		}
		if(pos.getY() < origin.getY() - 10){
			List<String> dirs = new ArrayList<String>();
			if(!level.canSeeSky(pos.offset(1, 10, 0))){
				dirs.add("+x");
			}
			if(!level.canSeeSky(pos.offset(-1, 10, 0))){
				dirs.add("-x");
			}
			if(!level.canSeeSky(pos.offset(0, 10, 1))){
				dirs.add("+z");
			}
			if(!level.canSeeSky(pos.offset(0, 10, -1))){
				dirs.add("-z");
			}
			if(dirs != null){
				//moundify(level, pos, dirs);
			}
		}
		return result;
	}

	private void moundify(WorldGenLevel level, BlockPos origin, List<String> dirs){
		if(!level.isEmptyBlock(origin)){
			return;
		}
		BlockPos.MutableBlockPos mound = new BlockPos.MutableBlockPos(origin.getX(), origin.getY(), origin.getZ());
		if(dirs.contains("+x")){
			if(!level.canSeeSky(origin.offset(1, 10, 0)) && origin.getY() < level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, origin.getX()+1, origin.getZ()) - 10) {
				System.out.println("moundified+x");
				for (int x = 1; x <= 5; x++) {
					for (int z = -x; z <= x; z++) {
						mound.set(origin.getX() + x, origin.getY(), origin.getZ() + z);
						double noiseValue = 15 * (0.8 + noise.getValue((double) mound.getX() / 70, (double) mound.getZ() / 70));
						int height = mound.getY() + (int) noiseValue;
						mound.setY((int) Mth.clampedLerp(height, mound.getY(), Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) / 10));
						while (true) {
							if (level.isEmptyBlock(mound)) {
								level.setBlock(mound, Blocks.NETHERRACK.defaultBlockState(), 2);
								mound.move(Direction.DOWN);
							} else {
								break;
							}
						}
					}
				}
			}
		}
		if(dirs.contains("-x")){
			if(!level.canSeeSky(origin.offset(-1, 10, 0)) && origin.getY() < level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, origin.getX()-1, origin.getZ()) - 10) {
				System.out.println("moundified-x");
				for (int x = -1; x >= -10; x--) {
					for (int z = x; z <= -x; z++) {
						mound.set(origin.getX() + x, origin.getY(), origin.getZ() + z);
						double noiseValue = 15 * (0.8 + noise.getValue((double) mound.getX() / 70, (double) mound.getZ() / 70));
						int height = mound.getY() + (int) noiseValue;
						mound.setY((int) Mth.clampedLerp(height, mound.getY(), Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) / 10));
						while (true) {
							if (level.isEmptyBlock(mound)) {
								level.setBlock(mound, Blocks.NETHERRACK.defaultBlockState(), 2);
								mound.move(Direction.DOWN);
							} else {
								break;
							}
						}
					}
				}
			}
		}
		if(dirs.contains("+z")) {
			if(!level.canSeeSky(origin.offset(0, 10, 1)) && origin.getY() < level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, origin.getX(), origin.getZ()+1) - 10) {
				System.out.println("moundified+z");
				for (int z = 1; z <= 10; z++) {
					for (int x = -z; x <= z; x++) {
						mound.set(origin.getX() + x, origin.getY(), origin.getZ() + z);
						double noiseValue = 15 * (0.8 + noise.getValue((double) mound.getX() / 70, (double) mound.getZ() / 70));
						int height = mound.getY() + (int) noiseValue;
						mound.setY((int) Mth.clampedLerp(height, mound.getY(), Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) / 10));
						while (true) {
							if (level.isEmptyBlock(mound)) {
								level.setBlock(mound, Blocks.NETHERRACK.defaultBlockState(), 2);
								mound.move(Direction.DOWN);
							} else {
								break;
							}
						}
					}
				}
			}
		}
		if(dirs.contains("-z")) {
			if(!level.canSeeSky(origin.offset(0, 10, -1)) && origin.getY() < level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, origin.getX(), origin.getZ()-1) - 10) {
				System.out.println("moundified-z");
				for (int z = -1; z >= -10; z--) {
					for (int x = z; x <= -z; x++) {
						mound.set(origin.getX() + x, origin.getY(), origin.getZ() + z);
						double noiseValue = 15 * (0.8 + noise.getValue((double) mound.getX() / 70, (double) mound.getZ() / 70));
						int height = mound.getY() + (int) noiseValue;
						mound.setY((int) Mth.clampedLerp(height, mound.getY(), Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) / 10));
						while (true) {
							if (level.isEmptyBlock(mound)) {
								level.setBlock(mound, Blocks.NETHERRACK.defaultBlockState(), 2);
								mound.move(Direction.DOWN);
							} else {
								break;
							}
						}
					}
				}
			}
		}
	}
}
