package com.pizzaprince.runeterramod.item;

import java.util.function.Supplier;

import com.pizzaprince.runeterramod.RuneterraMod;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModArmorMaterials implements ArmorMaterial{

	ASHE_ARMOR("ashe_armor", 28, new int[]{2, 4, 5, 2}, 18, SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F, 0.0F, () -> Ingredient.of(ItemStack.EMPTY));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultipler, int[] armor, int enchantValue,
                              SoundEvent sound, float toughness, float knockbackRes, Supplier<Ingredient> repair) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultipler;
        this.slotProtections = armor;
        this.enchantmentValue = enchantValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackRes;
        this.repairIngredient = new LazyLoadedValue<>(repair);
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type slot) {
        return HEALTH_PER_SLOT[slot.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type slot) {
        return this.slotProtections[slot.getSlot().getIndex()];
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return RuneterraMod.MOD_ID + ":" + this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
