
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

		public Survivor(EntityType<Survivor> entityType, Level world) {
			super(entityType, world);


		}

	
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
			Survivor survivor = new Survivor(Registration.SURVIVOR.get(),p_146743_);
		survivor.finalizeSpawn(p_146743_, p_146743_.getCurrentDifficultyAt(survivor.blockPosition()), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
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
		public void setSurvivorType(int type){
			this.getEntityData().set(DATA_TYPE_ID, type);
		}

		protected int getExperienceReward(Player p_70693_1_) {
			if (this.isBaby()) {
				this.xpReward = (int)((float)this.xpReward * 2.5F);
			}

			return super.getExperienceReward(p_70693_1_);
		}
		@Override
		public int getRemainingPersistentAngerTime() {
			return this.remainingPersistentAngerTime;
		}

		public void startPersistentAngerTimer() {
			this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
		}
		@Override
		public void setRemainingPersistentAngerTime(int p_230260_1_) {
			this.remainingPersistentAngerTime = p_230260_1_;
		}

		@Nullable
		@Override
		public UUID getPersistentAngerTarget() {
			return this.persistentAngerTarget;
		}

		@Override
		public void setPersistentAngerTarget(@Nullable UUID p_230259_1_) {
			this.persistentAngerTarget = p_230259_1_;
		}


		public void addAdditionalSaveData(CompoundTag p_213281_1_) {
			super.addAdditionalSaveData(p_213281_1_);
			p_213281_1_.putInt("Type", getSurvivorType());
			this.addPersistentAngerSaveData(p_213281_1_);
		}

		public void readAdditionalSaveData(CompoundTag p_70037_1_) {
			super.readAdditionalSaveData(p_70037_1_);
			this.setSurvivorType(p_70037_1_.getInt("Type"));
			if(!level.isClientSide) //FORGE: allow this entity to be read from nbt on client. (Fixes MC-189565)
				this.readPersistentAngerSaveData((ServerLevel)this.level, p_70037_1_);
		}
		public void setTarget(@Nullable LivingEntity p_70624_1_) {
			if (this.getTarget() == null && p_70624_1_ != null) {
				this.ticksUntilNextAlert = ALERT_INTERVAL.sample(this.random);
			}

			if (p_70624_1_ instanceof Player) {
				this.setLastHurtByPlayer((Player)p_70624_1_);
			}

			super.setTarget(p_70624_1_);
		}

		protected void updateNoActionTime() {
			float f = this.getBrightness();
			if (f > 0.5F) {
				this.noActionTime += 2;
			}

		}



	public void aiStep() {
			this.updateSwingTime();
			this.updateNoActionTime();
			if (getMainHandItem().isEdible() && getHealth() <= getMaxHealth()) eat(level, getMainHandItem());
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
			this.level.getEntitiesOfClass(Survivor.class, axisalignedbb).stream().filter((p_241408_1_) -> {
				return p_241408_1_ != this;
			}).filter((p_241407_0_) -> {
				return p_241407_0_.getTarget() == null;
			}).filter((p_241406_1_) -> {
				return !p_241406_1_.isAlliedTo(this.getTarget());
			}).forEach((p_241405_1_) -> {
				p_241405_1_.setTarget(this.getTarget());
			});
		}

		protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty)
		{
			super.populateDefaultEquipmentSlots(difficulty);
			if (this.random.nextFloat() < (this.level.getDifficulty() == Difficulty.HARD ? 0.45F : 0.20F)) {
				{
					int i = this.random.nextInt(3);

					if (i == 0)
					{
						if (this.random.nextFloat() < 0.25F)
							this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
						if (this.random.nextFloat() < 0.15F)
							this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
						if (this.random.nextFloat() < 0.3F)
							this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
					}
					else
					{
						this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
						this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
						if (this.random.nextFloat() < 0.3F)
							this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
						this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
					}
				}
			}
		}


	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @org.jetbrains.annotations.Nullable SpawnGroupData p_146749_, @org.jetbrains.annotations.Nullable CompoundTag p_146750_){
			float f = p_146747_.getSpecialMultiplier();
			this.setCanPickUpLoot(this.random.nextFloat() < 0.55F * f);

			if (p_146749_ == null)
			p_146749_ = new Survivor.GroupData( true, true,  p_146746_.getRandom().nextFloat() < 0.40D ? 1 : 0);


		    GroupData survivorentity$groupdata = (GroupData) p_146749_;

		    this.setSurvivorType(survivorentity$groupdata.Type);

				if (survivorentity$groupdata.canSpawnJockey) {
					if ((double) p_146746_.getRandom().nextFloat() < 0.05D) {
						List<Horse> list = p_146746_.getEntitiesOfClass(Horse.class, this.getBoundingBox().inflate(5.0D, 3.0D, 5.0D), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
						if (!list.isEmpty()) {
							Horse Horse = list.get(0);
							Horse.setTamed(true);
							this.startRiding(Horse);
						}
					} else if ((double) p_146746_.getRandom().nextFloat() < 0.05D) {
						Horse Horse1 = EntityType.HORSE.create(this.level);
						Horse1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
						Horse1.finalizeSpawn(p_146746_, p_146747_, MobSpawnType.JOCKEY, (SpawnGroupData) null, (CompoundTag) null);
						Horse1.setTamed(true);
						this.startRiding(Horse1);
						p_146746_.addFreshEntity(Horse1);
					}

			}
		if (survivorentity$groupdata.isShouldSpawnBaby() && this.random.nextFloat() <= survivorentity$groupdata.getBabySpawnChance()) {
			this.setAge(-24000);
			setBaby(true);
		}

			populateDefaultEquipmentSlots(p_146747_);
			return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);

		}


	protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
			super.dropCustomDeathLoot(p_213333_1_, p_213333_2_, p_213333_3_);
			Entity entity = p_213333_1_.getEntity();
			if (entity instanceof Creeper) {
				Creeper creeperentity = (Creeper)entity;
				if (creeperentity.canDropMobsSkull()) {
					ItemStack itemstack = this.getSkull();
					if (!itemstack.isEmpty()) {
						creeperentity.increaseDroppedSkulls();
						this.spawnAtLocation(itemstack);
					}
				}
			}

		}

		protected ItemStack getSkull()
		{
			return new ItemStack(Items.PLAYER_HEAD, 1);
		}

		public MobType getMobType() {
			return MobType.UNDEFINED;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			if (getSurvivorType() >= 1) return Registration.HURT_SURVIVOR_FEMALE.get();
			return Registration.HURT_SURVIVOR.get();
		}

		@Override
		public SoundEvent getDeathSound() {return SoundEvents.PLAYER_DEATH;}

		protected void registerGoals() {

			this.goalSelector.addGoal(1, new FloatGoal(this));
			this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
			this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
			this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
			this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
			this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
			this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
			if(random.nextFloat() < 0.5F)
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
			else this.targetSelector.addGoal(3, new PanicGoal(this, 1.2D));

		}
		public double getMyRidingOffset() {
		return this.isBaby() ? 0.0D : -0.45D;
	}


		public static class GroupData extends AgeableMobGroupData {
			public final boolean canSpawnJockey;
			public final int Type;

			public GroupData(boolean p_i231567_2_, boolean shouldSpawnBaby,  int p_i231567_3_) {
				super(shouldSpawnBaby);
				this.canSpawnJockey = p_i231567_2_;
				this.Type = p_i231567_3_;
			}
		}

		public static AttributeSupplier.Builder createAttributes() {
			return Mob.createMobAttributes().add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3D).add(Attributes.ATTACK_DAMAGE, 3D).add(Attributes.ARMOR, 0.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,20D);
		}
		public static class SurvivorModel<S extends Survivor> extends HumanoidModel<S> {
			public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ZombiesMore.MODID, name), "main");

			public SurvivorModel(ModelPart p_170677_) {
				super(p_170677_);
			}

			@Override
			public void renderToBuffer(PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_) {
				super.renderToBuffer(p_102034_, p_102035_, p_102036_, p_102037_, p_102038_, p_102039_, p_102040_, p_102041_);

			}
		}


			public static class SurvivorRenderer extends HumanoidMobRenderer<Survivor, SurvivorModel<Survivor>> {
				int type = 0;
				SurvivorModel<Survivor> slim;
				SurvivorModel<Survivor> normal = this.getModel();
				public SurvivorRenderer(EntityRendererProvider.Context context){
					this(context, ModelLayers.PLAYER, ModelLayers.PLAYER_INNER_ARMOR, ModelLayers.PLAYER_OUTER_ARMOR);
					this.slim = new SurvivorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM));

				}
				public SurvivorRenderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation layerLocation, ModelLayerLocation innerLayerLocation, ModelLayerLocation outerLayerLocation) {
					super(p_174458_, new SurvivorModel<>(p_174458_.bakeLayer(layerLocation)), 0.5F);
					this.addLayer(new HumanoidArmorLayer<>(this, new SurvivorModel<>(p_174458_.bakeLayer(innerLayerLocation)), new SurvivorModel<>(p_174458_.bakeLayer(outerLayerLocation))));
				}
				@Override
				public void render(Survivor p_115455_, float p_115456_, float p_115457_, PoseStack p_115458_, MultiBufferSource p_115459_, int p_115460_) {
					type = p_115455_.getSurvivorType();
					if(type >= 1) model = slim;
							else model = normal;

				    super.render(p_115455_, p_115456_, p_115457_, p_115458_, p_115459_, p_115460_);
				}

			@Override
			public ResourceLocation getTextureLocation(Survivor entity) {

				return new ResourceLocation(ZombiesMore.MODID, "textures/entity/" + name + "_" + type + ".png");
			}

		}
	}

