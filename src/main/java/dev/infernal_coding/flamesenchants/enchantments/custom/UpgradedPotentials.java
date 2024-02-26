package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class UpgradedPotentials extends Enchantment {
    public UpgradedPotentials() {
        super(Rarity.RARE, EnchantmentType.BREAKABLE, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    public int getMinEnchantability(int par1) {
        return 35;
    }

    public int getMaxEnchantability(int par1) {
        return super.getMinEnchantability(par1) + 45;
    }

    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean canVillagerTrade() {
        return false;
    }

    @SubscribeEvent
    public static void onAnvilUse(AnvilRepairEvent event) {

        ItemStack result = event.getItemResult();
        int level = EnchantmentHelper.getEnchantmentLevel(Registry.UPGRADED_POTENTIALS.get(), result);
        boolean stored = Util.hasPotentials(result);

        if (level > 0 || stored) {
            result.setRepairCost(0);
        }
    }
}
