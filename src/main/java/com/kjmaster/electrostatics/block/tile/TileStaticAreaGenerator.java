package com.kjmaster.electrostatics.block.tile;

import com.kjmaster.electrostatics.Electrostatics;
import com.kjmaster.electrostatics.config.ConfigHandler;
import com.kjmaster.electrostatics.item.ModItems;
import com.kjmaster.electrostatics.network.StaticAreaGeneratorPacket;
import net.minecraft.block.BlockCarpet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.List;

public class TileStaticAreaGenerator extends TileStaticGenerator {

    private EnergyStorage storage;
    private int xRange = 20;
    private int yRange = 4;

    public TileStaticAreaGenerator() {
        super();
        this.storage = getStorage();
    }

    @Override
    public void update() {
        handleReceivingEnergy();
        super.update();

    }

    private void handleReceivingEnergy() {
         AxisAlignedBB playerCheckingBB = new AxisAlignedBB(this.pos.getX() - xRange, this.pos.getY(), this.pos.getZ() - xRange,
                this.pos.getX() + xRange, this.pos.getY() + yRange, this.pos.getZ() + xRange);
        List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, playerCheckingBB);
        Electrostatics.LOGGER.info(playerCheckingBB);
        Electrostatics.LOGGER.info(players);
        Electrostatics.LOGGER.info(this.pos.getX());
        for (EntityPlayer player : players) {
            if (player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem().equals(ModItems.rubberBoots)
            && world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ)).getBlock() instanceof BlockCarpet) {
                if (world.isRemote) {
                    if (player.motionX != 0 || player.motionZ != 0) {
                        Electrostatics.INSTANCE.sendToServer(new StaticAreaGeneratorPacket((int)player.motionX, (int)player.motionY,
                                this.pos.getX(), this.pos.getY(), this.pos.getZ()));
                    }
                }
            }
        }
    }

    public void receiveEnergyFromMovingPlayer() {
        if (storage.getEnergyStored() < storage.getMaxEnergyStored())
            storage.receiveEnergy(ConfigHandler.rfPerTick, false);
    }
}
