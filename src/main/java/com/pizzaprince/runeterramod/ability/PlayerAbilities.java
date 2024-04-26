package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import com.pizzaprince.runeterramod.ability.ascendent.BaseAscendant;
import com.pizzaprince.runeterramod.ability.ascendent.CrocodileAscendant;
import com.pizzaprince.runeterramod.ability.ascendent.TurtleAscendant;
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
	private int static_cooldown = 10*20;
	private int outOfCombat = 0;
	private int tickCount = 0;
	private int giantAmp = 0;
	private BaseAscendant ascendant;
	private AscendantType ascendantType = AscendantType.NONE;
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
		this.giantAmp = 0;
		this.ascendantType = source.ascendantType;
		this.ascendant = source.ascendant;
	}

	public void setOnStaticCooldown(){
		addCooldown(static_cooldown);
	}

	public void saveNBTData(CompoundTag nbt) {
		nbt.putInt("cooldown", cooldown);
		nbt.putBoolean("canuseabilities", canUseAbilities);
		nbt.putInt("giantAmp", this.giantAmp);
		nbt.putInt("ascendantType", this.ascendantType.ordinal());
		if(ascendant != null) ascendant.saveNBTData(nbt);
	}

	public void loadNBTData(CompoundTag nbt) {
		cooldown = nbt.getInt("cooldown");
		canUseAbilities = nbt.getBoolean("canuseabilities");
		giantAmp = nbt.getInt("giantAmp");
		ascendantType = AscendantType.fromInt(nbt.getInt("ascendantType"));
		switch (ascendantType){
			case NONE -> ascendant = null;
			case CROCODILE -> ascendant = new CrocodileAscendant();
			case TURTLE -> ascendant = new TurtleAscendant();
		}
		if(ascendant != null) ascendant.loadNBTData(nbt);
	}

	public void tick(ServerPlayer player) {
		outOfCombat = Math.max(0, --outOfCombat);
		cooldown = Math.max(0, --cooldown);
		tickCount = ++tickCount % 20;
		if (ascendant != null) {
			ascendant.setTickCount(tickCount);
			ascendant.setOutOfCombat(outOfCombat);
			ascendant.tick(player);
		}
		if (cooldown == 0) {
			canUseAbilities = true;
		}
		updateGiantTicks(player);
		ModPackets.sendToClients(new CapSyncS2CPacket((player)));
	}

	public BaseAscendant getAscendant(){
		return ascendant;
	}

	public AscendantType getAscendantType(){
		return ascendantType;
	}

	public void ascend(Player player, AscendantType type){
		if(type == ascendantType){
			ascendant.onAscend(player);
			return;
		}
		if(ascendantType != AscendantType.NONE) descend(player);
		switch (type){
			case CROCODILE -> ascendant = new CrocodileAscendant();
			case TURTLE -> ascendant = new TurtleAscendant();
		}
		ascendant.onAscend(player);
		ascendantType = type;
	}

	public void descend(Player player){
		ascendant.onDescend(player);
		ascendant = null;
		ascendantType = AscendantType.NONE;
	}

	public void setInCombat(){
		this.outOfCombat = 240;
	}

	public int getCooldown() {
		return this.cooldown;
	}

	private void updateGiantTicks(ServerPlayer player) {
		if(player.hasEffect(ModEffects.GIANT.get())){
			int amp = player.getEffect(ModEffects.GIANT.get()).getAmplifier()+1;
			if(amp > giantAmp){
				ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() + (amp - giantAmp)*0.5f);
				giantAmp = amp;
			}
		} else {
			if(giantAmp > 0){
				ScaleTypes.BASE.getScaleData(player).setTargetScale(ScaleTypes.BASE.getScaleData(player).getTargetScale() - (giantAmp*0.5f));
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
