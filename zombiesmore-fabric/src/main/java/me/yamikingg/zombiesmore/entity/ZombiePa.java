package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import javax.annotation.Nullable;

public class ZombiePa extends AbstractMoZombie {
	public ZombiePa(EntityType<ZombiePa> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 11;
	public static String NAME = "zombie_pa";

	@Override
	protected float getSoundVolume() {
		return 1.0F;
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
		// Add Pa-specific equipment here if needed
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 80.0D)      // Very long follow range!
				.add(Attributes.MOVEMENT_SPEED, 0.3D)     // Normal speed
				.add(Attributes.ATTACK_DAMAGE, 6.0D)      // Higher damage than normal zombies
				.add(Attributes.ARMOR, 0.0D)              // No armor
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D) // Added missing value
				.add(Attributes.MAX_HEALTH, 20.0D);       // Normal health
	}
}