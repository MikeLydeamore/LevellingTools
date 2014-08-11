package com.insane.levellingtools;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by Michael on 11/08/2014.
 */
public class HarvestLevelUp {

    @SubscribeEvent
    public void levelUp(BlockEvent.HarvestDropsEvent event) {
        if (event.block.canHarvestBlock(event.harvester, event.blockMetadata) && event.harvester != null) { //If they are using the correct tool
            ItemStack heldItem = event.harvester.getHeldItem();
            if (heldItem != null) {
                if ((heldItem.getItem() instanceof ItemPickaxe) && event.block.getHarvestTool(event.blockMetadata).equals("pickaxe")) { //If it's a pickaxe!
                    increment(heldItem, "pickaxe");
                }
            }
        }
    }

    public void increment(ItemStack heldItem, String toolType) {
        NBTTagCompound currentTags = heldItem.getTagCompound();
        if (currentTags != null) {
            if (currentTags.hasKey(LevellingTools.MODID+"XPLevel")) { //Has already been levelled up in the past
                incrementXP(currentTags,heldItem);
            } else { //Has not yet been levelled, but has an NBT Compound.
                setFirstTime(currentTags, heldItem);
            }
        } else {
            setFirstTime(new NBTTagCompound(), heldItem);
        }

        //Calculate required amount of XP:
        int requiredXP = Config.baseXP + (currentTags.getInteger(LevellingTools.MODID+"CurrentLevel")-1) * Config.increasePerLevel;
        if ((currentTags.getInteger(LevellingTools.MODID+"XPLevel")>= requiredXP) && (currentTags.getInteger(LevellingTools.MODID+"CurrentLevel")<Config.maxLevel)) {
            incrementLevel(currentTags,heldItem,toolType);
        }
        //heldItem.setTagCompound(currentTags); //Update
        System.out.println(currentTags.getInteger(LevellingTools.MODID+"XPLevel"));
        System.out.println(currentTags.getInteger(LevellingTools.MODID+"CurrentLevel"));
    }

    public void setFirstTime(NBTTagCompound comp, ItemStack item) {
        comp.setInteger(LevellingTools.MODID+"XPLevel", 1);
        comp.setInteger(LevellingTools.MODID+"CurrentLevel",1);
        item.setTagCompound(comp);
    }

    public void incrementXP(NBTTagCompound comp, ItemStack item) {
        comp.setInteger(LevellingTools.MODID+"XPLevel",comp.getInteger(LevellingTools.MODID+"XPLevel")+1);
        item.setTagCompound(comp);
    }

    public void incrementLevel(NBTTagCompound comp, ItemStack item, String toolType) {
        comp.setInteger(LevellingTools.MODID+"CurrentLevel",comp.getInteger(LevellingTools.MODID+"CurrentLevel")+1);
        item.setTagCompound(comp);
        if (toolType.equals("pickaxe")) {
            item.addEnchantment(Enchantment.unbreaking, 1);
        }
    }

}
