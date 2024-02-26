package dev.infernal_coding.flamesenchants;

import dev.infernal_coding.flamesenchants.enchantments.custom.*;
import dev.infernal_coding.flamesenchants.enchantments.normal.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {

    public static void register(IEventBus bus) {
        enchants.register(bus);
    }
    public static final EquipmentSlotType[] ARMOR_SLOTS = {EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};

    static final DeferredRegister<Enchantment> enchants = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FlamesEnchants.MODID);

    public static final RegistryObject<Enchantment> ADVANCED_AQUA_AFFINITY = enchants.register("advanced_aqua_affinity", () ->
           new AdvancedAquaAffinity(Enchantment.Rarity.RARE, ARMOR_SLOTS)
    );

    public static final RegistryObject<Enchantment> SUPREME_AQUA_AFFINITY = enchants.register("supreme_aqua_affinity", () ->
            new SupremeAquaAffinity(Enchantment.Rarity.VERY_RARE, ARMOR_SLOTS)
    );

    public static final RegistryObject<Enchantment> ADVANCED_BANE_OF_ANTHROPODS = enchants.register("advanced_bane_of_anthropods", () ->
            new AdvancedBaneOfAnthropods(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND)
    );

    public static final RegistryObject<Enchantment> ADVANCED_BLAST_PROTECTION = enchants.register("advanced_blast_protection", () ->
            new AdvancedBlastProtection(Enchantment.Rarity.RARE, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> ADVANCED_CHANNELING = enchants.register("advanced_channeling", () ->
            new AdvancedChanneling(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));

    public static final RegistryObject<Enchantment> SUPREME_CHANNELING = enchants.register("supreme_channeling", () ->
            new SupremeChanneling(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));

    public static final RegistryObject<Enchantment> ADVANCED_DEPTH_STRIDER = enchants.register("advanced_depth_strider", () ->
            new AdvancedDepthStrider(Enchantment.Rarity.VERY_RARE));

    public static final RegistryObject<Enchantment> SUPREME_DEPTH_STRIDER = enchants.register("supreme_depth_strider", () ->
            new SupremeDepthStrider(Enchantment.Rarity.VERY_RARE));

    public static final RegistryObject<Enchantment> ADVANCED_EFFICIENCY = enchants.register("advanced_efficiency", () ->
            new AdvancedEfficiency(Enchantment.Rarity.RARE));

    public static final RegistryObject<Enchantment> SUPREME_EFFICIENCY = enchants.register("supreme_efficiency", () ->
            new SupremeEfficiency(Enchantment.Rarity.VERY_RARE));

    public static final RegistryObject<Enchantment> ADVANCED_FIRE_ASPECT = enchants.register("advanced_fire_aspect", () ->
            new AdvancedFireAspect(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));

    public static final RegistryObject<Enchantment> SUPREME_FIRE_ASPECT = enchants.register("supreme_fire_aspect", () ->
            new SupremeFireAspect(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));

    public static final RegistryObject<Enchantment> ADVANCED_FIRE_PROTECTION = enchants.register("advanced_fire_protection", () ->
            new AdvancedFireProtection(Enchantment.Rarity.RARE, ARMOR_SLOTS));

    public static final RegistryObject<Enchantment> ADVANCED_FLAME = enchants.register("advanced_flame", () ->
            new AdvancedFlame(Enchantment.Rarity.VERY_RARE));

    public static final RegistryObject<Enchantment> SUPREME_FLAME = enchants.register("supreme_flame", () ->
            new SupremeFlame(Enchantment.Rarity.VERY_RARE));
    public static final RegistryObject<Enchantment> ADVANCED_FORTUNE = enchants.register("advanced_fortune", () ->
            new AdvancedFortune(Enchantment.Rarity.RARE, EnchantmentType.DIGGER, EquipmentSlotType.MAINHAND));
    public static final RegistryObject<Enchantment> SUPREME_FORTUNE = enchants.register("supreme_fortune", () ->
            new SupremeFortune(Enchantment.Rarity.VERY_RARE, EnchantmentType.DIGGER, EquipmentSlotType.MAINHAND));

    public static final RegistryObject<Enchantment> ADVANCED_FROST_WALKER = enchants.register("advanced_frost_walker", () ->
            new AdvancedFrostWalker(Enchantment.Rarity.RARE, EquipmentSlotType.FEET));

    public static final RegistryObject<Enchantment> SUPREME_FROST_WALKER = enchants.register("supreme_frost_walker", () ->
            new SupremeFrostWalker(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET));
    public static final RegistryObject<Enchantment> ADVANCED_IMPALING = enchants.register("advanced_impaling", AdvancedImpaling::new);
    public static final RegistryObject<Enchantment> ADVANCED_LOOTING = enchants.register("advanced_looting", AdvancedLooting::new);
    public static final RegistryObject<Enchantment> SUPREME_LOOTING = enchants.register("supreme_looting", SupremeLooting::new);
    public static final RegistryObject<Enchantment> ADVANCED_LUCK_OF_THE_SEA = enchants.register("advanced_luck_of_the_sea", AdvancedLuckOfTheSea::new);
    public static final RegistryObject<Enchantment> SUPREME_LUCK_OF_THE_SEA = enchants.register("supreme_luck_of_the_sea", SupremeLuckOfTheSea::new);
    public static final RegistryObject<Enchantment> ADVANCED_LURE = enchants.register("advanced_lure", AdvancedLure::new);
    public static final RegistryObject<Enchantment> SUPREME_LURE = enchants.register("supreme_lure", SupremeLure::new);
    public static final RegistryObject<Enchantment> ADVANCED_MENDING = enchants.register("advanced_mending", AdvancedMending::new);

    public static final RegistryObject<Enchantment> ADVANCED_MULTISHOT = enchants.register("advanced_multishot", AdvancedMultishot::new);
    public static final RegistryObject<Enchantment> SUPREME_MULTISHOT = enchants.register("supreme_multishot", SupremeMultishot::new);

    public static final RegistryObject<Enchantment> ADVANCED_PIERCING = enchants.register("advanced_piercing", AdvancedPiercing::new);
    public static final RegistryObject<Enchantment> SUPREME_PIERCING = enchants.register("supreme_piercing", SupremePiercing::new);
    public static final RegistryObject<Enchantment> ADVANCED_PROJECTILE_PROTECTION = enchants.register("advanced_projectile_protection", AdvancedProjectileProtection::new);
    public static final RegistryObject<Enchantment> ADVANCED_PROTECTION = enchants.register("advanced_protection", AdvancedProtection::new);
    public static final RegistryObject<Enchantment> ADVANCED_PUNCH = enchants.register("advanced_punch", AdvancedPunch::new);
    public static final RegistryObject<Enchantment> SUPREME_PUNCH = enchants.register("supreme_punch", SupremePunch::new);
    public static final RegistryObject<Enchantment> ADVANCED_QUICK_CHARGE = enchants.register("advanced_quick_charge", AdvancedQuickCharge::new);
    public static final RegistryObject<Enchantment> SUPREME_QUICK_CHARGE = enchants.register("supreme_quick_charge", SupremeQuickCharge::new);
    public static final RegistryObject<Enchantment> ADVANCED_RESPIRATION = enchants.register("advanced_respiration", AdvancedRespiration::new);
    public static final RegistryObject<Enchantment> SUPREME_RESPIRATION = enchants.register("supreme_respiration", SupremeRespiration::new);
    public static final RegistryObject<Enchantment> ADVANCED_SHARPNESS = enchants.register("advanced_sharpness", AdvancedSharpness::new);
    public static final RegistryObject<Enchantment> SUPREME_SHARPNESS = enchants.register("supreme_sharpness", SupremeSharpness::new);

    public static final RegistryObject<Enchantment> ADVANCED_SMITE = enchants.register("advanced_smite", AdvancedSmite::new);
    public static final RegistryObject<Enchantment> SUPREME_SMITE = enchants.register("supreme_smite", SupremeSmite::new);
    public static final RegistryObject<Enchantment> ADVANCED_SOUL_SPEED = enchants.register("advanced_soul_speed", AdvancedSoulSpeed::new);
    public static final RegistryObject<Enchantment> SUPREME_SOUL_SPEED = enchants.register("supreme_soul_speed", SupremeSoulSpeed::new);
    public static final RegistryObject<Enchantment> ADVANCED_SWEEPING = enchants.register("advanced_sweeping", AdvancedSweepingEdge::new);
    public static final RegistryObject<Enchantment> SUPREME_SWEEPING = enchants.register("supreme_sweeping", SupremeSweepingEdge::new);
    public static final RegistryObject<Enchantment> ADVANCED_THORNS = enchants.register("advanced_thorns", AdvancedThorns::new);
    public static final RegistryObject<Enchantment> SUPREME_THORNS = enchants.register("supreme_thorns", SupremeThorns::new);
    public static final RegistryObject<Enchantment> ADVANCED_UNBREAKING = enchants.register("advanced_unbreaking", AdvancedUnbreaking::new);
    public static final RegistryObject<Enchantment> SUPREME_UNBREAKING = enchants.register("supreme_unbreaking", SupremeUnbreaking::new);

    public static final RegistryObject<Enchantment> ADVANCED_POWER = enchants.register("advanced_power", AdvancedPower::new);


    //Custom

    public static final RegistryObject<Enchantment> ADEPT = enchants.register("adept", Adept::new);
    public static final RegistryObject<Enchantment> ARROW_PIERCING_RUNE = enchants.register("rune_arrow_piercing", RuneArrowPiercing::new);
    public static final RegistryObject<Enchantment> ASH_DESTROYER = enchants.register("ash_destroyer", AshDestroyer::new);
    public static final RegistryObject<Enchantment> ATOMIC_DECONSTRUCTOR = enchants.register("atomic_deconstructor", AtomicDeconstructor::new);
    public static final RegistryObject<Enchantment> BLESSED_EDGE = enchants.register("blessed_edge", BlessedEdge::new);
    public static final RegistryObject<Enchantment> BLUNTNESS = enchants.register("bluntness", Bluntness::new);
    public static final RegistryObject<Enchantment> BRUTALITY = enchants.register("brutality", Brutality::new);
    public static final RegistryObject<Enchantment> BURNING_THORNS = enchants.register("burning_thorns", BurningThorns::new);
    public static final RegistryObject<Enchantment> BUTCHERING = enchants.register("butchering", Butchering::new);
    public static final RegistryObject<Enchantment> CLEAR_SKIES_FAVOR = enchants.register("clear_skies_favor", ClearskiesFavor::new);
    public static final RegistryObject<Enchantment> CRITICAL_STRIKE = enchants.register("critical_strike", CriticalStrike::new);
    public static final RegistryObject<Enchantment> CULLING = enchants.register("culling", Culling::new);
    public static final RegistryObject<Enchantment> CURSE_OF_DECAY = enchants.register("curse_of_decay", CurseOfDecay::new);
    public static final RegistryObject<Enchantment> CURSE_OF_INACCURACY = enchants.register("curse_of_inaccuracy", CurseOfInaccuracy::new);
    public static final RegistryObject<Enchantment> CURSE_OF_POSSESSION = enchants.register("curse_of_possession", CurseOfPossession::new);
    public static final RegistryObject<Enchantment> CURSE_OF_VULNERABILITY = enchants.register("curse_of_vulnerability", CurseOfVulnerability::new);
    public static final RegistryObject<Enchantment> CURSED_EDGE = enchants.register("cursed_edge", CursedEdge::new);
    public static final RegistryObject<Enchantment> DARK_SHADOWS = enchants.register("dark_shadows", DarkShadows::new);
    public static final RegistryObject<Enchantment> DISARMAMENT = enchants.register("disarmament", Disarmament::new);
    public static final RegistryObject<Enchantment> ENVENOMED = enchants.register("envenomed", Envenomed::new);
    public static final RegistryObject<Enchantment> EVASION = enchants.register("evasion", Evasion::new);
    public static final RegistryObject<Enchantment> FLINGING = enchants.register("flinging", Flinging::new);
    public static final RegistryObject<Enchantment> INHUMANE = enchants.register("inhumane", Inhumane::new);
    public static final RegistryObject<Enchantment> INNER_BERSERK = enchants.register("inner_berserk", InnerBerserk::new);
    public static final RegistryObject<Enchantment> INSTABILITY = enchants.register("instability", Instability::new);
    public static final RegistryObject<Enchantment> LEVITATOR = enchants.register("levitator", Levitator::new);
    public static final RegistryObject<Enchantment> LIFE_STEAL = enchants.register("life_steal", Lifesteal::new);
    public static final RegistryObject<Enchantment> LUCK_MAGNIFICATION = enchants.register("luck_magnification", LuckMagnification::new);
    public static final RegistryObject<Enchantment> NATURAL_BLOCKING = enchants.register("natural_blocking", NaturalBlocking::new);
    public static final RegistryObject<Enchantment> PARRY = enchants.register("parry", Parry::new);
    public static final RegistryObject<Enchantment> PENETRATING_EDGE = enchants.register("penetrating_edge", PenetratingEdge::new);
    public static final RegistryObject<Enchantment> POWERLESS = enchants.register("powerless", Powerless::new);
    public static final RegistryObject<Enchantment> RAINS_BESTOWMENT = enchants.register("rains_bestowment", RainsBestowment::new);
    public static final RegistryObject<Enchantment> REVILED_BLADE = enchants.register("reviled_blade", ReviledBlade::new);
    public static final RegistryObject<Enchantment> SPELL_BREAKER = enchants.register("spell_breaker", SpellBreaker::new);
    public static final RegistryObject<Enchantment> STRENGTHENED_VITALITY = enchants.register("strengthened_vitality", StrengthenedVitality::new);
    public static final RegistryObject<Enchantment> SUBJECT_ENGLISH = enchants.register("subject_english", () ->
            new SubjectEnchant(4));

    public static final RegistryObject<Enchantment> SUBJECT_PE = enchants.register("subject_pe", () ->
            new SubjectEnchant(5));

    public static final RegistryObject<Enchantment> UNPREDICTABLE = enchants.register("unpredictable", Unpredictable::new);
    public static final RegistryObject<Enchantment> UPGRADED_POTENTIALS = enchants.register("upgraded_potentials", UpgradedPotentials::new);
    public static final RegistryObject<Enchantment> WATER_ASPECT = enchants.register("water_aspect", WaterAspect::new);

}
