package com.avibah.megafix;

import net.minecraftforge.common.ForgeConfigSpec;

public class MFConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> EnableMegaBosses;
    public static final ForgeConfigSpec.ConfigValue<Boolean> AdjustForChance;

    public static final ForgeConfigSpec.ConfigValue<Boolean> EnableWormholes;
    public static final ForgeConfigSpec.ConfigValue<Integer> WormholeSpawnTicks;
    public static final ForgeConfigSpec.ConfigValue<Double> WormholeSpawnChance;
    public static final ForgeConfigSpec.ConfigValue<Double> WormholeAge;
    public static final ForgeConfigSpec.ConfigValue<Integer> WormholeDistance;


    static {
        BUILDER.push("MegaFix Config");

        EnableMegaBosses = BUILDER.comment("\nEnable spawning Mega Bosses").define("EnableMegaBosses", true);
        AdjustForChance = BUILDER.comment("\nEnable adjusting the Mega Boss spawn time for its percent chance relative to Legendary spawning").define("AdjustForChance", true);

        EnableWormholes = BUILDER.comment("\nEnable spawning wormholes").define("EnableWormholes", true);
        WormholeSpawnTicks = BUILDER.comment("\nSpawn frequency and chance for wormholes").define("WormholeSpawnTicks", 10000);
        WormholeSpawnChance = BUILDER.define("WormholeSpawnChance", 0.3);
        WormholeAge = BUILDER.comment("\nLife span of a spawned wormhole").define("WormholeAge", 2000.0);
        WormholeDistance = BUILDER.comment("\nDistance from player that a wormhole can spawn").define("WormholeDistance", 80);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
