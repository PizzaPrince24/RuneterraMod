package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.item.custom.AsheBow;
import com.pizzaprince.runeterramod.item.custom.BaccaiStaff;
import com.pizzaprince.runeterramod.item.custom.ShurimanTransfuserItem;
import com.pizzaprince.runeterramod.item.custom.SunDiskAltarItem;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;
import com.pizzaprince.runeterramod.item.custom.armor.RampagingBaccaiArmorItem;
import com.pizzaprince.runeterramod.item.custom.curios.ascension.CrocodileAscensionPendant;
import com.pizzaprince.runeterramod.item.custom.curios.ascension.TurtleAscensionPendant;
import com.pizzaprince.runeterramod.item.custom.curios.base.*;
import com.pizzaprince.runeterramod.item.custom.curios.epic.*;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RuneterraMod.MOD_ID);

	public static final RegistryObject<Item> SUN_STONE = ITEMS.register("sun_stone",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> ASHE_BOW = ITEMS.register("ashe_bow",
			() -> new AsheBow(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> ASHE_HELMET = ITEMS.register("ashe_helmet",
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

	public static final RegistryObject<Item> ASHE_CHESTPLATE = ITEMS.register("ashe_chestplate",
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

	public static final RegistryObject<Item> ASHE_LEGGINGS = ITEMS.register("ashe_leggings",
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

	public static final RegistryObject<Item> ASHE_BOOTS = ITEMS.register("ashe_boots",
			() -> new AsheArmorItem(ModArmorMaterials.ASHE_ARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> REKSAI_SPAWN_EGG = ITEMS.register("reksai_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityTypes.REKSAI, 0x1C1C1C, 0x456296, new Item.Properties()));

	public static final RegistryObject<Item> RAMPAGING_BACCAI_SPAWN_EGG = ITEMS.register("rampaging_baccai_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityTypes.RAMPAGING_BACCAI, 16750899, 16776960, new Item.Properties()));
	public static final RegistryObject<Item> SUN_DISK_ALTAR_ITEM = ITEMS.register("sun_disk_altar",
			() -> new SunDiskAltarItem(ModBlocks.SUN_DISK_ALTAR.get(), new Item.Properties()));

	public static final RegistryObject<Item> BACCAI_STAFF = ITEMS.register("baccai_staff",
			() -> new BaccaiStaff(new Item.Properties().stacksTo(1).setNoRepair()));

	public static final RegistryObject<Item> SUNFIRE_AEGIS = ITEMS.register("sunfire_aegis",
			() -> new SunfireAegis(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RYLAIS_SCEPTER = ITEMS.register("rylais_crystal_scepter",
			() -> new RylaisCrystalScepter(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> INFINITY_EDGE = ITEMS.register("infinity_edge",
			() -> new InfinityEdge(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RUNAANS_HURICANE = ITEMS.register("runaans_hurricane",
			() -> new RunaansHurricane(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> HEARTSTEEL = ITEMS.register("heartsteel",
			() -> new Heartsteel(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> BLOODTHIRSTER = ITEMS.register("bloodthirster",
			() -> new BloodThirster(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RADIANT_VIRTUE = ITEMS.register("radiant_virtue",
			() -> new RadiantVirtue(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> WARMOGS = ITEMS.register("warmogs",
			() -> new Warmogs(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> AMP_TOME = ITEMS.register("amplifying_tome",
			() -> new AmplifyingTome(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> BFSWORD = ITEMS.register("bfsword",
			() -> new BFSword(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> BLASTING_WAND = ITEMS.register("blasting_wand",
			() -> new BlastingWand(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> AGILITY_CLOAK = ITEMS.register("agility_cloak",
			() -> new AgilityCloak(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CLOTH_ARMOR = ITEMS.register("cloth_armor",
			() -> new ClothArmor(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> DAGGER = ITEMS.register("dagger",
			() -> new Dagger(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> FAERIE_CHARM = ITEMS.register("faerie_charm",
			() -> new FaerieCharm(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> LONG_SWORD = ITEMS.register("long_sword",
			() -> new LongSword(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> NEEDLESSLY_LARGE_ROD = ITEMS.register("needlessly_large_rod",
			() -> new NeedlesslyLargeRod(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> NULL_MAGIC_MANTLE = ITEMS.register("null_magic_mantle",
			() -> new NullMagicMantle(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> PICKAXE = ITEMS.register("pickaxe",
			() -> new Pickaxe(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> REJUVENATION_BEAD = ITEMS.register("rejuvenation_bead",
			() -> new RejuvenationBead(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RUBY_CRYSTAL = ITEMS.register("ruby_crystal",
			() -> new RubyCrystal(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SAPPHIRE_CRYSTAL = ITEMS.register("sapphire_crystal",
			() -> new SapphireCrystal(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SHEEN = ITEMS.register("sheen",
			() -> new Sheen(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CACTUS_JUICE = ITEMS.register("cactus_juice",
			() -> new Item(new Item.Properties().stacksTo(64).food(
					new FoodProperties.Builder().saturationMod(7).nutrition(6)
							.effect(() -> new MobEffectInstance(ModEffects.QUENCHED.get(), 90*20, 0, false, true, true), 1)
							.effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 90*20, 1, false, false, false), 1)
							.effect(() -> new MobEffectInstance(MobEffects.LUCK, 90*20, 2, false, false, false), 1)
							.alwaysEat().build()
			)));

	public static final RegistryObject<Item> SHURIMAN_TRANSFUSER_ITEM = ITEMS.register("shuriman_transfuser",
			() -> new ShurimanTransfuserItem(ModBlocks.SHURIMAN_ITEM_TRANSFUSER.get(), new Item.Properties()));

	public static final RegistryObject<Item> NEXUS_CRYSTAL = ITEMS.register("nexus_crystal",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> BAMIS_CINDER = ITEMS.register("bamis_cinder",
			() -> new BamisCinder(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CHAIN_VEST = ITEMS.register("chain_vest",
			() -> new ChainVest(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> GIANTS_BELT = ITEMS.register("giants_belt",
			() -> new GiantsBelt(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RECURVE_BOW = ITEMS.register("recurve_bow",
			() -> new RecurveBow(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> ZEAL = ITEMS.register("zeal",
			() -> new Zeal(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CRYSTALLINE_BRACER = ITEMS.register("crystalline_bracer",
			() -> new CrystallineBracer(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> KINDLEGEM = ITEMS.register("kindlegem",
			() -> new Kindlegem(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> VAMPIRIC_SCEPTER = ITEMS.register("vampiric_scepter",
			() -> new VampiricScepter(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> AEGIS_OF_THE_LEGION = ITEMS.register("aegis_of_the_legion",
			() -> new AegisOfTheLegion(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SUNFISH_BUCKET = ITEMS.register("sunfish_bucket",
			() -> new MobBucketItem(() -> ModEntityTypes.SUNFISH.get(), () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SUNFISH_SPAWN_EGG = ITEMS.register("sunfish_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityTypes.SUNFISH, 7434350, 14211288, new Item.Properties()));

	public static final RegistryObject<Item> RAMPAGING_BACCAI_ARMOR = ITEMS.register("rampaging_baccai_armor",
			() -> new RampagingBaccaiArmorItem(ModArmorMaterials.RAMPAGING_BACCAI_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CROCODILE_ASCENSION_PENDANT = ITEMS.register("crocodile_ascension_pendant",
			() -> new CrocodileAscensionPendant(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> TURTLE_ASCENSION_PENDANT = ITEMS.register("turtle_ascension_pendant",
			() -> new TurtleAscensionPendant(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> IRON_ELIXIR = ITEMS.register("iron_elixir",
			() -> new Item(new Item.Properties().stacksTo(1).food(
					new FoodProperties.Builder().saturationMod(0).nutrition(0)
							.effect(() -> new MobEffectInstance(ModEffects.GIANT.get(), 36000, 1, true, true, true), 1)
							.alwaysEat().build()
			)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
