package com.kjmaster.electrostatics.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class StaticAreaGeneratorPacket implements IMessage {

    int motionX;
    int motionY;
    int x;
    int y;
    int z;

    public StaticAreaGeneratorPacket() {}

    public StaticAreaGeneratorPacket(int motionX, int motionY, int x, int y, int z) {
        this.motionX = motionX;
        this.motionY = motionY;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        motionX = buf.readInt();
        motionY = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(motionX);
        buf.writeInt(motionY);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
}
