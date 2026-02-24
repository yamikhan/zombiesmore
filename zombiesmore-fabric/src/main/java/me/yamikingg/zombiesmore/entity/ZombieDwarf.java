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
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;
import me.yamikingg.zombiesmore.ZombiesMoreFabric;

public class ZombieDwarf extends AbstractMoZombie {
	public ZombieDwarf(EntityType<? extends AbstractMoZombie> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 15;
	public static String NAME = "zombie_dwarf";


	@Nullable
	public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(
			ServerLevelAccessor level,
			DifficultyInstance difficulty,
			net.minecraft.world.entity.MobSpawnType spawnType,
			@Nullable net.minecraft.world.entity.SpawnGroupData spawnData,
			@Nullable CompoundTag dataTag) {

        return super.finalizeSpawn(level, difficulty, spawnType, spawnData);
	}

	// Optional: Override populateDefaultEquipmentSlots if needed
	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE)); // Or your custom axe
		this.setDropChance(EquipmentSlot.MAINHAND, 0.1F);
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return this.isBaby()
				? super.getDimensions(pose).scale(0.6F)
				: EntityDimensions.scalable(0.7F, 1.3F);
	}


	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE) // ← ADD THIS
				.add(Attributes.FOLLOW_RANGE, 40.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25F)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)
				.add(Attributes.ARMOR, 6.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.6D)
				.add(Attributes.MAX_HEALTH, 25.0D);
	}


	public static class RendererZombieDwarf extends HumanoidMobRenderer<AbstractMoZombie, ZombieDwarfModel<AbstractMoZombie>> {
		public final ResourceLocation TEXTURE = new ResourceLocation(ZombiesMoreFabric.MODID, "textures/entity/" + NAME + ".png");

		@Override
		public ResourceLocation getTextureLocation(AbstractMoZombie entity) {
			return TEXTURE;
		}

		public RendererZombieDwarf(EntityRendererProvider.Context context) {
			this(context, ZombieDwarfModel.DWARF, ZombieDwarfModel.DWARF_INNER, ZombieDwarfModel.DWARF_OUTER);
		}

		public RendererZombieDwarf(EntityRendererProvider.Context context, ModelLayerLocation layerLocation, ModelLayerLocation innerLayerLocation, ModelLayerLocation outerLayerLocation) {
			super(context, new ZombieDwarfModel<>(context.bakeLayer(layerLocation)), 0.5F);
			this.addLayer(new HumanoidArmorLayer<>(this,
					new ZombieDwarfModel<>(context.bakeLayer(innerLayerLocation)),
					new ZombieDwarfModel<>(context.bakeLayer(outerLayerLocation)),
					context.getModelManager()));
		}

		@Override
		protected boolean isShaking(AbstractMoZombie entity) {
			return entity.isUnderWaterConverting();
		}
	}

	public static class ZombieDwarfModel<T extends AbstractMoZombie> extends HumanoidModel<T> {
		public boolean isAggressive(T entity) {
			return entity.isAggressive();
		}

		public static final ModelLayerLocation DWARF = new ModelLayerLocation(new ResourceLocation(ZombiesMoreFabric.MODID, NAME), "main");
		public static final ModelLayerLocation DWARF_INNER = new ModelLayerLocation(new ResourceLocation(ZombiesMoreFabric.MODID, NAME + "_inner"), "main");
		public static final ModelLayerLocation DWARF_OUTER = new ModelLayerLocation(new ResourceLocation(ZombiesMoreFabric.MODID, NAME + "_outer"), "main");

		public ZombieDwarfModel(ModelPart root) {
			super(root);
		}

		public static LayerDefinition createBodyLayer(CubeDeformation cubeDeformation, int y) {
			MeshDefinition meshdefinition = new MeshDefinition();
			PartDefinition partdefinition = meshdefinition.getRoot();

			PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
							.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, cubeDeformation)
							.texOffs(28, 0).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0F))
							.texOffs(24, 2).addBox(-1.0F, 5.0F, -4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0F))
							.texOffs(24, 2).addBox(2.0F, 0.0F, -4.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0F)),
					PartPose.offset(0.0F, 4.0F, 0.0F));

			PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create()
							.texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)),
					PartPose.offset(0.0F, 4.0F, 0.0F));

			PartDefinition helmet = hat.addOrReplaceChild("helmet", CubeListBuilder.create()
							.texOffs(32, 16).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.3F)),
					PartPose.ZERO);

			PartDefinition beard = head.addOrReplaceChild("beard", CubeListBuilder.create()
							.texOffs(0, 32).addBox(-3.0F, 1.0F, -4.5F, 6.0F, 4.0F, 1.0F, cubeDeformation),
					PartPose.ZERO);

			PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create()
							.texOffs(16, 16).addBox(-4.5F, -4.0F, -2.5F, 9.0F, 9.0F, 5.0F, cubeDeformation),
					PartPose.offset(0.0F, 10.0F, 0.0F));

			PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create()
							.texOffs(40, 16).mirror().addBox(-1F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, cubeDeformation).mirror(),
					PartPose.offset(-5.0F, 2.0F, 0.0F));

			PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create()
							.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, cubeDeformation),
					PartPose.offset(5.0F, 2.0F, 0.0F));

			PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create()
							.texOffs(0, 16).mirror().addBox(1.9F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, cubeDeformation).mirror(false),
					PartPose.offset(-1.9F, 14.0F, 0.0F));

			PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create()
							.texOffs(0, 16).addBox(-5.9F, 0.0F, -2.0F, 4.0F, 10.0F, 4.0F, cubeDeformation),
					PartPose.offset(1.9F, 14.0F, 0.0F));

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

			if (this.isAggressive(entity)) {
				this.rightArm.xRot = -0.8F;
				this.rightArm.yRot = -0.3F;
				this.leftArm.xRot = -0.4F;
				this.leftArm.yRot = 0.3F;
			}

			AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(entity), this.attackTime, ageInTicks);
		}

		@Override
		public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}
}