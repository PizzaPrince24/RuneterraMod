package com.pizzaprince.runeterramod.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {
	
	public static final String KEY_CATEGORY_RUNETERRA = "key.category.runeterramod.runeterra";
	public static final String KEY_ULTIMATE = "key.runeterramod.ultimate";

	public static final String KEY_TEST = "key.runeterramod.test";

	public static final KeyMapping ULTIMATE_KEY = new KeyMapping(KEY_ULTIMATE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORY_RUNETERRA);

	public static final KeyMapping TEST_KEY = new KeyMapping(KEY_TEST, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_T, KEY_CATEGORY_RUNETERRA);

}
