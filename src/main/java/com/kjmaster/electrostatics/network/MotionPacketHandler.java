package com.kjmaster.electrostatics.network;

import com.kjmaster.electrostatics.Electrostatics;
import com.kjmaster.electrostatics.block.tile.EnergyStorage;
import com.kjmaster.electrostatics.block.tile.TileStaticGenerator;
import com.kjmaster.electrostatics.config.ConfigHandler;
import com.kjmaster.kjlib.KJLib;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MotionPacketHandler implements IMessageHandler<ServerMotionPacket, IMessage> {

    @Override
    public IMessage onMessage(ServerMotionPacket message, MessageContext ctx) {

        KJLib.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(ServerMotionPacket message, MessageContext ctx) {

        int motionX = message.motionX;
        int motionY = message.motionY;
        EntityPlayer player = KJLib.proxy.getPlayerEntity(ctx);
        World world = player.world;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        Block carpetUnderPlayer = world.getBlockState(new BlockPos(x, y, z)).getBlock();
        if (carpetUnderPlayer instanceof BlockCarpet) {
            for (EnumFacing facing: EnumFacing.VALUES) {
                BlockPos pos = new BlockPos(x, y, z).offset(facing);
                TileEntity tile = world.getTileEntity(pos);
                if (tile != null) {
                    if (tile instanceof TileStaticGenerator) {
                        TileStaticGenerator tileStaticGenerator = (TileStaticGenerator) tile;
                        EnergyStorage storage = tileStaticGenerator.getStorage();
                        if (storage.getEnergyStored() < storage.getMaxEnergyStored())
                            storage.receiveEnergy(ConfigHandler.rfPerTick, false);
                    }
                }
            }
        }
    }
}
