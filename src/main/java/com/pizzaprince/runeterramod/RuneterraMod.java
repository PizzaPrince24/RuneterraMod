package com.pizzaprince.runeterramod;

import com.mojang.logging.LogUtils;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.client.renderer.entity.EnchantedCrystalArrowRenderer;
import com.pizzaprince.runeterramod.client.renderer.entity.IceArrowRenderer;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.custom.champion.model.RekSaiRenderer;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.util.ModItemProperties;
import com.pizzaprince.runeterramod.world.biome.ModBiomes;
import com.pizzaprince.runeterramod.world.biome.ModSurfaceRuleData;
import com.pizzaprince.runeterramod.world.biome.custom.region.ShurimaRegion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib3.GeckoLib;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import org.slf4j.Logger;

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
        
        ModBiomes.register(modEventBus);
        ModBiomes.registerBiomes();
        
        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    	event.enqueueWork(() -> {
    		ModPackets.register();
    		
    		Regions.register(new ShurimaRegion(new ResourceLocation(RuneterraMod.MOD_ID, "overworld"), 2));
    		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, RuneterraMod.MOD_ID, ModSurfaceRuleData.makeRules());
    	});
        ModItemProperties.addCustomItemProperties();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        	EntityRenderers.register(ModEntityTypes.REKSAI.get(), RekSaiRenderer::new);
        }
        
        @SubscribeEvent
        public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        	event.registerEntityRenderer(ModEntityTypes.ICE_ARROW.get(), IceArrowRenderer::new);
        	event.registerEntityRenderer(ModEntityTypes.ENCHANTED_CRYSTAL_ARROW.get(), EnchantedCrystalArrowRenderer::new);
        }
    }
}
