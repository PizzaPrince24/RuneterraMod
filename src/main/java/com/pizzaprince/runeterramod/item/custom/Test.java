package com.pizzaprince.runeterramod.item.custom;

import java.util.function.Consumer;

import com.pizzaprince.runeterramod.client.renderer.TestRenderer;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Test extends Item implements IAnimatable{
	public AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public Test(Properties p_41383_) {
		super(p_41383_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
	
	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private final BlockEntityWithoutLevelRenderer renderer = new TestRenderer();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

}
