package com.avibah.megafix;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.spawning.SpawnLocation;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.wormholes.SpawnActionWormhole;
import com.pixelmonmod.pixelmon.api.spawning.archetypes.entities.wormholes.SpawnInfoWormhole;
import com.pixelmonmod.pixelmon.api.spawning.conditions.LocationType;
import com.pixelmonmod.pixelmon.api.util.helpers.DimensionHelper;
import com.pixelmonmod.pixelmon.api.util.helpers.RandomHelper;
import com.pixelmonmod.pixelmon.api.world.MutableLocation;
import com.pixelmonmod.pixelmon.entities.WormholeEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MFWormholeSpawner {
    public long lastSpawn;
    public long nextSpawn;

    private double randomMultiplier() {
        return RandomHelper.getRandomNumberBetween(0.6f, 1.4f);
    }

    private ServerPlayerEntity choosePlayer() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        ArrayList<ServerPlayerEntity> players = new ArrayList<>(server.getPlayerList().getPlayers());
        List<ServerPlayerEntity> choose = new ArrayList<>();

        // only allow wormholes in the overworld and ultra space
        while (players.size() > 0) {
            ServerPlayerEntity focus = players.remove(0);
            RegistryKey<World> key = focus.level.dimension();

            if (key == World.OVERWORLD || key == DimensionHelper.getDimension("pixelmon:ultra_space"))
                choose.add(focus);
        }

        if (choose.size() > 0)
            return choose.get(RandomHelper.getRandomNumberBetween(0, choose.size() - 1));

        return null;
    }

    public void doSpawn() {
        ServerPlayerEntity player = choosePlayer();

        if (player != null) {
            // choose position to spawn
            double angle = Math.toRadians(RandomHelper.getRandomNumberBetween(0.0f, 360.0f));
            double x = Math.cos(angle) * randomMultiplier() * MFConfig.WormholeDistance.get() + player.getX();
            double y = RandomHelper.getRandomNumberBetween(0, 30) + 70;
            double z = Math.sin(angle) * randomMultiplier() * MFConfig.WormholeDistance.get() + player.getZ();

            World world = player.level;
            BlockPos pos = new BlockPos((int)x, (int)y, (int)z);
            // make sure block being spawned at is air
            while (!world.getBlockState(pos).getMaterial().equals(Material.AIR)) {
                y += randomMultiplier() * 30;
                pos = new BlockPos((int)x, (int)y, (int)z);
            }

            MutableLocation location = new MutableLocation(world, pos);
            Block block = world.getBlockState(pos).getBlock();
            Biome biome = world.getBiome(pos);

            Set<LocationType> types = new HashSet<>();
            types.add(LocationType.AIR);
            Set<Block> blocks = new HashSet<>();
            blocks.add(block);

            int age = (int)(randomMultiplier() * MFConfig.WormholeAge.get());
            WormholeEntity wormhole = new WormholeEntity(world, x, y, z, age);

            // set up a spawn event for any broadcasting that might use it
            SpawnInfoWormhole spawnInfo = new SpawnInfoWormhole();
            SpawnLocation spawnLocation = new SpawnLocation(
                    player, location, types, block, blocks, biome, true, 0, 0
            );

            SpawnActionWormhole action = (SpawnActionWormhole)spawnInfo.construct(null, spawnLocation);
            SpawnEvent event = new SpawnEvent(null, action);

            // spawn the wormhole!
            if (!Pixelmon.EVENT_BUS.post(event)) {
                world.addFreshEntity(wormhole);
                lastSpawn = System.currentTimeMillis();
            }
        }
    }

    private void setNextSpawn(long current) {
        nextSpawn = current + (long)(randomMultiplier() * 50.0 * MFConfig.WormholeSpawnTicks.get());
    }

    public void runSpawner() {
        long current = System.currentTimeMillis();

        if (nextSpawn <= 0)
            setNextSpawn(current);

        if (current >= nextSpawn) {
            if (RandomHelper.getRandomChance(MFConfig.WormholeSpawnChance.get()))
                doSpawn();

            setNextSpawn(current);
        }
    }
}
