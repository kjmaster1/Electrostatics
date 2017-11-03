package com.kjmaster.electrostatics.block.tile;

import com.kjmaster.electrostatics.config.ConfigHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileStaticGenerator extends TileEntity implements ITickable, IEnergyStorage {

    private EnergyStorage storage;

    public TileStaticGenerator() {
        this.storage = new EnergyStorage(1000, ConfigHandler.rfPerTick, ConfigHandler.rfPerTick);
    }

    public EnergyStorage getStorage() {
        return storage;
    }

    @Override
    public void update() {
        if (world != null)
            if (!world.isRemote) {
                handleSendingEnergy();
            }
    }

    private void handleSendingEnergy() {
        int energyStored = this.storage.getEnergyStored();
        for (EnumFacing facing: EnumFacing.VALUES) {
            BlockPos pos = getPos().offset(facing);
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                EnumFacing opposite = facing.getOpposite();
                IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, opposite);
                if (energyStorage != null) {
                    if (energyStorage.canReceive()) {
                        int rfToGive = 100 <= energyStored ? 100 : energyStored;
                        int received;
                        received = energyStorage.receiveEnergy(rfToGive, false);
                        energyStored -= this.storage.extractEnergy(received, false);
                        if (energyStored <= 0)
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.storage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.storage.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY ||
        super.hasCapability(capability, facing);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return storage.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return storage.canExtract();
    }

    @Override
    public boolean canReceive() {
        return storage.canReceive();
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY)
            return (T) this;
        else
            return super.getCapability(capability, facing);
    }
}