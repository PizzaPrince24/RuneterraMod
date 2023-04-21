package com.pizzaprince.runeterramod.world.feature.custom;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.pizzaprince.runeterramod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.fml.common.Mod;

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

		int height = rand.nextInt(10) + 5;

		if(noise == null) {
			noise = new SimplexNoise(rand);
		}

		if(!level.getBlockState(origin.below()).is(ModBlocks.SHURIMA_SAND.get())) {
			return false;
		}

		BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

		int randX = rand.nextInt(12) + 8;
		int randZ = rand.nextInt(12) + 8;
		int randXOffset = rand.nextInt(12) + randX / 4;
		int randZOffset = rand.nextInt(12) + randZ / 4;

		for(int x = -randX; x < randXOffset; x++){
			for(int z = -randZ; z < randZOffset; z++){
				blockPos.setX(origin.getX()+x);
				blockPos.setZ(origin.getZ()+z);
				int ground = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, blockPos.getX(), blockPos.getZ());
				if(level.getBlockState(new BlockPos(blockPos.getX(), ground, blockPos.getZ())).is(Blocks.WATER)) {
					continue;
				}
				double nosieValueTest = 5 * noise.getValue(((double)origin.getX() + (double)x) / 30, ((double)origin.getZ() + (double)z) / 30);
				System.out.println(nosieValueTest);
				double noiseValue = origin.getY() + height * (1 + (noise.getValue(origin.getX() + x, 1, origin.getZ() + z)));
				boolean useXOffset = x <= 0 ? false : true;
				boolean useZOffset = z <= 0 ? false : true;
				double xProp = Math.abs((double)x / (double)(useXOffset ? randXOffset : randX));
				double zProp = Math.abs((double)z / (double)(useZOffset ? randZOffset : randZ));
				boolean xGreater = xProp > zProp;
				//System.out.println(xGreater + ", " + xProp + ", " + zProp);
				double fac;
				if(!((xGreater && x == 0) || (!xGreater && z == 0))){
					fac = Math.abs(xGreater ? ((useXOffset ? randXOffset : randX) / x) : ((useZOffset ? randZOffset : randZ) / z));
					//System.out.println(fac);
				} else {
					fac = 0;
				}
				double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
				double finalDist = Math.sqrt(Math.pow(x * fac, 2) + Math.pow(z * fac, 2));
				double proportion;
				if(finalDist == 0){
					proportion = 0;
				} else {
					proportion = dist / finalDist;
				}
				int y = (int) nosieValueTest + 140;
				blockPos.setY(y);
				if (level.isEmptyBlock(blockPos)) {
					level.setBlock(blockPos, Blocks.STONE.defaultBlockState(), 2);
				}
				/*
				int y = (int) Mth.clampedLerp(noiseValue, ground, proportion);
				for(int y1 = y; y1 >= ground; y1--) {
					int y2 = y1-1;
					if(y2 < 0){
						continue;
					}
					blockPos.setY(y2);
					if (level.isEmptyBlock(blockPos)) {
						level.setBlock(blockPos, Blocks.STONE.defaultBlockState(), 2);
					}
				}
				*/
			}
		}

		return true;
	}
}
