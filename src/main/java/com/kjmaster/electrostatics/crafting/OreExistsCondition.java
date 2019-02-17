package com.kjmaster.electrostatics.crafting;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.BooleanSupplier;

public class OreExistsCondition implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        String oreName = JsonUtils.getString(json, "ore");
        boolean flipped = JsonUtils.getBoolean(json, "flipped");
        if (flipped) {
            return () -> !OreDictionary.doesOreNameExist(oreName);
        }
        return () -> OreDictionary.doesOreNameExist(oreName);
    }
}
