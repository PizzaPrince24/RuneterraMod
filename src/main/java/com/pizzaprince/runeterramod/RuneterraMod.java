package com.pizzaprince.runeterramod;

import com.mojang.logging.LogUtils;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.client.ModMenuTypes;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.effect.ModPotions;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.item.ModCreativeModeTab;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.recipe.ModRecipes;
import com.pizzaprince.runeterramod.sound.ModSounds;
import com.pizzaprince.runeterramod.util.ModItemProperties;
import com.pizzaprince.runeterramod.world.biome.ModBiomes;
import com.pizzaprince.runeterramod.world.biome.ModOverworldRegionPrimary;
import com.pizzaprince.runeterramod.world.biome.ModSurfaceRuleData;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.feature.ModFeatures;
import com.pizzaprince.runeterramod.world.plant.ModPlantTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(RuneterraMod.MOD_ID)
public class RuneterraMod {
    public static final String MOD_ID = "runeterramod";
    private static final Logger LOGGER = LogUtils.getLogger();
    public RuneterraMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModEntityTypes.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModPotions.register(modEventBus);
        
        ModBiomes.register(modEventBus);
        ModFeatures.register(modEventBus);

        ModCreativeModeTab.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        ModParticles.register(modEventBus);

        ModSounds.register(modEventBus);

        ModAttributes.register(modEventBus);

        ModPlantTypes.register();

        ModDimensions.register();

        ModDamageTypes.register();

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(ModCreativeModeTab::addCreativeItems);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModPackets.register();

            Regions.register(new ModOverworldRegionPrimary(new ResourceLocation(RuneterraMod.MOD_ID, "primary"), 10));
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, RuneterraMod.MOD_ID, ModSurfaceRuleData.makeRules());

            ModPotions.addPotionRecipes();
        });
        ModItemProperties.addCustomItemProperties();
    }
}
