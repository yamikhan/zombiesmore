
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

public class ZombiePirate extends AbstractMoZombie {
		public ZombiePirate(EntityType<ZombiePirate> entityType, Level world) {
			super(entityType, world);
		}
		public static int ID = 7;
		public static String name = "zombie_pirate";
		protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 25.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3D).add(Attributes.ATTACK_DAMAGE, 4D).add(Attributes.ARMOR, 0.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,20D);
		}
	}
