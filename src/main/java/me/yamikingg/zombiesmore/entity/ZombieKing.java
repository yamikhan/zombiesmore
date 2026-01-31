
package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ZombieKing extends AbstractMoZombie {
		public ZombieKing(EntityType<ZombieKing> entityType, Level world) {
			super(entityType, world);
		}
		public static int ID = 19;
		public static String name = "zombie_king";


	}
