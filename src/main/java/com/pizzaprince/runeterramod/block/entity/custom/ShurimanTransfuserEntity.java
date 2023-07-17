package com.pizzaprince.runeterramod.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ShurimanTransfuserEntity extends AbstractItemTransuferEntity implements GeoBlockEntity {

    private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public ShurimanTransfuserEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState state){
        if(this.getUpdatePacket().getTag().getInt("item_tranfuser.progress") > 0){
            state.getController().setAnimation(RawAnimation.begin().then("animation.shuriman_transfuser.craft", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        state.getController().setAnimation(RawAnimation.begin().then("animation.shuriman_transfuser.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
