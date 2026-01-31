
package me.yamikingg.zombiesmore.entity;

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
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
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
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import me.yamikingg.zombiesmore.ZombiesMore;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;


public class ZombieCreeper extends Creeper {
		public ZombieCreeper(EntityType<ZombieCreeper> entityType, Level world) {
			super(entityType,world);
		}

	private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(ZombieCreeper.class, EntityDataSerializers.BOOLEAN);
		private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (p_213697_0_) -> {
			return p_213697_0_ == Difficulty.HARD;
		};
		private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
		public static final int ID = 5;
	    private UUID conversionStarter;
		public static final String name = "zombie_creeper";
	    private int creeperConversionTime;

		private boolean canBreakDoors;

	    protected void defineSynchedData() {
		    super.defineSynchedData();
		    this.entityData.define(DATA_CONVERTING_ID, false);
	    }

		protected void registerGoals() {
			this.goalSelector.addGoal(1, new RestrictSunGoal(this));
			this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0D));
			this.goalSelector.addGoal(4, new ZombieCreeper.AttackTurtleEggGoal(this, 1.0D, 3));
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

		public void setCanBreakDoors(boolean p_146070_1_) {
			if (GoalUtils.hasGroundPathNavigation(this)) {
				if (this.canBreakDoors != p_146070_1_) {
					this.canBreakDoors = p_146070_1_;
					((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(p_146070_1_);
					if (p_146070_1_) {
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
			AttackTurtleEggGoal(PathfinderMob p_i50465_2_, double p_i50465_3_, int p_i50465_5_) {
				super(Blocks.TURTLE_EGG, p_i50465_2_, p_i50465_3_, p_i50465_5_);
			}

			public void playDestroyProgressSound(Level p_203114_1_, BlockPos p_203114_2_) {
				p_203114_1_.playSound((Player)null, p_203114_2_, SoundEvents.ZOMBIE_DESTROY_EGG, SoundSource.HOSTILE, 0.5F, 0.9F + random.nextFloat() * 0.2F);
			}

			public void playBreakSound(Level p_203116_1_, BlockPos p_203116_2_) {
				p_203116_1_.playSound((Player)null, p_203116_2_, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + p_203116_1_.random.nextFloat() * 0.2F);
			}

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
		public void addAdditionalSaveData(CompoundTag p_213281_1_) {
			super.addAdditionalSaveData(p_213281_1_);
			p_213281_1_.putBoolean("CanBreakDoors", this.canBreakDoors());
			p_213281_1_.putInt("ConversionTime", this.isConverting() ? this.creeperConversionTime : -1);
			if (this.conversionStarter != null) {
				p_213281_1_.putUUID("ConversionPlayer", this.conversionStarter);
			}

		}

		public void readAdditionalSaveData(CompoundTag p_70037_1_) {
			super.readAdditionalSaveData(p_70037_1_);
			this.setCanBreakDoors(p_70037_1_.getBoolean("CanBreakDoors"));
			if (p_70037_1_.contains("ConversionTime", 99) && p_70037_1_.getInt("ConversionTime") > -1) {
				this.startConverting(p_70037_1_.hasUUID("ConversionPlayer") ? p_70037_1_.getUUID("ConversionPlayer") : null, p_70037_1_.getInt("ConversionTime"));
			}

		}
	public void aiStep() {
		boolean flag = this.isSunBurnTick();
		if (flag) {
			ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
			if (!itemstack.isEmpty()) {
				if (itemstack.isDamageableItem()) {
					itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
					if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
						this.broadcastBreakEvent(EquipmentSlot.HEAD);
						this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
					}
				}

				flag = false;
			}

			if (flag) {
				this.setSecondsOnFire(8);
			}
		}

		super.aiStep();
	}
	public void tick() {
		if (!this.level.isClientSide && this.isAlive() && this.isConverting()) {
			int i = this.getConversionProgress();
			this.creeperConversionTime -= i;
			if (this.creeperConversionTime <= 0 && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.VILLAGER, (timer) -> this.creeperConversionTime = timer)) {
				this.finishConversion((ServerLevel)this.level);
			}
		}

		super.tick();
	}
	public boolean isConverting() {
		return this.getEntityData().get(DATA_CONVERTING_ID);
	}
	public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
		ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
		if (itemstack.getItem() == Items.GOLDEN_APPLE) {
			if (this.hasEffect(MobEffects.WEAKNESS)) {
				if (!p_230254_1_.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				if (!this.level.isClientSide) {
					this.startConverting(p_230254_1_.getUUID(), this.random.nextInt(2401) + 3600);
				}

				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.CONSUME;
			}
		} else {
			return super.mobInteract(p_230254_1_, p_230254_2_);
		}
	}
	private int getConversionProgress() {
		int i = 1;
		if (this.random.nextFloat() < 0.01F) {
			int j = 0;
			BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

			for(int k = (int)this.getX() - 4; k < (int)this.getX() + 4 && j < 14; ++k) {
				for(int l = (int)this.getY() - 4; l < (int)this.getY() + 4 && j < 14; ++l) {
					for(int i1 = (int)this.getZ() - 4; i1 < (int)this.getZ() + 4 && j < 14; ++i1) {
						Block block = this.level.getBlockState(blockpos$mutable.set(k, l, i1)).getBlock();
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
	private void startConverting(@Nullable UUID p_191991_1_, int p_191991_2_) {
		this.conversionStarter = p_191991_1_;
		this.creeperConversionTime = p_191991_2_;
		this.getEntityData().set(DATA_CONVERTING_ID, true);
		this.removeEffect(MobEffects.WEAKNESS);
		this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, p_191991_2_, Math.min(this.level.getDifficulty().getId() - 1, 0)));
		this.level.broadcastEntityEvent(this, (byte)16);
	}
	private void finishConversion(ServerLevel p_213791_1_) {
		Creeper creeperEntity = this.convertTo(EntityType.CREEPER, false);

		creeperEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
		if (!this.isSilent()) {
			p_213791_1_.levelEvent((Player)null, 1027, this.blockPosition(), 0);
		}
		net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, creeperEntity);
	}
		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 45.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.ATTACK_DAMAGE, 0.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,25D);
		}
	@OnlyIn(Dist.CLIENT)
	public static class CreeperRenderer extends net.minecraft.client.renderer.entity.CreeperRenderer {
		public CreeperRenderer(EntityRendererProvider.Context p_i50974_1_) {
			super(p_i50974_1_);
		}


		@Override
		public ResourceLocation getTextureLocation(Creeper entity) {
			return new ResourceLocation(ZombiesMore.MODID, "textures/entity/" + name +".png");
		}
	}
	}

