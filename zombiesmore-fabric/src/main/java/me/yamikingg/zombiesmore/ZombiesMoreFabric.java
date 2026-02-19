package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.entity.AbstractMoZombie;
import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import me.yamikingg.zombiesmore.init.Registration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
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

        // Survivor
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(), // or any biome selector
                MobCategory.CREATURE,                         // CREATURE since it's neutral/passive-ish
                Registration.SURVIVOR,
                130, 1, 4
        );
        // Disco Zombie
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.DISCO_ZOMBIE,
                160, 1, 4
        );

        // Zombie Dwarf
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_DWARF,
                100, 1, 5
        );

        // Zombie Chef
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_CHEF,
                180, 1, 4
        );

        // Nether Zombie
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInTheNether(),
                MobCategory.MONSTER,
                Registration.NETHER_ZOMBIE,
                160, 1, 4
        );
        // Zombie PA
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_PA,
                150, 1, 2
        );
        // Zombie Creeper
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_CREEPER,
                130, 1, 3
        );
        // Zombie Heroprine
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_HEROBRINE,
                4, 1, 1
        );
        // Zombie Notch
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_NOTCH,
                4, 1, 1
        );
        // Zombie King
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_KING,
                65, 1, 2
        );
        // Zombie Knight
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_KNIGHT,
                95, 1, 4
        );
        // Zombie Cyborg
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_CYBORG,
                150, 1, 4
        );
        // Zombie Miner
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_MINER,
                140, 2, 7
        );
        // Zombie Pirate
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                Registration.ZOMBIE_PIRATE,
                75, 1, 3
        );

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
                Survivor::checkSurvivorSpawnRules);

        LOGGER.info("Spawn placements registered successfully!");
    }
}