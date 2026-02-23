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

public class ZombieKing extends AbstractMoZombie {
	public ZombieKing(EntityType<ZombieKing> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 19;
	public static String NAME = "zombie_king";


	@Nullable
	public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(
			ServerLevelAccessor level,
			DifficultyInstance difficulty,
			net.minecraft.world.entity.MobSpawnType spawnType,
			@Nullable net.minecraft.world.entity.SpawnGroupData spawnData,
			@Nullable CompoundTag dataTag) {

		net.minecraft.world.entity.SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
		return data;
	}

	// Optional: Override populateDefaultEquipmentSlots if needed
	@Override
	protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		// Add king-specific equipment here (e.g., crown, royal scepter, etc.)
	}

	// You should add an attribute creation method for proper registration
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 50.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.25F)
				.add(Attributes.ATTACK_DAMAGE, 8.0D) // Higher than normal zombies
				.add(Attributes.ARMOR, 8.0D) // Better armor
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.5D) // Can summon reinforcements
				.add(Attributes.MAX_HEALTH, 80.0D); // More health
	}
}