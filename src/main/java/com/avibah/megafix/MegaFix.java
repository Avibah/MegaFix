package com.avibah.megafix;

import com.pixelmonmod.pixelmon.api.config.PixelmonConfigProxy;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    private long prevSpawnTime;

    public MegaFix() {
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

            tickRatio = (float)bossTicks / legendaryTicks;
            prevSpawnTime = spawner.nextSpawnTime;

            fixEnabled = true;
            LOGGER.info("Enabled Mega Boss fix");
        }

        // casual fighting the spawner frequency logic
        if (spawner.nextSpawnTime != prevSpawnTime) {
            long diff = spawner.nextSpawnTime - spawner.lastCycleTime;

            spawner.nextSpawnTime = spawner.lastCycleTime + (long)(diff * tickRatio);
            prevSpawnTime = spawner.nextSpawnTime;
        }
    }
}
