package com.pizzaprince.runeterramod.ability;

import java.util.ArrayList;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.item.AbstractAbility;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IAbilityItem {
	
	default AbstractAbility getSelectedAbility(ItemStack stack) {
		return this.getAbilities().get(stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":selected"));
	}
	
	default void nextAbility(ItemStack stack) {
		CompoundTag nbt = stack.getOrCreateTag();
		int i = nbt.getInt(RuneterraMod.MOD_ID + ":selected") + 1;
		if(i >= this.getAbilities().size()) {
			i = 0;
		}
		nbt.putInt(RuneterraMod.MOD_ID + ":selected", i);
	}
	
	default void setSelectedAbilityCooldown(ItemStack stack, int cooldown) {
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putInt(RuneterraMod.MOD_ID + ":" + this.getSelectedAbilityId(stack) + "_cooldown", cooldown);
	}
	
	default void setCooldownById(ItemStack stack, int cooldown, int id) {
		CompoundTag nbt = stack.getOrCreateTag();
		nbt.putInt(RuneterraMod.MOD_ID + ":" + id + "_cooldown", cooldown);
	}
	
	default int getSelectedAbilityCooldown(ItemStack stack) {
		return stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":" + this.getSelectedAbilityCooldown(stack) + "_cooldown");
	}
	
	default int getCooldownById(ItemStack stack, int id) {
		return stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":" + id + "_cooldown");
	}
	
	default boolean isOffCooldown(ItemStack stack) {
		if(stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":" + this.getSelectedAbilityId(stack) + "_cooldown") == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	ArrayList<AbstractAbility> getAbilities();
	
	default boolean fireAbility(Level level, LivingEntity entity, ItemStack stack) {
		if(this.isOffCooldown(stack)) {
			this.getAbilities().get(stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":selected")).fireAbility(entity, level);
			this.setSelectedAbilityCooldown(stack, this.getAbilities().get(stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":selected")).getCooldown());
			return true;
		} else {
			if(entity instanceof Player player) {
				player.sendSystemMessage(Component.literal("Cooldown is " + (stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":" + this.getSelectedAbilityId(stack) + "_cooldown"))/20 + " seconds"));
			}
			return false;
		}
	}
	
	default int getSelectedAbilityId(ItemStack stack) {
		return this.getAbilities().get(stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":selected")).getId();
	}
	
	default ResourceLocation getSelectedAbilityIcon(ItemStack stack) {
		return this.getAbilities().get(stack.getOrCreateTag().getInt(RuneterraMod.MOD_ID + ":selected")).getIcon();
	}
	
	default ArrayList<ResourceLocation> getAllAbilityIcons(ItemStack stack){
		ArrayList<ResourceLocation> list = new ArrayList<ResourceLocation>();
		for(AbstractAbility ability : this.getAbilities()) {
			list.add(ability.getIcon());
		}
		return list;
	}
	
	default void tick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {
		CompoundTag nbt = stack.getOrCreateTag();
		for(AbstractAbility ability : this.getAbilities()) {
			int i = nbt.getInt(RuneterraMod.MOD_ID + ":" + ability.getId() + "_cooldown");
			i = Math.max(0, --i);
			nbt.putInt(RuneterraMod.MOD_ID + ":" + ability.getId() + "_cooldown", i);
		}
	}
	
}
