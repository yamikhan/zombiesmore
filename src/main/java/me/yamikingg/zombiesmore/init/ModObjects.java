package me.yamikingg.zombiesmore.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import me.yamikingg.zombiesmore.entity.ZombieNotch;
import org.jetbrains.annotations.NotNull;

import static me.yamikingg.zombiesmore.ZombiesMore.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModObjects {

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(Registration.DISCO_GLASSES.get());
        }
    };

    // For 1.19.2, you need to use Biome Modifiers in JSON files instead of BiomeLoadingEvent
    // BiomeLoadingEvent was removed in 1.19+
    // You'll need to create biome modifier JSON files in data/modid/forge/biome_modifier/

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