package me.yamikingg.zombiesmore.init;

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
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import me.yamikingg.zombiesmore.entity.ZombieNotch;

import static me.yamikingg.zombiesmore.ZombiesMore.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModObjects {


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        MobSpawnSettingsBuilder spawns = event.getSpawns();
        if (event.getCategory() == Biome.BiomeCategory.TAIGA || event.getCategory() == Biome.BiomeCategory.DESERT)
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GIANT, 3, 1, 2));
        if (event.getCategory() == Biome.BiomeCategory.NETHER)
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.NETHER_ZOMBIE.get(), 3, 1, 4));
        else if (event.getCategory() != Biome.BiomeCategory.THEEND){
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.DISCO_ZOMBIE.get(), 6, 1, 4));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_CREEPER.get(), 3, 1, 3));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_DWARF.get(), 10, 1, 5));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_CHEF.get(), 8, 1, 4));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_CYBORG.get(), 5, 1, 4));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_HEROBRINE.get(), 1, 1, 1));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_NOTCH.get(), 2, 1, 1));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_KING.get(), 4, 1, 2));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_KNIGHT.get(), 3, 1, 4));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_MINER.get(), 4, 2, 7));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_PA.get(), 5, 1, 2));
            spawns.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(Registration.ZOMBIE_PIRATE.get(), 7, 1, 3));
        }
        spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(Registration.SURVIVOR.get(), 3, 1, 4));

    }
    @SubscribeEvent
    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof Giant giant) {
            giant.goalSelector.addGoal(1, new LookAtPlayerGoal(giant, Player.class, 8.0F));
            giant.goalSelector.addGoal(1, new RandomLookAroundGoal(giant));
            giant.goalSelector.addGoal(2, new MeleeAttackGoal(giant, 1.0D, false));
            giant.goalSelector.addGoal(6, new MoveThroughVillageGoal(giant, 1.0D, true, 4, () -> true));
            giant.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(giant, 1.0D));
            giant.goalSelector.addGoal(1, (new HurtByTargetGoal(giant)).setAlertOthers(ZombifiedPiglin.class));
            giant.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, ZombieNotch.class, true));
            giant.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Player.class, true));
            giant.goalSelector.addGoal(2, new NearestAttackableTargetGoal<>(giant, Villager.class, true));
            giant.goalSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, IronGolem.class, true));
            giant.goalSelector.addGoal(3, new NearestAttackableTargetGoal<>(giant, Turtle.class, true));
        }
    }
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(MODID) {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.DISCO_GLASSES.get());
        }
    };
}