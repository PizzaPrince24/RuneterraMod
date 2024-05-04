package com.pizzaprince.runeterramod.ability.ascendent;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.effect.ModAttributes;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.util.CustomPoisonEffect;
import com.pizzaprince.runeterramod.util.WaypointNBTEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ScorpionAscendant extends BaseAscendant{

    private static AttributeModifier HEALTH = new AttributeModifier("scorpion_ascendant_health",
            20, AttributeModifier.Operation.ADDITION);
    private static AttributeModifier AP = new AttributeModifier("scorpion_ascendant_ability_power",
            10, AttributeModifier.Operation.ADDITION);
    private int venom = 1000;
    private int maxVenom = 1000;
    private boolean freezeGun = false;
    private int entityID = -1;
    private float initialDist = -1;
    private ArrayList<CustomPoisonEffect> customPoisonEffects = new ArrayList<>();
    private int selectedPoison = 0;
    private double ap = 0;
    @Override
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("venom", venom);
        for(int i = 0; i < customPoisonEffects.size(); i++){
            CompoundTag tag = new CompoundTag();
            CustomPoisonEffect effect = customPoisonEffects.get(i);
            tag.putString("name", effect.getName());
            tag.putInt("length", effect.getEffects().length);
            for(int j = 0; j < effect.getEffects().length; j++){
                tag.putString(""+j+"a", effect.getEffects()[j].getNamespace());
                tag.putString(""+j+"b", effect.getEffects()[j].getPath());
            }
            nbt.put("" + i, tag);
        }
        nbt.putInt("selectedPoison", selectedPoison);
    }
    @Override
    public void loadNBTData(CompoundTag nbt) {
        venom = nbt.getInt("venom");
        int i = 0;
        CompoundTag tag = nbt.getCompound("" + i);
        while(!tag.isEmpty()){
            String name = tag.getString("name");
            ResourceLocation[] effects = new ResourceLocation[tag.getInt("length")];
            for(int j = 0; j < tag.getInt("length"); j++){
                effects[j] = new ResourceLocation(tag.getString(""+j+"a"), tag.getString(""+j+"b"));
            }
            customPoisonEffects.add(new CustomPoisonEffect(effects, name));
            i++;
            tag = nbt.getCompound("" + i);
        }
        selectedPoison = nbt.getInt("selectedPoison");
    }

    @Override
    public void tick(ServerPlayer player) {
        addVenom(100);
        if(player.level().getEntity(entityID) != null && player.level().getEntity(entityID) instanceof LivingEntity entity){
            entity.addEffect(new MobEffectInstance(ModEffects.STUN.get(), 5, 0, false, false, false));
            updateFrozenEntityMovement(player, entity);
            addVenom(-10);
            if(venom == 0 || player.isCrouching()){
                stopFreeze();
            }
        } else {
            stopFreeze();
        }
        this.ap = player.getAttributeValue(ModAttributes.ABILITY_POWER.get());
    }

    private void updateFrozenEntityMovement(ServerPlayer player, LivingEntity entity){
        if(initialDist == -1) initialDist = player.distanceTo(entity);
        Vec3 target = player.position().add(player.getLookAngle().normalize().scale(initialDist).add(0, player.getEyeHeight() - entity.getBbHeight()/2, 0));
        Vec3 origin = entity.position();
        entity.setDeltaMovement(target.subtract(origin));
    }

    public void addVenom(int toAdd){
        venom = Mth.clamp(venom+toAdd, 0, maxVenom);
    }

    public int getVenom(){
        return venom;
    }

    public int venomForSelectedPoison(){
        CustomPoisonEffect effect = getSelectedPoisonEffect();
        int count = 0;
        for(ResourceLocation l : effect.getEffects()){
            if(l != null) count++;
        }
        count = Math.min((int)(ap/5d), count);
        return count*10;
    }

    public void applySelectedPoisonEffectsToEntity(LivingEntity entity){
        getMobEffectsFromSelected().forEach(entity::addEffect);
    }

    private List<MobEffectInstance> getMobEffectsFromSelected(){
        ArrayList<MobEffectInstance> effects = new ArrayList<>();
        CustomPoisonEffect effect = getSelectedPoisonEffect();
        MobEffect selectedEffect = null;
        int durationCount = 0;
        int amplifierCount = 0;
        for(int i = 0; i < Math.min((int)(ap/5d), effect.getEffects().length); i++){
            if(effect.getEffects()[i] != null){
                if(!effect.getEffects()[i].equals(CustomPoisonEffect.LENGTHEN) && !effect.getEffects()[i].equals(CustomPoisonEffect.AMP) && !effect.getEffects()[i].equals(CustomPoisonEffect.EMPTY)) {
                    if(selectedEffect != null){
                        effects.add(new MobEffectInstance(selectedEffect, (int)(40*Math.pow(2, durationCount)), amplifierCount));
                        selectedEffect = ForgeRegistries.MOB_EFFECTS.getValue(effect.getEffects()[i]);
                        durationCount = 0;
                        amplifierCount = 0;
                    } else {
                        selectedEffect = ForgeRegistries.MOB_EFFECTS.getValue(effect.getEffects()[i]);
                    }
                }
                if(effect.getEffects()[i].equals(CustomPoisonEffect.AMP)){
                    amplifierCount++;
                }
                if(effect.getEffects()[i].equals(CustomPoisonEffect.LENGTHEN)){
                    durationCount++;
                }
            }
        }
        if(selectedEffect != null) effects.add(new MobEffectInstance(selectedEffect, (int)(40*Math.pow(2, durationCount)), amplifierCount));
        return effects;
    }

    public void startFreeze(int entityID){
        freezeGun = true;
        this.entityID = entityID;
    }

    public void addCustomPoisonEffect(CustomPoisonEffect effect){
        for(int i = 0; i < customPoisonEffects.size(); i++){
            if(customPoisonEffects.get(i).getName().equals(effect.getName())){
                customPoisonEffects.set(i, effect);
                return;
            }
        }
        customPoisonEffects.add(effect);
    }

    public void makePotionFromSelected(ServerPlayer player){
        ItemStack bottleItem = new ItemStack(Items.POTION);
        PotionUtils.setCustomEffects(bottleItem, getMobEffectsFromSelected());
        bottleItem.getTag().putInt("CustomPotionColor", 6950317);
        if(!player.addItem(bottleItem)){
            player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), bottleItem));
        }
        addVenom(-venomForSelectedPoison());
    }

    public CustomPoisonEffect getSelectedPoisonEffect(){
        return customPoisonEffects.get(selectedPoison);
    }

    public void removeCustomPoisonEffect(String name){
        for(int i = 0; i < customPoisonEffects.size(); i++){
            if(customPoisonEffects.get(i).getName().equals(name)){
                customPoisonEffects.remove(i);
                if(selectedPoison >= i){
                    setSelectedPoison(selectedPoison-1);
                }
                return;
            }
        }
    }

    public void setSelectedPoison(int index){
        if(index < 0 || index >= customPoisonEffects.size()) selectedPoison = 0;
        else selectedPoison = index;
    }

    public List<CustomPoisonEffect> getAllCustomPoisonEffects(){
        return customPoisonEffects;
    }

    public CustomPoisonEffect getPoisonEffectByName(String name){
        for(int i = 0; i < customPoisonEffects.size(); i++){
            if(customPoisonEffects.get(i).getName().equals(name)){
                return customPoisonEffects.get(i);
            }
        }
        return null;
    }

    public void stopFreeze(){
        freezeGun = false;
        this.entityID = -1;
        this.initialDist = -1;
    }

    @Override
    public void onAscend(Player player) {
        if(!player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(HEALTH);
            player.heal((float)HEALTH.getAmount());
        }
        if(!player.getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(AP)){
            player.getAttribute(ModAttributes.ABILITY_POWER.get()).addTransientModifier(AP);
        }
    }

    @Override
    public void onDescend(Player player) {
        if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(HEALTH)) {
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(HEALTH);
        }
        if(player.getAttribute(ModAttributes.ABILITY_POWER.get()).hasModifier(AP)) {
            player.getAttribute(ModAttributes.ABILITY_POWER.get()).removeModifier(AP);
        }
    }
}
