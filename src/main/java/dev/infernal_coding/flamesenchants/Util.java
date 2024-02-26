package dev.infernal_coding.flamesenchants;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

public class Util {
    public static final Constructor<ApplyBonus> bonus = ObfuscationReflectionHelper.
            findConstructor(ApplyBonus.class, ILootCondition[].class, Enchantment.class, ApplyBonus.IFormula.class);

    public static final Constructor<LootTable> table = ObfuscationReflectionHelper.
            findConstructor(LootTable.class, LootParameterSet.class, LootPool[].class, ILootFunction[].class);

    public static final Constructor<ItemLootEntry> itemEntry = ObfuscationReflectionHelper.
            findConstructor(ItemLootEntry.class, Item.class, int.class, int.class, ILootCondition[].class, ILootFunction[].class);

    public static final Constructor<AlternativesLootEntry> alternative = ObfuscationReflectionHelper.
            findConstructor(AlternativesLootEntry.class, LootEntry[].class, ILootCondition[].class);

    public static final Constructor<LootPool> pool = ObfuscationReflectionHelper
            .findConstructor(LootPool.class, LootEntry[].class, ILootCondition[].class, ILootFunction[].class, IRandomRange.class, RandomValueRange.class, String.class);

    public static final Field poolConditions = ObfuscationReflectionHelper.findField(LootPool.class, "field_186454_b");
    public static final Field poolFunctions = ObfuscationReflectionHelper.findField(LootPool.class, "field_216102_d");
    public static final Field functions = ObfuscationReflectionHelper.findField(StandaloneLootEntry.class, "field_216160_g");
    public static final Field entries = ObfuscationReflectionHelper.findField(ParentedLootEntry.class, "field_216147_c");
    public static final Field lootEntries = ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a");
    public static final Field pools = ObfuscationReflectionHelper.findField(LootTable.class, "field_186466_c");

    public static final Field item = ObfuscationReflectionHelper.findField(ItemLootEntry.class, "field_186368_a");

    public static final Field weight = ObfuscationReflectionHelper.findField(StandaloneLootEntry.class, "field_216158_e");
    public static final Field quality = ObfuscationReflectionHelper.findField(StandaloneLootEntry.class, "field_216159_f");
    public static final Field standaloneFunctions = ObfuscationReflectionHelper.findField(StandaloneLootEntry.class, "field_216160_g");
    public static final Field conditions = ObfuscationReflectionHelper.findField(LootEntry.class, "field_216144_d");

    public static ItemStack getHeldItem(Hand hand, LivingEntity entity) {
        if (hand == Hand.MAIN_HAND) {
            return entity.getHeldItemMainhand();
        } else if (hand == Hand.OFF_HAND) {
            return entity.getHeldItemOffhand();
        } else return ItemStack.EMPTY;
    }

    public static boolean hasPotentials(ItemStack stack) {
        ListNBT enchs = EnchantedBookItem.getEnchantments(stack);
        for (INBT inbt : enchs) {
            CompoundNBT nbt = (CompoundNBT) inbt;
            String id = nbt.getString("id");
            if (id.equals(Registry.UPGRADED_POTENTIALS.getId().toString())) {
                return true;
            }
        }
        return false;
    }
    public static int getChestLevel(Enchantment enchantment, LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(enchantment, entity.getItemStackFromSlot(EquipmentSlotType.CHEST));
    }

    public static int getLegsLevel(Enchantment enchantment, LivingEntity entity) {
        return EnchantmentHelper.getEnchantmentLevel(enchantment, entity.getItemStackFromSlot(EquipmentSlotType.LEGS));
    }
    public static float calculateDamageIgnoreSwipe(float damage, float constant, float multiplier, float trueMultiplier, LivingEntity attacker, int level) {
        float afterCalculated = 0.0F;
        float damageReducer = 0.0F;
        ItemStack weapon = attacker.getActiveItemStack();
        if (!(damage > 1.0F)) {
            return 1.0F;
        } else {

            float secondDamage = (damage + constant + (float)level * multiplier) * trueMultiplier;
            float damager = (damage + constant + multiplier * (float)level - 2.0F) * trueMultiplier;
            if (damager == 0.0F) {
                damager = secondDamage;
            }

            float percentage = secondDamage / damager;
            float newDamage = damage * percentage;
            return newDamage;
        }
    }

    public static float calculateDamageForNegativeSwipe(float damage, float constant, float multiplier, float trueMultiplier, LivingEntity attacker, int level) {
        float afterCalculated = 0.0F;
        float damageReducer = 0.0F;
        ItemStack weapon = attacker.getActiveItemStack();
        if (!(damage > 1.0F)) {
            return 1.0F;
        } else {
            float secondDamage = (damage + constant + (float)level * multiplier) * trueMultiplier;
            float damager = (damage + constant + multiplier * (float)level - 2.0F) * trueMultiplier;
            if (damager <= 0.0F) {
                damager = secondDamage;
            }

            float percentage = secondDamage / damager;
            float newDamage = damager * percentage;
            return newDamage;
        }
    }

    public static class AdvancedUniformBonusCount implements ApplyBonus.IFormula {
        public static final ResourceLocation ID = new ResourceLocation("advanced_uniform_bonus_count");
        private final int bonusMultiplier;

        public AdvancedUniformBonusCount(int bonusMultiplier) {
            this.bonusMultiplier = bonusMultiplier;
        }

        @Override
        public int func_216204_a(Random rand, int blockCount, int level) {
          return blockCount + rand.nextInt(this.bonusMultiplier * level * 2 + 3);
        }

        @Override
        public void func_216202_a(JsonObject p_216202_1_, JsonSerializationContext p_216202_2_) {}

        @Override
        public ResourceLocation func_216203_a() {
            return ID;
        }
    }

    public static class AdvancedOreDrops implements ApplyBonus.IFormula {
        public static final ResourceLocation ID = new ResourceLocation("advanced_ore_drops");


        @Override
        public int func_216204_a(Random rand, int blockCount, int level) {
            if (level > 0) {
                int i = rand.nextInt(level + 3) * 2 - 1;
                if (i < 0) {
                    i = 0;
                }
                return blockCount * (i + 1);
            } else {
                return blockCount;
            }
        }

        @Override
        public void func_216202_a(JsonObject p_216202_1_, JsonSerializationContext p_216202_2_) {}

        @Override
        public ResourceLocation func_216203_a() {
            return ID;
        }
    }

    public static final class AdvancedBinomialWithBonusCountFormula implements ApplyBonus.IFormula {
        public static final ResourceLocation ID = new ResourceLocation("advanced_binomial_with_bonus_count");
        private final int extra;
        private final float probability;

        public AdvancedBinomialWithBonusCountFormula(int extra, float probability) {
            this.extra = extra;
            this.probability = probability;
        }

        public int func_216204_a(Random rand, int blockCount, int level) {
            for(int i = 0; i < level + this.extra; ++i) {
                if (rand.nextFloat() < this.probability) {
                    ++blockCount;
                }
            }

            return 3 + blockCount * 2;
        }

        @Override
        public void func_216202_a(JsonObject p_216202_1_, JsonSerializationContext p_216202_2_) {
            
        }

        public ResourceLocation func_216203_a() {
            return ID;
        }
    }

    public static class SupremeUniformBonusCount implements ApplyBonus.IFormula {
        public static final ResourceLocation ID = new ResourceLocation("supreme_uniform_bonus_count");
        private final int bonusMultiplier;

        public SupremeUniformBonusCount(int bonusMultiplier) {
            this.bonusMultiplier = bonusMultiplier;
        }

        @Override
        public int func_216204_a(Random rand, int blockCount, int level) {
            return blockCount + rand.nextInt(this.bonusMultiplier * level * 4 + 9);
        }

        @Override
        public void func_216202_a(JsonObject p_216202_1_, JsonSerializationContext p_216202_2_) {}

        @Override
        public ResourceLocation func_216203_a() {
            return ID;
        }
    }

    public static class SupremeOreDrops implements ApplyBonus.IFormula {
        public static final ResourceLocation ID = new ResourceLocation("supreme_ore_drops");


        @Override
        public int func_216204_a(Random rand, int blockCount, int level) {
            if (level > 0) {
                int i = rand.nextInt(level + 9) * 4 - 1;
                if (i < 0) {
                    i = 0;
                }
                return blockCount * (i + 1);
            } else {
                return blockCount;
            }
        }

        @Override
        public void func_216202_a(JsonObject p_216202_1_, JsonSerializationContext p_216202_2_) {}

        @Override
        public ResourceLocation func_216203_a() {
            return ID;
        }
    }

    public static final class SupremeBinomialWithBonusCountFormula implements ApplyBonus.IFormula {
        public static final ResourceLocation ID = new ResourceLocation("supreme_binomial_with_bonus_count");
        private final int extra;
        private final float probability;

        public SupremeBinomialWithBonusCountFormula(int extra, float probability) {
            this.extra = extra;
            this.probability = probability;
        }

        public int func_216204_a(Random rand, int blockCount, int level) {
            for(int i = 0; i < level + this.extra; ++i) {
                if (rand.nextFloat() < this.probability) {
                    ++blockCount;
                }
            }

            return 9 + blockCount * 4;
        }

        @Override
        public void func_216202_a(JsonObject p_216202_1_, JsonSerializationContext p_216202_2_) {

        }

        public ResourceLocation func_216203_a() {
            return ID;
        }
    }
}
