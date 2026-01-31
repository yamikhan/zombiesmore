
package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ZombieMiner extends AbstractMoZombie {
		public ZombieMiner(EntityType<ZombieMiner> entityType, Level world) {
			super(entityType, world);
		}
		public static int ID = 3;
		public static String name = "zombie_miner";

		protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_PICKAXE));
		}
	}
