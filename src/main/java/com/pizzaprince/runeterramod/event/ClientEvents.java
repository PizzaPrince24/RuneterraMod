package com.pizzaprince.runeterramod.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.CrocodileAscendant;
import com.pizzaprince.runeterramod.block.entity.ModBlockEntities;
import com.pizzaprince.runeterramod.block.entity.client.ShurimanTransfuserRenderer;
import com.pizzaprince.runeterramod.block.entity.client.SunDiskAltarRenderer;
import com.pizzaprince.runeterramod.block.entity.client.SunForgeRenderer;
import com.pizzaprince.runeterramod.camera.CameraSequences;
import com.pizzaprince.runeterramod.client.ClientAbilityData;
import com.pizzaprince.runeterramod.client.ModMenuTypes;
import com.pizzaprince.runeterramod.client.overlay.CrocAscendentRageOverlay;
import com.pizzaprince.runeterramod.client.screen.CustomPoisonCreationScreen;
import com.pizzaprince.runeterramod.client.screen.FastFlightScreen;
import com.pizzaprince.runeterramod.client.screen.SunDiskAltarScreen;
import com.pizzaprince.runeterramod.client.screen.SunForgeScreen;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.entity.client.custom.RampagingBaccaiRenderer;
import com.pizzaprince.runeterramod.entity.client.custom.RekSaiRenderer;
import com.pizzaprince.runeterramod.entity.client.custom.RenektonRenderer;
import com.pizzaprince.runeterramod.entity.client.custom.SunFishRenderer;
import com.pizzaprince.runeterramod.entity.client.layer.CrocodileTailModel;
import com.pizzaprince.runeterramod.entity.client.layer.CrocodileTailRenderLayer;
import com.pizzaprince.runeterramod.entity.client.layer.ShellRenderLayer;
import com.pizzaprince.runeterramod.entity.client.projectile.EnchantedCrystalArrowRenderer;
import com.pizzaprince.runeterramod.entity.client.projectile.IceArrowRenderer;
import com.pizzaprince.runeterramod.entity.client.projectile.RunaansHomingBoltRenderer;
import com.pizzaprince.runeterramod.item.ModItems;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.AscendedKeyPressC2SPacket;
import com.pizzaprince.runeterramod.networking.packet.UltimateKeyPressC2SPacket;
import com.pizzaprince.runeterramod.util.KeyBinding;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.pizzaprince.runeterramod.camera.CinematicCamera.MC;
public class ClientEvents {
	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, value = Dist.CLIENT)
	public static class ClientForgeEvents {

		@SubscribeEvent
		public static void onKeyInput(InputEvent.Key event) {
			if(KeyBinding.ULTIMATE_KEY.consumeClick()) {
				if(!ClientAbilityData.isStunned()) {
					ModPackets.sendToServer(new UltimateKeyPressC2SPacket());
				}
			}
			if(KeyBinding.TEST_KEY.consumeClick()){
				CameraSequences.createTestSequence(MC.player).play();
				MC.player.setDeltaMovement(5, 0, 0);
				MC.player.hurtMarked = true;
			}
			if(KeyBinding.ASCENDED_KEY.consumeClick()){
				if(!ClientAbilityData.isStunned()){
					AtomicBoolean shouldSend = new AtomicBoolean(true);
					LocalPlayer player = Minecraft.getInstance().player;
					player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						if(cap.getAscendantType() == AscendantType.EAGLE){
							if(player.isFallFlying()) {
								if (ClientAbilityData.getEagleDashCooldown() == 0) {
									Vec3 movement = player.getDeltaMovement();
									Vec3 looking = player.getLookAngle();
									player.setDeltaMovement(movement.add(looking.x * 1.5 + (looking.x * 1.5D - movement.x) * 0.5D,
											looking.y * 1.5 + (looking.y * 1.5D - movement.y) * 0.5D, looking.z * 1.5 + (looking.z * 1.5D - movement.z) * 0.5D));
									ClientAbilityData.setEagleDashOnCooldown();
								}
							} else if(player.onGround()) {
								Minecraft.getInstance().setScreen(new FastFlightScreen(player.level(), player));
							}
						}
						if(cap.getAscendantType() == AscendantType.SCORPION){
							if(!(player.getMainHandItem().getItem() instanceof BottleItem) && player.isCrouching()){
								Minecraft.getInstance().setScreen(new CustomPoisonCreationScreen(player.level(), player));
								shouldSend.set(false);
							}
						}
					});
					if(shouldSend.get()) ModPackets.sendToServer(new AscendedKeyPressC2SPacket(ClientAbilityData.getLookAtEntityID()));
				}
			}
		}

		@SubscribeEvent
		public static void onKeyInput(InputEvent.MouseScrollingEvent event) {
			if(ClientAbilityData.isStunned()) {
				event.setCanceled(true);
			}
		}

		//@SubscribeEvent
		//public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event){
		//	if(KeyBinding.TEST_KEY.isDown()) {
		//		event.getCamera().move(-10, 0, 10);
		//		event.setPitch(event.getPitch()+20);
		//	}
		//}

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

		@SubscribeEvent
		public static void computeFOVEvent(ComputeFovModifierEvent event){
			Player player = event.getPlayer();
			player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
				if(cap.getAscendantType() == AscendantType.CROCODILE && ((CrocodileAscendant)cap.getAscendant()).getSpinTicks() >= 0){
					event.setNewFovModifier(event.getFovModifier() * (2f / ((float) player.getAttributeValue(Attributes.MOVEMENT_SPEED) / player.getAbilities().getWalkingSpeed() + 1.0f)));
				}
			});
		}

		@SubscribeEvent
		public static void renderFogEvent(ViewportEvent.RenderFog event){
			float sandstormLevel = ClientAbilityData.getSandstormLevel();
			float oFar = event.getFarPlaneDistance();
			float oNear = event.getNearPlaneDistance();
			event.setFarPlaneDistance(Mth.lerp(sandstormLevel, oFar, 12f));
			event.setNearPlaneDistance(Mth.lerp(sandstormLevel, oNear, -3f));
			if(sandstormLevel > 0.2f){
				event.setFogShape(FogShape.SPHERE);
			}
			event.setCanceled(true);
		}

		@SubscribeEvent
		public static void computeFogColor(ViewportEvent.ComputeFogColor event){
			float sandstormLevel = ClientAbilityData.getSandstormLevel();
			event.setRed(Mth.lerp(sandstormLevel, event.getRed(), 199f / 255f));
			event.setGreen(Mth.lerp(sandstormLevel, event.getGreen(), 145f / 255f));
			event.setBlue(Mth.lerp(sandstormLevel, event.getBlue(), 108f / 255f));
		}
	}

	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {
		@SubscribeEvent
		public static void onKeyRegister(RegisterKeyMappingsEvent event) {
			event.register(KeyBinding.ULTIMATE_KEY);
			event.register(KeyBinding.TEST_KEY);
			event.register(KeyBinding.ASCENDED_KEY);
		}


	}

	@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			EntityRenderers.register(ModEntityTypes.REKSAI.get(), RekSaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.RAMPAGING_BACCAI.get(), RampagingBaccaiRenderer::new);
			EntityRenderers.register(ModEntityTypes.RENEKTON.get(), RenektonRenderer::new);
			MenuScreens.register(ModMenuTypes.SUN_DISK_ALTAR_MENU.get(), SunDiskAltarScreen::new);
			MenuScreens.register(ModMenuTypes.SUN_FORGE_MENU.get(), SunForgeScreen::new);
			BlockEntityRenderers.register(ModBlockEntities.SUN_DISK_ALTAR_ENTITY.get(), SunDiskAltarRenderer::new);
			BlockEntityRenderers.register(ModBlockEntities.SUN_FORGE_ENTITY.get(), SunForgeRenderer::new);
			BlockEntityRenderers.register(ModBlockEntities.SHURIMAN_ITEM_TRANSFUSER_ENTITY.get(), ShurimanTransfuserRenderer::new);
			PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(new ResourceLocation(RuneterraMod.MOD_ID, "animation"),
					1, player -> new ModifierLayer<>());
		}

		@SubscribeEvent
		public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
			event.registerEntityRenderer(ModEntityTypes.ICE_ARROW.get(), IceArrowRenderer::new);
			event.registerEntityRenderer(ModEntityTypes.ENCHANTED_CRYSTAL_ARROW.get(), EnchantedCrystalArrowRenderer::new);
			event.registerEntityRenderer(ModEntityTypes.RUNAANS_HOMING_BOLT.get(), RunaansHomingBoltRenderer::new);
			event.registerEntityRenderer(ModEntityTypes.SUNFISH.get(), SunFishRenderer::new);
		}

		@SubscribeEvent
		public static void registerLayers(EntityRenderersEvent.AddLayers event){
			for(String name : event.getSkins()){
				LivingEntityRenderer<Player, PlayerModel<Player>> renderer = event.getSkin(name);
				renderer.addLayer(new CrocodileTailRenderLayer<>(renderer, event.getEntityModels()));
				renderer.addLayer(new ShellRenderLayer<>(renderer, event.getEntityModels()));
			}
		}

		@SubscribeEvent
		public static void addLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
			event.registerLayerDefinition(CrocodileTailModel.LAYER_LOCATION, () -> CrocodileTailModel.createBodyLayer());
		}

		@SubscribeEvent
		public static void registerGuis(RegisterGuiOverlaysEvent event){
			event.registerAboveAll("rage", CrocAscendentRageOverlay.RAGE_GUI);
		}

		@SubscribeEvent
		public static void registerItemColors(RegisterColorHandlersEvent.Item event){
			event.register((itemStack, tintIndex) -> PotionUtils.getColor(itemStack), ModItems.SCORPION_CHARM.get());
		}
	}

}
