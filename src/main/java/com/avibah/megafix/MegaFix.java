package com.avibah.megafix;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.event.world.WorldEvent;
import com.pixelmonmod.pixelmon.spawning.LegendarySpawner;
import com.pixelmonmod.pixelmon.spawning.PixelmonSpawning;

@Mod("megafix")
public class MegaFix
{
    private static final Logger LOGGER = LogManager.getLogger();

    public MegaFix() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    private void onWorldLoad(WorldEvent.Load event) {
        LegendarySpawner spawner = PixelmonSpawning.megaBossSpawner;
        spawner.firesChooseEvent = true;

        LOGGER.info("Enabled Mega Boss spawner");
    }
}
