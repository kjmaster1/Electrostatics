package com.kjmaster.electrostatics.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ServerMotionPacket implements IMessage {

    int motionX;
    int motionY;

    public ServerMotionPacket() {}

    public ServerMotionPacket(int motionX, int motionY) {
        this.motionX = motionX;
        this.motionY = motionY;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        motionX = buf.readInt();
        motionY = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(motionX);
        buf.writeInt(motionY);
    }
}
