package dev.infernal_coding.flamesenchants.enchantments.normal;

import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LureEnchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class AdvancedLure extends LureEnchantment {

   @Override
   public boolean canVillagerTrade() {
      return false;
   }

   public AdvancedLure() {
      super(Enchantment.Rarity.RARE, EnchantmentType.FISHING_ROD, EquipmentSlotType.MAINHAND);
   }

   @Override
   public int getMinEnchantability(int p_77321_1_) {
      return (int) (super.getMinEnchantability(p_77321_1_) * 1.5);
   }

   @Override
   public int getMaxEnchantability(int p_223551_1_) {
      return (int) (super.getMaxEnchantability(p_223551_1_) * 1.5);
   }

   /**
    * Returns the maximum level that the enchantment can have.
    */
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean canApplyTogether(Enchantment ench) {
      return super.canApplyTogether(ench) && ench != Enchantments.LURE && ench != Registry.SUPREME_LURE.get();
   }
}