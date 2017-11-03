package com.kjmaster.electrostatics.item;

import com.kjmaster.electrostatics.Electrostatics;
import com.kjmaster.electrostatics.network.ServerMotionPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArmor extends net.minecraft.item.ItemArmor {

    private String name;

    public ItemArmor(ArmorMaterial material, EntityEquipmentSlot slot, String name) {
        super(material, 0, slot);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
        setCreativeTab(Electrostatics.electrostaticTab);
    }

    void registerItemModel() {
        Electrostatics.proxy.registerItemRenderer(this, 0, name);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem().equals(ModItems.rubberBoots)) {
            if (world.isRemote) {
                if (player.motionX != 0 || player.motionZ != 0) {
                    Electrostatics.INSTANCE.sendToServer(new ServerMotionPacket((int)player.motionX, (int)player.motionY));
                }
            }
        }
    }
}