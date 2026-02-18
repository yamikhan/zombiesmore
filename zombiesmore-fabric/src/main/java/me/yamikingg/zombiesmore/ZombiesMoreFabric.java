package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.entity.AbstractMoZombie;
import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import me.yamikingg.zombiesmore.init.Registration;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ZombiesMoreFabric implements ModInitializer {
    public static final String MODID = "zombiesmore_yamikingg";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing ZombiesMore...");

        Registration.init();
        Registration.registerEntityAttributes();

        registerSpawnPlacements();

        LOGGER.info("ZombiesMore initialized successfully!");
    }

    private void registerSpawnPlacements() {
        LOGGER.info("Registering spawn placements...");

        SpawnPlacements.register(Registration.DISCO_ZOMBIE,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.NETHER_ZOMBIE,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_DWARF,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_CHEF,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_CYBORG,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_HEROBRINE,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_KING,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_KNIGHT,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_MINER,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_PA,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_PIRATE,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_CREEPER,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ZombieCreeper::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.SURVIVOR,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Survivor::checkMobSpawnRules);

        LOGGER.info("Spawn placements registered successfully!");
    }
}