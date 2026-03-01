package me.yamikingg.zombiesmore.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.UUID;

public class ZombieCreeper extends Creeper {

	public ZombieCreeper(EntityType<ZombieCreeper> entityType, Level world) {
		super(entityType, world);
	}

	private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID =
			SynchedEntityData.defineId(ZombieCreeper.class, EntityDataSerializers.BOOLEAN);

	public static final int ID = 5;
	public static final String NAME = "zombie_creeper";

	private UUID conversionStarter;
	private int creeperConversionTime;
	private boolean canBreakDoors;

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_CONVERTING_ID, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ZOMBIE_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.ZOMBIE_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.ZOMBIE_DEATH;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("CanBreakDoors", this.canBreakDoors);
		tag.putInt("ConversionTime", this.isConverting() ? this.creeperConversionTime : -1);
		if (this.conversionStarter != null) {
			tag.putUUID("ConversionPlayer", this.conversionStarter);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.canBreakDoors = tag.getBoolean("CanBreakDoors");
		if (tag.contains("ConversionTime", 99) && tag.getInt("ConversionTime") > -1) {
			this.startConverting(tag.hasUUID("ConversionPlayer") ? tag.getUUID("ConversionPlayer") : null, tag.getInt("ConversionTime"));
		}
	}

	@Override
	public void aiStep() {
		if (this.isAlive() && this.isSunBurnTick()) {
			this.setRemainingFireTicks(8);
		}
		super.aiStep();
	}

	@Override
	public void tick() {
		if (!this.level().isClientSide && this.isAlive() && this.isConverting()) {
			int i = this.getConversionProgress();
			this.creeperConversionTime -= i;
			if (this.creeperConversionTime <= 0) {
				this.finishConversion((ServerLevel) this.level());
			}
		}
		super.tick();
	}

	public boolean isConverting() {
		return this.entityData.get(DATA_CONVERTING_ID);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.is(Items.GOLDEN_APPLE)) {
			if (this.hasEffect(MobEffects.WEAKNESS)) {
				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				if (!this.level().isClientSide) {
					this.startConverting(player.getUUID(), this.random.nextInt(2401) + 3600);
				}

				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.CONSUME;
			}
		}
		return super.mobInteract(player, hand);
	}

	protected int getConversionProgress() {
		int i = 1;
		if (this.random.nextFloat() < 0.01F) {
			int j = 0;
			BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

			for(int k = (int)this.getX() - 4; k < (int)this.getX() + 4 && j < 14; ++k) {
				for(int l = (int)this.getY() - 4; l < (int)this.getY() + 4 && j < 14; ++l) {
					for(int i1 = (int)this.getZ() - 4; i1 < (int)this.getZ() + 4 && j < 14; ++i1) {
						Block block = this.level().getBlockState(mutablePos.set(k, l, i1)).getBlock();
						if (block == Blocks.IRON_BARS || block instanceof BedBlock) {
							if (this.random.nextFloat() < 0.3F) {
								++i;
							}
							++j;
						}
					}
				}
			}
		}
		return i;
	}

	private void startConverting(@Nullable UUID converterUUID, int conversionTime) {
		this.conversionStarter = converterUUID;
		this.creeperConversionTime = conversionTime;
		this.entityData.set(DATA_CONVERTING_ID, true);
		this.removeEffect(MobEffects.WEAKNESS);
		this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, conversionTime, Math.min(this.level().getDifficulty().getId() - 1, 0)));
		this.level().broadcastEntityEvent(this, (byte)16);
	}

	private void finishConversion(ServerLevel serverLevel) {
		Creeper creeperEntity = this.convertTo(EntityType.CREEPER, false);
		if (creeperEntity != null) {
			creeperEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
			if (!this.isSilent()) {
				serverLevel.levelEvent(null, 1027, this.blockPosition(), 0);
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.23D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.MAX_HEALTH, 25.0D);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
		this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return data;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
	}
}