package com.pizzaprince.runeterramod.entity.custom.projectile;

import java.util.List;

import javax.annotation.Nullable;

import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.networking.ModPackets;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.block.Blocks;
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
	protected void onHitBlock(BlockHitResult result) {
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

		entity.getServer().getLevel(entity.getCommandSenderWorld().dimension())
				.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BLUE_ICE.defaultBlockState()), this.getX(), this.getY(), this.getZ(), 20, 0, 0, 0, 0);

		entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));

		this.playSound(soundEvent);

		this.kill();
		this.discard();
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}


}


