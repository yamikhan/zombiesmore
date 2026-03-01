package me.yamikingg.zombiesmore.client.renderer;

import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.client.model.ZombieDwarfModel;
import me.yamikingg.zombiesmore.entity.ZombieDwarf;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieDwarfRenderer extends MobRenderer<ZombieDwarf, ZombieDwarfModel> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ZombiesMore.MODID, "textures/entity/" + ZombieDwarf.NAME + ".png");

    public ZombieDwarfRenderer(EntityRendererProvider.Context context) {
        super(context, new ZombieDwarfModel(context.bakeLayer(ZombieDwarfModel.DWARF)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieDwarf entity) {
        return TEXTURE;
    }
}