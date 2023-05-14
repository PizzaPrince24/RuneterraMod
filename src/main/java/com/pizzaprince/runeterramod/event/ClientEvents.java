package com.pizzaprince.runeterramod.event;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.block.entity.client.SunDiskAltarRenderer;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.client.renderer.armor.AsheArmorRenderer;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.KeyPressC2SPacket;
import com.pizzaprince.runeterramod.util.KeyBinding;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

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
		}
		
		@SubscribeEvent
	    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event) {
	        GeoArmorRenderer.registerArmorRenderer(AsheArmorItem.class, () -> new AsheArmorRenderer());
	    }
		
	}

}
