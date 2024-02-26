package dev.infernal_coding.flamesenchants;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.minecraft.loot.LootTableManager.validateLootTable;

public class MixinCalls {

    public static float addEfficiency(PlayerEntity player, float f) {
        ItemStack itemstack = player.getHeldItemMainhand();

        int i1 = EnchantmentHelper.getEnchantmentLevel(Registry.SUPREME_EFFICIENCY.get(), itemstack);
        int i2 = EnchantmentHelper.getEnchantmentLevel(Registry.ADVANCED_EFFICIENCY.get(), itemstack);
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, itemstack);

        if (i1 > 0 && !itemstack.isEmpty()) {
            f += (float) (151 + Math.pow(i1, 4));
        } else if (i2 > 0 && !itemstack.isEmpty()) {
            f += (float) (26 + Math.pow(i2, 3));
        } else if (i > 0 && !itemstack.isEmpty()) {
            f += (float) (i * i + 1);
        }
        return f;
    }

    public static void onCollideWithPlayer(PlayerEntity entityIn, ExperienceOrbEntity instance) {

        if (!instance.world.isRemote) {
            if (instance.delayBeforeCanPickup == 0 && entityIn.xpCooldown == 0) {
                if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp(entityIn, instance))) return;
                entityIn.xpCooldown = 2;
                entityIn.onItemPickup(instance, 1);
                Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomEquippedWithEnchantment(Enchantments.MENDING, entityIn, ItemStack::isDamaged);
                Map.Entry<EquipmentSlotType, ItemStack> advancedEntry = EnchantmentHelper.getRandomEquippedWithEnchantment(Registry.ADVANCED_MENDING.get(), entityIn, ItemStack::isDamaged);

                if (advancedEntry != null) {
                    ItemStack itemstack = advancedEntry.getValue();
                    if (!itemstack.isEmpty() && itemstack.isDamaged()) {
                        int i = Math.min((int)(instance.xpValue * 2 * itemstack.getXpRepairRatio()), itemstack.getDamage());
                        instance.xpValue -= i / 2;
                        itemstack.setDamage(itemstack.getDamage() - i);
                    }
                } else if (entry != null) {
                    ItemStack itemstack = entry.getValue();
                    if (!itemstack.isEmpty() && itemstack.isDamaged()) {
                        int i = Math.min((int)(instance.xpValue * itemstack.getXpRepairRatio()), itemstack.getDamage());
                        instance.xpValue -= i / 2;
                        itemstack.setDamage(itemstack.getDamage() - i);
                    }
                }

                if (instance.xpValue > 0) {
                    entityIn.giveExperiencePoints(instance.xpValue);
                }

                instance.remove();
            }

        }
    }

    public static void iterate(Map<ResourceLocation, LootTable> lootMap, ValidationTracker tracker) {
        lootMap.forEach((id, lootTable) -> {
            validateLootTable(tracker, id, lootTable);
        });
    }

    public static LootTable getUpdatedTable(LootTable table) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<LootPool> oldPools = (List<LootPool>) Util.pools.get(table);
        LootTable newTable = Util.table.newInstance(table.getParameterSet(), new ArrayList<>(oldPools).toArray(new LootPool[0]), new ArrayList<>(Arrays.asList(table.functions)).toArray(new ILootFunction[0]));

        List<LootPool> pools = (List<LootPool>) Util.pools.get(newTable);
        List<LootPool> newPools = new ArrayList<>();

        for (LootPool pool : pools) {

            List<LootEntry> entries = (List<LootEntry>) Util.lootEntries.get(pool);
            List<LootEntry> newEntries = new ArrayList<>();

            for (LootEntry entry : entries) {
                if (entry instanceof AlternativesLootEntry) {
                    AlternativesLootEntry alternate = (AlternativesLootEntry) entry;
                    List<LootEntry> temp = new ArrayList<>(Arrays.asList((LootEntry[]) Util.entries.get(alternate)));

                    LootEntry[] nested = temp.toArray(new LootEntry[0]);
                    for (int i = 0; i < nested.length; i++) {
                        if (nested[i] instanceof ItemLootEntry) {
                            List<ILootFunction> newFuncs = new ArrayList<>();
                            ItemLootEntry itemEntry = (ItemLootEntry) nested[i];
                            ILootFunction[] functions = (ILootFunction[]) Util.functions.get(itemEntry);

                            for (ILootFunction func : functions) {
                                if (func instanceof ApplyBonus) {
                                    ApplyBonus bonus = (ApplyBonus) func;

                                    if (bonus.field_215877_d instanceof ApplyBonus.UniformBonusCountFormula) {
                                        ApplyBonus.UniformBonusCountFormula formula = (ApplyBonus.UniformBonusCountFormula) bonus.field_215877_d;

                                        Util.SupremeUniformBonusCount supremeFormula = new Util.SupremeUniformBonusCount(formula.bonusMultiplier);
                                        Util.AdvancedUniformBonusCount advancedFormula = new Util.AdvancedUniformBonusCount(formula.bonusMultiplier);

                                        ApplyBonus supremeBonus = Util.bonus.newInstance(bonus.conditions, Registry.SUPREME_FORTUNE.get(), supremeFormula);
                                        ApplyBonus advancedBonus = Util.bonus.newInstance(bonus.conditions, Registry.ADVANCED_FORTUNE.get(), advancedFormula);

                                        newFuncs.add(supremeBonus);
                                        newFuncs.add(advancedBonus);
                                    } else if (bonus.field_215877_d instanceof ApplyBonus.OreDropsFormula) {
                                        ApplyBonus supremeBonus = Util.bonus.newInstance(bonus.conditions, Registry.SUPREME_FORTUNE.get(), new Util.SupremeOreDrops());
                                        ApplyBonus advancedBonus = Util.bonus.newInstance(bonus.conditions, Registry.ADVANCED_FORTUNE.get(), new Util.AdvancedOreDrops());

                                        newFuncs.add(supremeBonus);
                                        newFuncs.add(advancedBonus);
                                    } else if (bonus.field_215877_d instanceof ApplyBonus.BinomialWithBonusCountFormula) {
                                        ApplyBonus.BinomialWithBonusCountFormula formula = (ApplyBonus.BinomialWithBonusCountFormula) bonus.field_215877_d;

                                        Util.SupremeBinomialWithBonusCountFormula supremeFormula = new Util.SupremeBinomialWithBonusCountFormula(formula.extra, formula.probability);
                                        Util.AdvancedBinomialWithBonusCountFormula advancedFormula = new Util.AdvancedBinomialWithBonusCountFormula(formula.extra, formula.probability);

                                        ApplyBonus supremeBonus = Util.bonus.newInstance(bonus.conditions, Registry.SUPREME_FORTUNE.get(), supremeFormula);
                                        ApplyBonus advancedBonus = Util.bonus.newInstance(bonus.conditions, Registry.ADVANCED_FORTUNE.get(), advancedFormula);

                                        newFuncs.add(supremeBonus);
                                        newFuncs.add(advancedBonus);
                                    }
                                }
                                newFuncs.add(func);
                            }
                            Item item = (Item) Util.item.get(itemEntry);
                            int weightIn = Util.weight.getInt(itemEntry);
                            int quality = Util.quality.getInt(itemEntry);
                            ILootCondition[] conditions = (ILootCondition[]) Util.conditions.get(itemEntry);
                            ILootFunction[] functions1 = (ILootFunction[]) Util.standaloneFunctions.get(itemEntry);

                            nested[i] = Util.itemEntry.newInstance(item, weightIn, quality, conditions, newFuncs.toArray(new ILootFunction[0]));
                        }
                    }
                    ILootCondition[] conditions = (ILootCondition[]) Util.conditions.get(alternate);
                    AlternativesLootEntry newAlternate = Util.alternative.newInstance(nested, conditions);

                    newEntries.add(newAlternate);
                } else newEntries.add(entry);
            }

            ILootCondition[] conditions = ((List<ILootCondition>) Util.poolConditions.get(pool)).toArray(new ILootCondition[0]);
            ILootFunction[] functions = (ILootFunction[]) Util.poolFunctions.get(pool);
            IRandomRange rolls = pool.getRolls();
            RandomValueRange bonusRolls = (RandomValueRange) pool.getBonusRolls();
            String name = pool.getName();

            LootPool newPool = Util.pool.newInstance(newEntries.toArray(new LootEntry[0]),
                    conditions, functions, rolls, bonusRolls, name);
            newPools.add(newPool);
        }
        Util.pools.set(newTable, newPools);
        return newTable;
    }


}
