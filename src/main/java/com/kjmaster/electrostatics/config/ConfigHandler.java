package com.kjmaster.electrostatics.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
    public static Configuration config;

    public static boolean isTreeTrapEnabled;
    public static int rfPerTick;
    public static int damage;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    private static void syncConfig() {
        String category;
        category = "Electrostatic generator";
        config.addCustomCategoryComment(category, "Settings for the electrostatic generator");
        rfPerTick = config.getInt("rfPerTick", category, 10, 0, 10000,
                "Set the amount of rf per tick the electrostatic generator produces.");
        category = "Tree tap";
        config.addCustomCategoryComment(category, "Settings for the tree tap");
        isTreeTrapEnabled = config.getBoolean("isTreeTapEnabled", category, true,
                "Set this to false to disable the tree taps functionality" +
                        " and force users to get rubber from another mod.");
        damage = config.getInt("damage", category, 30, 1, 256,
                "Set the amount of damage a tree tap takes when used to tap resin");
        config.save();
    }
}
