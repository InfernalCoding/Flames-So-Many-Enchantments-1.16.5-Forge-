package dev.infernal_coding.flamesenchants.enchantments.custom;

import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class CurseOfPossession extends Enchantment {

    public CurseOfPossession() {
        super(Rarity.VERY_RARE, EnchantmentType.BREAKABLE, EquipmentSlotType.values());
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 25 * par1;
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 25;
    }

    public boolean isAllowedOnBooks() {
        return false;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void toss(ItemTossEvent event) {
        ItemEntity item = event.getEntityItem();
        ItemStack theThrown = item.getItem();
        int l = EnchantmentHelper.getEnchantmentLevel(Registry.CURSE_OF_POSSESSION.get(), theThrown);
        if (l > 0) {
            PlayerEntity player = event.getPlayer();
            player.addItemStackToInventory(theThrown);
            event.setCanceled(true);
        }
    }
}
