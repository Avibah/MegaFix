package com.avibah.megafix;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.config.PixelmonConfigProxy;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
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
    private MFWormholeSpawner wormholeSpawner;

    private float tickRatio;
    private float chanceRatio;
    private long prevSpawnTime;

    public MegaFix() {
        LOGGER.info("Initializing MegaFix...");

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MFConfig.SPEC, "megafix.toml");
        MinecraftForge.EVENT_BUS.register(this);
        Pixelmon.EVENT_BUS.register(this);

        wormholeSpawner = new MFWormholeSpawner();
        LOGGER.info("Prepared Wormhole spawner");
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        // enable bosses and prep values on first tick
        if (!fixEnabled && MFConfig.EnableMegaBosses.get()) {
            spawner = PixelmonSpawning.megaBossSpawner;
            spawner.firesChooseEvent = true;

            int legendaryTicks = PixelmonConfigProxy.getSpawning().getLegendarySpawnTicks();
            int bossTicks = PixelmonConfigProxy.getSpawning().getBossSpawning().getBossSpawnTicks();

            float legendaryChance = PixelmonConfigProxy.getSpawning().getLegendarySpawnChance();
            float bossChance = PixelmonConfigProxy.getSpawning().getBossSpawning().getBossSpawnChance();

            tickRatio = (float)bossTicks / legendaryTicks;
            chanceRatio = MFConfig.AdjustForChance.get() ? legendaryChance / bossChance : 1;

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

        // run wormhole spawn attempt
        if (MFConfig.EnableWormholes.get())
            wormholeSpawner.runSpawner();
    }
}
