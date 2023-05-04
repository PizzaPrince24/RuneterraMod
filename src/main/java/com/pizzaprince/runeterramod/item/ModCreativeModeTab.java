package com.pizzaprince.runeterramod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {

	public static final CreativeModeTab RUNETERRA_TAB = new CreativeModeTab("runeterratab") {
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.SUN_STONE.get());
		}
	};
	
}
