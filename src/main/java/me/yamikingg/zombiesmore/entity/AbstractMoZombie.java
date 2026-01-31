
package me.yamikingg.zombiesmore.entity;


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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import me.yamikingg.zombiesmore.ZombiesMore;

public abstract class AbstractMoZombie extends Zombie {

		public AbstractMoZombie(EntityType<? extends Zombie> entityType, Level world) {
			super(entityType,world);

		}
		public static int ID;
		public static String name;

		public String getNameID(){
			return this.name;
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
			return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 45.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE).add(Attributes.MAX_HEALTH,25D);
		}

		protected void addBehaviourGoals() {
			super.addBehaviourGoals();
			specialGoals();

		}
		protected void specialGoals(){
			this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, ZombieNotch.class, true));
		}
		@OnlyIn(Dist.CLIENT)
		public static class MoZombieRenderer extends AbstractZombieRenderer<AbstractMoZombie, ZombieModel<AbstractMoZombie>> {
			private String location;

			public MoZombieRenderer(EntityRendererProvider.Context p_174456_, String name) {
				this(p_174456_, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
				location = name;
			}

			public MoZombieRenderer(EntityRendererProvider.Context p_174458_, ModelLayerLocation p_174459_, ModelLayerLocation p_174460_, ModelLayerLocation p_174461_) {
				super(p_174458_, new ZombieModel<>(p_174458_.bakeLayer(p_174459_)), new ZombieModel<>(p_174458_.bakeLayer(p_174460_)), new ZombieModel<>(p_174458_.bakeLayer(p_174461_)));
			}

			@Override
			public ResourceLocation getTextureLocation(Zombie entity) {
				return new ResourceLocation(ZombiesMore.MODID, "textures/entity/" + location + ".png");
			}
		}
	}
