package com.pizzaprince.runeterramod.effect;

import com.pizzaprince.runeterramod.RuneterraMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, RuneterraMod.MOD_ID);

    public static final RegistryObject<Attribute> ABILITY_POWER = ATTRIBUTES.register("ability_power",
            () -> new RangedAttribute("runeterramod:ability_power", 0.0, 0.0, 5096.0).setSyncable(true));

    public static final RegistryObject<Attribute> MAGIC_RESIST = ATTRIBUTES.register("magic_resist",
            () -> new RangedAttribute("runeterramod:magic_resist", 0.0, 0.0, 5096.0).setSyncable(true));

    public static final RegistryObject<Attribute> ABILITY_HASTE = ATTRIBUTES.register("ability_haste",
            () -> new RangedAttribute("runeterramod:ability_haste", 0.0, 0.0, 5096.0).setSyncable(true));

    public static final RegistryObject<Attribute> TENACITY = ATTRIBUTES.register("tenacity",
            () -> new RangedAttribute("runeterramod:tenacity", 0.0, 0.0, 1.0).setSyncable(true));

    public static final RegistryObject<Attribute> OMNIVAMP = ATTRIBUTES.register("omnivamp",
            () -> new RangedAttribute("runeterramod:omnivamp", 0.0, 0.0, 1.0).setSyncable(true));

    public static final RegistryObject<Attribute> LETHALITY = ATTRIBUTES.register("lethality",
            () -> new RangedAttribute("runeterramod:lethality", 0.0, 0.0, 5096.0).setSyncable(true));

    public static final RegistryObject<Attribute> MAGIC_PENETRATION = ATTRIBUTES.register("magic_penetration",
            () -> new RangedAttribute("runeterramod:magic_penetration", 0.0, 0.0, 5096.0).setSyncable(true));

    public static final RegistryObject<Attribute> CRIT_CHANCE = ATTRIBUTES.register("crit_chance",
            () -> new RangedAttribute("runeterramod:crit_chance", 0.0, 0.0, 1.0).setSyncable(true));

    public static final RegistryObject<Attribute> CRIT_DAMAGE = ATTRIBUTES.register("crit_damage",
            () -> new RangedAttribute("runeterramod:crit_damage", 1.5, 0.0, 5096.0).setSyncable(true));

    public static void register(IEventBus eventBus){
        ATTRIBUTES.register(eventBus);
    }
}
