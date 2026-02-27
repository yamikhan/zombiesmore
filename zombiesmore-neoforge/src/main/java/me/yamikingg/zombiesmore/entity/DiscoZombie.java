package me.yamikingg.zombiesmore.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DiscoZombie extends AbstractMoZombie {

	public static final int ID = 23;
	public static final String NAME = "disco_zombie";

	public DiscoZombie(EntityType<? extends AbstractMoZombie> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	public @NotNull SoundEvent getAmbientSound() {
		return SoundEvents.HUSK_AMBIENT;
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public void tick() {
		super.tick();

		// Only spawn particles on client side
		if (this.level().isClientSide()) {
			RandomSource random = this.random;

			// Spawn note particles around the zombie with some chance to reduce spam
			if (random.nextInt(3) == 0) { // 1 in 3 chance each tick
				for (int l = 0; l < 2; ++l) { // Spawn 2 particles when we do spawn
					double offsetX = (random.nextDouble() - 0.5D) * 2.0D;
					double offsetY = random.nextDouble() * 2.0D;
					double offsetZ = (random.nextDouble() - 0.5D) * 2.0D;

					// Position relative to zombie
					double particleX = this.getX() + offsetX;
					double particleY = this.getY() + offsetY;
					double particleZ = this.getZ() + offsetZ;

					// Small random velocity
					double velX = (random.nextDouble() - 0.5D) * 0.05D;
					double velY = random.nextDouble() * 0.05D;
					double velZ = (random.nextDouble() - 0.5D) * 0.05D;

					// Note: Note particle color is determined by the pitch parameter (0-24)
					// 0 = green, 6 = blue, 12 = purple, 18 = red, 24 = yellow
					int noteColor = random.nextInt(25); // Random color

					this.level().addParticle(ParticleTypes.NOTE,
							particleX, particleY, particleZ,
							noteColor / 24.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	protected void specialGoals() {
		// You can override special goals here if DiscoZombie needs different targeting
		super.specialGoals();
	}

	@Override
	protected boolean convertsInWater() {
		return false; // Disco zombies don't convert in water
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		// You could add server-side disco logic here if needed
	}
}