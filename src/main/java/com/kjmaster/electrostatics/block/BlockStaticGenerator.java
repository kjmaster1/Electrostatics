package com.kjmaster.electrostatics.block;

import com.kjmaster.electrostatics.Electrostatics;
import com.kjmaster.electrostatics.block.tile.TileStaticGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockStaticGenerator extends Block implements ITileEntityProvider {

    private String name;
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    BlockStaticGenerator(String name, Material material, CreativeTabs tabs, float hardness, float resistance,
                                String tool, int harvest) {
        super(material);
        setRegistryName(name);
        setUnlocalizedName(name);
        this.name = name;
        setHardness(hardness);
        setResistance(resistance);
        setCreativeTab(tabs);
        setHarvestLevel(tool, harvest);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileStaticGenerator();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileStaticGenerator();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    void registerItemModel(Item itemBlock) {
        Electrostatics.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }
}
