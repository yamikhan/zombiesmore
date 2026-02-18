package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ZombieChef extends AbstractMoZombie {
	public ZombieChef(EntityType<ZombieChef> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID = 25;
	public static String NAME = "zombie_chef";

	@Override
	protected void populateDefaultEquipmentSlots(net.minecraft.util.RandomSource random, DifficultyInstance difficulty) {
		super.populateDefaultEquipmentSlots(random, difficulty); // Call super first
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
	}

	// Create attribute builder method
	public static AttributeSupplier.Builder createAttributes() {
		return Zombie.createAttributes()
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.23F)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D);
	}
}