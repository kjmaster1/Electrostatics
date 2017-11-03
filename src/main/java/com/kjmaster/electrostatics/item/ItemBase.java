package com.kjmaster.electrostatics.item;

import com.kjmaster.electrostatics.Electrostatics;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    protected String name;

    ItemBase(String name, CreativeTabs tab, int stackSize) {
        this.name = name;
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);
        setMaxStackSize(stackSize);
    }

    void registerItemModel() {
        Electrostatics.proxy.registerItemRenderer(this, 0, name);
    }
}
