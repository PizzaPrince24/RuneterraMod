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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ShurimanDuneFeature extends Feature<NoneFeatureConfiguration>{

	public ShurimanDuneFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel worldgenlevel = context.level();
		RandomSource rand = context.random();
		
		if(!worldgenlevel.getBlockState(origin.below()).is(ModBlocks.SHURIMA_SAND.get())) {
			return false;
		}
		
		this.setBlock(worldgenlevel, origin, ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		
		BlockPos height = origin.above(Mth.ceil(Mth.randomBetween(rand, 1, 10)));
		BlockPos south = origin.south(Mth.ceil(Mth.randomBetween(rand, 0, height.getY()-origin.getY()-1)));
		BlockPos north = origin.north(Mth.ceil(Mth.randomBetween(rand, 0, height.getY()-origin.getY()-1)));
		BlockPos east = origin.east(Mth.ceil(Mth.randomBetween(rand, 0, height.getY()-origin.getY()-1)));
		BlockPos west = origin.west(Mth.ceil(Mth.randomBetween(rand, 0, height.getY()-origin.getY()-1)));
		
		for(int i = origin.getY(); i < height.getY(); i++) {
			this.setBlock(worldgenlevel, origin.above(i-origin.getY()), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		}
		
		for(int i = origin.getZ(); i > north.getZ(); i--) {
			this.setBlock(worldgenlevel, origin.north(origin.getZ()-i), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		}
		
		for(int i = origin.getZ(); i < south.getZ(); i++) {
			this.setBlock(worldgenlevel, origin.south(i-origin.getZ()), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		}
		
		for(int i = origin.getX(); i < east.getX(); i++) {
			this.setBlock(worldgenlevel, origin.east(i-origin.getX()), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		}
		
		for(int i = origin.getX(); i > west.getX(); i--) {
			this.setBlock(worldgenlevel, origin.west(origin.getX()-i), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		}
		
		BlockPos currentBlock = origin.south().east();
		this.setBlock(worldgenlevel, currentBlock, ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		this.setBlock(worldgenlevel, origin.south().west(), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		this.setBlock(worldgenlevel, origin.north().east(), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		this.setBlock(worldgenlevel, origin.north().west(), ModBlocks.SHURIMA_SAND.get().defaultBlockState());
		
		ArrayList<BlockPos> yetToBeChecked = new ArrayList<BlockPos>(Arrays.asList(origin.south().west(), origin.north().east(), origin.north().west()));
		
		while(getPotentialPositions(worldgenlevel, currentBlock).size() > 0 || yetToBeChecked.size() > 0) {
			List<BlockPos> positions = getPotentialPositions(worldgenlevel, currentBlock);
			System.out.println("Size of positions: " + positions.size());
			for(BlockPos block : positions) {
				this.setBlock(worldgenlevel, block, ModBlocks.SHURIMA_SAND.get().defaultBlockState());
				yetToBeChecked.add(block);
			}
			
			if(yetToBeChecked.contains(currentBlock)) {
				yetToBeChecked.remove(currentBlock);
			}
			//positions.forEach(block -> this.setBlock(worldgenlevel, block, ModBlocks.SHURIMA_SAND.get().defaultBlockState()));
			currentBlock = yetToBeChecked.get((int)(Math.random()*(yetToBeChecked.size())));
			//System.out.println("Number of Blocks Nearby: " + getPotentialPositions(worldgenlevel, currentBlock).size());
			//getPotentialPositions(worldgenlevel, currentBlock).forEach(block -> System.out.println("Coords: " + block.getX() + ", " + block.getY() + ", " + block.getZ()));
		}
		
		return true;
		
		
		/* default ice spike feature generation
		BlockPos blockpos = context.origin();
		RandomSource randomsource = context.random();

		WorldGenLevel worldgenlevel;
		for (worldgenlevel = context.level(); worldgenlevel.isEmptyBlock(blockpos)
				&& blockpos.getY() > worldgenlevel.getMinBuildHeight() + 2; blockpos = blockpos.below()) {
		}
		blockpos = blockpos.above(randomsource.nextInt(4));
		int i = randomsource.nextInt(4) + 7;
		int j = i / 4 + randomsource.nextInt(2);
		if (j > 1 && randomsource.nextInt(60) == 0) {
			blockpos = blockpos.above(10 + randomsource.nextInt(30));
		}

		for (int k = 0; k < i; ++k) {
			float f = (1.0F - (float) k / (float) i) * (float) j;
			int l = Mth.ceil(f);

			for (int i1 = -l; i1 <= l; ++i1) {
				float f1 = (float) Mth.abs(i1) - 0.25F;

				for (int j1 = -l; j1 <= l; ++j1) {
					float f2 = (float) Mth.abs(j1) - 0.25F;
					if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f))
							&& (i1 != -l && i1 != l && j1 != -l && j1 != l || !(randomsource.nextFloat() > 0.75F))) {
						BlockState blockstate = worldgenlevel.getBlockState(blockpos.offset(i1, k, j1));
						// if (blockstate.isAir() || isDirt(blockstate) ||
						// blockstate.is(Blocks.SNOW_BLOCK)
						// || blockstate.is(Blocks.ICE)) {
						this.setBlock(worldgenlevel, blockpos.offset(i1, k, j1),
								ModBlocks.SHURIMA_SAND.get().defaultBlockState());
						// }

						if (k != 0 && l > 1) {
							blockstate = worldgenlevel.getBlockState(blockpos.offset(i1, -k, j1));
							// if (blockstate.isAir() || isDirt(blockstate) ||
							// blockstate.is(Blocks.SNOW_BLOCK)
							// || blockstate.is(Blocks.ICE)) {
							this.setBlock(worldgenlevel, blockpos.offset(i1, -k, j1),
									ModBlocks.SHURIMA_SAND.get().defaultBlockState());
							// }
						}
					}
				}
			}
		}

		int k1 = j - 1;
		if (k1 < 0) {
			k1 = 0;
		} else if (k1 > 1) {
			k1 = 1;
		}

		for (int l1 = -k1; l1 <= k1; ++l1) {
			for (int i2 = -k1; i2 <= k1; ++i2) {
				BlockPos blockpos1 = blockpos.offset(l1, -1, i2);
				int j2 = 50;
				if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
					j2 = randomsource.nextInt(5);
				}

				while (blockpos1.getY() > 50) {
					BlockState blockstate1 = worldgenlevel.getBlockState(blockpos1);
					if (!blockstate1.isAir() && !isDirt(blockstate1) && !blockstate1.is(Blocks.SNOW_BLOCK)
							&& !blockstate1.is(Blocks.ICE) && !blockstate1.is(Blocks.PACKED_ICE)) {
						break;
					}

					this.setBlock(worldgenlevel, blockpos1, ModBlocks.SHURIMA_SAND.get().defaultBlockState());
					blockpos1 = blockpos1.below();
					--j2;
					if (j2 <= 0) {
						blockpos1 = blockpos1.below(randomsource.nextInt(5) + 1);
						j2 = randomsource.nextInt(5);
					}
				}
			}
		}

		return true;
		*/
	}
	
	private int numBlocksNearby(WorldGenLevel level, BlockPos origin) {
		int i = 0;
		if(level.getBlockState(origin.south()).is(ModBlocks.SHURIMA_SAND.get())) {
			i++;
		}
		if(level.getBlockState(origin.north()).is(ModBlocks.SHURIMA_SAND.get())) {
			i++;
		}
		if(level.getBlockState(origin.east()).is(ModBlocks.SHURIMA_SAND.get())) {
			i++;
		}
		if(level.getBlockState(origin.west()).is(ModBlocks.SHURIMA_SAND.get())) {
			i++;
		}
		if(level.getBlockState(origin.below()).is(ModBlocks.SHURIMA_SAND.get())) {
			i++;
		}
		return i;
	}
	
	private List<BlockPos> getPotentialPositions(WorldGenLevel level, BlockPos origin){
		List<BlockPos> positions = Arrays.asList(origin.north(), origin.south(), origin.east(), origin.west(), origin.above());
		List<BlockPos> goodPos = new ArrayList<BlockPos>();
		for(BlockPos position : positions) {
			if(numBlocksNearby(level, position) > 1 && level.isEmptyBlock(position)) {
				goodPos.add(position);
			}
		}
		for(BlockPos block : goodPos) {
			System.out.println("Coords: " + block.getX() + ", " + block.getY() + ", " + block.getZ());
		}
		return goodPos;
	}
}
