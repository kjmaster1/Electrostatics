package com.kjmaster.electrostatics.block;

import com.kjmaster.electrostatics.Electrostatics;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    public static BlockStaticGenerator electroStaticGenerator = new BlockStaticGenerator("electrostatic_generator",
            Material.IRON, Electrostatics.electrostaticTab, 1.0F, 10F, "pickaxe", 1);

    public static void register(IForgeRegistry<Block> registry) {
        registry.registerAll(electroStaticGenerator);
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.registerAll(electroStaticGenerator.createItemBlock());
    }

    public static void registerModels() {
        electroStaticGenerator.registerItemModel(Item.getItemFromBlock(electroStaticGenerator));
    }
}
