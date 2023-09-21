package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.networking.ModPackets;
import com.pizzaprince.runeterramod.networking.packet.CapSyncS2CPacket;
import com.pizzaprince.runeterramod.world.dimension.ModDimensions;
import com.pizzaprince.runeterramod.world.dimension.ShellDimCapabilityProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Set;
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

	private boolean isTurtleAscended = false;

	private String lastDimension = Level.OVERWORLD.toString();

	private BlockPos lastPos = new BlockPos(0, 0, 0);

	private int shellNum = -1;

	private float shellHealth = 500f;

	private boolean isRetracting = false;

	private boolean retracted = false;

	private int shellRetract = 0;

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
	}

	public void tick(Player player) {
		outOfCombat = Math.max(0, --outOfCombat);
		cooldown = Math.max(0, --cooldown);
		tickCount = ++tickCount % 20;
		if (cooldown == 0) {
			canUseAbilities = true;
		}
		if(tickCount % 2 == 0 && outOfCombat == 0){
			addRage(-1);
		}
		if(isRetracting && !retracted){
			shellRetract = Math.min(++shellRetract, 10);
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
	}

	public void setInCombat(){
		this.outOfCombat = 160;
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

	public void addRage(int rage){
		this.crocodileAscendedRage = Mth.clamp(rage, 0, 100);
	}

	public int getRage(){
		return this.crocodileAscendedRage;
	}

	public float getDamageMultiplierFromRage(){
		return ((float)this.crocodileAscendedRage)*0.003f;
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
