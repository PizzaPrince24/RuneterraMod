package com.pizzaprince.runeterramod.client;

import com.ibm.icu.number.Scale;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.item.ModItems;

import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.AOEParticleS2CPacket;
import com.pizzaprince.runeterramod.world.biome.ModBiomes;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.EasingType;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;
import virtuoel.pehkui.api.ScaleEasings;
import virtuoel.pehkui.api.ScaleTypes;

public class ClientAbilityData {

	private static int currentRage = 0;

	private static int rageToAdd = 0;

	private static final int maxRage = 100;
	private static boolean isStunned = false;
	private static int stunDuration;

	private static int tickCount = 0;

	private static int rageTick = -1;

	private static boolean hasSpunTick = false;

	private static CameraType lastCamera = CameraType.FIRST_PERSON;

	private static int lookAtEntityID = -1;

	private static int rageArtTicks = -1;

	private static int rageArtEntityId = -1;

	private static boolean hasTickedRageArt = false;
	private static float rageArtYRot = -1;

	private static float sandstormLevel = 0f;

	public static boolean isStunned() {
		return ClientAbilityData.isStunned;
	}

	public static void setStunned() {
		ClientAbilityData.isStunned = true;
		ClientAbilityData.stunDuration = 5;
	}

	public static void setRage(int value){
		if(value > ClientAbilityData.currentRage || value - ClientAbilityData.currentRage < -1){
			ClientAbilityData.rageToAdd = value - ClientAbilityData.currentRage;
			ClientAbilityData.rageTick = 40;
		} else {
			ClientAbilityData.currentRage = value;
		}
	}

	public static int getRage(){
		return ClientAbilityData.currentRage;
	}

	public static void tick() {
		Minecraft mc = Minecraft.getInstance();
		ClientLevel level = mc.level;
		ClientAbilityData.tickCount = ++ClientAbilityData.tickCount % 20;
		ClientAbilityData.stunDuration--;
		ClientAbilityData.stunDuration = Math.max(0, ClientAbilityData.stunDuration);
		if(ClientAbilityData.stunDuration == 0) {
			ClientAbilityData.isStunned = false;
		}
		if(ClientAbilityData.rageTick == 0){
			ClientAbilityData.rageTick = -1;
		}
		ClientAbilityData.rageTick--;
		//try to get rage to ease in and out over one second
		if(ClientAbilityData.rageTick >= 0) {
			int addedRage = (int)(ScaleEasings.LINEAR.apply((40f - (float) ClientAbilityData.rageTick) / 40f) * (float)ClientAbilityData.rageToAdd);
			//ClientAbilityData.rageToAdd = Math.max(0, ClientAbilityData.rageToAdd - addedRage);
			ClientAbilityData.rageToAdd = ClientAbilityData.rageToAdd - addedRage;
			ClientAbilityData.currentRage = Math.min(ClientAbilityData.maxRage, ClientAbilityData.currentRage + addedRage);
		}
		mc.player.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
			if(cap.getSpinTicks() >= 22){
				mc.options.setCameraType(ClientAbilityData.lastCamera);
				ClientAbilityData.hasSpunTick = false;
			} else if(cap.getSpinTicks() >= 0){
				if(!ClientAbilityData.hasSpunTick){
					ClientAbilityData.hasSpunTick = true;
					ClientAbilityData.lastCamera = mc.options.getCameraType();
					mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
				}
			}
			setLookAtTarget(mc.player);
			updateRageArtMovement();
		});
		Holder<Biome> biome = level.getBiome(mc.cameraEntity.blockPosition());
		if((biome.is(ModBiomes.SHURIMAN_DESERT) || biome.is(ModBiomes.SHURIMAN_WASTELAND)) && level.isRaining()){
			ClientAbilityData.sandstormLevel += 0.025f;
		} else {
			ClientAbilityData.sandstormLevel -= 0.025f;
		}
		ClientAbilityData.sandstormLevel = Mth.clamp(ClientAbilityData.sandstormLevel, 0f, 1f);
	}

	private static void updateRageArtMovement(){
		if(ClientAbilityData.rageArtTicks >= 0 && !ClientAbilityData.hasTickedRageArt){
			//ClientAbilityData.rageArtTicks++;
			Player player = Minecraft.getInstance().player;
			player.setYRot(ClientAbilityData.rageArtYRot);
			player.setYBodyRot(ClientAbilityData.rageArtYRot);
			LivingEntity target = (LivingEntity) Minecraft.getInstance().level.getEntity(ClientAbilityData.rageArtEntityId);
			if(target == null){
				return;
			}
			if(ClientAbilityData.rageArtTicks >= 2 && ClientAbilityData.rageArtTicks < 15){
				Vec3 moveVec = new Vec3(target.getX() - player.getX(), target.getY() - player.getY(), target.getZ() - player.getZ());
				moveVec = moveVec.add(moveVec.x > 0 ? -(target.getBbWidth()/2 + player.getBbWidth()/2) : (target.getBbWidth()/2 + player.getBbWidth()/2), 0,
						moveVec.z > 0 ? -(target.getBbWidth()/2 + player.getBbWidth()/2) : (target.getBbWidth()/2 + player.getBbWidth()/2));
				//player.move(MoverType.SELF, moveVec.multiply((rageArtTicks)/26d, (rageArtTicks)/26d, (rageArtTicks)/26d));
				//player.moveTo(moveVec.multiply((rageArtTicks)/26d, (rageArtTicks)/26d, (rageArtTicks)/26d));
				player.setDeltaMovement(moveVec.multiply((ClientAbilityData.rageArtTicks)/30d, (ClientAbilityData.rageArtTicks)/30d, (ClientAbilityData.rageArtTicks)/30d));
				player.hurtMarked = true;
			}
			if(ClientAbilityData.rageArtTicks >= 15 && ClientAbilityData.rageArtTicks < 33){
				Vec3 targetVec = new Vec3(target.getX() - player.getX(), (target.getY() + target.getBbHeight()) - player.getY(), target.getZ() - player.getZ());
				//player.move(MoverType.SELF, targetVec.multiply((rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d));
				//player.moveTo(targetVec.multiply((rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d));
				player.setDeltaMovement(targetVec.multiply((ClientAbilityData.rageArtTicks - 14)/30d, (ClientAbilityData.rageArtTicks - 14)/30d, (ClientAbilityData.rageArtTicks - 14)/30d));
				player.hurtMarked = true;
			}
			if(ClientAbilityData.rageArtTicks >= 34 && ClientAbilityData.rageArtTicks < 56){
				int num = Math.min(55, ClientAbilityData.rageArtTicks);
				double targetHeight = target.getY() + target.getBbHeight() + 12.0;
				Vec3 moveVec = new Vec3((target.getX() - player.getX())/20d, (targetHeight - player.getY())*(((double)num-33.0)/22.0), (target.getZ() - player.getZ())/20d);
				player.setDeltaMovement(moveVec);
				player.hurtMarked = true;
			}
			if(ClientAbilityData.rageArtTicks >= 56 && ClientAbilityData.rageArtTicks < 90){
				player.setDeltaMovement(0, 0, 0);
				player.hurtMarked = true;
			}
			if(ClientAbilityData.rageArtTicks > 90 && ClientAbilityData.rageArtTicks < 110){
				player.addDeltaMovement(new Vec3(0.01, 0, 0));
				player.hurtMarked = true;
			}
			ClientAbilityData.hasTickedRageArt = true;
		}
	}

	public static void startRageArt(int eID, float yRot){
		ClientAbilityData.rageArtTicks = 0;
		ClientAbilityData.rageArtEntityId = eID;
		ClientAbilityData.hasTickedRageArt = false;
		ClientAbilityData.rageArtYRot = yRot;
	}

	public static void setRageArtTicks(int ticks){
		ClientAbilityData.rageArtTicks = ticks;
		ClientAbilityData.hasTickedRageArt = false;
	}

	public static int getRageArtTicks(){
		return ClientAbilityData.rageArtTicks;
	}

	private static void setLookAtTarget(Player player) {
		Vec3 origin = new Vec3(player.getX() + (player.getBbWidth() / 2), player.getY() + player.getEyeHeight(), player.getZ() + (player.getBbWidth() / 2));
		Vec3 direction = player.getForward();
		Vec3 target = origin.add(direction.scale(10));
		Vec3 ray = target.subtract(origin);
		AABB bb = player.getBoundingBox().inflate(10);
		EntityHitResult hit = ProjectileUtil.getEntityHitResult(player, origin, target, bb, entity -> !entity.isSpectator() && entity instanceof LivingEntity, ray.lengthSqr());
		if(hit != null){
			if(hit.getEntity() instanceof LivingEntity pTarget) {
				lookAtEntityID = pTarget.getId();
			} else {
				lookAtEntityID = -1;
			}
		} else {
			lookAtEntityID = -1;
		}
	}

	public static int getLookAtEntityID(){
		return ClientAbilityData.lookAtEntityID;
	}

	public static float getSandstormLevel(){
		return ClientAbilityData.sandstormLevel;
	}

	public static int numArmorPieces(Player player, ArmorMaterial material) {
		int num = 0;
		for (ItemStack armorStack: player.getInventory().armor) {
            if(armorStack.getItem() instanceof ArmorItem armor) {
                if(armor.getMaterial() == material) {
					num++;
                }
            }
        }

		return num;
	}

	public static boolean hasFullArmorSetOn(Player player, ArmorMaterial material) {
		ItemStack boots = player.getInventory().getArmor(0);
		ItemStack leggings = player.getInventory().getArmor(1);
		ItemStack breastplate = player.getInventory().getArmor(2);
		ItemStack helmet = player.getInventory().getArmor(3);

		if(!helmet.isEmpty() && !breastplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty()) {
			for (ItemStack armorStack: player.getInventory().armor) {
				if(!(armorStack.getItem() instanceof ArmorItem)) {
					return false;
				}
			}

			ArmorItem _boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
			ArmorItem _leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
			ArmorItem _breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
			ArmorItem _helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

			return _helmet.getMaterial() == material && _breastplate.getMaterial() == material &&
					_leggings.getMaterial() == material && _boots.getMaterial() == material;

		} else {
			return false;
		}
	}
}
