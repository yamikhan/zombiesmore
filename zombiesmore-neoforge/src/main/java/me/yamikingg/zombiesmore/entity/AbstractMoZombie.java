package me.yamikingg.zombiesmore.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public abstract class AbstractMoZombie extends Zombie {

	public AbstractMoZombie(EntityType<? extends Zombie> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID;
	public static String NAME;

	public String getNAMEID() {
		return NAME;
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
	protected float getSoundVolume() {
		return 1.0F;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 45.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
				.add(Attributes.MAX_HEALTH, 25.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		specialGoals();
	}

	protected void specialGoals() {
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieNotch.class, true));
	}

	@Override
	protected boolean convertsInWater() {
		return false;
	}
}