
package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ZombieCyborg extends AbstractMoZombie {
		public ZombieCyborg(EntityType<ZombieCyborg> entityType, Level world) {
			super(entityType,world);
		}
		public static int ID = 13;
		public static String name = "zombie_cyborg";

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 50.0D).add(Attributes.MOVEMENT_SPEED, (double)0.4F).add(Attributes.ATTACK_DAMAGE, 4D).add(Attributes.ARMOR, 6.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,20D);
		}
	}
