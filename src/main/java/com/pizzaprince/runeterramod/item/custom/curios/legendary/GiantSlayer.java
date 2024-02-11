package com.pizzaprince.runeterramod.item.custom.curios.legendary;

import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.curios.WarmogsCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeEntityTypeTagsProvider;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.function.Consumer;

public class GiantSlayer extends Item implements ICurioItem {

    private static AttributeModifier GIANT_SLAYER_ATTACK_SPEED = new AttributeModifier("giant_slayer_attack_speed",
            0.25, AttributeModifier.Operation.ADDITION);

    private static AttributeModifier GIANT_SLAYER_ATTACK_DAMAGE = new AttributeModifier("giant_slayer_attack_damage",
            3, AttributeModifier.Operation.ADDITION);

    private Consumer<LivingHurtEvent> hitEffect = event -> {
        if(event.getEntity().getType().is(Tags.EntityTypes.BOSSES)){
            float healthDiff = event.getEntity().getMaxHealth() - ((LivingEntity)event.getSource().getEntity()).getMaxHealth();
            float mult = Mth.clampedLerp(1f, 1.4f, healthDiff/200);
            event.setAmount(event.getAmount()*mult);
        }
    };
    public GiantSlayer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(GIANT_SLAYER_ATTACK_SPEED)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(GIANT_SLAYER_ATTACK_SPEED);
        }
        if(!slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(GIANT_SLAYER_ATTACK_DAMAGE)) {
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(GIANT_SLAYER_ATTACK_DAMAGE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.addPermaHitEffect("giant_slayer_damage", hitEffect);
        });
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        if(slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).hasModifier(GIANT_SLAYER_ATTACK_SPEED)){
            slotContext.entity().getAttribute(Attributes.ATTACK_SPEED).removeModifier(GIANT_SLAYER_ATTACK_SPEED);
        }
        if(slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(GIANT_SLAYER_ATTACK_DAMAGE)){
            slotContext.entity().getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(GIANT_SLAYER_ATTACK_DAMAGE);
        }
        slotContext.entity().getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).ifPresent(cap -> {
            cap.removePermaHitEffect("giant_slayer_damage");
        });
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Deal up to 40% more damage against bosses based on how much more health they have than you").withStyle(ChatFormatting.RED));
        pTooltipComponents.add(Component.literal("+0.25 Attack Speed").withStyle(ChatFormatting.GOLD));
        pTooltipComponents.add(Component.literal("+3 Attack Damage").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
