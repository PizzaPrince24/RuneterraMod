package com.pizzaprince.runeterramod.entity.custom.projectile;

import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.networking.ModPackets;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
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
		if(this.level() instanceof ServerLevel level) {
			level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 20, 0, 0, 0, 0);
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

		entity.getServer().getLevel(entity.getCommandSenderWorld().dimension())
				.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 20, 0, 0, 0, 0);

		this.kill();
		this.discard();
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

}


