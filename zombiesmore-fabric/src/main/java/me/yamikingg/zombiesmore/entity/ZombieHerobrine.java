package me.yamikingg.zombiesmore.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import javax.annotation.Nullable;

public class ZombieHerobrine extends AbstractMoZombie {
	public ZombieHerobrine(EntityType<ZombieHerobrine> entityType, Level world) {
		super(entityType, world);
	}

	public static final int ID = 17;
	public static final String NAME = "zombie_herobrine";

	@Override
	public boolean fireImmune() {
		return true;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return null;
	}

	@Override
	public SoundEvent getDeathSound() {
		return null;
	}

	// 1.20.1 requires proper finalizeSpawn method for spawning
	@Override
	@Nullable
	public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(
			ServerLevelAccessor level,
			DifficultyInstance difficulty,
			net.minecraft.world.entity.MobSpawnType spawnType,
			@Nullable net.minecraft.world.entity.SpawnGroupData spawnData,
			@Nullable CompoundTag dataTag) {

		net.minecraft.world.entity.SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
		return data;
	}

	// Optional: Override populateDefaultEquipmentSlots if needed
	@Override
	protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		// Add Herobrine-specific equipment here if needed
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 1.0F) // Very fast!
				.add(Attributes.ATTACK_DAMAGE, 45.0D) // Very high damage!
				.add(Attributes.ARMOR, 4.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D) // Added missing value
				.add(Attributes.MAX_HEALTH, 300.0D); // Very high health!
	}
}