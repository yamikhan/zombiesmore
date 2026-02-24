package me.yamikingg.zombiesmore.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class NetherZombie extends AbstractMoZombie {

	public static final int ID = 9;
	public static final String NAME = "nether_zombie";

	public NetherZombie(EntityType<? extends AbstractMoZombie> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public boolean fireImmune() {
		return true; // Nether mobs are immune to fire
	}

	@Override
	public SoundEvent getAmbientSound() {
		return SoundEvents.HUSK_AMBIENT;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.ZOMBIFIED_PIGLIN_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.GHAST_DEATH;
	}

	@Override
	protected boolean convertsInWater() {
		return false; // Nether mobs shouldn't convert in water
	}

	@Override
	protected boolean isSunSensitive() {
		return false; // Nether mobs aren't sensitive to sunlight
	}

	@Override
	protected void specialGoals() {
		super.specialGoals();
		// Add any NetherZombie-specific targeting here
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 50.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.ATTACK_DAMAGE, 5.0D)
				.add(Attributes.ARMOR, 4.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
				.add(Attributes.MAX_HEALTH, 25.0D);
	}

	// Optional: Add burning effect to attacks
	@Override
	public boolean doHurtTarget(net.minecraft.world.entity.Entity entity) {
		boolean flag = super.doHurtTarget(entity);
		if (flag && entity instanceof net.minecraft.world.entity.LivingEntity) {
			// Set target on fire for 3 seconds
			entity.setRemainingFireTicks(3);
		}
		return flag;
	}
}