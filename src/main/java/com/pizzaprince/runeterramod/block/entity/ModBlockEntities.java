package com.pizzaprince.runeterramod.block.entity;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.block.entity.custom.SunDiskAltarEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RuneterraMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<SunDiskAltarEntity>> SUN_DISK_ALTAR_ENTITY = BLOCK_ENTITIES.register("animated_sun_disk_altar",
            () -> BlockEntityType.Builder.of(SunDiskAltarEntity::new, ModBlocks.SUN_DISK_ALTAR.get()).build(null));
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
