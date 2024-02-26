package dev.infernal_coding.flamesenchants.mixin;

import dev.infernal_coding.flamesenchants.MixinCalls;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Redirect(method = "getDrops", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTableManager;getLootTableFromLocation(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/loot/LootTable;"))
    private LootTable $injectCustomFortune(LootTableManager manager, ResourceLocation key) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        LootTable original = manager.getLootTableFromLocation(key);
        return MixinCalls.getUpdatedTable(original);
    }
}
