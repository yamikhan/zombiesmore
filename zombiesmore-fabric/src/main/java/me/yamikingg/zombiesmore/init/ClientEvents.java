package me.yamikingg.zombiesmore.init;

import com.google.common.collect.ImmutableMap;
import me.yamikingg.zombiesmore.entity.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import me.yamikingg.zombiesmore.Config;

@Environment(EnvType.CLIENT)
public class ClientEvents implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerLayerDefinitions();
	}

	private void registerEntityRenderers() {
		EntityRendererProvider<AbstractMoZombie> dwarfRenderFactory = ZombieDwarf.RendererZombieDwarf::new;
		if (!Config.OLD_DWARF_ZOMBIE_MODEL) {
			dwarfRenderFactory = manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieDwarf.NAME);
		}
		
		EntityRendererRegistry.register(Registration.DISCO_ZOMBIE, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, DiscoZombie.NAME));
		EntityRendererRegistry.register(Registration.NETHER_ZOMBIE, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, NetherZombie.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_CHEF, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieChef.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_CYBORG, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieCyborg.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_HEROBRINE, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieHerobrine.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_KING, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieKing.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_KNIGHT, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieKnight.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_MINER, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieMiner.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_NOTCH, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieNotch.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_PA, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombiePa.NAME));
		EntityRendererRegistry.register(Registration.ZOMBIE_PIRATE, (manager) -> new AbstractMoZombie.MoZombieRenderer(manager, ZombiePirate.NAME));
		EntityRendererRegistry.register(Registration.SURVIVOR, Survivor.SurvivorRenderer::new);
		EntityRendererRegistry.register(Registration.ZOMBIE_CREEPER, ZombieCreeper.CreeperRenderer::new);
		EntityRendererRegistry.register(Registration.ZOMBIE_DWARF, dwarfRenderFactory);
	}

	private void registerLayerDefinitions() {
		// Layer definitions would go here if needed
		// For now, they can be registered during entity rendering
	}
}
