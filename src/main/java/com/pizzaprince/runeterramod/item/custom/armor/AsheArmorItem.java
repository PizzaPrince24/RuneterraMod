package com.pizzaprince.runeterramod.item.custom.armor;

import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.item.client.AsheArmorRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class AsheArmorItem extends ArmorItem implements GeoItem {
	private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public AsheArmorItem(ArmorMaterial materialIn, ArmorItem.Type slot, Properties builder) {
		super(materialIn, slot, builder);
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
		controllerRegistrar.add(new AnimationController(this, "controller", 20, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}

	private PlayState predicate(AnimationState state) {
		return PlayState.CONTINUE;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private AsheArmorRenderer renderer;
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				if(this.renderer == null){
					this.renderer = new AsheArmorRenderer();
				}

				this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
				return this.renderer;
			}
		});
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if(!level.isClientSide()) {
			if(hasCorrectArmorOn(player)) {

			}
		}
	}

	private boolean hasCorrectArmorOn(Player player) {
		ItemStack boots = player.getInventory().getArmor(0);
		ItemStack leggings = player.getInventory().getArmor(1);
		ItemStack breastplate = player.getInventory().getArmor(2);
		ItemStack helmet = player.getInventory().getArmor(3);

		if(!helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty()) {
			for (ItemStack armorStack: player.getInventory().armor) {
				if(!(armorStack.getItem() instanceof ArmorItem)) {
					return false;
				}
			}

			ArmorItem _boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
			ArmorItem _leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
			ArmorItem _breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
			ArmorItem _helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

			if(_boots == ModItems.ASHE_BOOTS.get() && _leggings == ModItems.ASHE_LEGGINGS.get()
					&& _breastplate == ModItems.ASHE_CHESTPLATE.get() && _helmet == ModItems.ASHE_HELMET.get()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
		components.add(Component.literal("+7% Draw Speed With Ashe's Bow").withStyle(ChatFormatting.GOLD));

		super.appendHoverText(stack, level, components, flag);
	}


}
