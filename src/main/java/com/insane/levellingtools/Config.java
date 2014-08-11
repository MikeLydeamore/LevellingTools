package com.insane.levellingtools;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Michael on 11/08/2014.
 */
public class Config {
    public static int baseXP;
    public static int increasePerLevel;
    public static int maxLevel;
    public static void doConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();
        config.addCustomCategoryComment("XP Requirements","XP to level calculated as: RequiredXP = BaseXP + (Level-1)*increasePerLevel");
        baseXP = config.get("XP Requirements", "BaseXP", 10, "[Default: 50]").getInt(10);
        increasePerLevel = config.get("XP Requirements", "increasePerLevel", 10, "[Default: 20]").getInt(10);

        maxLevel = config.get("Limits", "Maximum Level", 2, "[Default: 10]").getInt(2);
        config.save();
    }
}
