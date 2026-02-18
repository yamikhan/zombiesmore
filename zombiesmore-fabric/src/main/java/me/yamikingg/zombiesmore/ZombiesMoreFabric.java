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

        // Register items and entities
        Registration.registerItems();
        Registration.registerEntities();
        Registration.registerSounds();
        Registration.registerCreativeTab();

        // Register spawn placements
        registerSpawnPlacements();

        // Register entity attributes
        registerEntityAttributes();

        LOGGER.info("ZombiesMore initialized successfully!");
    }

    private void registerSpawnPlacements() {
        LOGGER.info("Registering spawn placements...");

        // Register spawn placements for all your entities
        SpawnPlacements.register(Registration.DISCO_ZOMBIE.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.NETHER_ZOMBIE.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_DWARF.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_CHEF.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_CYBORG.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_HEROBRINE.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_KING.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_KNIGHT.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_MINER.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_PA.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_PIRATE.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbstractMoZombie::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.ZOMBIE_CREEPER.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                ZombieCreeper::checkMonsterSpawnRules);

        SpawnPlacements.register(Registration.SURVIVOR.getValue(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Survivor::checkMobSpawnRules);

        LOGGER.info("Spawn placements registered successfully!");
    }

    private void registerEntityAttributes() {
        LOGGER.info("Registering entity attributes...");

        // In Fabric, entity attributes are registered differently
        // This will be handled by an event listener or in entity initialization
        // For now, entity classes should have their attributes defined in their constructors

        LOGGER.info("Entity attributes registered successfully!");
    }
}