package me.yamikingg.zombiesmore.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.entity.Survivor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SurvivorModel<S extends Survivor> extends HumanoidModel<S> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.tryBuild(ZombiesMore.MODID, Survivor.NAME), "main");

    public SurvivorModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay, int packedColor) {
        this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
        this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
        this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
        this.rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
        this.leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, packedColor);
    }
}