package com.kjmaster.electrostatics.network;

import com.kjmaster.electrostatics.block.tile.TileStaticAreaGenerator;
import com.kjmaster.kjlib.KJLib;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StaticAreaGeneratorHandler implements IMessageHandler<StaticAreaGeneratorPacket, IMessage> {

    @Override
    public IMessage onMessage(StaticAreaGeneratorPacket message, MessageContext ctx) {

        KJLib.proxy.getThreadFromContext(ctx).addScheduledTask(new Runnable() {
            @Override
            public void run() {
                processMessage(message, ctx);
            }
        });
        return null;
    }

    private void processMessage(StaticAreaGeneratorPacket message, MessageContext ctx) {

        int motionX = message.motionX;
        int motionY = message.motionY;
        EntityPlayer player = KJLib.proxy.getPlayerEntity(ctx);
        World world = player.world;
        double x = message.x;
        double y = message.y;
        double z = message.z;
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof TileStaticAreaGenerator) {
            TileStaticAreaGenerator staticAreaGenerator = (TileStaticAreaGenerator) tileEntity;
            staticAreaGenerator.receiveEnergyFromMovingPlayer();
        }
    }
}
