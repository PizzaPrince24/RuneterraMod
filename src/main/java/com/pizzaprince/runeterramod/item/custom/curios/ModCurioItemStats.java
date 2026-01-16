package com.pizzaprince.runeterramod.item.custom.curios;

import com.pizzaprince.runeterramod.effect.ModAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import oshi.util.tuples.Pair;

import java.util.List;

public class ModCurioItemStats {

    public static final List<Pair<Attribute, AttributeModifier>> EMPTY = List.of();


    //------------------------------------------BASIC CURIO ITEMS--------------------------------------------------
    public static final List<Pair<Attribute, AttributeModifier>> AGILITY_CLOAK = List.of(
            new Pair<>(ModAttributes.CRIT_CHANCE.get(), new AttributeModifier("agility_cloak_crit", 0.15, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> AMP_TOME = List.of(
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("amp_tome_damage", 1, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> BFSWORD = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("bfsword_damage", 2, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> BLASTING_WAND = List.of(
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("blasting_wand_damage", 2, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> CLOTH_ARMOR = List.of(
            new Pair<>(Attributes.ARMOR, new AttributeModifier("cloth_armor_armor", 1, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> DAGGER = List.of(
            new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("dagger_attack_speed", 0.12, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> GLOWING_MOTE = List.of(
            new Pair<>(ModAttributes.ABILITY_HASTE.get(), new AttributeModifier("glowing_mote_ability_haste", 5, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> LONG_SWORD = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("long_sword_damage", 0.5, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> NEEDLESSLY_LARGE_ROD = List.of(
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("needlessly_large_rod_damage", 3.5, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> NULL_MAGIC_MANTLE = List.of(
            new Pair<>(ModAttributes.MAGIC_RESIST.get(), new AttributeModifier("null_magic_mantle_armor", 1, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> PICKAXE = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("pickaxe_damage", 1, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> RUBY_CRYSTAL = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("ruby_crystal_health", 3, AttributeModifier.Operation.ADDITION))
    );




    //-------------------------------------EPIC CURIO ITEMS---------------------------------------------
    public static final List<Pair<Attribute, AttributeModifier>> BAMIS_CINDER = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("bamis_cinder_health", 6, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> CHAIN_VEST = List.of(
            new Pair<>(Attributes.ARMOR, new AttributeModifier("chain_vest_armor", 4, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> CRYSTALLINE_BRACER = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("crystalline_bracer_health", 4, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> GIANTS_BELT = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("giants_belt_health", 7, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> KINDLEGEM = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("kindlegem_health", 4, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.ABILITY_HASTE.get(), new AttributeModifier("kindlegem_ability_haste", 10, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> LOST_CHAPTER = List.of(
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("lost_chapter_ap", 2, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.ABILITY_HASTE.get(), new AttributeModifier("lost_chapter_ability_haste", 15, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> NOONQUIVER = List.of(
            new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("noonquiver_attack_speed", 0.15, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("noonquiver_attack_damage", 1.5, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> RECURVE_BOW = List.of(
            new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("recurve_bow_attack_speed", 0.20, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> SERRATED_DIRK = List.of(
            new Pair<>(ModAttributes.LETHALITY.get(), new AttributeModifier("serrated_dirk_lethality", 1.5, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("serrated_dirk_damage", 1, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> VAMPIRIC_SCEPTER = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("vampiric_scepter_damage", 1.5, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.OMNIVAMP.get(), new AttributeModifier("vampiric_scepter_omnivamp", 0.07, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> ZEAL = List.of(
            new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("zeal_attack_speed", 0.15, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.CRIT_CHANCE.get(), new AttributeModifier("zeal_crit_chance", 0.15, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.MOVEMENT_SPEED, new AttributeModifier("zeal_speed", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL))
    );






    //--------------------------------------LEGENDARY CURIO ITEMS---------------------------------------------
    public static final List<Pair<Attribute, AttributeModifier>> SUNFIRE_AEGIS = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("sunfire_aegis_health", 9, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ARMOR, new AttributeModifier("sunfire_aegis_armor", 5, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> BLOODTHIRSTER = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("bloodthirster_damage", 3, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.OMNIVAMP.get(), new AttributeModifier("bloodthirster_omnivamp", 0.18, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> OPPORTUNITY = List.of(
            new Pair<>(ModAttributes.LETHALITY.get(), new AttributeModifier("opportunity_lethality", 3, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("opportunity_damage", 3, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.MOVEMENT_SPEED, new AttributeModifier("opportunity_move_speed", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL))
    );
    public static final List<Pair<Attribute, AttributeModifier>> COSMIC_DRIVE = List.of(
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("cosmic_drive_ability_power", 4, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("cosmic_drive_health", 5, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.MOVEMENT_SPEED, new AttributeModifier("cosmic_drive_move_speed", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL)),
            new Pair<>(ModAttributes.ABILITY_HASTE.get(), new AttributeModifier("cosmic_drive_ability_haste", 25, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> DEATHBLADE = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("deathblade_damage", 4, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("deathblade_damage_multiplied", 0.35, AttributeModifier.Operation.MULTIPLY_TOTAL))
    );
    public static final List<Pair<Attribute, AttributeModifier>> ECLIPSE = List.of(
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("eclipse_damage", 3.5, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.ABILITY_HASTE.get(), new AttributeModifier("eclipse_ability_haste", 15, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> GIANT_SLAYER = List.of(
            new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("giant_slayer_attack_speed", 0.25, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("giant_slayer_attack_damage", 3, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> GUINSOOS_RAGEBLADE = List.of(
            new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("guinsoos_attack_speed", 0.35, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("guinsoos_ability_power", 2, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("guinsoos_attack_damage", 2, AttributeModifier.Operation.ADDITION))
    );
    public static final List<Pair<Attribute, AttributeModifier>> HEARTSTEEL = List.of(
            new Pair<>(Attributes.MAX_HEALTH, new AttributeModifier("heartsteel_base_health", 20, AttributeModifier.Operation.ADDITION)),
            new Pair<>(ModAttributes.ABILITY_POWER.get(), new AttributeModifier("guinsoos_ability_power", 2, AttributeModifier.Operation.ADDITION)),
            new Pair<>(Attributes.ATTACK_DAMAGE, new AttributeModifier("guinsoos_attack_damage", 2, AttributeModifier.Operation.ADDITION))
    );
}
