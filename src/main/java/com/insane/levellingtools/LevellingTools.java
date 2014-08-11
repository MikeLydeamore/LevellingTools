package com.insane.levellingtools;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.IOException;

/**
 * Created by Michael on 11/08/2014.
 */

@Mod(modid=LevellingTools.MODID, name = "Levelling Tools", version="1.0")

public class LevellingTools {

    public static final String MODID = "LevellingTools";

    @Mod.Instance("LevellingTools")
    public static LevellingTools instance;

    @SidedProxy(clientSide="com.insane.levellingtools.client.ClientProxy", serverSide ="com.insane.levellingtools.CommonProxy")
    public static CommonProxy proxy;

    public static File configFile;

    //public Enchantment[] possibleEnchantments = {Enchantment.}


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFile = new File(event.getModConfigurationDirectory()+"/LevellingTools.cfg");
        try {
            create(configFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Config.doConfig(configFile);
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();
        MinecraftForge.EVENT_BUS.register(new HarvestLevelUp());
    }





    private void create(File... files) throws IOException
    {
        for (File file : files)
        {
            if (!file.exists())
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println(file.getAbsolutePath());
            }
        }
    }
}
