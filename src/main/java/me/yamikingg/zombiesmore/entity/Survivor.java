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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

	public static final String NAME = "survivor";
	public static final int ID = 27;

	private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.05D, AttributeModifier.Operation.ADDITION);

	private int ticksUntilNextAlert;
	private UUID persistentAngerTarget;
	private int remainingPersistentAngerTime;

	public Survivor(EntityType<? extends Survivor> entityType, Level world) {
		super(entityType, world);
		this.xpReward = 5;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob parent) {
		Survivor survivor = new Survivor(Registration.SURVIVOR.get(), serverLevel);
		survivor.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(survivor.blockPosition()), MobSpawnType.BREEDING, null, null);
		return survivor;
	}

	@Override
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

	@Override
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

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Type", getSurvivorType());
		this.addPersistentAngerSaveData(compoundTag);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setSurvivorType(compoundTag.getInt("Type"));
		this.readPersistentAngerSaveData((ServerLevel) this.level(), compoundTag);
	}

	@Override
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
		int brightness = this.level().getRawBrightness(this.blockPosition(), 0);
		if (brightness > 7) {
			this.noActionTime += 2;
		}
	}

	@Override
	public void aiStep() {
		this.updateSwingTime();
		this.updateNoActionTime();

		// Heal with food if health is low
		ItemStack mainHandItem = this.getMainHandItem();
		if (mainHandItem.isEdible() && this.getHealth() < this.getMaxHealth() && this.canEat(true)) {
			this.eat(this.level(), mainHandItem);
		}

		super.aiStep();
	}

	private boolean canEat(boolean b) {
		return true;
	}

	@Override
	protected void customServerAiStep() {
		AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (this.isAngry()) {
			if (!this.isBaby() && !movementSpeed.hasModifier(SPEED_MODIFIER_ATTACKING)) {
				movementSpeed.addTransientModifier(SPEED_MODIFIER_ATTACKING);
			}
		} else if (movementSpeed.hasModifier(SPEED_MODIFIER_ATTACKING)) {
			movementSpeed.removeModifier(SPEED_MODIFIER_ATTACKING.getId());
		}

		this.updatePersistentAnger((ServerLevel)this.level(), true);
		if (this.getTarget() != null) {
			this.maybeAlertOthers();
		}

		if (this.isAngry()) {
			this.lastHurtByPlayerTime = this.tickCount;
		}

		super.customServerAiStep();
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
		double followRange = this.getAttributeValue(Attributes.FOLLOW_RANGE);
		AABB alertArea = new AABB(this.getX() - followRange, this.getY() - 10.0D, this.getZ() - followRange,
				this.getX() + followRange, this.getY() + 10.0D, this.getZ() + followRange);

		List<Survivor> nearbySurvivors = this.level().getEntitiesOfClass(Survivor.class, alertArea);
		for (Survivor survivor : nearbySurvivors) {
			if (survivor != this && survivor.getTarget() == null && !survivor.isAlliedTo(this.getTarget())) {
				survivor.setTarget(this.getTarget());
			}
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(@NotNull RandomSource randomSource, @NotNull DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(randomSource, difficulty);
		if (this.random.nextFloat() < (this.level().getDifficulty() == Difficulty.HARD ? 0.45F : 0.20F)) {
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
	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		float f = difficulty.getSpecialMultiplier();
		this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);

		if (spawnGroupData == null) {
			boolean shouldSpawnBaby = serverLevelAccessor.getRandom().nextFloat() < 0.05F;
			int type = serverLevelAccessor.getRandom().nextFloat() < 0.40F ? 1 : 0;
			spawnGroupData = new Survivor.GroupData(true, shouldSpawnBaby, type);
		}

		if (spawnGroupData instanceof GroupData survivorGroupData) {
			this.setSurvivorType(survivorGroupData.type);

			if (survivorGroupData.canSpawnJockey) {
				if (serverLevelAccessor.getRandom().nextFloat() < 0.05D) {
					List<Horse> nearbyHorses = serverLevelAccessor.getEntitiesOfClass(Horse.class,
							this.getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);

					if (!nearbyHorses.isEmpty()) {
						Horse horse = nearbyHorses.get(0);
						horse.setTamed(true);
						horse.setOwnerUUID(this.getUUID());
						this.startRiding(horse);
					}
				} else if (serverLevelAccessor.getRandom().nextFloat() < 0.05D) {
					Horse horse = EntityType.HORSE.create(this.level());
					if (horse != null) {
						horse.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
						horse.finalizeSpawn(serverLevelAccessor, difficulty, MobSpawnType.JOCKEY, null, null);
						horse.setTamed(true);
						horse.setOwnerUUID(this.getUUID());
						this.startRiding(horse);
						serverLevelAccessor.addFreshEntity(horse);
					}
				}
			}

			if (survivorGroupData.isShouldSpawnBaby() && this.random.nextFloat() <= 0.05F) {
				this.setAge(-24000);
			}
		}

		this.populateDefaultEquipmentSlots(this.getRandom(), difficulty);
		return super.finalizeSpawn(serverLevelAccessor, difficulty, spawnType, spawnGroupData, compoundTag);
	}

	@Override
	protected void dropCustomDeathLoot(@NotNull DamageSource damageSource, int looting, boolean hitByPlayer) {
		super.dropCustomDeathLoot(damageSource, looting, hitByPlayer);
		Entity entity = damageSource.getEntity();
		if (entity instanceof Creeper creeper) {
			if (creeper.canDropMobsSkull()) {
				ItemStack skull = this.getSkull();
				if (!skull.isEmpty()) {
					creeper.increaseDroppedSkulls();
					this.spawnAtLocation(skull);
				}
			}
		}
	}

	protected ItemStack getSkull() {
		return new ItemStack(Items.PLAYER_HEAD);
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEFINED;
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource ds) {
		if (getSurvivorType() >= 1) {
			return Registration.HURT_SURVIVOR_FEMALE.get();
		}
		return Registration.HURT_SURVIVOR.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PLAYER_DEATH;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));

		if (random.nextFloat() < 0.5F) {
			this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
		} else {
			this.targetSelector.addGoal(4, new PanicGoal(this, 1.2D));
		}
	}

	// ================ UPDATED MOUNTING METHODS FOR 1.20.2 ================

	@Override
	protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
		if (this.hasPassenger(pPassenger)) {
			// Calculate the default Y position (entity's Y + passenger's default offset)
			double defaultY = this.getY() + this.getPassengersRidingOffset(pPassenger) + pPassenger.getMyRidingOffset(this);
			// Apply your custom offset: -0.45D for adults, 0.0D for babies
			double customYOffset = this.isBaby() ? 0.0D : -0.45D;

			// Set the final position with your custom offset
			pCallback.accept(pPassenger, this.getX(), defaultY + customYOffset, this.getZ());
		} else {
			super.positionRider(pPassenger, pCallback);
		}
	}

	// Helper method for getting the default passenger offset (optional, but good to have)
	public double getPassengersRidingOffset(Entity pPassenger) {
		// You can customize this based on the passenger if needed
		// For now, return the default from Entity class logic
		return 0.0D;
	}

	// ================ END OF UPDATED METHODS ================

	public static class GroupData extends AgeableMob.AgeableMobGroupData {
		public final boolean canSpawnJockey;
		public final int type;

		public GroupData(boolean canSpawnJockey, boolean shouldSpawnBaby, int type) {
			super(shouldSpawnBaby);
			this.canSpawnJockey = canSpawnJockey;
			this.type = type;
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.FOLLOW_RANGE, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 0.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
				.add(Attributes.MAX_HEALTH, 20.0D);
	}

	// Client-side renderer classes
	@OnlyIn(Dist.CLIENT)
	public static class SurvivorModel<S extends Survivor> extends HumanoidModel<S> {
		public static final ModelLayerLocation LAYER_LOCATION =
				new ModelLayerLocation(new ResourceLocation(ZombiesMore.MODID, NAME), "main");

		public SurvivorModel(ModelPart modelPart) {
			super(modelPart);
		}

		@Override
		public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer,
								   int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
			super.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class SurvivorRenderer extends HumanoidMobRenderer<Survivor, SurvivorModel<Survivor>> {
		private static final ResourceLocation MALE_TEXTURE = new ResourceLocation(ZombiesMore.MODID, "textures/entity/survivor_0.png");
		private static final ResourceLocation FEMALE_TEXTURE = new ResourceLocation(ZombiesMore.MODID, "textures/entity/survivor_1.png");

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
			// Switch between normal and slim model based on survivor type
			int type = survivor.getSurvivorType();
			this.model = (type >= 1) ? slimModel : normalModel;

			super.render(survivor, entityYaw, partialTicks, poseStack, buffer, packedLight);
		}

		@Override
		public ResourceLocation getTextureLocation(Survivor entity) {
			int type = entity.getSurvivorType();
			return (type >= 1) ? FEMALE_TEXTURE : MALE_TEXTURE;
		}
	}
}