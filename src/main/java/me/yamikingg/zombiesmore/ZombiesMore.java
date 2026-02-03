
package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.init.ModObjects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import me.yamikingg.zombiesmore.entity.AbstractMoZombie;
import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import me.yamikingg.zombiesmore.init.Registration;


@Mod(ZombiesMore.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ZombiesMore {
	public static final String MODID = "zombiesmore_yamikingg";
	public static final String VERSION = "0.1.0";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static IEventBus MOD_EVENT_BUS;


	public ZombiesMore() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		Config.register();
		// Removed the CREATIVE_TABS registration - not needed in 1.19.2
		// ModObjects.CREATIVE_TABS.register(modEventBus);
		MOD_EVENT_BUS = modEventBus;
		MOD_EVENT_BUS.register(Registration.class);
		Registration.init();
		MOD_EVENT_BUS.register(this);

		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-client.toml"));
	}


	@SubscribeEvent
	public static void common(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LOGGER.info("Success started spawn placements register");
			SpawnPlacements.register(Registration.DISCO_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.NETHER_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_DWARF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_CHEF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_CYBORG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_HEROBRINE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(EntityType.GIANT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkAnyLightMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_KING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_KNIGHT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_MINER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_PA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_PIRATE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractMoZombie::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.ZOMBIE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieCreeper::checkMonsterSpawnRules);
			SpawnPlacements.register(Registration.SURVIVOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Survivor::checkMobSpawnRules);
		});
	}
}