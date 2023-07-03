package com.pizzaprince.runeterramod;

import com.mojang.logging.LogUtils;
import com.pizzaprince.runeterramod.ability.item.AbilityItemCapabilityAttacher;
import com.pizzaprince.runeterramod.ability.item.custom.curios.SunfireAegisCapabilityAttacher;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.block.entity.client.SunDiskAltarRenderer;
import com.pizzaprince.runeterramod.client.ModMenuTypes;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarScreen;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.client.EnchantedCrystalArrowRenderer;
import com.pizzaprince.runeterramod.entity.client.IceArrowRenderer;
import com.pizzaprince.runeterramod.entity.client.RampagingBaccaiRenderer;
import com.pizzaprince.runeterramod.entity.client.RekSaiRenderer;
import com.pizzaprince.runeterramod.item.ModCreativeModeTab;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.util.ModItemProperties;
import com.pizzaprince.runeterramod.world.biome.ModBiomes;
import com.pizzaprince.runeterramod.world.biome.ModOverworldRegionPrimary;
import com.pizzaprince.runeterramod.world.biome.ModSurfaceRuleData;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.feature.ModConfiguredFeatures;
import com.pizzaprince.runeterramod.world.feature.ModFeatures;
import com.pizzaprince.runeterramod.world.feature.ModPlacedFeatures;
import com.pizzaprince.runeterramod.world.plant.ModPlantTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntityTypes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        
        ModBiomes.register(modEventBus);
        ModFeatures.register(modEventBus);

        ModCreativeModeTab.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModPlantTypes.register();

        ModDimensions.register();

        AbilityItemCapabilityAttacher.register();
        SunfireAegisCapabilityAttacher.register();

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
        });
        ModItemProperties.addCustomItemProperties();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntityTypes.REKSAI.get(), RekSaiRenderer::new);
            EntityRenderers.register(ModEntityTypes.RAMPAGING_BACCAI.get(), RampagingBaccaiRenderer::new);
            MenuScreens.register(ModMenuTypes.SUN_DISK_ALTAR_MENU.get(), SunDiskAltarScreen::new);
            BlockEntityRenderers.register(ModBlockEntities.SUN_DISK_ALTAR_ENTITY.get(), SunDiskAltarRenderer::new);
        }
        
        @SubscribeEvent
        public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntityTypes.ICE_ARROW.get(), IceArrowRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.ENCHANTED_CRYSTAL_ARROW.get(), EnchantedCrystalArrowRenderer::new);
        }
    }
}
