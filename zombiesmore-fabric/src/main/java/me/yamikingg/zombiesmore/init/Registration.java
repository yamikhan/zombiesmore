package me.yamikingg.zombiesmore.init;

import me.yamikingg.zombiesmore.entity.*;
import me.yamikingg.zombiesmore.item.DiscoGlassesMaterial;
import me.yamikingg.zombiesmore.ZombiesMoreFabric;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public class Registration {

	// ============== ITEMS ==============
	public static final Item DISCO_GLASSES = new ArmorItem(
			DiscoGlassesMaterial.GLASSES,
			ArmorItem.Type.HELMET,
			new Item.Properties()
	);

	public static final Item GIANT_SPAWN_EGG = new SpawnEggItem(
			EntityType.GIANT, 44975, 7969893, new Item.Properties()
	);

	// ============== SOUND EVENTS ==============
	public static final SoundEvent HURT_SURVIVOR = SoundEvent.createVariableRangeEvent(
			new ResourceLocation(ZombiesMoreFabric.MODID, "hurt_survivor")
	);
	public static final SoundEvent HURT_SURVIVOR_FEMALE = SoundEvent.createVariableRangeEvent(
			new ResourceLocation(ZombiesMoreFabric.MODID, "hurt_survivor_female")
	);

	// ============== ENTITIES ==============
	public static final EntityType<Survivor> SURVIVOR = FabricEntityTypeBuilder.<Survivor>create(MobCategory.CREATURE, Survivor::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<DiscoZombie> DISCO_ZOMBIE = FabricEntityTypeBuilder.<DiscoZombie>create(MobCategory.MONSTER, DiscoZombie::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieChef> ZOMBIE_CHEF = FabricEntityTypeBuilder.<ZombieChef>create(MobCategory.MONSTER, ZombieChef::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieCyborg> ZOMBIE_CYBORG = FabricEntityTypeBuilder.<ZombieCyborg>create(MobCategory.MONSTER, ZombieCyborg::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieHerobrine> ZOMBIE_HEROBRINE = FabricEntityTypeBuilder.<ZombieHerobrine>create(MobCategory.MONSTER, ZombieHerobrine::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieKing> ZOMBIE_KING = FabricEntityTypeBuilder.<ZombieKing>create(MobCategory.MONSTER, ZombieKing::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieKnight> ZOMBIE_KNIGHT = FabricEntityTypeBuilder.<ZombieKnight>create(MobCategory.MONSTER, ZombieKnight::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieMiner> ZOMBIE_MINER = FabricEntityTypeBuilder.<ZombieMiner>create(MobCategory.MONSTER, ZombieMiner::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieNotch> ZOMBIE_NOTCH = FabricEntityTypeBuilder.<ZombieNotch>create(MobCategory.MONSTER, ZombieNotch::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombiePa> ZOMBIE_PA = FabricEntityTypeBuilder.<ZombiePa>create(MobCategory.MONSTER, ZombiePa::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombiePirate> ZOMBIE_PIRATE = FabricEntityTypeBuilder.<ZombiePirate>create(MobCategory.MONSTER, ZombiePirate::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieDwarf> ZOMBIE_DWARF = FabricEntityTypeBuilder.<ZombieDwarf>create(MobCategory.MONSTER, ZombieDwarf::new)
			.dimensions(EntityDimensions.scalable(0.7f, 1.3f)).build();

	public static final EntityType<NetherZombie> NETHER_ZOMBIE = FabricEntityTypeBuilder.<NetherZombie>create(MobCategory.MONSTER, NetherZombie::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.95f)).build();

	public static final EntityType<ZombieCreeper> ZOMBIE_CREEPER = FabricEntityTypeBuilder.<ZombieCreeper>create(MobCategory.MONSTER, ZombieCreeper::new)
			.dimensions(EntityDimensions.scalable(0.6f, 1.51f)).build();

	// ============== SPAWN EGGS ==============
	public static final Item SURVIVOR_SPAWN_EGG = new SpawnEggItem(SURVIVOR, -9611202, -12053227, new Item.Properties());
	public static final Item DISCO_SPAWN_EGG = new SpawnEggItem(DISCO_ZOMBIE, -3342388, -16777216, new Item.Properties());
	public static final Item CHEF_SPAWN_EGG = new SpawnEggItem(ZOMBIE_CHEF, -3342388, -6750208, new Item.Properties());
	public static final Item CYBORG_SPAWN_EGG = new SpawnEggItem(ZOMBIE_CYBORG, -10066330, -6750208, new Item.Properties());
	public static final Item HEROBRINE_SPAWN_EGG = new SpawnEggItem(ZOMBIE_HEROBRINE, -16751104, -16751002, new Item.Properties());
	public static final Item KING_SPAWN_EGG = new SpawnEggItem(ZOMBIE_KING, -16751104, -3355648, new Item.Properties());
	public static final Item KNIGHT_SPAWN_EGG = new SpawnEggItem(ZOMBIE_KNIGHT, -16738048, -10066330, new Item.Properties());
	public static final Item MINER_SPAWN_EGG = new SpawnEggItem(ZOMBIE_MINER, -6750208, -13434676, new Item.Properties());
	public static final Item NOTCH_SPAWN_EGG = new SpawnEggItem(ZOMBIE_NOTCH, -11851502, -16738048, new Item.Properties());
	public static final Item PA_SPAWN_EGG = new SpawnEggItem(ZOMBIE_PA, -10066330, -16737997, new Item.Properties());
	public static final Item PIRATE_SPAWN_EGG = new SpawnEggItem(ZOMBIE_PIRATE, -16738048, -10092544, new Item.Properties());
	public static final Item DWARF_SPAWN_EGG = new SpawnEggItem(ZOMBIE_DWARF, -3355444, -16738048, new Item.Properties());
	public static final Item NETHER_ZOMBIE_SPAWN_EGG = new SpawnEggItem(NETHER_ZOMBIE, -10092544, -13434880, new Item.Properties());
	public static final Item ZOMBIE_CREEPER_SPAWN_EGG = new SpawnEggItem(ZOMBIE_CREEPER, -16724992, -16724788, new Item.Properties());

	// ============== CREATIVE TAB ==============
	public static final CreativeModeTab ITEM_GROUP = FabricItemGroup.builder()
			.title(Component.translatable("itemGroup." + ZombiesMoreFabric.MODID))
			.icon(() -> new ItemStack(DISCO_GLASSES))
			.displayItems((enabledFeatures, entries) -> {
				entries.accept(DISCO_GLASSES);
				entries.accept(GIANT_SPAWN_EGG);
				entries.accept(SURVIVOR_SPAWN_EGG);
				entries.accept(DISCO_SPAWN_EGG);
				entries.accept(CHEF_SPAWN_EGG);
				entries.accept(CYBORG_SPAWN_EGG);
				entries.accept(HEROBRINE_SPAWN_EGG);
				entries.accept(KING_SPAWN_EGG);
				entries.accept(KNIGHT_SPAWN_EGG);
				entries.accept(MINER_SPAWN_EGG);
				entries.accept(NOTCH_SPAWN_EGG);
				entries.accept(PA_SPAWN_EGG);
				entries.accept(PIRATE_SPAWN_EGG);
				entries.accept(DWARF_SPAWN_EGG);
				entries.accept(NETHER_ZOMBIE_SPAWN_EGG);
				entries.accept(ZOMBIE_CREEPER_SPAWN_EGG);
			})
			.build();

	// ============== REGISTRATION ==============
	public static void init() {
		// Items
		Registry.register(BuiltInRegistries.ITEM, id("disco_glasses"), DISCO_GLASSES);
		Registry.register(BuiltInRegistries.ITEM, id("giant_spawn_egg"), GIANT_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(Survivor.NAME + "_spawn_egg"), SURVIVOR_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(DiscoZombie.NAME + "_spawn_egg"), DISCO_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieChef.NAME + "_spawn_egg"), CHEF_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieCyborg.NAME + "_spawn_egg"), CYBORG_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieHerobrine.NAME + "_spawn_egg"), HEROBRINE_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieKing.NAME + "_spawn_egg"), KING_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieKnight.NAME + "_spawn_egg"), KNIGHT_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieMiner.NAME + "_spawn_egg"), MINER_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieNotch.NAME + "_spawn_egg"), NOTCH_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombiePa.NAME + "_spawn_egg"), PA_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombiePirate.NAME + "_spawn_egg"), PIRATE_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieDwarf.NAME + "_spawn_egg"), DWARF_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(NetherZombie.NAME + "_spawn_egg"), NETHER_ZOMBIE_SPAWN_EGG);
		Registry.register(BuiltInRegistries.ITEM, id(ZombieCreeper.NAME + "_spawn_egg"), ZOMBIE_CREEPER_SPAWN_EGG);

		// Sound Events
		Registry.register(BuiltInRegistries.SOUND_EVENT, id("hurt_survivor"), HURT_SURVIVOR);
		Registry.register(BuiltInRegistries.SOUND_EVENT, id("hurt_survivor_female"), HURT_SURVIVOR_FEMALE);

		// Entities
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(Survivor.NAME), SURVIVOR);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(DiscoZombie.NAME), DISCO_ZOMBIE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieChef.NAME), ZOMBIE_CHEF);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieCyborg.NAME), ZOMBIE_CYBORG);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieHerobrine.NAME), ZOMBIE_HEROBRINE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieKing.NAME), ZOMBIE_KING);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieKnight.NAME), ZOMBIE_KNIGHT);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieMiner.NAME), ZOMBIE_MINER);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieNotch.NAME), ZOMBIE_NOTCH);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombiePa.NAME), ZOMBIE_PA);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombiePirate.NAME), ZOMBIE_PIRATE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieDwarf.NAME), ZOMBIE_DWARF);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(NetherZombie.NAME), NETHER_ZOMBIE);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, id(ZombieCreeper.NAME), ZOMBIE_CREEPER);

		// Creative Tab
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("item_group"), ITEM_GROUP);
	}

	// ============== ENTITY ATTRIBUTES ==============
	public static void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(DISCO_ZOMBIE, DiscoZombie.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_DWARF, ZombieDwarf.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_CHEF, ZombieChef.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_CYBORG, ZombieCyborg.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_HEROBRINE, ZombieHerobrine.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_KING, ZombieKing.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_KNIGHT, ZombieKnight.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_MINER, ZombieMiner.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_NOTCH, ZombieNotch.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_PA, ZombiePa.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_PIRATE, ZombiePirate.createAttributes());
		FabricDefaultAttributeRegistry.register(NETHER_ZOMBIE, NetherZombie.createAttributes()); // ✅ Fixed from original
		FabricDefaultAttributeRegistry.register(SURVIVOR, Survivor.createAttributes());
		FabricDefaultAttributeRegistry.register(ZOMBIE_CREEPER, ZombieCreeper.createAttributes());
	}

	private static ResourceLocation id(String path) {
		return new ResourceLocation(ZombiesMoreFabric.MODID, path);
	}
}