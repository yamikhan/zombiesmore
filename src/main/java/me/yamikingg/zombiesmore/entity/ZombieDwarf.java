
package me.yamikingg.zombiesmore.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import me.yamikingg.zombiesmore.ZombiesMore;

public class ZombieDwarf extends AbstractMoZombie {
		public ZombieDwarf(EntityType<? extends AbstractMoZombie> entityType, Level world) {
			super(entityType,world);
		}

		public static int ID = 15;
		public static String name = "zombie_dwarf";

		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 60.0D).add(Attributes.MOVEMENT_SPEED, (double)0.4F).add(Attributes.ATTACK_DAMAGE, 1D).add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,15D);
		}
		public static class RendererZombieDwarf extends HumanoidMobRenderer<AbstractMoZombie, ZombieDwarfModel<AbstractMoZombie>>
		{
			public final ResourceLocation TEXTURE = new ResourceLocation(ZombiesMore.MODID, "textures/entity/" + name +".png");

			@Override
			public ResourceLocation getTextureLocation(AbstractMoZombie p_110775_1_) {
				return TEXTURE;
			}
			public RendererZombieDwarf(EntityRendererProvider.Context p_174458_){
				this(p_174458_, ZombieDwarfModel.DWARF, ZombieDwarfModel.DWARF_INNER, ZombieDwarfModel.DWARF_OUTER);
			}

			public RendererZombieDwarf(EntityRendererProvider.Context p_174458_, ModelLayerLocation layerLocation, ModelLayerLocation innerLayerLocation, ModelLayerLocation outerLayerLocation) {
				super(p_174458_, new ZombieDwarfModel<>(p_174458_.bakeLayer(layerLocation)), 0.5F);
				this.addLayer(new HumanoidArmorLayer<>(this, new ZombieDwarfModel<>(p_174458_.bakeLayer(innerLayerLocation)), new ZombieDwarfModel<>(p_174458_.bakeLayer(outerLayerLocation))));
			}

			protected boolean isShaking(AbstractMoZombie p_230495_1_) {
				return p_230495_1_.isUnderWaterConverting();
			}

		}

	@Override
	protected float getStandingEyeHeight(Pose p_34313_, EntityDimensions p_34314_) {
		return this.isBaby() ? 0.80F : 1.51F;
	}


		public static class ZombieDwarfModel<T extends AbstractMoZombie> extends HumanoidModel<T> {

			public boolean isAggressive(T p_212850_1_) {
				return p_212850_1_.isAggressive();
			}

			public static final ModelLayerLocation DWARF = new ModelLayerLocation(new ResourceLocation(ZombiesMore.MODID, name), "main");
			public static final ModelLayerLocation DWARF_INNER = new ModelLayerLocation(new ResourceLocation(ZombiesMore.MODID, name + "_inner"), "main");
			public static final ModelLayerLocation DWARF_OUTER = new ModelLayerLocation(new ResourceLocation(ZombiesMore.MODID, name + "_outer"), "main");

			public ZombieDwarfModel(ModelPart root) {
				super(root);
			}

			public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation, int y) {
				MeshDefinition meshdefinition = new MeshDefinition();
				PartDefinition partdefinition = meshdefinition.getRoot();

				PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation)
						.texOffs(28, 0).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0F))
						.texOffs(24, 2).addBox(-1.0F, 5.0F, -4.0F, 2.0F, 3.0F, 1.0F,new CubeDeformation(0F))
						.texOffs(24, 2).addBox(2.0F, 0.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

				PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 4.0F, 0.0F));

				PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 10.0F, 4.0F, cubeDeformation), PartPose.offset(0.0F, 9.0F, 0.0F));

				PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, cubeDeformation).mirror(), PartPose.offset(-5.0F, 2.0F, 0.0F));
				PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, cubeDeformation), PartPose.offset(5.0F, 2.0F, 0.0F));

				PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(1.9F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, cubeDeformation).mirror(false), PartPose.offset(-1.9F, 14.0F, 0.0F));
				PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-5.9F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, cubeDeformation), PartPose.offset(1.9F, 14.0F, 0.0F));

				return LayerDefinition.create(meshdefinition, 64, y);
			}

			@Override
			public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
				super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				if (this.crouching) {
					this.body.xRot = 0.5F;
					this.rightArm.xRot += 0.4F;
					this.leftArm.xRot += 0.4F;
					this.rightLeg.z = 4.0F;
					this.leftLeg.z = 4.0F;
					this.rightLeg.y = 12.2F;
					this.leftLeg.y = 12.2F;
					this.head.y = 8.2F;
					this.body.y = 3.2F;
					this.leftArm.y = 5.2F;
					this.rightArm.y = 5.2F;
				} else {
					this.body.xRot = 0.0F;
					this.rightLeg.z = 0.1F;
					this.leftLeg.z = 0.1F;
					this.rightLeg.y = 14.0F;
					this.leftLeg.y = 14.0F;
					this.head.y = 4F;
					this.body.y = 9.0F;
					this.leftArm.y = 6.0F;
					this.rightArm.y = 6.0F;
				}
				AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(entity), this.attackTime, ageInTicks);
			}

			@Override
			public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
				super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
			}


		}
	}

