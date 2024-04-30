package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = RuneterraMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RuneterraMod.MOD_ID);

	public static RegistryObject<CreativeModeTab> RUNETERRA_TAB = CREATIVE_MODE_TABS.register("runeterra_tab",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SUN_STONE.get())).title(Component.translatable("itemGroup.runeterratab")).build());

	public static void register(IEventBus eventBus){
		CREATIVE_MODE_TABS.register(eventBus);
	}

	public static void addCreativeItems(BuildCreativeModeTabContentsEvent event){
		if(event.getTab() == ModCreativeModeTab.RUNETERRA_TAB.get()){
			//items
			event.accept(ModItems.SUN_STONE);
			event.accept(ModItems.PURIFIED_SUN_STONE);
			event.accept(ModItems.PURIFIED_SUN_STONE_DUST);
			event.accept(ModItems.ASHE_BOW);
			event.accept(ModItems.ASHE_HELMET);
			event.accept(ModItems.ASHE_CHESTPLATE);
			event.accept(ModItems.ASHE_LEGGINGS);
			event.accept(ModItems.ASHE_BOOTS);
			event.accept(ModItems.REKSAI_SPAWN_EGG);
			event.accept(ModItems.SUN_DISK_ALTAR_ITEM);
			event.accept(ModItems.SUN_FORGE_ITEM);
			event.accept(ModItems.BACCAI_STAFF);
			event.accept(ModItems.CACTUS_JUICE);
			event.accept(ModItems.RAMPAGING_BACCAI_SPAWN_EGG);
			event.accept(ModItems.SUNFIRE_AEGIS);
			event.accept(ModItems.RYLAIS_SCEPTER);
			event.accept(ModItems.INFINITY_EDGE);
			event.accept(ModItems.RUNAANS_HURICANE);
			event.accept(ModItems.HEARTSTEEL);
			event.accept(ModItems.BLOODTHIRSTER);
			event.accept(ModItems.JEWELED_GAUNTLET);
			event.accept(ModItems.WARMOGS);
			event.accept(ModItems.AMP_TOME);
			event.accept(ModItems.BFSWORD);
			event.accept(ModItems.BLASTING_WAND);
			event.accept(ModItems.AGILITY_CLOAK);
			event.accept(ModItems.CLOTH_ARMOR);
			event.accept(ModItems.DAGGER);
			event.accept(ModItems.LONG_SWORD);
			event.accept(ModItems.NEEDLESSLY_LARGE_ROD);
			event.accept(ModItems.NULL_MAGIC_MANTLE);
			event.accept(ModItems.PICKAXE);
			event.accept(ModItems.JAKSHO);
			event.accept(ModItems.REJUVENATION_BEAD);
			event.accept(ModItems.RUBY_CRYSTAL);
			event.accept(ModItems.GLOWING_MOTE);
			event.accept(ModItems.SHEEN);
			event.accept(ModItems.SHURIMAN_TRANSFUSER_ITEM);
			event.accept(ModItems.NEXUS_CRYSTAL);
			event.accept(ModItems.ROD_OF_AGES);
			event.accept(ModItems.ECLIPSE);
			event.accept(ModItems.OPPORTUNITY);
			event.accept(ModItems.VOLTAIC_CYCLOSWORD);
			event.accept(ModItems.COSMIC_DRIVE);
			event.accept(ModItems.GUINSOOS_RAGEBLADE);
			event.accept(ModItems.SERYLDAS_GRUDGE);
			event.accept(ModItems.BAMIS_CINDER);
			event.accept(ModItems.CHAIN_VEST);
			event.accept(ModItems.NOONQUIVER);
			event.accept(ModItems.LOST_CHAPTER);
			event.accept(ModItems.SERRATED_DIRK);
			event.accept(ModItems.GIANT_SLAYER);
			event.accept(ModItems.GIANTS_BELT);
			event.accept(ModItems.RECURVE_BOW);
			event.accept(ModItems.DEATHBLADE);
			event.accept(ModItems.RABADONS_DEATHCAP);
			event.accept(ModItems.ZEAL);
			event.accept(ModItems.CRYSTALLINE_BRACER);
			event.accept(ModItems.KINDLEGEM);
			event.accept(ModItems.VAMPIRIC_SCEPTER);
			event.accept(ModItems.SUNFISH_SPAWN_EGG);
			event.accept(ModItems.RAMPAGING_BACCAI_ARMOR);
			event.accept(ModItems.CROCODILE_ASCENSION_PENDANT);
			event.accept(ModItems.TURTLE_ASCENSION_PENDANT);
			event.accept(ModItems.IRON_ELIXIR);
			event.accept(ModItems.RENEKTON_SPAWN_EGG);
			event.accept(ModItems.SUN_STONE_SPEAR);
			event.accept(ModItems.INFUSED_SUN_STONE_SPEAR);
			event.accept(ModItems.PURIFIED_SUN_STONE_SPEAR);
			event.accept(ModItems.EAGLE_ASCENSION_PENDANT);

			//blocks
			event.accept(ModBlocks.SUN_STONE_BLOCK);
			event.accept(ModBlocks.SUN_STONE_ORE);
			event.accept(ModBlocks.SHURIMAN_SAND);
			event.accept(ModBlocks.SHURIMAN_SANDSTONE);
			event.accept(ModBlocks.SUN_DISK_SHARD);
			event.accept(ModBlocks.SUN_DISK_ALTAR);
			event.accept(ModBlocks.SUN_DISK_BLOCK);
			event.accept(ModBlocks.SHURIMAN_CACTUS);
			event.accept(ModBlocks.SHURIMAN_SANDSTONE_STAIRS);
			event.accept(ModBlocks.SMOOTH_SHURIMAN_SANDSTONE);
			event.accept(ModBlocks.SMOOTH_SHURIMAN_SANDSTONE_STAIRS);
			event.accept(ModBlocks.SMOOTH_SHURIMAN_SANDSTONE_SLAB);
			event.accept(ModBlocks.FANCY_GOBLET);
			event.accept(ModBlocks.BASIC_GOBLET);
			event.accept(ModBlocks.SHELL_BLOCK);
		}
	}

}
