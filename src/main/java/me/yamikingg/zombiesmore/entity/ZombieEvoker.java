package me.yamikingg.zombiesmore.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.level.Level;

public class ZombieEvoker extends Evoker {
    public static final String NAME = "zombie_evoker";

    public ZombieEvoker(EntityType<? extends Evoker> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public boolean isInvertedHealAndHarm() {
        return true;
    }


    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.EVOKER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.EVOKER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.EVOKER_DEATH;
    }

    public SoundEvent getCelebrateSound() {
        return SoundEvents.EVOKER_CELEBRATE;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }


    @Override
    public boolean canBeAffected(net.minecraft.world.effect.MobEffectInstance effectInstance) {
        return super.canBeAffected(effectInstance);
    }

    public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
        return Evoker.createAttributes()
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MAX_HEALTH, 25.0D)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED, 0.35F)
                .add(net.minecraft.world.entity.ai.attributes.Attributes.FOLLOW_RANGE, 50.0D);
    }
}