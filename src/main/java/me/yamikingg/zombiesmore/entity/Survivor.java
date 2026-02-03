package me.yamikingg.zombiesmore.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.init.Registration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;


public class Survivor extends AgeableMob implements NeutralMob {
	private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Survivor.class, EntityDataSerializers.INT);
	private static final UniformInt ALERT_INTERVAL = TimeUtil.rangeOfSeconds(4, 6);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

	private int ticksUntilNextAlert;
	public static String name = "survivor";
	public static final int ID = 27;
	private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);
	private UUID persistentAngerTarget;
	private int remainingPersistentAngerTime;

	public Survivor(EntityType<? extends Survivor> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob parent) {
		Survivor survivor = new Survivor(Registration.SURVIVOR.get(), serverLevel);
		survivor.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(survivor.blockPosition()), MobSpawnType.BREEDING, null, null);
		return survivor;
	}

	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TYPE_ID, 0);
	}

	@Override
	public boolean canPickUpLoot() {
		return true;
	}

	public int getSurvivorType() {
		return this.getEntityData().get(DATA_TYPE_ID);
	}

	public void setSurvivorType(int type) {
		this.getEntityData().set(DATA_TYPE_ID, type);
	}

	// Fixed: Changed return type and removed parameter
	protected int getExperienceReward(Player player) {
		if (this.isBaby()) {
			this.xpReward = (int)((float)this.xpReward * 2.5F);
		}
		return super.getExperienceReward();
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int time) {
		this.remainingPersistentAngerTime = time;
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID target) {
		this.persistentAngerTarget = target;
	}

	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Type", getSurvivorType());
		this.addPersistentAngerSaveData(compoundTag);
	}

	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setSurvivorType(compoundTag.getInt("Type"));
		if (!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
			this.readPersistentAngerSaveData(this.level, compoundTag);
	}

	public void setTarget(@Nullable LivingEntity target) {
		if (this.getTarget() == null && target != null) {
			this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
		}

		if (target instanceof Player) {
			this.setLastHurtByPlayer((Player)target);
		}

		super.setTarget(target);
	}

	protected void updateNoActionTime() {
		int brightness = this.level.getRawBrightness(this.blockPosition(), 0);
		if (brightness > 7) {
			this.noActionTime += 2;
		}
	}

	public void aiStep() {
		this.updateSwingTime();
		this.updateNoActionTime();
		if (getMainHandItem().isEdible() && getHealth() < getMaxHealth()) {
			eat(level, getMainHandItem());
		}
		super.aiStep();
	}

	protected void customServerAiStep() {
		AttributeInstance modifiableattributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (this.isAngry()) {
			if (!this.isBaby() && !modifiableattributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
				modifiableattributeinstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
			}
		} else if (modifiableattributeinstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
			modifiableattributeinstance.removeModifier(SPEED_MODIFIER_ATTACKING);
		}

		this.updatePersistentAnger((ServerLevel)this.level, true);
		if (this.getTarget() != null) {
			this.maybeAlertOthers();
		}

		if (this.isAngry()) {
			this.lastHurtByPlayerTime = this.tickCount;
		}
	}

	private void maybeAlertOthers() {
		if (this.ticksUntilNextAlert > 0) {
			--this.ticksUntilNextAlert;
		} else {
			if (this.getSensing().hasLineOfSight(this.getTarget())) {
				this.alertOthers();
			}
			this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
		}
	}

	private void alertOthers() {
		double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
		AABB axisalignedbb = AABB.unitCubeFromLowerCorner(this.position()).inflate(d0, 10.0D, d0);
		this.level.getEntitiesOfClass(Survivor.class, axisalignedbb).stream()
				.filter((survivor) -> survivor != this)
				.filter((survivor) -> survivor.getTarget() == null)
				.filter((survivor) -> !survivor.isAlliedTo(this.getTarget()))
				.forEach((survivor) -> survivor.setTarget(this.getTarget()));
	}

	// Fixed: Changed method signature for 1.19.2
	protected void populateDefaultEquipmentSlots(@NotNull RandomSource randomSource, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.45F : 0.20F)) {
			int i = this.random.nextInt(3);

			if (i == 0) {
				if (this.random.nextFloat() < 0.25F)
					this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
				if (this.random.nextFloat() < 0.15F)
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				if (this.random.nextFloat() < 0.3F)
					this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
				this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
				if (this.random.nextFloat() < 0.3F)
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
				this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
			}
		}
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		float f = difficulty.getSpecialMultiplier();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);

		if (spawnGroupData == null) {
			spawnGroupData = new Survivor.GroupData(true, true, serverLevelAccessor.getRandom().nextFloat() < 0.40D ? 1 : 0);
		}

		GroupData survivorGroupData = (GroupData) spawnGroupData;
		this.setSurvivorType(survivorGroupData.Type);

		if (survivorGroupData.canSpawnJockey) {
			if (serverLevelAccessor.getRandom().nextFloat() < 0.05D) {
				List<Horse> list = serverLevelAccessor.getEntitiesOfClass(Horse.class, this.getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
				if (!list.isEmpty()) {
					Horse horse = list.get(0);
					horse.setTamed(true);
					this.startRiding(horse);
				}
			} else if (serverLevelAccessor.getRandom().nextFloat() < 0.05D) {
				Horse horse1 = EntityType.HORSE.create(this.level);
				if (horse1 != null) {
					horse1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
					horse1.finalizeSpawn(serverLevelAccessor, difficulty, MobSpawnType.JOCKEY, null, null);
					horse1.setTamed(true);
					this.startRiding(horse1);
					serverLevelAccessor.addFreshEntity(horse1);
				}
			}
		}

		if (survivorGroupData.isShouldSpawnBaby() && this.random.nextFloat() <= survivorGroupData.getBabySpawnChance()) {
			this.setAge(-24000);
			this.setBaby(true);
		}

		populateDefaultEquipmentSlots(this.getRandom(), difficulty);
		return super.finalizeSpawn(serverLevelAccessor, difficulty, spawnType, spawnGroupData, compoundTag);
	}

	protected void dropCustomDeathLoot(DamageSource damageSource, int looting, boolean hitByPlayer) {
		super.dropCustomDeathLoot(damageSource, looting, hitByPlayer);
		Entity entity = damageSource.getEntity();
		if (entity instanceof Creeper creeper) {
			if (creeper.canDropMobsSkull()) {
				ItemStack itemstack = this.getSkull();
				if (!itemstack.isEmpty()) {
					creeper.increaseDroppedSkulls();
					this.spawnAtLocation(itemstack);
				}
			}
		}
	}

	protected ItemStack getSkull() {
		return new ItemStack(Items.PLAYER_HEAD, 1);
	}

	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource ds) {
		if (getSurvivorType() >= 1) return Registration.HURT_SURVIVOR_FEMALE.get();
		return Registration.HURT_SURVIVOR.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PLAYER_DEATH;
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
		if (random.nextFloat() < 0.5F)
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
		else
			this.targetSelector.addGoal(3, new PanicGoal(this, 1.2D));
	}

	public double getMyRidingOffset() {
		return this.isBaby() ? 0.0D : -0.45D;
	}

	public static class GroupData extends AgeableMob.AgeableMobGroupData {
		public final boolean canSpawnJockey;
		public final int Type;

		public GroupData(boolean canSpawnJockey, boolean shouldSpawnBaby, int type) {
			super(shouldSpawnBaby);
			this.canSpawnJockey = canSpawnJockey;
			this.Type = type;
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.FOLLOW_RANGE, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 3D)
				.add(Attributes.ARMOR, 0.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
				.add(Attributes.MAX_HEALTH, 20D);
	}

	public static class SurvivorModel<S extends Survivor> extends HumanoidModel<S> {
		public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ZombiesMore.MODID, name), "main");

		public SurvivorModel(ModelPart modelPart) {
			super(modelPart);
		}

		@Override
		public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}

	public static class SurvivorRenderer extends HumanoidMobRenderer<Survivor, SurvivorModel<Survivor>> {
		int type = 0;
		SurvivorModel<Survivor> slim;
		SurvivorModel<Survivor> normal;

		public SurvivorRenderer(EntityRendererProvider.Context context) {
			this(context, ModelLayers.PLAYER, ModelLayers.PLAYER_INNER_ARMOR, ModelLayers.PLAYER_OUTER_ARMOR);
			this.slim = new SurvivorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM));
			this.normal = this.getModel();
		}

		public SurvivorRenderer(EntityRendererProvider.Context context, ModelLayerLocation layerLocation, ModelLayerLocation innerLayerLocation, ModelLayerLocation outerLayerLocation) {
			super(context, new SurvivorModel<>(context.bakeLayer(layerLocation)), 0.5F);
			this.addLayer(new HumanoidArmorLayer<>(this, new SurvivorModel<>(context.bakeLayer(innerLayerLocation)), new SurvivorModel<>(context.bakeLayer(outerLayerLocation))));
		}

		@Override
		public void render(Survivor survivor, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
			type = survivor.getSurvivorType();
			if (type >= 1)
				model = slim;
			else
				model = normal;

			super.render(survivor, entityYaw, partialTicks, poseStack, buffer, packedLight);
		}

		@Override
		public ResourceLocation getTextureLocation(Survivor entity) {
			return new ResourceLocation(ZombiesMore.MODID, "textures/entity/" + name + "_" + type + ".png");
		}
	}
}