
package me.yamikingg.zombiesmore.entity;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;


public class ZombieHerobrine extends AbstractMoZombie {
		public ZombieHerobrine(EntityType<ZombieHerobrine> entityType, Level world) {
			super(entityType,world);
		}
		public static final int ID = 17;
		public static final String name = "zombie_herobrine";

		@Override
		public boolean fireImmune() {
			return true;
		}

		@Override
		public SoundEvent getAmbientSound() {
			return null;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return null;
		}

		@Override
		public SoundEvent getDeathSound() {
			return null;
		}

		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 30.0D).add(Attributes.MOVEMENT_SPEED, (double)1F).add(Attributes.ATTACK_DAMAGE, 45D).add(Attributes.ARMOR, 4.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,300D);
		}
	}
