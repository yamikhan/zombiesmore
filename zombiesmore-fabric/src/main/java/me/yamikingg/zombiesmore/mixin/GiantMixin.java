package me.yamikingg.zombiesmore.mixin;

import me.yamikingg.zombiesmore.entity.ZombieNotch;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class GiantMixin {

    @Shadow
    public GoalSelector goalSelector;

    @Shadow
    public GoalSelector targetSelector;

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void injectGiantGoals(CallbackInfo ci) {
        if (!((Object) this instanceof Giant giant)) return;

        goalSelector.addGoal(1, new LookAtPlayerGoal(giant, Player.class, 8.0F));
        goalSelector.addGoal(1, new RandomLookAroundGoal(giant));
        goalSelector.addGoal(2, new MeleeAttackGoal(giant, 1.0D, false));
        goalSelector.addGoal(6, new MoveThroughVillageGoal(giant, 1.0D, true, 4, () -> true));
        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(giant, 1.0D));

        targetSelector.addGoal(1, new HurtByTargetGoal(giant).setAlertOthers(ZombifiedPiglin.class));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, ZombieNotch.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Player.class, true));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Villager.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, IronGolem.class, true));
        targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, Turtle.class, true));
    }
}