package dev.infernal_coding.flamesenchants.enchantments.custom;

import com.mojang.authlib.GameProfile;
import dev.infernal_coding.flamesenchants.FlamesEnchants;
import dev.infernal_coding.flamesenchants.Registry;
import dev.infernal_coding.flamesenchants.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.nbt.StringNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = FlamesEnchants.MODID)
public class Culling extends Enchantment {
    public Culling() {
        super(Rarity.RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantability(int par1) {
        return 20 + 15 * (par1 - 1);
    }

    @Override
    public int getMaxEnchantability(int par1) {
        return this.getMinEnchantability(par1) + 30;
    }

    public boolean canApply(ItemStack fTest) {
        return fTest.getItem() instanceof AxeItem && super.canApply(fTest);
    }

    public static void handleEvent(LivingHurtEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
                if (attacker != null) {
                    ItemStack dmgSource = Util.getHeldItem(attacker.getActiveHand(), attacker);
                    int level = EnchantmentHelper.getEnchantmentLevel(Registry.CULLING.get(), dmgSource);

                    if (level > 0) {
                        LivingEntity living = fEvent.getEntityLiving();
                        float half = (float) level;
                        float maxHealth = living.getMaxHealth();
                        float currentHealthPercent = fEvent.getEntityLiving().getHealth() / maxHealth;

                        if (currentHealthPercent <= 0.10000000149011612D + ((double) half * 0.07D - 0.009999999776482582D)) {
                            attacker.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 20 + level * 5, 1));
                        }
                    }
                }
        }
    }

    @SubscribeEvent
    public static void handleEvent(LivingDropsEvent fEvent) {
        if (fEvent.getSource().getTrueSource() instanceof LivingEntity) {
                LivingEntity attacker = (LivingEntity) fEvent.getSource().getTrueSource();
                if (attacker != null) {
                    ItemStack dmgSource = attacker.getHeldItem(attacker.getActiveHand());
                    if (EnchantmentHelper.getEnchantmentLevel(Registry.CULLING.get(), dmgSource) > 0) {
                        if (Math.random() <= 0.3499999940395355D) {
                            ItemStack itemHead = null;
                            if (fEvent.getEntity() instanceof CreeperEntity) {
                                itemHead = new ItemStack(Items.CREEPER_HEAD, 1);
                            }

                            if (fEvent.getEntity() instanceof SkeletonEntity) {
                                itemHead = new ItemStack(Items.SKELETON_SKULL, 1);
                            }

                            if (fEvent.getEntity() instanceof ZombieEntity) {
                                itemHead = new ItemStack(Items.ZOMBIE_HEAD, 1);
                            }

                            if (fEvent.getEntity() instanceof PlayerEntity) {
                                itemHead = new ItemStack(Items.PLAYER_HEAD, 1);
                                GameProfile gameprofile = ((PlayerEntity) fEvent.getEntity()).getGameProfile();
                                itemHead.getOrCreateTag().put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
                            }

                            if (fEvent.getEntity() instanceof SpiderEntity) {
                                itemHead = new ItemStack(Items.PLAYER_HEAD, 1);
                                writeSpiderSkull(itemHead.getOrCreateTag());
                            }

                            if (fEvent.getEntity() instanceof CaveSpiderEntity) {
                                itemHead = new ItemStack(Items.PLAYER_HEAD, 1);
                                writeCaveSpiderSkull(itemHead.getOrCreateTag());
                            }

                            if (fEvent.getEntity() instanceof EndermanEntity) {
                                itemHead = new ItemStack(Items.PLAYER_HEAD, 1);
                                writeEndermanSkull(itemHead.getOrCreateTag());
                            }

                            if (fEvent.getEntity() instanceof ZombifiedPiglinEntity) {
                                itemHead = new ItemStack(Items.PLAYER_HEAD, 1);
                                writePigmanSkull(itemHead.getOrCreateTag());
                            }

                            if (fEvent.getEntity() instanceof BlazeEntity) {
                                itemHead = new ItemStack(Items.PLAYER_HEAD, 1);
                                writeBlazeSkull(itemHead.getOrCreateTag());
                            }

                            BlockPos pos = fEvent.getEntity().getPosition();

                            if (itemHead != null) {
                                ItemEntity entityItem = new ItemEntity(fEvent.getEntityLiving().world, pos.getX(), pos.getY(), pos.getZ(), itemHead);
                                fEvent.getDrops().add(entityItem);
                            }
                        }
                    }
                }
        }
    }

    public static void writeSpiderSkull(CompoundNBT nbt) {
        CompoundNBT skull = new CompoundNBT();
        nbt.put("SkullOwner", skull);
        skull.putString("Name", "Spider");
        skull.putUniqueId("Id", UUID.fromString("5ad55f34-41b6-4bd2-9c32-18983c635936"));
        CompoundNBT properties = new CompoundNBT();
        skull.put("Properties", properties);

        ListNBT textures = new ListNBT();
        properties.put("textures", textures);

        textures.add(StringNBT.valueOf("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==="));
    }

    public static void writeCaveSpiderSkull(CompoundNBT nbt) {
        CompoundNBT skull = new CompoundNBT();
        nbt.put("SkullOwner", skull);
        skull.putString("Name", "Cave Spider");
        skull.putUniqueId("Id", UUID.fromString("cab28771-f0cd-4fe7-b129-02c69eba79a5"));
        CompoundNBT properties = new CompoundNBT();
        skull.put("Properties", properties);

        ListNBT textures = new ListNBT();
        properties.put("textures", textures);
        textures.add(StringNBT.valueOf("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19="));
    }

    public static void writeEndermanSkull(CompoundNBT nbt) {
        CompoundNBT skull = new CompoundNBT();
        nbt.put("SkullOwner", skull);
        skull.putString("Name", "Enderman");
        skull.putUniqueId("Id", UUID.fromString("40ffb372-12f6-4678-b3f2-2176bf56dd4b"));
        CompoundNBT properties = new CompoundNBT();
        skull.put("Properties", properties);

        ListNBT textures = new ListNBT();
        properties.put("textures", textures);
        textures.add(StringNBT.valueOf("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1OWJiMGE3YTMyOTY1YjNkOTBkOGVhZmE4OTlkMTgzNWY0MjQ1MDllYWRkNGU2YjcwOWFkYTUwYjljZiJ9fX0="));
    }

    public static void writePigmanSkull(CompoundNBT nbt) {
        CompoundNBT skull = new CompoundNBT();
        nbt.put("SkullOwner", skull);
        skull.putString("Name", "Zombified Piglin");
        skull.putUniqueId("Id", UUID.fromString("18a2bb50-334a-4084-9184-2c380251a24b"));
        CompoundNBT properties = new CompoundNBT();
        skull.put("Properties", properties);

        ListNBT textures = new ListNBT();
        properties.put("textures", textures);
        textures.add(StringNBT.valueOf("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRlOWM2ZTk4NTgyZmZkOGZmOGZlYjMzMjJjZDE4NDljNDNmYjE2YjE1OGFiYjExY2E3YjQyZWRhNzc0M2ViIn19fQ==="));
    }

    public static void writeBlazeSkull(CompoundNBT nbt) {
        CompoundNBT skull = new CompoundNBT();
        nbt.put("SkullOwner", skull);
        skull.putString("Name", "Blaze");
        skull.putUniqueId("Id", UUID.fromString("4c38ed11-596a-4fd4-ab1d-26f386c1cbac"));
        CompoundNBT properties = new CompoundNBT();
        skull.put("Properties", properties);

        ListNBT textures = new ListNBT();
        properties.put("textures", textures);
        textures.add(StringNBT.valueOf("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==="));
    }
}
