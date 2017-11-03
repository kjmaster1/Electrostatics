package com.kjmaster.electrostatics;

import com.kjmaster.electrostatics.block.ModBlocks;
import com.kjmaster.electrostatics.config.ConfigHandler;
import com.kjmaster.electrostatics.item.ModItems;
import com.kjmaster.electrostatics.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Electrostatics.MODID, version = Electrostatics.VERSION)
public class Electrostatics
{
    public static final String MODID = "electrostatics";
    static final String VERSION = "1.0.0";
    private static final Logger LOGGER = LogManager.getLogger(Electrostatics.MODID);
    public static final ItemArmor.ArmorMaterial rubberArmorMaterial = EnumHelper.addArmorMaterial("rubber",
            MODID + ":rubber",5, new int[]{1, 2, 3, 1}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    private static File configDir;

    public static CreativeTabs electrostaticTab = new CreativeTabs("Electrostatics") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.electroStaticGenerator);
        }
    };

    @Mod.Instance
    public static Electrostatics instance;

    @SidedProxy(serverSide = "com.kjmaster.electrostatics.proxy.CommonProxy",
            clientSide = "com.kjmaster.electrostatics.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info(MODID + ": Starting Pre-Initialization");
        configDir = new File(event.getModConfigurationDirectory() + "/" + MODID);
        configDir.mkdirs();
        ConfigHandler.init(new File(configDir.getPath(), MODID + ".cfg"));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info(MODID + ": Starting Initialization");
        proxy.registerTileEntities();
        proxy.registerPackets();
        proxy.registerOreDic();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info(MODID + ": Starting Post-Initialization");
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
        }

    }
}
