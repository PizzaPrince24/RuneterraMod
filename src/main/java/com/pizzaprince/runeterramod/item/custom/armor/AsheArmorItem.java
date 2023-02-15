package com.pizzaprince.runeterramod.item.custom.armor;

import java.util.List;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.item.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AsheArmorItem extends GeoArmorItem implements IAnimatable{
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public AsheArmorItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
		super(materialIn, slot, builder);
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
