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

public class ZombieMiner extends AbstractMoZombie {
	public ZombieMiner(EntityType<ZombieMiner> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 3;
	public static String NAME = "zombie_miner";


	@Nullable
	public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(
			ServerLevelAccessor level,
			DifficultyInstance difficulty,
			net.minecraft.world.entity.MobSpawnType spawnType,
			@Nullable net.minecraft.world.entity.SpawnGroupData spawnData,
			@Nullable CompoundTag dataTag) {

		net.minecraft.world.entity.SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnData);
		this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
		return data;
	}

	// Updated method signature for 1.20.1
	@Override
	protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty);
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
		// Optional: Add miner-themed helmet (iron helmet looks like a mining helmet)
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
	}

	// You should add an attribute creation method for proper registration
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.23F)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
				.add(Attributes.MAX_HEALTH, 20.0D);
	}
}