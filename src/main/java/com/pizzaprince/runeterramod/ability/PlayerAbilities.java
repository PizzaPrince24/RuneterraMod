package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.effect.ModDamageTypes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.*;
import com.pizzaprince.runeterramod.particle.ModParticles;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.dimension.ShellDimCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.checkerframework.checker.units.qual.C;
import oshi.util.tuples.Pair;
import virtuoel.pehkui.api.ScaleTypes;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerAbilities {
	private int cooldown;
	private boolean canUseAbilities;
	private int abilityHaste = 0;
	private boolean sheenHit = false;
	private int static_cooldown = 10*20;
	private int outOfCombat = 0;
	private int tickCount = 0;
	private boolean isCrocodileAscended = false;
	private int crocodileAscendedRage = 0;
	private boolean canGainRage = true;
	private UUID spinSlow = null;
	private boolean isTurtleAscended = false;
	private String lastDimension = Level.OVERWORLD.toString();
	private BlockPos lastPos = new BlockPos(0, 0, 0);
	private int shellNum = -1;
	private float shellHealth = 500f;
	private boolean isRetracting = false;
	private boolean retracted = false;
	private int shellRetract = 0;
	private int spinTicks = -1;
	private int rageArtTicks = -1;
	private int rageArtTargetId = -1;

	private Vec3 rageArtTargetPos;

	private boolean canRageArtCrash = false;
	private int giantAmp = 0;
	private ArrayList<Pair<String, Consumer<LivingHurtEvent>>> temporaryAttackEffects = new ArrayList<>();
	private ArrayList<Pair<String, Consumer<LivingHurtEvent>>> permanentAttackEffects = new ArrayList<>();
	private ArrayList<Pair<String, Consumer<LivingHurtEvent>>> temporaryOnDamagedEffects = new ArrayList<>();
	private ArrayList<Pair<String, Consumer<LivingHurtEvent>>> permanentOnDamagedEffects = new ArrayList<>();

	public boolean canUseAbilities() {
		if(cooldown <= 0) {
			cooldown = 0;
			canUseAbilities = true;
		}
		return canUseAbilities;
	}

	public void addCooldown(int cooldown) {
		this.cooldown = (int) Math.max(0, (cooldown));
		if(this.cooldown != 0) {
			this.canUseAbilities = false;
		}
	}

	public void resetCooldown() {
		this.cooldown = 0;
		this.canUseAbilities = true;
	}

	public void copyFrom(PlayerAbilities source) {
		this.cooldown = source.cooldown;
		this.canUseAbilities = source.canUseAbilities;
		this.isCrocodileAscended = source.isCrocodileAscended;
		this.isTurtleAscended = source.isTurtleAscended;
		this.shellNum = source.shellNum;
		this.isRetracting = false;
		this.retracted = false;
		this.spinTicks = -1;
		this.giantAmp = 0;
		//this.rageArtTicks = -1;
		//this.rageArtTargetId = -1;
	}

	public void setOnStaticCooldown(){
		addCooldown(static_cooldown);
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt("cooldown", cooldown);
		nbt.putBoolean("canuseabilities", canUseAbilities);
		nbt.putBoolean("isCrocodileAscended", isCrocodileAscended);
		nbt.putBoolean("isTurtleAscended", isTurtleAscended);
		nbt.putString("lastDimension", lastDimension);
		nbt.putInt("lastX", lastPos.getX());
		nbt.putInt("lastY", lastPos.getY());
		nbt.putInt("lastZ", lastPos.getZ());
		nbt.putInt("shellNum", this.shellNum);
		nbt.putBoolean("retracting", this.isRetracting);
		nbt.putBoolean("retracted", this.retracted);
		nbt.putInt("spinTicks", this.spinTicks);
		nbt.putInt("giantAmp", this.giantAmp);
		//nbt.putInt("rageArtTicks", this.rageArtTicks);
		//nbt.putInt("rageArtTargetId", this.rageArtTargetId);
	}

	public void loadNBTData(CompoundTag nbt) {
		cooldown = nbt.getInt("cooldown");
		canUseAbilities = nbt.getBoolean("canuseabilities");
		isCrocodileAscended = nbt.getBoolean("isCrocodileAscended");
		isTurtleAscended = nbt.getBoolean("isTurtleAscended");
		lastDimension = nbt.getString("lastDimension");
		lastPos = new BlockPos(nbt.getInt("lastX"), nbt.getInt("lastY"), nbt.getInt("lastZ"));
		shellNum = nbt.getInt("shellNum");
		isRetracting = nbt.getBoolean("retracting");
		retracted = nbt.getBoolean("retracted");
		spinTicks = nbt.getInt("spinTicks");
		giantAmp = nbt.getInt("giantAmp");
		//rageArtTicks = nbt.getInt("rageArtTicks");
		//rageArtTargetId = nbt.getInt("rageArtTargetId");
	}

	public void tick(ServerPlayer player) {
		outOfCombat = Math.max(0, --outOfCombat);
		cooldown = Math.max(0, --cooldown);
		tickCount = ++tickCount % 20;
		if (cooldown == 0) {
			canUseAbilities = true;
		}
		if(tickCount % 4 == 0 && outOfCombat == 0){
			addRage(-1, player);
		}
		if(isRetracting && !retracted){
			shellRetract = Math.min(++shellRetract, 10);
			//player.setPose(Pose.SWIMMING);
			if(shellRetract == 10){
				//retractIntoShell(player);
				isRetracting = false;
				retracted = true;
			}
		}
		if(isRetracting && retracted){
			shellRetract = Math.max(0, --shellRetract);
			if(shellRetract == 0){
				isRetracting = false;
				retracted = false;
			}
		}
		if(retracted){
			//player.setPose(Pose.SWIMMING);
		}
		updateSpin(player);
		updateRageArt(player);
		updateGiantTicks(player);
		ModPackets.sendToClients(new CapSyncS2CPacket((player)));
	}

	private void updateRageArt(ServerPlayer player){
		if(rageArtTicks >= 0){
			rageArtTicks++;
			canGainRage = false;
			LivingEntity target = (LivingEntity) player.level().getEntity(this.rageArtTargetId);
			if(target == null){
				rageArtTicks = -1;
			}
			if(rageArtTicks == 1){
				this.rageArtTargetPos = target.position();
			}
			if(this.rageArtTargetPos != null && target != null && rageArtTicks < 90){
				target.teleportTo(rageArtTargetPos.x, rageArtTargetPos.y, rageArtTargetPos.z);
			}
			//if(rageArtTicks >= 2 && rageArtTicks < 15){
			//	Vec3 moveVec = new Vec3(target.getX() - player.getX(), target.getY() - player.getY(), target.getZ() - player.getZ());
			//	moveVec = moveVec.add(moveVec.x > 0 ? -(target.getBbWidth()/2 + player.getBbWidth()/2) : (target.getBbWidth()/2 + player.getBbWidth()/2), 0,
			//			moveVec.z > 0 ? -(target.getBbWidth()/2 + player.getBbWidth()/2) : (target.getBbWidth()/2 + player.getBbWidth()/2));
			//	//player.move(MoverType.SELF, moveVec.multiply((rageArtTicks)/26d, (rageArtTicks)/26d, (rageArtTicks)/26d));
			//	//player.moveTo(moveVec.multiply((rageArtTicks)/26d, (rageArtTicks)/26d, (rageArtTicks)/26d));
			//	player.setDeltaMovement(moveVec.multiply((rageArtTicks)/104d, (rageArtTicks)/104d, (rageArtTicks)/104d));
			//	player.hurtMarked = true;
			//}
			//if(rageArtTicks >= 15 && rageArtTicks < 28){
			//	Vec3 targetVec = new Vec3(target.getX() - player.getX(), (target.getY() + target.getBbHeight()) - player.getY(), target.getZ() - player.getZ());
			//	//player.move(MoverType.SELF, targetVec.multiply((rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d));
			//	//player.moveTo(targetVec.multiply((rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d, (rageArtTicks - 14)/24d));
			//	player.setDeltaMovement(targetVec.multiply((rageArtTicks - 14)/96d, (rageArtTicks - 14)/96d, (rageArtTicks - 14)/96d));
			//	player.hurtMarked = true;
			//}
			//if(rageArtTicks >= 28 && rageArtTicks < 110){
			//	player.teleportTo(target.getX(), target.getY() + target.getBbHeight(), target.getZ());
			//}
			//if(rageArtTicks > 55 && rageArtTicks < 85){
			//	player.teleportTo(target.getX(), target.getY() + target.getBbHeight() + 12.3, target.getZ());
			//}
			if(rageArtTicks == 90){
				this.canRageArtCrash = true;
			}
			if(player.onGround() && canRageArtCrash){
				float scale = ScaleTypes.BASE.getScaleData(player).getBaseScale();
				player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(7 + ((scale-1)*7))).forEach(entity -> {
					entity.hurt(ModDamageTypes.getDamageSource(ModDamageTypes.RAGE_ART, player), (20 + (float)(player.getAttributeValue(Attributes.ATTACK_DAMAGE)*2))*scale);
					//entity.knockback(0.5, entity.getX() - player.getX(), entity.getZ() - player.getZ());
					entity.addDeltaMovement(new Vec3(entity.getX() - player.getX(), 1, entity.getZ() - player.getZ()).normalize().scale(2));
				});

				float radius = 13 + (scale-1)*7;
				float angle = 0f;
				float radiusStep = radius * 0.05f;
				float angleStep = (float)Math.PI / (radius*15);

				while(angle < (Math.PI*2)){
					for(float r = radius; r > 0; r -= radiusStep){
						double velY = (Math.cos(Math.min(3*Math.PI, r) / 3.0)*5.0) + 5.0;
						double x = player.getX() + (Math.cos(angle)*r);
						double z = player.getZ() + (Math.sin(angle)*r);
						int y = Minecraft.getInstance().level.getHeight(Heightmap.Types.WORLD_SURFACE, (int)x, (int)z);

						player.serverLevel().sendParticles(ModParticles.SAND_PARTICLE.get(), x + Math.random()*0.4, y + Math.random()*(radius/2.5),
								z + Math.random()*0.4, 1, 0, 0, 0, velY / 15.0);
					}
					angle += angleStep;
				}
				//ModPackets.sendToNearbyPlayers(new AOEParticleS2CPacket(player.getX(), player.getZ(), 7 + (scale-1)*7), player.serverLevel(), player.getOnPos());
				this.canRageArtCrash = false;
			}
			if(rageArtTicks >= 172){
				this.rageArtTicks = -1;
				this.rageArtTargetId = -1;
				this.canGainRage = true;
			}
			ModPackets.sendToPlayer(new RageArtTickSyncS2CPacket(this.rageArtTicks), player);
			ModPackets.sendToClients(new RageArtCapSyncS2CPacket(this.rageArtTicks, player.getId()));
		} else {
			this.rageArtTicks = -1;
			this.rageArtTargetId = -1;
			this.canGainRage = true;
		}
	}

	public void setInCombat(){
		this.outOfCombat = 240;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	public void addAbilityHaste(int num){
		this.abilityHaste += num;
	}

	public void removeAbilityHaste(int num){
		this.abilityHaste -= num;
		if(this.abilityHaste < 0){
			this.abilityHaste = 0;
		}
	}

	public int getAbilityHaste(){
		return this.abilityHaste;
	}

	public void setSheenHit(boolean set){
		this.sheenHit = set;
	}

	public boolean isSheenHit(){
		return this.sheenHit;
	}

	public void setCrocodileAscended(boolean f){
		isCrocodileAscended = f;
	}

	public void setTurtleAscended(boolean turtleAscended) {
		isTurtleAscended = turtleAscended;
	}

	public boolean isCrocodileAscended(){
		return isCrocodileAscended;
	}

	public boolean isTurtleAscended() {
		return isTurtleAscended;
	}

	public String getLastDimension(){
		return lastDimension;
	}

	public void setLastDimension(String name){
		lastDimension = name;
	}

	public BlockPos getLastPos(){
		return lastPos;
	}

	public void setLastPos(BlockPos last){
		lastPos = last;
	}

	public void setShellNum(int num){
		shellNum = num;
	}

	public int getShellNum(){
		return shellNum;
	}

	public float getShellHealth(){
		return shellHealth;
	}

	public void damageShell(float damage){
		shellHealth = Math.max(0, shellHealth-damage);
	}

	public void calculateShellDamage(LivingHurtEvent event){
		Entity source = event.getSource().getEntity();
		Player player = (Player) event.getEntity();

		Vec3 sourcePos = source.position();
		Vec3 playerPos = player.position();
		float bodyRot = player.yBodyRot % 360;

		float angle = 180f;
		float entityHitAngle = (float) ((Math.atan2(sourcePos.z - playerPos.z, sourcePos.x - playerPos.x) * (180 / Math.PI) - 90) % 360);
		float entityRelativeAngle = entityHitAngle - bodyRot;
		if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
			damageShell(event.getAmount());
			event.setAmount(0);
		} else {
			damageShell(event.getAmount() / 2f);
			event.setAmount(event.getAmount() / 2f);
		}
	}

	public boolean isRetracting(){
		return isRetracting;
	}

	public void setIsRetracting(boolean y){
		isRetracting = y;
	}

	public boolean isRetracted(){
		return retracted;
	}

	public void retractIntoShell(Player player){
		ServerLevel level = player.getServer().getLevel(player.level().dimension());
		this.setLastDimension(player.level().dimension().toString());
		this.setLastPos(player.getOnPos().above());
		level.setChunkForced(player.chunkPosition().x, player.chunkPosition().z, true);
		level.getChunkSource().addRegionTicket(TicketType.FORCED, player.chunkPosition(), 3, player.chunkPosition());
		boolean hasAccessedBefore;
		if(this.getShellNum() == -1){
			player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel().getCapability(ShellDimCapabilityProvider.SHELL_DIM_CAPABILITY).ifPresent(shell -> {
				System.out.println(this.getShellNum());
				this.setShellNum(shell.callNewHolder());
				System.out.println(this.getShellNum());
			});
			hasAccessedBefore = false;
		} else {
			hasAccessedBefore = true;
		}

		if(!hasAccessedBefore){
			int num = this.getShellNum();
			for(int x = 1 + 1000*num; x <= 7 + 1000*num; x++){
				for(int y = 99; y < 106; y++){
					for(int z = 1; z <= 7; z++){
						if(y==99 || y==105){
							player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel().setBlockAndUpdate(new BlockPos(x, y, z), ModBlocks.SHELL_BLOCK.get().defaultBlockState());
						} else if(x % 1000 == 1 || z == 1 || x % 1000 == 7 || z == 7){
							player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel().setBlockAndUpdate(new BlockPos(x, y, z), ModBlocks.SHELL_BLOCK.get().defaultBlockState());
						}
					}
				}
			}
		}
		player.teleportTo(player.getServer().getLevel(ModDimensions.TURTLE_SHELL_SPACE_DIM).getLevel(), 4 + 1000*this.getShellNum(), 100, 4, Set.of(), player.getYHeadRot(), player.getXRot());
	}

	public void addRage(int rage, ServerPlayer player){
		if(rage > 0){
			if(canGainRage){
				this.crocodileAscendedRage = Mth.clamp(this.crocodileAscendedRage+rage, 0, 100);
				ModPackets.sendToPlayer(new CrocRageS2CPacket(this.crocodileAscendedRage), player);
			}
		} else {
			this.crocodileAscendedRage = Mth.clamp(this.crocodileAscendedRage+rage, 0, 100);
			ModPackets.sendToPlayer(new CrocRageS2CPacket(this.crocodileAscendedRage), player);
		}
	}

	public int getRage(){
		return this.crocodileAscendedRage;
	}

	public float getDamageMultiplierFromRage(){
		return ((float)this.crocodileAscendedRage)*0.003f;
	}

	public void startSpin(){
		spinTicks = 0;
	}

	public int getSpinTicks(){
		return spinTicks;
	}

	public void setSlowUUID(UUID set){
		this.spinSlow = set;
	}

	public void startRageArt(int targetId){
		this.rageArtTicks = 0;
		this.rageArtTargetId = targetId;
	}

	public int getRageArtTargetID(){
		return this.rageArtTargetId;
	}

	public int getRageArtTicks(){
		return this.rageArtTicks;
	}

	public void setRageArtTicks(int ticks){
		this.rageArtTicks = ticks;
	}

	private void updateSpin(ServerPlayer player){
		if(spinTicks >= 0){
			canGainRage = false;
			spinTicks++;
			if(spinTicks == 2){
				float scale = ScaleTypes.BASE.getScaleData(player).getBaseScale();
				player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4 + ((scale-1)*2), 0, 4 + ((scale-1)*2))).forEach(target -> {
					float angle = 140f;
					float entityHitAngle = (float) ((Math.atan2(target.getZ() - player.getZ(), target.getX() - player.getX()) * (180 / Math.PI) - 90) % 360);
					float entityAttackingAngle = player.yBodyRot % 360;
					if (entityHitAngle < 0) {
						entityHitAngle += 360;
					}
					if (entityAttackingAngle < 0) {
						entityAttackingAngle += 360;
					}
					float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
					if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
						target.hurt(player.level().damageSources().playerAttack(player), (float)player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + 4f);
						player.heal(2);
					}
				});
			}
			if(spinTicks == 13){
				float scale = ScaleTypes.BASE.getScaleData(player).getBaseScale();
				player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(4 + ((scale-1)*2), 0, 4 + ((scale-1)*2))).forEach(target -> {
					float angle = 140f;
					float entityHitAngle = (float) ((Math.atan2(target.getZ() - player.getZ(), target.getX() - player.getX()) * (180 / Math.PI) - 90) % 360);
					float entityAttackingAngle = player.yBodyRot % 360;
					if (entityHitAngle < 0) {
						entityHitAngle += 360;
					}
					if (entityAttackingAngle < 0) {
						entityAttackingAngle += 360;
					}
					float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
					if ((entityRelativeAngle <= angle && entityRelativeAngle >= -angle / 2) || (entityRelativeAngle >= 360 - angle || entityRelativeAngle <= -360 + angle / 2)) {
						target.hurt(player.level().damageSources().playerAttack(player), (float)player.getAttribute(Attributes.ATTACK_DAMAGE).getValue() + 4f);
						player.heal(2);
						target.knockback(1, -Math.sin(Math.toRadians((player.yBodyRot % 360) - 90)), Math.cos(Math.toRadians((player.yBodyRot % 360) - 90)));
					}
				});
			}
			if(spinTicks >= 25){
				spinTicks = -1;
				canGainRage = true;
				player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(this.spinSlow);
				this.spinSlow = null;
			}
		}
	}

	private void updateGiantTicks(ServerPlayer player) {
		if(player.hasEffect(ModEffects.GIANT.get())){
			int amp = player.getEffect(ModEffects.GIANT.get()).getAmplifier()+1;
			if(amp > giantAmp){
				ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() + (amp - giantAmp)*1f);
				giantAmp = amp;
			}
		} else {
			if(giantAmp > 0){
				ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() - (giantAmp*1f));
				giantAmp = 0;
			}
		}
	}

	public void addTempHitEffect(String name, Consumer<LivingHurtEvent> effect){
		temporaryAttackEffects.add(new Pair<>(name, effect));
	}

	public void addPermaHitEffect(String name, Consumer<LivingHurtEvent> effect){
		permanentAttackEffects.add(new Pair<>(name, effect));
	}

	public void addTempOnDamageEffect(String name, Consumer<LivingHurtEvent> effect){
		temporaryOnDamagedEffects.add(new Pair<>(name, effect));
	}

	public void addPermaOnDamageEffect(String name, Consumer<LivingHurtEvent> effect){
		permanentOnDamagedEffects.add(new Pair<>(name, effect));
	}

	public ArrayList<Pair<String, Consumer<LivingHurtEvent>>> getTempHitEffects() {
		return temporaryAttackEffects;
	}

	public ArrayList<Pair<String, Consumer<LivingHurtEvent>>> getPermaHitEffects() {
		return permanentAttackEffects;
	}

	public boolean removeTempHitEffect(String name){
		boolean r = false;
		for(int i = 0; i < temporaryAttackEffects.size(); i++){
			if(temporaryAttackEffects.get(i).getA().equals(name)){
				temporaryAttackEffects.remove(i);
				i--;
				r = true;
			}
		}
		return r;
	}

	public boolean removePermaHitEffect(String name){
		boolean r = false;
		for(int i = 0; i < permanentAttackEffects.size(); i++){
			if(permanentAttackEffects.get(i).getA().equals(name)){
				permanentAttackEffects.remove(i);
				i--;
				r = true;
			}
		}
		return r;
	}

	public boolean removeTempOnDamageEffect(String name){
		boolean r = false;
		for(int i = 0; i < temporaryOnDamagedEffects.size(); i++){
			if(temporaryOnDamagedEffects.get(i).getA().equals(name)){
				temporaryOnDamagedEffects.remove(i);
				i--;
				r = true;
			}
		}
		return r;
	}

	public boolean removePermaOnDamageEffect(String name){
		boolean r = false;
		for(int i = 0; i < permanentOnDamagedEffects.size(); i++){
			if(permanentOnDamagedEffects.get(i).getA().equals(name)){
				permanentOnDamagedEffects.remove(i);
				i--;
				r = true;
			}
		}
		return r;
	}

	public void applyHitEffects(LivingHurtEvent event){
		permanentAttackEffects.forEach(effect -> {
			effect.getB().accept(event);
		});
		temporaryAttackEffects.forEach(effect -> {
			effect.getB().accept(event);
		});
		temporaryAttackEffects = new ArrayList<>();
	}

	public void applyOnDamageEffects(LivingHurtEvent event){
		permanentOnDamagedEffects.forEach(effect -> {
			effect.getB().accept(event);
		});
		temporaryOnDamagedEffects.forEach(effect -> {
			effect.getB().accept(event);
		});
		temporaryOnDamagedEffects = new ArrayList<>();
	}

}
