
package me.yamikingg.zombiesmore.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

	public class DiscoZombie extends AbstractMoZombie {
		public DiscoZombie(EntityType<? extends AbstractMoZombie> entityType, Level world) {
			super(entityType,world);

		}
		public static int ID = 23;
		public static String name = "disco_zombie";


		@Override
		public @NotNull SoundEvent getAmbientSound() {
			return SoundEvents.MUSIC_DISC_STAL;
		}

		@Override
		protected float getSoundVolume() {
			return 1.0F;
		}

		public void tick() {
			super.tick();

			Random random = (Random) this.random;
			if (true)
				for (int l = 0; l < 1; ++l) {
					double d0 = (getX() + random.nextFloat());
					double d1 = (getY() + random.nextFloat());
					double d2 = (getZ() + random.nextFloat());
					int i1 = random.nextInt(2) * 2 - 1;
					double d3 = (random.nextFloat() - 0.5D) * 0.25D;
					double d4 = (random.nextFloat() - 0.5D) * 0.25D;
					double d5 = (random.nextFloat() - 0.5D) * 0.25D;
					level.addParticle(ParticleTypes.NOTE, d0, d1, d2, d3, d4, d5);
				}
		}

	}

