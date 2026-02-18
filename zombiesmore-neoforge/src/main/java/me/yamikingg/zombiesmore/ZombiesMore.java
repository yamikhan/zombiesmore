package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.entity.AbstractMoZombie;
import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import me.yamikingg.zombiesmore.init.Registration;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;  // ← Contains the Type enum
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ZombiesMore.MODID)
public class ZombiesMore {
	public static final String MODID = "zombiesmore_yamikingg";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public ZombiesMore(ModContainer container) {
		IEventBus modEventBus = container.getEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::registerSpawnPlacements);

		Registration.init(modEventBus);
		Config.register();
		Config.loadConfig(Config.CLIENT_CONFIG,
				net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml"));
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LOGGER.info("Common setup completed");
		});
	}

	// ✅ FIXED: Use SpawnPlacements.Type for NeoForge 1.20.2
	private void registerSpawnPlacements(final SpawnPlacementRegisterEvent event) {
		LOGGER.info("Registering spawn placements with NeoForge event...");

		// Helper method to reduce repetition
		registerMonsterSpawn(event, Registration.DISCO_ZOMBIE.get());
		registerMonsterSpawn(event, Registration.NETHER_ZOMBIE.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_DWARF.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_CHEF.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_CYBORG.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_HEROBRINE.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_KING.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_KNIGHT.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_MINER.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_PA.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_PIRATE.get());
		registerMonsterSpawn(event, Registration.ZOMBIE_NOTCH.get());

		// Special cases with custom spawn predicates
		event.register(Registration.ZOMBIE_CREEPER.get(),
				SpawnPlacements.Type.ON_GROUND,  // ✅ CORRECT: SpawnPlacements.Type
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ZombieCreeper::checkMonsterSpawnRules,
				SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(Registration.SURVIVOR.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Survivor::checkMobSpawnRules,
				SpawnPlacementRegisterEvent.Operation.REPLACE);

		LOGGER.info("Spawn placements registered successfully!");
	}

	// ✅ Helper method to avoid code duplication
	private <T extends AbstractMoZombie> void registerMonsterSpawn(
			SpawnPlacementRegisterEvent event, EntityType<T> entityType) {
		event.register(entityType,
				SpawnPlacements.Type.ON_GROUND,  // ✅ SpawnPlacements.Type for 1.20.2
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				AbstractMoZombie::checkMonsterSpawnRules,
				SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

	private void registerAttributes(final EntityAttributeCreationEvent event) {
		LOGGER.info("Registering entity attributes...");

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
		event.put(Registration.ZOMBIE_NOTCH.get(),
				me.yamikingg.zombiesmore.entity.ZombieNotch.createAttributes().build());

		LOGGER.info("Entity attributes registered successfully!");
	}
}