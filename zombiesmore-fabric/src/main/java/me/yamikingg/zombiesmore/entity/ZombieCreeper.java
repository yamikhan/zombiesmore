package me.yamikingg.zombiesmore.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
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
import me.yamikingg.zombiesmore.ZombiesMoreFabric;

import org.jetbrains.annotations.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class ZombieCreeper extends Creeper {
	public ZombieCreeper(EntityType<ZombieCreeper> entityType, Level world) {
		super(entityType, world);
	}

	private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(ZombieCreeper.class, EntityDataSerializers.BOOLEAN);
	private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (difficulty) -> difficulty == Difficulty.HARD;
	private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
	public static final int ID = 5;
	private UUID conversionStarter;
	public static final String NAME = "zombie_creeper";
	private int creeperConversionTime;
	private boolean canBreakDoors;

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(DATA_CONVERTING_ID, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new RestrictSunGoal(this));
		this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new AttackTurtleEggGoal(this, 1.0D, 3));
		super.registerGoals();
		this.addBehaviourGoals();
	}

	protected void addBehaviourGoals() {
		this.goalSelector.addGoal(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::canBreakDoors));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
	}

	public boolean canBreakDoors() {
		return this.canBreakDoors;
	}

	public void setCanBreakDoors(boolean canBreakDoors) {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			if (this.canBreakDoors != canBreakDoors) {
				this.canBreakDoors = canBreakDoors;
				((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(canBreakDoors);
				if (canBreakDoors) {
					this.goalSelector.addGoal(1, this.breakDoorGoal);
				} else {
					this.goalSelector.removeGoal(this.breakDoorGoal);
				}
			}
		} else if (this.canBreakDoors) {
			this.goalSelector.removeGoal(this.breakDoorGoal);
			this.canBreakDoors = false;
		}
	}

	class AttackTurtleEggGoal extends RemoveBlockGoal {
		AttackTurtleEggGoal(PathfinderMob mob, double speedModifier, int verticalSearchRange) {
			super(Blocks.TURTLE_EGG, mob, speedModifier, verticalSearchRange);
		}


		public void playDestroyProgressSound(Level level, BlockPos pos) {
			level.playSound(null, pos, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + random.nextFloat() * 0.2F);
		}

		@Override
		public void playBreakSound(Level level, BlockPos pos) {
			level.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
		}

		@Override
		public double acceptedDistance() {
			return 1.14D;
		}
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.ZOMBIE_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.ZOMBIE_VILLAGER_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.HUSK_DEATH;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("CanBreakDoors", this.canBreakDoors());
		tag.putInt("ConversionTime", this.isConverting() ? this.creeperConversionTime : -1);
		if (this.conversionStarter != null) {
			tag.putUUID("ConversionPlayer", this.conversionStarter);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setCanBreakDoors(tag.getBoolean("CanBreakDoors"));
		if (tag.contains("ConversionTime", 99) && tag.getInt("ConversionTime") > -1) {
			this.startConverting(tag.hasUUID("ConversionPlayer") ? tag.getUUID("ConversionPlayer") : null, tag.getInt("ConversionTime"));
		}
	}

	@Override
	public void aiStep() {
		boolean flag = this.isSunBurnTick();
		if (flag) {
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
			if (!itemstack.isEmpty()) {
				if (itemstack.isDamageableItem()) {
					itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
					if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
						this.canUseSlot(EquipmentSlot.HEAD);
						this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
					}
				}
				flag = false;
			}

			if (flag) {
				this.setRemainingFireTicks(8);
			}
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
		return this.getEntityData().get(DATA_CONVERTING_ID);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		if (itemstack.getItem() == Items.GOLDEN_APPLE) {
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
		} else {
			return super.mobInteract(player, hand);
		}
	}

	private int getConversionProgress() {
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
		this.getEntityData().set(DATA_CONVERTING_ID, true);
		this.removeEffect(MobEffects.WEAKNESS);
		this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, conversionTime, Math.min(this.level().getDifficulty().getId() - 1, 0)));
		this.level().broadcastEntityEvent(this, (byte)16);
	}

	private void finishConversion(ServerLevel serverLevel) {
		Creeper creeperEntity = this.convertTo(EntityType.CREEPER, false);
		creeperEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
		if (!this.isSilent()) {
			serverLevel.levelEvent(null, 1027, this.blockPosition(), 0);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 45.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.ATTACK_DAMAGE, 0.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
				.add(Attributes.MAX_HEALTH, 25.0D);
	}


	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
		this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return data;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		// Add any custom equipment here if needed
	}

	@Environment(EnvType.CLIENT)
	public static class CreeperRenderer extends net.minecraft.client.renderer.entity.CreeperRenderer {
		public CreeperRenderer(EntityRendererProvider.Context context) {
			super(context);
		}

		@Override
		public ResourceLocation getTextureLocation(Creeper entity) {
			return ResourceLocation.fromNamespaceAndPath(ZombiesMoreFabric.MODID, "textures/entity/" + NAME + ".png");
		}
	}
}