package com.pizzaprince.runeterramod.item.custom;

import com.pizzaprince.runeterramod.item.client.BaccaiStaffRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class BaccaiStaff extends Item implements GeoAnimatable {
    public AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public BaccaiStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }

    private PlayState predicate(AnimationState state) {
        return PlayState.CONTINUE;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);

        consumer.accept(new IClientItemExtensions() {

            private final BlockEntityWithoutLevelRenderer renderer = new BaccaiStaffRenderer();
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }


}
