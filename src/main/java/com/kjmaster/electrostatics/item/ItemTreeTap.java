package com.kjmaster.electrostatics.item;

import com.google.common.collect.Sets;
import com.kjmaster.electrostatics.Electrostatics;
import com.kjmaster.electrostatics.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import java.util.Set;

public class ItemTreeTap extends ItemTool {

    static Set<Block> effectiveBlocks = Sets.newHashSet();
    private String name;

    ItemTreeTap(ToolMaterial materialIn, Set<Block> effectiveBlocksIn, String name, int stackSize, CreativeTabs tab) {
        super(materialIn, effectiveBlocksIn);
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(tab);
        setMaxStackSize(stackSize);
        this.name = name;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block.isWood(worldIn, pos) && !worldIn.isRemote && ConfigHandler.isTreeTrapEnabled) {
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.resin, 1));
            ItemStack tapStack = player.getHeldItem(hand);
            int damage = tapStack.getItemDamage();
            tapStack.setItemDamage(damage + ConfigHandler.damage);
            damage = tapStack.getItemDamage();
            if (damage > tapStack.getMaxDamage())
                tapStack.shrink(1);
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    void registerItemModel() {
        Electrostatics.proxy.registerItemRenderer(this, 0, name);
    }
}
