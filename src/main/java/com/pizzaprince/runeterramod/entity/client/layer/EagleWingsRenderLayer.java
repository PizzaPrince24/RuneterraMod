package com.pizzaprince.runeterramod.entity.client.layer;

import com.pizzaprince.runeterramod.RuneterraMod;
import com.pizzaprince.runeterramod.ability.PlayerAbilities;
import com.pizzaprince.runeterramod.ability.PlayerAbilitiesProvider;
import com.pizzaprince.runeterramod.ability.ascendent.AscendantType;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EagleWingsRenderLayer<T extends LivingEntity, M extends EntityModel<T>> extends ElytraLayer<T, M> {

    private static final ResourceLocation EAGLE_WINGS_LOCATION = new ResourceLocation(RuneterraMod.MOD_ID, "textures/entity/eagle_wings.png");
    public EagleWingsRenderLayer(RenderLayerParent<T, M> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer, pModelSet);
    }

    @Override
    public boolean shouldRender(ItemStack stack, T entity) {
        if(entity.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).resolve().isPresent()){
            return entity.getCapability(PlayerAbilitiesProvider.PLAYER_ABILITIES).resolve().get().getAscendantType() == AscendantType.EAGLE;
        }
        return false;
    }

    @Override
    public ResourceLocation getElytraTexture(ItemStack stack, T entity) {
        return EAGLE_WINGS_LOCATION;
    }
}
