package com.kjmaster.electrostatics.crafting;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModClassLoader;

import java.util.function.BooleanSupplier;

public class TEModeCondition implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        boolean flipped = JsonUtils.getBoolean(json, "flipped");
        if (flipped) {
            return () -> !Loader.isModLoaded("thermalexpansion");
        }
        return () -> Loader.isModLoaded("thermalexpansion");
    }
}
