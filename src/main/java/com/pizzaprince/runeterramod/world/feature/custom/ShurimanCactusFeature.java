package com.pizzaprince.runeterramod.world.feature.custom;

import com.mojang.serialization.Codec;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.custom.ShurimanCactus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ShurimanCactusFeature extends Feature<NoneFeatureConfiguration> {
    public ShurimanCactusFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        BlockPos origin = pContext.origin();
        WorldGenLevel level = pContext.level();
        RandomSource rand = pContext.random();
        ChunkAccess chunk = level.getChunk(origin);

        int height = rand.nextInt(3)+1;
        for(int i = 0; i <= height; i++){
            BlockState state = ModBlocks.SHURIMAN_CACTUS.get().defaultBlockState().setValue(ShurimanCactus.FACING, getRandDir(rand.nextInt(3)))
                    .setValue(ShurimanCactus.POD_STATE, rand.nextInt(2));
            if(!state.canSurvive(level, origin.offset(0, i, 0))){
                if(i == 0){
                    return false;
                }
                continue;
            }
            chunk.setBlockState(origin.offset(0, i, 0), state, false);
        }
        return true;
    }

    private Direction getRandDir(int num){
        if(num == 0){
            return Direction.EAST;
        }
        if(num == 1){
            return Direction.NORTH;
        }
        if(num == 2){
            return Direction.WEST;
        }
        if(num == 3){
            return Direction.SOUTH;
        }
        return Direction.NORTH;
    }

}
