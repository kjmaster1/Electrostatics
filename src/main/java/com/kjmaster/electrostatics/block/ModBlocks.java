package com.kjmaster.electrostatics.block;

import com.kjmaster.electrostatics.Electrostatics;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    public static BlockStaticGenerator staticGenerator = new BlockStaticGenerator("electrostatic_generator",
            Material.IRON, Electrostatics.electrostaticTab, 1.0F, 10F, "pickaxe", 1);
    public static BlockStaticAreaGenerator staticAreaGenerator = new BlockStaticAreaGenerator();

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(staticGenerator, staticAreaGenerator);
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(staticGenerator.createItemBlock(), staticAreaGenerator.createItemBlock());
    }

    public static void registerModels() {
        staticGenerator.registerItemModel(Item.getItemFromBlock(staticGenerator));
        staticAreaGenerator.registerItemModel(Item.getItemFromBlock(staticAreaGenerator));
    }
}
