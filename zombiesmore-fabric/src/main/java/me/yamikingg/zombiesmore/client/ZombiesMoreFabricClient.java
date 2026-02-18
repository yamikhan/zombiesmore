package me.yamikingg.zombiesmore;

import me.yamikingg.zombiesmore.entity.Survivor;
import me.yamikingg.zombiesmore.init.Registration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ZombiesMoreFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register model layer
        net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.registerModelLayer(
                Survivor.SurvivorModel.LAYER_LOCATION,
                Survivor.SurvivorModel::createBodyLayer // You'll need to add this static method to SurvivorModel
        );

        // Register entity renderer
        EntityRendererRegistry.register(Registration.SURVIVOR, Survivor.SurvivorRenderer::new);
    }
}