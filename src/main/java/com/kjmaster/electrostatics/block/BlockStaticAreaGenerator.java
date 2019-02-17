package com.kjmaster.electrostatics.block;

import com.kjmaster.electrostatics.Electrostatics;

import com.kjmaster.electrostatics.block.tile.TileStaticAreaGenerator;
import com.kjmaster.kjlib.common.blocks.BlockFacingBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockStaticAreaGenerator extends BlockFacingBase {

    private String name = "electrostatic_area_generator";

    public BlockStaticAreaGenerator() {
        super("electrostatic_area_generator", Material.IRON, Electrostatics.electrostaticTab,
                1.0F, 10F, true, TileStaticAreaGenerator.class);
    }

    void registerItemModel(Item itemBlock) {
        Electrostatics.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(name);
    }
}
