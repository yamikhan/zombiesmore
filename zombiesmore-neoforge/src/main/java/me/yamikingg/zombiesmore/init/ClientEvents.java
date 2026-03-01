package me.yamikingg.zombiesmore.init;

import me.yamikingg.zombiesmore.Config;
import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.client.model.ZombieDwarfModel;
import me.yamikingg.zombiesmore.client.renderer.*;
import me.yamikingg.zombiesmore.entity.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = ZombiesMore.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void entityRenderEvent(EntityRenderersEvent.RegisterRenderers event) {
        // Dwarf renderer with config option
        EntityRendererProvider<ZombieDwarf> dwarfRenderFactory;
        if (Config.oldDwarfZombieModel.get()) {
            dwarfRenderFactory = ZombieDwarfRenderer::new;
        } else {
            dwarfRenderFactory = manager -> new MoZombieRenderer(manager, ZombieDwarf.NAME);
        }

        // Register all renderers using the separated client-only classes
        event.registerEntityRenderer(Registration.DISCO_ZOMBIE.get(), manager -> new MoZombieRenderer(manager, DiscoZombie.NAME));
        event.registerEntityRenderer(Registration.NETHER_ZOMBIE.get(), manager -> new MoZombieRenderer(manager, NetherZombie.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_CHEF.get(), manager -> new MoZombieRenderer(manager, ZombieChef.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_CYBORG.get(), manager -> new MoZombieRenderer(manager, ZombieCyborg.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_HEROBRINE.get(), manager -> new MoZombieRenderer(manager, ZombieHerobrine.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_KING.get(), manager -> new MoZombieRenderer(manager, ZombieKing.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_KNIGHT.get(), manager -> new MoZombieRenderer(manager, ZombieKnight.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_MINER.get(), manager -> new MoZombieRenderer(manager, ZombieMiner.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_NOTCH.get(), manager -> new MoZombieRenderer(manager, ZombieNotch.NAME));
        event.registerEntityRenderer(Registration.ZOMBIE_PA.get(), manager -> new MoZombieRenderer(manager, ZombiePa.NAME)); // <-- ADDED THIS LINE
        event.registerEntityRenderer(Registration.ZOMBIE_PIRATE.get(), manager -> new MoZombieRenderer(manager, ZombiePirate.NAME));

        // These now use the separated renderer classes
        event.registerEntityRenderer(Registration.SURVIVOR.get(), SurvivorRenderer::new);
        event.registerEntityRenderer(Registration.ZOMBIE_CREEPER.get(), ZombieCreeperRenderer::new);
        event.registerEntityRenderer(Registration.ZOMBIE_DWARF.get(), dwarfRenderFactory);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ZombieDwarfModel.DWARF, () -> ZombieDwarfModel.createBodyLayer(new CubeDeformation(0), 64));
        event.registerLayerDefinition(ZombieDwarfModel.DWARF_INNER, () -> ZombieDwarfModel.createBodyLayer(new CubeDeformation(0.5F), 32));
        event.registerLayerDefinition(ZombieDwarfModel.DWARF_OUTER, () -> ZombieDwarfModel.createBodyLayer(new CubeDeformation(1F), 32));
    }
}