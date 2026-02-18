package me.yamikingg.zombiesmore.init;

import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import me.yamikingg.zombiesmore.entity.ZombieNotch;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModObjects {

    @SubscribeEvent
    public static void onEntityJoinWorld(final EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Giant giant) {
            // Clear existing goals first to avoid duplicates
            giant.goalSelector.getAvailableGoals().clear();
            giant.targetSelector.getAvailableGoals().clear();

            // Add movement and behavior goals
            giant.goalSelector.addGoal(1, new LookAtPlayerGoal(giant, Player.class, 8.0F));
            giant.goalSelector.addGoal(1, new RandomLookAroundGoal(giant));
            giant.goalSelector.addGoal(2, new MeleeAttackGoal(giant, 1.0D, false));
            giant.goalSelector.addGoal(6, new MoveThroughVillageGoal(giant, 1.0D, true, 4, () -> true));
            giant.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(giant, 1.0D));

            // Add target goals
            giant.targetSelector.addGoal(1, (new HurtByTargetGoal(giant)).setAlertOthers(ZombifiedPiglin.class));
            giant.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, ZombieNotch.class, true));
            giant.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Player.class, true));
            giant.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Villager.class, true));
            giant.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, IronGolem.class, true));
            giant.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, Turtle.class, true));
        }
    }
}