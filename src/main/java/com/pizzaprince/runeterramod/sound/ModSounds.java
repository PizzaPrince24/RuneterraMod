package com.pizzaprince.runeterramod.sound;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RuneterraMod.MOD_ID);
    public static final RegistryObject<SoundEvent> BACCAI_GRUNT = registerSoundEvent("baccai_grunt");
    public static final RegistryObject<SoundEvent> BACCAI_NO_MERCY = registerSoundEvent("baccai_no_mercy");
    public static final RegistryObject<SoundEvent> BACCAI_ROAR = registerSoundEvent("baccai_roar");
    public static final RegistryObject<SoundEvent> BACCAI_ATTACK = registerSoundEvent("baccai_attack");
    public static final RegistryObject<SoundEvent> BACCAI_PENANCE = registerSoundEvent("baccai_penance");
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(RuneterraMod.MOD_ID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }
    public static void register(IEventBus eventBus){
        SOUNDS.register(eventBus);
    }
}
