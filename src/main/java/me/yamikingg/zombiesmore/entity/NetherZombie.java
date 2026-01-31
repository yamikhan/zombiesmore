
package me.yamikingg.zombiesmore.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;


public class NetherZombie extends AbstractMoZombie {
		public NetherZombie(EntityType<NetherZombie> entityType, Level world) {
			super(entityType, world);
		}
		public static int ID = 9;
		public static String name = "nether_zombie";

		@Override
		public boolean fireImmune() {
			return true;
		}

		@Override
		public SoundEvent getAmbientSound() {
			return SoundEvents.HUSK_AMBIENT;
		}

		@Override
		public SoundEvent getHurtSound(DamageSource ds) {
			return SoundEvents.ZOMBIFIED_PIGLIN_HURT;
		}

		@Override
		public SoundEvent getDeathSound() {
			return SoundEvents.GHAST_DEATH;
		}

		public static AttributeSupplier.Builder createAttributes() {
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 50.0D).add(Attributes.MOVEMENT_SPEED, (double)0.35F).add(Attributes.ATTACK_DAMAGE, 5D).add(Attributes.ARMOR, 4.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,25D);
		}
	}
