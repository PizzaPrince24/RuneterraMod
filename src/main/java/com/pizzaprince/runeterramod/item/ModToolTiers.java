package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

    public static final Tier SUN_STONE = TierSortingRegistry.registerTier(new ForgeTier(3, 969, 6f, 2.5f, 12,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.SUN_STONE.get())),
            new ResourceLocation(RuneterraMod.MOD_ID, "sun_stone_tool"), List.of(Tiers.IRON), List.of(Tiers.NETHERITE));

    public static final Tier INFUSED_SUN_STONE = TierSortingRegistry.registerTier(new ForgeTier(4, 1751, 8f, 4f, 19,
            Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.EMPTY), new ResourceLocation(RuneterraMod.MOD_ID, "infused_sun_stone_tool"), List.of(Tiers.DIAMOND), List.of());

    public static final Tier PURIFIED_SUN_STONE = TierSortingRegistry.registerTier(new ForgeTier(5, 2873, 11f, 6f, 26,
                    Tags.Blocks.NEEDS_NETHERITE_TOOL, () -> Ingredient.of(ModItems.PURIFIED_SUN_STONE.get())),
            new ResourceLocation(RuneterraMod.MOD_ID, "purified_sun_stone_tool"), List.of(Tiers.NETHERITE), List.of());
}
