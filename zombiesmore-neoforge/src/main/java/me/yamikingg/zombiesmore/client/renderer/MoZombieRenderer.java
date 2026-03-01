package me.yamikingg.zombiesmore.client.renderer;

import me.yamikingg.zombiesmore.ZombiesMore;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoZombieRenderer<T extends Zombie> extends AbstractZombieRenderer<T, ZombieModel<T>> {
    private final ResourceLocation texture;

    public MoZombieRenderer(EntityRendererProvider.Context context, String name) {
        this(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR, name);
    }

    public MoZombieRenderer(EntityRendererProvider.Context context,
                            ModelLayerLocation layer,
                            ModelLayerLocation innerArmorLayer,
                            ModelLayerLocation outerArmorLayer,
                            String name) {
        super(context,
                new ZombieModel<>(context.bakeLayer(layer)),
                new ZombieModel<>(context.bakeLayer(innerArmorLayer)),
                new ZombieModel<>(context.bakeLayer(outerArmorLayer)));
        this.texture = ResourceLocation.fromNamespaceAndPath(ZombiesMore.MODID, "textures/entity/" + name + ".png");
    }

    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        return this.texture;
    }
}