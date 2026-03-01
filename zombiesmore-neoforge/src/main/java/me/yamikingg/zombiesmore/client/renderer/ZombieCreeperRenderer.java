package me.yamikingg.zombiesmore.client.renderer;

import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.entity.ZombieCreeper;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieCreeperRenderer extends CreeperRenderer {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ZombiesMore.MODID, "textures/entity/" + ZombieCreeper.NAME + ".png");

    public ZombieCreeperRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.world.entity.monster.Creeper entity) {
        return TEXTURE;
    }
}