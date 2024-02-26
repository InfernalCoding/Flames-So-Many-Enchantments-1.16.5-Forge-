package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RepairContainer.class)
public class RepairContainerMixin {



    @Redirect(method = "updateRepairOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/container/RepairContainer;detectAndSendChanges()V"))
    public void $editCost(RepairContainer instance) {
        ItemStack left = instance.getInventory().get(0);
        ItemStack right = instance.getInventory().get(1);

        boolean storedLevelLeft = Util.hasPotentials(left);
        boolean storedLevelRight = Util.hasPotentials(right);

        if (storedLevelLeft && !(right.getItem() instanceof EnchantedBookItem)) {
            instance.setMaximumCost(10);
        } else if (storedLevelRight && !(left.getItem() instanceof EnchantedBookItem)) {
            instance.setMaximumCost(10);

        }
    }
}
