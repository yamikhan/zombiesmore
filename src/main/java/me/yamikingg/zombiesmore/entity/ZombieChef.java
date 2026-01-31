
package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;


public class ZombieChef extends AbstractMoZombie {
		public ZombieChef(EntityType<ZombieChef> entityType, Level world) {
			super(entityType,world);
		}
		public static int ID = 25;
		public static String name = "zombie_chef";

		protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
		}

	}
