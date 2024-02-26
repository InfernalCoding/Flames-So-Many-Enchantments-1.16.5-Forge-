package dev.infernal_coding.flamesenchants;

import dev.infernal_coding.flamesenchants.enchantments.custom.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FlamesEnchants.MODID)
public class FlamesEnchants
{

    public static final Map<UUID, Integer> respirationMap = new HashMap<>();

    public static final String MODID = "flamesenchants";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public FlamesEnchants() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        Registry.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    /*@SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) throws IOException {
        Path path = event.getServer().getDataDirectory().toPath()
                .resolve(FolderName.PLAYERDATA.getFileName())
                .resolve("drowning_log.csv");
        File file = new File(path.toString());


        if (file.exists()) {
            Files.lines(path).forEach(line -> {
                try {
                    String[] split = line.split(",");
                    UUID id = UUID.fromString(split[0]);
                    int timeUnderWater = Integer.parseInt(split[1]);
                    respirationMap.put(id, timeUnderWater);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    @SubscribeEvent
    public static void onServerClose(FMLServerStoppingEvent event) throws IOException {
        Path path = event.getServer().getDataDirectory().toPath()
                .resolve(FolderName.PLAYERDATA.getFileName())
                .resolve("drowning_log.csv");
        File file = new File(path.toString());

        if (!file.exists()) {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            respirationMap.forEach((id, ticks) -> {
                writer.println(id + "," + ticks);
            });

            writer.close();
        }
    }*/

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void getLootingLevel(LootingLevelEvent event) {
            LivingEntity entity = event.getEntityLiving();

            int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.ADVANCED_LOOTING.get(), entity);
            int i3 = EnchantmentHelper.getMaxEnchantmentLevel(Registry.SUPREME_LOOTING.get(), entity);

            if (i3 > 0) {
                event.setLootingLevel(i3);
            } else if (i2 > 0) event.setLootingLevel(i2);
        }

        @SubscribeEvent
        public static void damageEvents(LivingDamageEvent event) {
            AshDestroyer.handleEnchant(event);
            CurseOfVulnerability.onDamage(event);
            CursedEdge.handleEnchant(event);
            InnerBerserk.handleEnchant(event);
            Instability.onAttack(event);
            NaturalBlocking.naturalBlock(event);
            ReviledBlade.handleEnchant(event);
        }

        @SubscribeEvent
        public static void hurtEvents(LivingHurtEvent event) {
            BlessedEdge.handleEnchant(event);
            Butchering.handleEnchant(event);
            Bluntness.handleEnchant(event);
            ClearskiesFavor.handleEvent(event);
            Culling.handleEvent(event);
            DarkShadows.onEvent(event);
            Disarmament.handleEnchant(event);
            Lifesteal.handlingFirst(event);
            PenetratingEdge.handleEnchant(event);
            RainsBestowment.handleEvent(event);
            SpellBreaker.onHurt(event);
            Unpredictable.handleEnchant(event);
            WaterAspect.handleEnchant(event);
        }

        @SubscribeEvent
        public static void attackEvent(LivingAttackEvent event) {
            AtomicDeconstructor.handleEnchant(event);
            CurseOfInaccuracy.onAttack(event);
            Evasion.handleEnchant(event);
            Parry.handleEnchant(event);
        }
    }
}
