
package me.yamikingg.zombiesmore.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class ZombiePa extends AbstractMoZombie {
		public ZombiePa(EntityType<ZombiePa> entityType, Level world) {
			super(entityType, world);
		}
		public static int ID = 11;
		public static String name = "zombie_pa";


		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 80.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3D).add(Attributes.ATTACK_DAMAGE, 6D).add(Attributes.ARMOR, 0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,20D);
		}

	}

