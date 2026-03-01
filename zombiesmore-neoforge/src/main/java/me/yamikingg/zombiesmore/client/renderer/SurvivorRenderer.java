package me.yamikingg.zombiesmore.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.client.model.SurvivorModel;
import me.yamikingg.zombiesmore.entity.Survivor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SurvivorRenderer extends HumanoidMobRenderer<Survivor, SurvivorModel<Survivor>> {
    private static final ResourceLocation MALE_TEXTURE =
            ResourceLocation.tryBuild(ZombiesMore.MODID, "textures/entity/survivor_0.png");
    private static final ResourceLocation FEMALE_TEXTURE =
            ResourceLocation.tryBuild(ZombiesMore.MODID, "textures/entity/survivor_1.png");

    private final SurvivorModel<Survivor> normalModel;
    private final SurvivorModel<Survivor> slimModel;

    public SurvivorRenderer(EntityRendererProvider.Context context) {
        super(context, new SurvivorModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);

        this.normalModel = this.getModel();
        this.slimModel = new SurvivorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM));

        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public void render(Survivor survivor, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        int type = survivor.getSurvivorType();
        this.model = (type >= 1) ? slimModel : normalModel;
        super.render(survivor, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Survivor entity) {
        return (entity.getSurvivorType() >= 1) ? FEMALE_TEXTURE : MALE_TEXTURE;
    }
}