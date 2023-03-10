package com.pizzaprince.runeterramod.item.custom;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.jetbrains.annotations.ApiStatus.OverrideOnly;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.IAbilityItem;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.item.AbstractAbility;
import com.pizzaprince.runeterramod.ability.item.custom.EnchantedCrystalArrowAbility;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.entity.custom.projectile.IceArrow;
import com.pizzaprince.runeterramod.item.ModArmorMaterials;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TippedArrowItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AsheBow extends BowItem implements IAbilityItem{
	public static final int COOLDOWN = 90*20;
	private int cooldownTracker = 0;
	private int flag = 2;
	private int trackerFlag = 20;
	private int id = 0;
	private EnchantedCrystalArrowAbility enchantedCrystalArrowAbility = new EnchantedCrystalArrowAbility(SoundEvents.ARROW_SHOOT, null, COOLDOWN, id());
	private ArrayList<AbstractAbility> abilityList = new ArrayList<AbstractAbility>(Arrays.asList(enchantedCrystalArrowAbility));
	
	private int id() {
		return id++;
	}
	
	public AsheBow(Properties properties) {
		super(properties);
	}
	
	@Override
	public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
	      if (p_40669_ instanceof Player player) {
	         boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_40667_) > 0;
	         ItemStack itemstack = player.getProjectile(p_40667_);

	         int i = this.getUseDuration(p_40667_) - p_40670_;
	         i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.isEmpty() || flag);

	         if (true) {
	            if (false) {
	               itemstack = new ItemStack(Items.ARROW);
	            }

	            float f = getPowerForTime(i, p_40669_);
	            if (!((double)f < 0.1D)) {
	               boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_40667_, player));
	               if (!p_40668_.isClientSide) {
	                  IceArrow arrowitem = new IceArrow(p_40668_, p_40669_);
	                  AbstractArrow abstractarrow = arrowitem;
	                  abstractarrow = customArrow(abstractarrow);
	                  abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
	                  if (f == 1.0F) {
	                     abstractarrow.setCritArrow(true);
	                  }

	                  int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_40667_);
	                  if (j > 0) {
	                     abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
	                  }

	                  int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_40667_);
	                  if (k > 0) {
	                     abstractarrow.setKnockback(k);
	                  }

	                  if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_40667_) > 0) {
	                     abstractarrow.setSecondsOnFire(100);
	                  }

	                  p_40667_.hurtAndBreak(1, player, (p_40665_) -> {
	                     p_40665_.broadcastBreakEvent(player.getUsedItemHand());
	                  });
	                  if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
	                     abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
	                  }

	                  p_40668_.addFreshEntity(abstractarrow);
	               }

	               p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
	               /*if (!flag1 && !player.getAbilities().instabuild) {
	                  itemstack.shrink(1);
	                  if (itemstack.isEmpty()) {
	                     player.getInventory().removeItem(itemstack);
	                  }
	               }*/

	               player.awardStat(Stats.ITEM_USED.get(this));
	            }
	         }
	      }
	   }
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
		ItemStack itemstack = p_40673_.getItemInHand(p_40674_);

	    InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, p_40672_, p_40673_, p_40674_, false);
	    if (ret != null) return ret;

	    p_40673_.startUsingItem(p_40674_);
	    return InteractionResultHolder.consume(itemstack);
	      
	}
	
	public static float getPowerForTime(int ticks, LivingEntity entity) {
		float f;
		if(entity instanceof Player player) {
			f = (float)ticks / (20.0F - ((float)1.4 * ClientAbilityData.numArmorPieces(player, ModArmorMaterials.ASHE_ARMOR)));
		} else {
			f = (float)ticks / 20.0F;
		}
	    f = (f * f + f * 2.0F) / 3.0F;
	    if (f > 1.0F) {
	    	f = 1.0F;
	    }

	    return f;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
		if(entity instanceof Player player) {
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
				if(abilities.isTrackingCooldown()) {
					abilities.trackCooldown(72000 - count);
				} else {
					abilities.setTrackingCooldown(true);
				}
			});
		}
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		CompoundTag nbt = stack.getOrCreateTag();
		if(entity instanceof Player player) {
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(abilities -> {
				if(!player.isUsingItem()) {
					if(abilities.getTrackerCooldown() > 0) {
						for(AbstractAbility ability : this.getAbilities()) {
							int i = nbt.getInt(RuneterraMod.MOD_ID + ":" + ability.getId() + "_cooldown");
							this.setCooldownById(stack, Math.max(0, i - abilities.getTrackerCooldown()), ability.getId());
						}
						abilities.resetTrackerCooldown();
						abilities.setTrackingCooldown(false);
					} else {
						this.tick(stack, level, entity, itemSlot, isSelected);
					}
				}
			});
		}
	}

	@Override
	public ArrayList<AbstractAbility> getAbilities() {
		return abilityList;
	}
	

}
