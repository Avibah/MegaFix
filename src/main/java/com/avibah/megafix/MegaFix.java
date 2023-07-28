package com.avibah.megafix;

import com.pixelmonmod.pixelmon.api.config.PixelmonConfigProxy;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;

@Mod("megafix")
public class MegaFix
{
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean fixEnabled = false;

    private LegendarySpawner spawner;
    private float tickRatio;
    private float chanceRatio;
    private long prevSpawnTime;

    public MegaFix() {
        LOGGER.info("Initializing MegaFix...");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC, "megafix.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        // enable bosses and prep values on first tick
        if (!fixEnabled) {
            spawner = PixelmonSpawning.megaBossSpawner;
            spawner.firesChooseEvent = true;

            int legendaryTicks = PixelmonConfigProxy.getSpawning().getLegendarySpawnTicks();
            int bossTicks = PixelmonConfigProxy.getSpawning().getBossSpawning().getBossSpawnTicks();

            float legendaryChance = PixelmonConfigProxy.getSpawning().getLegendarySpawnChance();
            float bossChance = PixelmonConfigProxy.getSpawning().getBossSpawning().getBossSpawnChance();

            tickRatio = (float)bossTicks / legendaryTicks;
            chanceRatio = AdjustForChance.get() ? legendaryChance / bossChance : 1;

            prevSpawnTime = spawner.nextSpawnTime;

            fixEnabled = true;
            LOGGER.info("Enabled Mega Boss fix");
        }

        // casual fighting the spawner frequency logic
        if (spawner.nextSpawnTime != prevSpawnTime) {
            long diff = spawner.nextSpawnTime - spawner.lastCycleTime;

            spawner.nextSpawnTime = spawner.lastCycleTime + (long)(diff * tickRatio * chanceRatio);
            prevSpawnTime = spawner.nextSpawnTime;
        }
    }


    // config
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> AdjustForChance;

    static {
        BUILDER.push("MegaFix Config");

        AdjustForChance = BUILDER.comment("Enable adjusting the Mega Boss spawn time for its percent chance relative to Legendary spawning").define("AdjustForChance", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
