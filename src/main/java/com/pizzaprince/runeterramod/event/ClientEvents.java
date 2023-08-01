package com.pizzaprince.runeterramod.event;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.block.entity.client.ShurimanTransfuserRenderer;
import com.pizzaprince.runeterramod.block.entity.client.SunDiskAltarRenderer;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.client.ModMenuTypes;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarScreen;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.client.*;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.KeyPressC2SPacket;
import com.pizzaprince.runeterramod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {
	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		
		@SubscribeEvent
		public static void onKeyInput(InputEvent.Key event) {
			if(KeyBinding.ULTIMATE_KEY.consumeClick()) {
				if(!ClientAbilityData.isStunned()) {
					ModPackets.sendToServer(new KeyPressC2SPacket());
				}
			}
			if(KeyBinding.TEST_KEY.consumeClick()){
				//Minecraft.getInstance().gameRenderer.getMainCamera()
			}
		}
		
		@SubscribeEvent
		public static void onKeyInput(InputEvent.MouseScrollingEvent event) {
			if(ClientAbilityData.isStunned()) {
				event.setCanceled(true);
			}
		}
		
		@SubscribeEvent
		public static void keyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
			if(ClientAbilityData.isStunned()) {
				event.setSwingHand(false);
				event.setCanceled(true);
			}
		}
		
		@SubscribeEvent
		public static void onKeyPress(ScreenEvent.KeyPressed.Pre event) {
			if(ClientAbilityData.isStunned()) {
				event.setCanceled(true);
			}
		}
	}
	
	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {
		@SubscribeEvent
		public static void onKeyRegister(RegisterKeyMappingsEvent event) {
			event.register(KeyBinding.ULTIMATE_KEY);
			event.register(KeyBinding.TEST_KEY);
		}
		
	}

	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			EntityRenderers.register(ModEntityTypes.REKSAI.get(), RekSaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.RAMPAGING_BACCAI.get(), RampagingBaccaiRenderer::new);
			MenuScreens.register(ModMenuTypes.SUN_DISK_ALTAR_MENU.get(), SunDiskAltarScreen::new);
			BlockEntityRenderers.register(ModBlockEntities.SUN_DISK_ALTAR_ENTITY.get(), SunDiskAltarRenderer::new);
			BlockEntityRenderers.register(ModBlockEntities.SHURIMAN_ITEM_TRANSFUSER_ENTITY.get(), ShurimanTransfuserRenderer::new);
		}

		@SubscribeEvent
		public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
			event.registerEntityRenderer(ModEntityTypes.ICE_ARROW.get(), IceArrowRenderer::new);
			event.registerEntityRenderer(ModEntityTypes.ENCHANTED_CRYSTAL_ARROW.get(), EnchantedCrystalArrowRenderer::new);
			event.registerEntityRenderer(ModEntityTypes.RUNAANS_HOMING_BOLT.get(), RunaansHomingBoltRenderer::new);
			event.registerEntityRenderer(ModEntityTypes.SUNFISH.get(), SunFishRenderer::new);
		}
	}

}
