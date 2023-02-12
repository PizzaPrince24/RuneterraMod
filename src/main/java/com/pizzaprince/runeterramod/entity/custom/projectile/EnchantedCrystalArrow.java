package com.pizzaprince.runeterramod.entity.custom.projectile;

import java.util.List;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.IceArrowParticleS2CPacket;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class EnchantedCrystalArrow extends AbstractArrow{
	
	private SoundEvent soundEvent = SoundEvents.GLASS_BREAK;
	
	public EnchantedCrystalArrow(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
		super.setNoGravity(true);
		setBaseDamage(6.0D);
	}
	
	public EnchantedCrystalArrow(Level level, LivingEntity entity) {
		super(ModEntityTypes.ENCHANTED_CRYSTAL_ARROW.get(), entity, level);
		super.setNoGravity(true);
		setBaseDamage(6.0D);
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
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		
		entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 100, 0, false, false, true));
		      
		this.playSound(soundEvent);
		ModPackets.sendToNearbyPlayers(new IceArrowParticleS2CPacket(this.getX(), this.getY(), this.getZ()), this.getLevel(), entity.getOnPos());
		
		this.kill();
		this.discard();
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

}


