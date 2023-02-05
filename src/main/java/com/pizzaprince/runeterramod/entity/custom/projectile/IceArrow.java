package com.pizzaprince.runeterramod.entity.custom.projectile;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.IceArrowParticleS2CPacket;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SplashPotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import oshi.jna.platform.windows.PowrProf.BATTERY_QUERY_INFORMATION_LEVEL;
import net.minecraft.world.entity.projectile.Arrow;

public class IceArrow extends AbstractArrow{
	   private int life;
	   @Nullable
	   private BlockState lastState;
	   protected boolean inGround;
	   protected int inGroundTime;
	   public AbstractArrow.Pickup pickup = AbstractArrow.Pickup.DISALLOWED;
	   public int shakeTime;
	   private SoundEvent soundEvent = SoundEvents.GLASS_BREAK;
	   @Nullable
	   private IntOpenHashSet piercingIgnoreEntityIds;
	   @Nullable
	   private List<Entity> piercedAndKilledEntities;
	
	public IceArrow(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}
	
	public IceArrow(Level level, LivingEntity entity) {
		super(ModEntityTypes.ICE_ARROW.get(), entity, level);
	}
	
	@Override
	protected void onHitBlock(BlockHitResult p_36755_) {
		if(this.level instanceof ServerLevel) {
			ModPackets.sendToNearbyPlayers(new IceArrowParticleS2CPacket(this.getX(), this.getY(), this.getZ()), this.getLevel(), p_36755_.getBlockPos());
		}
		this.playSound(soundEvent);
		this.kill();    
		this.discard();
	}

	@Override
	protected void doPostHurtEffects(LivingEntity p_36744_) {
		super.doPostHurtEffects(p_36744_);
		      
		p_36744_.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));
		      
		this.playSound(soundEvent);
		ModPackets.sendToNearbyPlayers(new IceArrowParticleS2CPacket(this.getX(), this.getY(), this.getZ()), this.getLevel(), p_36744_.getOnPos());
		
		this.kill();
		this.discard();
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}
		

	}


