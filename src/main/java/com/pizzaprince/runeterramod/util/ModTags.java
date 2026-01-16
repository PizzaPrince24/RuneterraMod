package com.pizzaprince.runeterramod.util;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class DamageTypes {
        public static final TagKey<DamageType> MAGIC = tag("magic");

        public static final TagKey<DamageType> NO_INVULN = tag("no_invuln");
        private static TagKey<DamageType> tag(String name){
            return TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(RuneterraMod.MOD_ID, name));
        }
    }

    public static class Items{

        public static final TagKey<Item> BASIC_ITEMS = tag("basic_items");

        public static final TagKey<Item> EPIC_ITEMS = tag("epic_items");

        public static final TagKey<Item> LEGENDARY_ITEMS = tag("legendary_items");

        public static final TagKey<Item> SUN_FORGE_REPAIRABLE = tag("sun_forge_repairable");

        private static TagKey<Item> tag(String name){
            return TagKey.create(Registries.ITEM, new ResourceLocation(RuneterraMod.MOD_ID, name));
        }
    }
}
