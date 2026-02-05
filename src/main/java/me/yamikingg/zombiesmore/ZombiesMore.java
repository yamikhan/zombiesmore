package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.entity.AbstractMoZombie;
import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import me.yamikingg.zombiesmore.init.Registration;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ZombiesMore.MODID)
public class ZombiesMore {
	public static final String MODID = "zombiesmore_yamikingg";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public ZombiesMore() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register setup and attribute creation events
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::registerAttributes);

		// Initialize registration
		Registration.init(modEventBus);

		// Load config
		Config.register();
		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml"));
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LOGGER.info("Registering spawn placements...");

			// Register spawn placements for all your entities
			SpawnPlacements.register(Registration.DISCO_ZOMBIE.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.NETHER_ZOMBIE.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_DWARF.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_CHEF.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_CYBORG.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_HEROBRINE.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_KING.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_KNIGHT.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_MINER.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_PA.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_PIRATE.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					AbstractMoZombie::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.ZOMBIE_CREEPER.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					ZombieCreeper::checkMonsterSpawnRules);

			SpawnPlacements.register(Registration.SURVIVOR.get(),
					SpawnPlacements.Type.ON_GROUND,
					Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					Survivor::checkMobSpawnRules);

			LOGGER.info("Spawn placements registered successfully!");
		});
	}

	private void registerAttributes(final EntityAttributeCreationEvent event) {
		LOGGER.info("Registering entity attributes...");

		// Register attributes for all your entities
		// Make sure each entity class has a createAttributes() method
		event.put(Registration.DISCO_ZOMBIE.get(),
				me.yamikingg.zombiesmore.entity.DiscoZombie.createAttributes().build());

		event.put(Registration.NETHER_ZOMBIE.get(),
				me.yamikingg.zombiesmore.entity.NetherZombie.createAttributes().build());

		event.put(Registration.ZOMBIE_DWARF.get(),
				me.yamikingg.zombiesmore.entity.ZombieDwarf.createAttributes().build());

		event.put(Registration.ZOMBIE_CHEF.get(),
				me.yamikingg.zombiesmore.entity.ZombieChef.createAttributes().build());

		event.put(Registration.ZOMBIE_CYBORG.get(),
				me.yamikingg.zombiesmore.entity.ZombieCyborg.createAttributes().build());

		event.put(Registration.ZOMBIE_HEROBRINE.get(),
				me.yamikingg.zombiesmore.entity.ZombieHerobrine.createAttributes().build());

		event.put(Registration.ZOMBIE_KING.get(),
				me.yamikingg.zombiesmore.entity.ZombieKing.createAttributes().build());

		event.put(Registration.ZOMBIE_KNIGHT.get(),
				me.yamikingg.zombiesmore.entity.ZombieKnight.createAttributes().build());

		event.put(Registration.ZOMBIE_MINER.get(),
				me.yamikingg.zombiesmore.entity.ZombieMiner.createAttributes().build());

		event.put(Registration.ZOMBIE_PA.get(),
				me.yamikingg.zombiesmore.entity.ZombiePa.createAttributes().build());

		event.put(Registration.ZOMBIE_PIRATE.get(),
				me.yamikingg.zombiesmore.entity.ZombiePirate.createAttributes().build());

		event.put(Registration.ZOMBIE_CREEPER.get(),
				me.yamikingg.zombiesmore.entity.ZombieCreeper.createAttributes().build());

		event.put(Registration.SURVIVOR.get(),
				me.yamikingg.zombiesmore.entity.Survivor.createAttributes().build());

		LOGGER.info("Entity attributes registered successfully!");
	}
}