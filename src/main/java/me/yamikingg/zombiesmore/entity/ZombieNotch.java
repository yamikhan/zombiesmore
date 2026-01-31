
package me.yamikingg.zombiesmore.entity;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;


public  class ZombieNotch extends AbstractMoZombie {
		public ZombieNotch(EntityType<ZombieNotch> entityType, Level world) {
			super(entityType, world);
		}
		public static int ID = 1;
		public static String name = "zombie_notch";

		@Override
		protected void specialGoals(){
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieHerobrine.class, true));
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}


		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.MOVEMENT_SPEED, (double)0.25F).add(Attributes.ATTACK_DAMAGE, 100D).add(Attributes.ARMOR, 4.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,200D);
		}
	}
