package me.yamikingg.zombiesmore.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import me.yamikingg.zombiesmore.ZombiesMoreFabric;

public abstract class AbstractMoZombie extends Zombie {

	public AbstractMoZombie(EntityType<? extends Zombie> entityType, Level world) {
		super(entityType, world);
	}

	public static int ID;
	public static String NAME;

	public String getNAMEID() {
		return this.NAME;
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return SoundEvents.ZOMBIE_VILLAGER_HURT;
	}

	@Override
	public SoundEvent getDeathSound() {
		return SoundEvents.HUSK_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FOLLOW_RANGE, 45.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3F)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D) // Fixed: Added value
				.add(Attributes.MAX_HEALTH, 25.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		specialGoals();
	}

	protected void specialGoals() {
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieNotch.class, true));
	}

	// New method for 1.20+ - if you want to keep the old NAME for compatibility
	@Override
	protected boolean convertsInWater() {
		return false; // Husk-specific behavior, change as needed
	}

	@Environment(EnvType.CLIENT)
	public static class MoZombieRenderer extends AbstractZombieRenderer<AbstractMoZombie, ZombieModel<AbstractMoZombie>> {
		private final ResourceLocation texture;

		public MoZombieRenderer(EntityRendererProvider.Context context, String NAME) {
			this(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR, NAME);
		}

		public MoZombieRenderer(EntityRendererProvider.Context context,
								ModelLayerLocation layer,
								ModelLayerLocation innerArmorLayer,
								ModelLayerLocation outerArmorLayer,
								String NAME) {
			super(context,
					new ZombieModel<>(context.bakeLayer(layer)),
					new ZombieModel<>(context.bakeLayer(innerArmorLayer)),
					new ZombieModel<>(context.bakeLayer(outerArmorLayer)));
			this.texture = new ResourceLocation(ZombiesMoreFabric.MODID, "textures/entity/" + NAME + ".png");
		}

		@Override
		public ResourceLocation getTextureLocation(AbstractMoZombie entity) {
			return texture;
		}
	}
}