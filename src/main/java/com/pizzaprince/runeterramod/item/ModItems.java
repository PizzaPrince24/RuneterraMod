package com.pizzaprince.runeterramod.item;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.curios.HeartsteelCapabilityProvider;
import com.pizzaprince.runeterramod.ability.curios.ImmolationCapabilityProvider;
import com.pizzaprince.runeterramod.ability.curios.RegenerationCapabilityProvider;
import com.pizzaprince.runeterramod.block.ModBlocks;
import com.pizzaprince.runeterramod.effect.ModEffects;
import com.pizzaprince.runeterramod.entity.ModEntityTypes;
import com.pizzaprince.runeterramod.item.custom.*;
import com.pizzaprince.runeterramod.item.custom.armor.AsheArmorItem;
import com.pizzaprince.runeterramod.item.custom.armor.RampagingBaccaiArmorItem;
import com.pizzaprince.runeterramod.item.custom.curios.ModCurioItem;
import com.pizzaprince.runeterramod.item.custom.curios.ModCurioItemAbilities;
import com.pizzaprince.runeterramod.item.custom.curios.ModCurioItemStats;
import com.pizzaprince.runeterramod.item.custom.curios.ascension.CrocodileAscensionPendant;
import com.pizzaprince.runeterramod.item.custom.curios.ascension.EagleAscensionPendant;
import com.pizzaprince.runeterramod.item.custom.curios.ascension.ScorpionAscensionPendant;
import com.pizzaprince.runeterramod.item.custom.curios.ascension.TurtleAscensionPendant;
import com.pizzaprince.runeterramod.item.custom.curios.base.*;
import com.pizzaprince.runeterramod.item.custom.curios.legendary.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RuneterraMod.MOD_ID);








	//------------------------------------NON CURIO ITEMS-------------------------------------------------
	public static final RegistryObject<Item> SUN_STONE = ITEMS.register("sun_stone",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> PURIFIED_SUN_STONE = ITEMS.register("purified_sun_stone",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> PURIFIED_SUN_STONE_DUST = ITEMS.register("purified_sun_stone_dust",
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

	public static final RegistryObject<Item> SUN_FORGE_ITEM = ITEMS.register("sun_forge",
			() -> new SunForgeItem(ModBlocks.SUN_FORGE.get(), new Item.Properties()));

	public static final RegistryObject<Item> BACCAI_STAFF = ITEMS.register("baccai_staff",
			() -> new BaccaiStaff(new Item.Properties().stacksTo(1).setNoRepair()));

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

	public static final RegistryObject<Item> EAGLE_ASCENSION_PENDANT = ITEMS.register("eagle_ascension_pendant",
			() -> new EagleAscensionPendant(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SCORPION_ASCENSION_PENDANT = ITEMS.register("scorpion_ascension_pendant",
			() -> new ScorpionAscensionPendant(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RENEKTON_SPAWN_EGG = ITEMS.register("renekton_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntityTypes.RENEKTON, 78368, 40778, new Item.Properties()));

	public static final RegistryObject<Item> SUN_STONE_SPEAR = ITEMS.register("sun_stone_spear",
			() -> new SunStoneSpear(ModToolTiers.SUN_STONE, 3, -2f, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> INFUSED_SUN_STONE_SPEAR = ITEMS.register("infused_sun_stone_spear",
			() -> new SunStoneSpear(ModToolTiers.INFUSED_SUN_STONE, 3, -2f, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> PURIFIED_SUN_STONE_SPEAR = ITEMS.register("purified_sun_stone_spear",
			() -> new SunStoneSpear(ModToolTiers.PURIFIED_SUN_STONE, 3, -2f, new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SCORPION_CHARM = ITEMS.register("scorpion_ascendant_effect_charm",
			() -> new ScorpionCharm(new Item.Properties().stacksTo(1)));









	//------------------------------------------BASIC CURIO ITEMS--------------------------------------------------
	public static final RegistryObject<Item> AMP_TOME = ITEMS.register("amplifying_tome",
			() -> new ModCurioItem(ModCurioItemStats.AMP_TOME));

	public static final RegistryObject<Item> BFSWORD = ITEMS.register("bfsword",
			() -> new ModCurioItem(ModCurioItemStats.BFSWORD));

	public static final RegistryObject<Item> BLASTING_WAND = ITEMS.register("blasting_wand",
			() -> new ModCurioItem(ModCurioItemStats.BLASTING_WAND));

	public static final RegistryObject<Item> AGILITY_CLOAK = ITEMS.register("agility_cloak",
			() -> new ModCurioItem(ModCurioItemStats.AGILITY_CLOAK));

	public static final RegistryObject<Item> CLOTH_ARMOR = ITEMS.register("cloth_armor",
			() -> new ModCurioItem(ModCurioItemStats.CLOTH_ARMOR));

	public static final RegistryObject<Item> DAGGER = ITEMS.register("dagger",
			() -> new ModCurioItem(ModCurioItemStats.DAGGER));

	public static final RegistryObject<Item> LONG_SWORD = ITEMS.register("long_sword",
			() -> new ModCurioItem(ModCurioItemStats.LONG_SWORD));

	public static final RegistryObject<Item> NEEDLESSLY_LARGE_ROD = ITEMS.register("needlessly_large_rod",
			() -> new ModCurioItem(ModCurioItemStats.NEEDLESSLY_LARGE_ROD));

	public static final RegistryObject<Item> NULL_MAGIC_MANTLE = ITEMS.register("null_magic_mantle",
			() -> new ModCurioItem(ModCurioItemStats.NULL_MAGIC_MANTLE));

	public static final RegistryObject<Item> PICKAXE = ITEMS.register("pickaxe",
			() -> new ModCurioItem(ModCurioItemStats.PICKAXE));

	public static final RegistryObject<Item> REJUVENATION_BEAD = ITEMS.register("rejuvenation_bead",
			() -> new ModCurioItem(ModCurioItemStats.EMPTY){
				@Override
				public void curioTick(SlotContext slotContext, ItemStack stack) {
					stack.getCapability(RegenerationCapabilityProvider.REGENERATION_CAPABILITY).ifPresent(cap -> {
						cap.tick(slotContext.entity());
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pTooltipComponents.add(Component.literal("Regenerate 1 health every 10 seconds").withStyle(ChatFormatting.RED));
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});

	public static final RegistryObject<Item> GLOWING_MOTE = ITEMS.register("glowing_mote",
			() -> new ModCurioItem(ModCurioItemStats.GLOWING_MOTE));

	public static final RegistryObject<Item> RUBY_CRYSTAL = ITEMS.register("ruby_crystal",
			() -> new ModCurioItem(ModCurioItemStats.RUBY_CRYSTAL));

	public static final RegistryObject<Item> SHEEN = ITEMS.register("sheen",
			() -> new Sheen(new Item.Properties().stacksTo(1)));










	//-------------------------------------EPIC CURIO ITEMS---------------------------------------------
	public static final RegistryObject<Item> BAMIS_CINDER = ITEMS.register("bamis_cinder",
			() -> new ModCurioItem(ModCurioItemStats.BAMIS_CINDER){
				@Override
				public void curioTick(SlotContext slotContext, ItemStack stack) {
					stack.getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(cap -> {
						cap.tick(slotContext.entity());
					});
				}
				@Override
				public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.addPermaHitEffect("bamis_cinder_burn", ModCurioItemAbilities.IMMOLATION_HIT_EFFECT);
						cap.addPermaOnDamageEffect("bamis_cinder_burn", ModCurioItemAbilities.IMMOLATION_HIT_EFFECT);
					});
				}
				@Override
				public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.removePermaHitEffect("bamis_cinder_burn");
						cap.removePermaOnDamageEffect("bamis_cinder_burn");
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pTooltipComponents.add(Component.literal("Taking or dealing any damage will cause enemies around you to burn for 1 magic damage every second for 3 seconds.").withStyle(ChatFormatting.RED));
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});

	public static final RegistryObject<Item> CHAIN_VEST = ITEMS.register("chain_vest",
			() -> new ModCurioItem(ModCurioItemStats.CHAIN_VEST));

	public static final RegistryObject<Item> GIANTS_BELT = ITEMS.register("giants_belt",
			() -> new ModCurioItem(ModCurioItemStats.GIANTS_BELT));

	public static final RegistryObject<Item> RECURVE_BOW = ITEMS.register("recurve_bow",
			() -> new ModCurioItem(ModCurioItemStats.RECURVE_BOW));

	public static final RegistryObject<Item> ZEAL = ITEMS.register("zeal",
			() -> new ModCurioItem(ModCurioItemStats.ZEAL));

	public static final RegistryObject<Item> CRYSTALLINE_BRACER = ITEMS.register("crystalline_bracer",
			() -> new ModCurioItem(ModCurioItemStats.CRYSTALLINE_BRACER){
				@Override
				public void curioTick(SlotContext slotContext, ItemStack stack) {
					stack.getCapability(RegenerationCapabilityProvider.REGENERATION_CAPABILITY).ifPresent(cap -> {
						cap.tick(slotContext.entity());
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pTooltipComponents.add(Component.literal("Regenerate 1 health every 5 seconds").withStyle(ChatFormatting.RED));
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});

	public static final RegistryObject<Item> KINDLEGEM = ITEMS.register("kindlegem",
			() -> new ModCurioItem(ModCurioItemStats.KINDLEGEM));

	public static final RegistryObject<Item> VAMPIRIC_SCEPTER = ITEMS.register("vampiric_scepter",
			() -> new ModCurioItem(ModCurioItemStats.VAMPIRIC_SCEPTER));

	public static final RegistryObject<Item> NOONQUIVER = ITEMS.register("noonquiver",
			() -> new ModCurioItem(ModCurioItemStats.NOONQUIVER));

	public static final RegistryObject<Item> LOST_CHAPTER = ITEMS.register("lost_chapter",
			() -> new ModCurioItem(ModCurioItemStats.LOST_CHAPTER));

	public static final RegistryObject<Item> SERRATED_DIRK = ITEMS.register("serrated_dirk",
			() -> new ModCurioItem(ModCurioItemStats.SERRATED_DIRK));








	//--------------------------------------LEGENDARY CURIO ITEMS---------------------------------------------
	public static final RegistryObject<Item> JEWELED_GAUNTLET = ITEMS.register("jeweled_gauntlet",
			() -> new JeweledGauntlet(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> GIANT_SLAYER = ITEMS.register("giant_slayer",
			() -> new ModCurioItem(ModCurioItemStats.GIANT_SLAYER){
				@Override
				public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.addPermaHitEffect("giant_slayer_damage", ModCurioItemAbilities.GIANT_SLAYER_HIT_EFFECT);
					});
				}
				@Override
				public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.removePermaHitEffect("giant_slayer_damage");
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pTooltipComponents.add(Component.literal("Deal up to 40% more damage against bosses based on how much more health they have than you.").withStyle(ChatFormatting.RED));
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});

	public static final RegistryObject<Item> DEATHBLADE = ITEMS.register("deathblade",
			() -> new ModCurioItem(ModCurioItemStats.DEATHBLADE));

	public static final RegistryObject<Item> RABADONS_DEATHCAP = ITEMS.register("rabadons_deathcap",
			() -> new RabadonsDeathcap(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> JAKSHO = ITEMS.register("jaksho",
			() -> new JakSho(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> ROD_OF_AGES = ITEMS.register("rod_of_ages",
			() -> new RodOfAges(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> ECLIPSE = ITEMS.register("eclipse",
			() -> new Eclipse(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> OPPORTUNITY = ITEMS.register("opportunity",
			() -> new ModCurioItem(ModCurioItemStats.OPPORTUNITY));

	public static final RegistryObject<Item> VOLTAIC_CYCLOSWORD = ITEMS.register("voltaic_cyclosword",
			() -> new VoltaicCyclosword(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> COSMIC_DRIVE = ITEMS.register("cosmic_drive",
			() -> new ModCurioItem(ModCurioItemStats.COSMIC_DRIVE){
				@Override
				public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.addPermaHitEffect("cosmic_drive_burst_speed", ModCurioItemAbilities.COSMIC_DRIVE_HIT_EFFECT);
					});
				}
				@Override
				public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.removePermaHitEffect("cosmic_drive_burst_speed");
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pTooltipComponents.add(Component.literal("Dealing any magic damage will grant Speed I for 2 seconds").withStyle(ChatFormatting.RED));
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});

	public static final RegistryObject<Item> GUINSOOS_RAGEBLADE = ITEMS.register("guinsoos_rageblade",
			() -> new GuinsoosRageblade(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SERYLDAS_GRUDGE = ITEMS.register("seryldas_grudge",
			() -> new SeryldasGrudge(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> SUNFIRE_AEGIS = ITEMS.register("sunfire_aegis",
			() -> new ModCurioItem(ModCurioItemStats.SUNFIRE_AEGIS){
				@Override
				public void curioTick(SlotContext slotContext, ItemStack stack) {
					stack.getCapability(ImmolationCapabilityProvider.IMMOLATION_CAPABILITY).ifPresent(cap -> {
						cap.tick(slotContext.entity());
					});
				}
				@Override
				public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.addPermaHitEffect("sunfire_aegis_burn", ModCurioItemAbilities.IMMOLATION_HIT_EFFECT);
						cap.addPermaOnDamageEffect("sunfire_aegis_burn", ModCurioItemAbilities.IMMOLATION_HIT_EFFECT);
					});
				}
				@Override
				public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
					slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
						cap.removePermaHitEffect("sunfire_aegis_burn");
						cap.removePermaOnDamageEffect("sunfire_aegis_burn");
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pTooltipComponents.add(Component.literal("Taking or dealing any damage will cause enemies around you to burn for 2 magic damage every second for 3 seconds.").withStyle(ChatFormatting.RED));
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});
	/*
	public static final RegistryObject<Item> SUNFIRE_AEGIS = ITEMS.register("sunfire_aegis",
			() -> new SunfireAegis(new Item.Properties().stacksTo(1)));

	 */

	public static final RegistryObject<Item> RYLAIS_SCEPTER = ITEMS.register("rylais_crystal_scepter",
			() -> new RylaisCrystalScepter(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> INFINITY_EDGE = ITEMS.register("infinity_edge",
			() -> new InfinityEdge(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> RUNAANS_HURICANE = ITEMS.register("runaans_hurricane",
			() -> new RunaansHurricane(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> HEARTSTEEL = ITEMS.register("heartsteel",
			() -> new ModCurioItem(ModCurioItemStats.HEARTSTEEL){
				@Override
				public void curioTick(SlotContext slotContext, ItemStack stack) {
					stack.getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
						cap.tick(slotContext.entity());
					});
				}
				@Override
				public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
					if(newStack.equals(stack, false)) return;
					stack.getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
						cap.resetScale((Player)slotContext.entity());
						if(slotContext.entity().getAttribute(Attributes.MAX_HEALTH).hasModifier(cap.getModifier())) {
							slotContext.entity().getAttribute(Attributes.MAX_HEALTH).removeModifier(cap.getModifier());
						}
						System.out.println("UNEQUIP");
						cap.resetSavedScale();
					});
				}
				@Override
				public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
					pStack.getCapability(HeartsteelCapabilityProvider.HEARTSTEEL_CAPABILITY).ifPresent(cap -> {
						pTooltipComponents.add(Component.literal("Grants an additional 2 health for every boss you slay").withStyle(ChatFormatting.RED));
						pTooltipComponents.add(Component.literal("Grants 5% Size for every 20 health you have").withStyle(ChatFormatting.RED));
						pTooltipComponents.add(Component.literal("Current Bonus: +" + cap.getStacks() + " Hearts").withStyle(ChatFormatting.GOLD));
					});
					super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
				}
			});

	public static final RegistryObject<Item> BLOODTHIRSTER = ITEMS.register("bloodthirster",
			() -> new ModCurioItem(ModCurioItemStats.BLOODTHIRSTER));

	public static final RegistryObject<Item> WARMOGS = ITEMS.register("warmogs",
			() -> new Warmogs(new Item.Properties().stacksTo(1)));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
}
