package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.nbt.CompoundTag;
import javax.annotation.Nullable;

public class ZombiePirate extends AbstractMoZombie {
	public ZombiePirate(EntityType<ZombiePirate> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 7;
	public static String NAME = "zombie_pirate";

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
		this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return data;
	}

	// Updated method signature for 1.20.1
	@Override
	protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));

		// Optional: Add pirate-themed equipment
		// Pirate hat/headband
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
		// Eye patch could be represented by a pumpkin on head sometimes
		if (random.nextInt(10) == 0) {
			this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CARVED_PUMPKIN));
		}
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}


	@Override
	public int getAirSupply() {
		return Integer.MAX_VALUE;
	}

	// Optional: Make them spawn near water
	@Override
	public boolean checkSpawnRules(net.minecraft.world.level.LevelAccessor level, net.minecraft.world.entity.MobSpawnType spawnType) {
		// Pirates should spawn near water
		return super.checkSpawnRules(level, spawnType);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 25.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 4.0D)        // Golden sword does less damage but pirates are skilled
				.add(Attributes.ARMOR, 0.0D)                // No armor, but maybe add leather armor?
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D) // Added missing value
				.add(Attributes.MAX_HEALTH, 20.0D);
	}
}