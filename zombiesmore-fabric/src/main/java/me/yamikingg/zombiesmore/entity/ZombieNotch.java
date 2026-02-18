package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class ZombieNotch extends AbstractMoZombie {
	public ZombieNotch(EntityType<ZombieNotch> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 1;
	public static String NAME = "zombie_notch";

	@Override
	protected void specialGoals() {
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieHerobrine.class, true));
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	// 1.20.1 requires proper finalizeSpawn method for spawning
	@Override
	@Nullable
	public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(
            @NotNull ServerLevelAccessor level,
            @NotNull DifficultyInstance difficulty,
            net.minecraft.world.entity.@NotNull MobSpawnType spawnType,
            @Nullable net.minecraft.world.entity.SpawnGroupData spawnData,
            @Nullable CompoundTag dataTag) {

		net.minecraft.world.entity.SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData, dataTag);
		return data;
	}

	// Optional: Override populateDefaultEquipmentSlots if needed
	@Override
	protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		// Add Notch-specific equipment here if needed (e.g., diamond sword/apple?)
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 30.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25F)
				.add(Attributes.ATTACK_DAMAGE, 100.0D) // Extremely high damage!
				.add(Attributes.ARMOR, 4.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D) // Added missing value
				.add(Attributes.MAX_HEALTH, 200.0D); // Very high health!
	}
}