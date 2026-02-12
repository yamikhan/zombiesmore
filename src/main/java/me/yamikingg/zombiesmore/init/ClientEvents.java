package me.yamikingg.zombiesmore.init;


import com.google.common.collect.ImmutableMap;
import me.yamikingg.zombiesmore.entity.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import me.yamikingg.zombiesmore.Config;
import me.yamikingg.zombiesmore.ZombiesMore;


@Mod.EventBusSubscriber( modid = ZombiesMore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {


	@SubscribeEvent
	public static void entityRenderEvent(EntityRenderersEvent.RegisterRenderers event) {

		EntityRendererProvider<AbstractMoZombie> dwarfRenderFactory = ZombieDwarf.RendererZombieDwarf::new;
		if (Config.oldDwarfZombieModel.get())  dwarfRenderFactory = manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieDwarf.NAME);
		event.registerEntityRenderer(Registration.DISCO_ZOMBIE.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, DiscoZombie.NAME));
		event.registerEntityRenderer(Registration.NETHER_ZOMBIE.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, NetherZombie.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_CHEF.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieChef.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_CYBORG.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieCyborg.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_HEROBRINE.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieHerobrine.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_KING.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieKing.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_KNIGHT.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieKnight.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_MINER.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieMiner.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_NOTCH.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombieNotch.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_PA.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombiePa.NAME));
		event.registerEntityRenderer(Registration.ZOMBIE_PIRATE.get(), manager -> new AbstractMoZombie.MoZombieRenderer(manager, ZombiePirate.NAME));
		event.registerEntityRenderer(Registration.SURVIVOR.get(), Survivor.SurvivorRenderer::new);
		event.registerEntityRenderer(Registration.ZOMBIE_CREEPER.get(), ZombieCreeper.CreeperRenderer::new);
		event.registerEntityRenderer(Registration.ZOMBIE_DWARF.get(), dwarfRenderFactory);
		event.registerEntityRenderer(Registration.ZOMBIE_EVOKER.get(), manager -> new net.minecraft.client.renderer.entity.EvokerRenderer(manager){
			@Override
			public ResourceLocation getTextureLocation(Entity entity) {
				return new ResourceLocation(ZombiesMore.MODID, "textures/entity/zombie_evoker.png");
			}
		});
	}
	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {

		ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();

		event.registerLayerDefinition(ZombieDwarf.ZombieDwarfModel.DWARF, () -> ZombieDwarf.ZombieDwarfModel.createBodyLayer(new CubeDeformation(0), 64));
		event.registerLayerDefinition(ZombieDwarf.ZombieDwarfModel.DWARF_INNER, () -> ZombieDwarf.ZombieDwarfModel.createBodyLayer(new CubeDeformation(0.5F), 32));
		event.registerLayerDefinition(ZombieDwarf.ZombieDwarfModel.DWARF_OUTER, () -> ZombieDwarf.ZombieDwarfModel.createBodyLayer(new CubeDeformation(1F), 32));
	}
}
