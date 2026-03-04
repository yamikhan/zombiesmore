package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.entity.AbstractMoZombie;
import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import me.yamikingg.zombiesmore.init.Registration;
import me.yamikingg.zombiesmore.item.DiscoGlassesMaterial;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ZombiesMore.MODID)
public class ZombiesMore {
	public static final String MODID = "zombiesmore_yamikingg";
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	public ZombiesMore(FMLJavaModLoadingContext context) {
		IEventBus modEventBus = context.getModEventBus();

		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(this::registerAttributes);
		modEventBus.addListener(this::registerSpawnPlacements);

		Registration.init(modEventBus);

		net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);

		Config.register();
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LOGGER.info("Common setup complete!");
		});
	}

	private void registerSpawnPlacements(final SpawnPlacementRegisterEvent event) {
		LOGGER.info("Registering spawn placements...");

		registerPlacement(event, Registration.DISCO_ZOMBIE.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.NETHER_ZOMBIE.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_DWARF.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_CHEF.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_CYBORG.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_HEROBRINE.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_KING.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_KNIGHT.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_MINER.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_PA.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_PIRATE.get(), AbstractMoZombie::checkMonsterSpawnRules);
		registerPlacement(event, Registration.ZOMBIE_CREEPER.get(), ZombieCreeper::checkMonsterSpawnRules);
		registerPlacement(event, Registration.SURVIVOR.get(), Survivor::checkMobSpawnRules);

		LOGGER.info("Spawn placements registered successfully!");
	}

	private <T extends Mob> void registerPlacement(
			SpawnPlacementRegisterEvent event,
			EntityType<T> entityType,
			SpawnPlacements.SpawnPredicate<T> predicate
	) {
		event.register(
				entityType,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				predicate,
				SpawnPlacementRegisterEvent.Operation.REPLACE
		);
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