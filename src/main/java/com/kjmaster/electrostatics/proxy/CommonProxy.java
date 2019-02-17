package com.kjmaster.electrostatics.proxy;

import com.kjmaster.electrostatics.Electrostatics;
import com.kjmaster.electrostatics.block.tile.TileStaticAreaGenerator;
import com.kjmaster.electrostatics.block.tile.TileStaticGenerator;
import com.kjmaster.electrostatics.item.ModItems;
import com.kjmaster.electrostatics.network.MotionPacketHandler;
import com.kjmaster.electrostatics.network.ServerMotionPacket;
import com.kjmaster.electrostatics.network.StaticAreaGeneratorHandler;
import com.kjmaster.electrostatics.network.StaticAreaGeneratorPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

    public void registerItemRenderer(Item item, int meta, String id) {}

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileStaticGenerator.class, Electrostatics.MODID + ":tile_static_generator");
        GameRegistry.registerTileEntity(TileStaticAreaGenerator.class, Electrostatics.MODID + "tile_static_area_generator");
    }

    public void registerPackets() {
        Electrostatics.INSTANCE.registerMessage(MotionPacketHandler.class, ServerMotionPacket.class, 1, Side.SERVER);
        Electrostatics.INSTANCE.registerMessage(StaticAreaGeneratorHandler.class, StaticAreaGeneratorPacket.class, 2, Side.SERVER);
    }

    public void registerOreDic() {
        OreDictionary.registerOre("itemRubber", ModItems.rubber);
    }
}
