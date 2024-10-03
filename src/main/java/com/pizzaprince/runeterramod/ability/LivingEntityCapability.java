package com.pizzaprince.runeterramod.ability;

import com.pizzaprince.runeterramod.effect.ModEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import virtuoel.pehkui.api.ScaleTypes;

public class LivingEntityCapability {

    private int giantAmp = 0;
    private int dwarfAmp = 0;

    public void tick(LivingEntity entity) {
        updateGiantTicks(entity);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("giantAmp", this.giantAmp);
        nbt.putInt("dwarfAmp", this.dwarfAmp);
    }

    public void loadNBTData(CompoundTag nbt) {
        giantAmp = nbt.getInt("giantAmp");
        dwarfAmp = nbt.getInt("dwarfAmp");
    }

    private void updateGiantTicks(LivingEntity entity) {
        if(entity.hasEffect(ModEffects.DWARF.get())){
            int amp = entity.getEffect(ModEffects.DWARF.get()).getAmplifier()+1;
            if(amp > dwarfAmp){
                ScaleTypes.BASE.getScaleData(entity).setTargetScale(ScaleTypes.BASE.getScaleData(entity).getTargetScale() * (float)Math.pow(0.75, amp - dwarfAmp));
                dwarfAmp = amp;
            }
        } else {
            if(dwarfAmp > 0){
                ScaleTypes.BASE.getScaleData(entity).setTargetScale(ScaleTypes.BASE.getScaleData(entity).getTargetScale() / (float)Math.pow(0.75f, dwarfAmp));
                dwarfAmp = 0;
            }
        }
        if(entity.hasEffect(ModEffects.GIANT.get())){
            int amp = entity.getEffect(ModEffects.GIANT.get()).getAmplifier()+1;
            if(amp > giantAmp){
                ScaleTypes.BASE.getScaleData(entity).setTargetScale(ScaleTypes.BASE.getScaleData(entity).getTargetScale() + (amp - giantAmp)*0.5f);
                giantAmp = amp;
            }
        } else {
            if(giantAmp > 0){
                ScaleTypes.BASE.getScaleData(entity).setTargetScale(ScaleTypes.BASE.getScaleData(entity).getTargetScale() - (giantAmp*0.5f));
                giantAmp = 0;
            }
        }
    }
}
