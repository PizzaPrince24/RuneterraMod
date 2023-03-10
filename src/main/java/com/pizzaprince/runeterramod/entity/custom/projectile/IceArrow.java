package com.pizzaprince.runeterramod.entity.custom.projectile;

import java.util.List;

import javax.annotation.Nullable;

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
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class IceArrow extends AbstractArrow{
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


