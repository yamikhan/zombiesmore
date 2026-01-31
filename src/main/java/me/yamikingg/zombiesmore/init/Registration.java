package me.yamikingg.zombiesmore.init;

import me.yamikingg.zombiesmore.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.item.DiscoGlassesMaterial;


public class Registration {

	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ZombiesMore.MODID);
	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ZombiesMore.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ZombiesMore.MODID);

	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ENTITIES.register(eventBus);
		SOUNDS.register(eventBus);
		ITEMS.register(eventBus);

	}

	public static final RegistryObject<Item> DISCO_GLASSES = ITEMS.register("disco_glasses", () -> new ArmorItem(DiscoGlassesMaterial.GLASSES, EquipmentSlot.HEAD, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<SoundEvent> HURT_SURVIVOR = SOUNDS.register("hurt_survivor", () -> new SoundEvent(new ResourceLocation(ZombiesMore.MODID, "hurt_survivor")));
	public static final RegistryObject<SoundEvent> HURT_SURVIVOR_FEMALE = SOUNDS.register("hurt_survivor_female", () -> new SoundEvent(new ResourceLocation(ZombiesMore.MODID, "hurt_survivor_female")));

	public static final RegistryObject<Item> GIANT_SPAWN_EGG = ITEMS.register( "giant_spawn_egg", () -> new ForgeSpawnEggItem(() -> EntityType.GIANT, 44975, 7969893, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<Survivor>> SURVIVOR = ENTITIES.register(Survivor.name, () -> EntityType.Builder.of(Survivor::new, MobCategory.CREATURE).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, Survivor.name).toString()));

	public static final RegistryObject<Item> SURVIVOR_SPAWN_EGG = ITEMS.register(Survivor.name + "_spawn_egg", () -> new ForgeSpawnEggItem(SURVIVOR::get, -9611202, -12053227, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<DiscoZombie>> DISCO_ZOMBIE = ENTITIES.register(DiscoZombie.name, () -> EntityType.Builder.of(DiscoZombie::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, DiscoZombie.name).toString()));
	public static final RegistryObject<Item> DISCO_SPAWN_EGG = ITEMS.register(DiscoZombie.name + "_spawn_egg", () -> new ForgeSpawnEggItem(DISCO_ZOMBIE::get, -3342388, -16777216, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieChef>> ZOMBIE_CHEF = ENTITIES.register(ZombieChef.name, () -> EntityType.Builder.of(ZombieChef::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieChef.name).toString()));
	public static final RegistryObject<Item> CHEF_SPAWN_EGG = ITEMS.register(ZombieChef.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_CHEF::get, -3342388, -6750208, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieCyborg>> ZOMBIE_CYBORG = ENTITIES.register(ZombieCyborg.name, () -> EntityType.Builder.of(ZombieCyborg::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieCyborg.name).toString()));
	public static final RegistryObject<Item> CYBORG_SPAWN_EGG = ITEMS.register(ZombieCyborg.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_CYBORG::get, -10066330, -6750208, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieHerobrine>> ZOMBIE_HEROBRINE = ENTITIES.register(ZombieHerobrine.name, () -> EntityType.Builder.of(ZombieHerobrine::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieHerobrine.name).toString()));
	public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = ITEMS.register(ZombieHerobrine.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_HEROBRINE::get, -16751104, -16751002, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieKing>> ZOMBIE_KING = ENTITIES.register(ZombieKing.name, () -> EntityType.Builder.of(ZombieKing::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieKing.name).toString()));
	public static final RegistryObject<Item> KING_SPAWN_EGG = ITEMS.register(ZombieKing.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_KING::get, -16751104, -3355648, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieKnight>> ZOMBIE_KNIGHT = ENTITIES.register(ZombieKnight.name, () -> EntityType.Builder.of(ZombieKnight::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieKnight.name).toString()));
	public static final RegistryObject<Item> KNIGHT_SPAWN_EGG = ITEMS.register(ZombieKnight.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_KNIGHT::get, -16738048, -10066330, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieMiner>> ZOMBIE_MINER = ENTITIES.register(ZombieMiner.name, () -> EntityType.Builder.of(ZombieMiner::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieMiner.name).toString()));
	public static final RegistryObject<Item> MINER_SPAWN_EGG = ITEMS.register(ZombieMiner.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_MINER::get, -6750208, -13434676, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieNotch>> ZOMBIE_NOTCH = ENTITIES.register(ZombieNotch.name, () -> EntityType.Builder.of(ZombieNotch::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombieNotch.name).toString()));
	public static final RegistryObject<Item> NOTCH_SPAWN_EGG = ITEMS.register(ZombieNotch.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_NOTCH::get, -11851502, -16738048, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombiePa>> ZOMBIE_PA = ENTITIES.register(ZombiePa.name, () -> EntityType.Builder.of(ZombiePa::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombiePa.name).toString()));
	public static final RegistryObject<Item> PA_SPAWN_EGG = ITEMS.register(ZombiePa.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_PA::get, -10066330, -16737997, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombiePirate>> ZOMBIE_PIRATE = ENTITIES.register(ZombiePirate.name, () -> EntityType.Builder.of(ZombiePirate::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, ZombiePirate.name).toString()));
	public static final RegistryObject<Item> PIRATE_SPAWN_EGG = ITEMS.register(ZombiePirate.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_PIRATE::get, -16738048, -10092544, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieDwarf>> ZOMBIE_DWARF = ENTITIES.register(ZombieDwarf.name, () -> EntityType.Builder.of(ZombieDwarf::new, MobCategory.MONSTER).sized(0.6f, 1.7f).setTrackingRange(50).build(new ResourceLocation(ZombiesMore.MODID, ZombieDwarf.name).toString()));
	public static final RegistryObject<Item> DWARF_SPAWN_EGG = ITEMS.register(ZombieDwarf.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_DWARF::get, -3355444, -16738048, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<NetherZombie>> NETHER_ZOMBIE = ENTITIES.register(NetherZombie.name, () -> EntityType.Builder.of(NetherZombie::new, MobCategory.MONSTER).sized(0.6f, 1.95F).setTrackingRange(25).build(new ResourceLocation(ZombiesMore.MODID, NetherZombie.name).toString()));
	public static final RegistryObject<Item> NETHER_ZOMBIE_SPAWN_EGG = ITEMS.register(NetherZombie.name + "_spawn_egg", () -> new ForgeSpawnEggItem(NETHER_ZOMBIE::get, -10092544, -13434880, new Item.Properties().tab(ModObjects.ITEM_GROUP)));

	public static final RegistryObject<EntityType<ZombieCreeper>> ZOMBIE_CREEPER = ENTITIES.register(ZombieCreeper.name, () -> EntityType.Builder.of(ZombieCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.51f).setTrackingRange(50).build(new ResourceLocation(ZombiesMore.MODID, ZombieCreeper.name).toString()));
	public static final RegistryObject<Item> ZOMBIE_CREEPER_SPAWN_EGG = ITEMS.register(ZombieCreeper.name + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_CREEPER::get, -16724992, -16724788, new Item.Properties().tab(ModObjects.ITEM_GROUP)));


	@SubscribeEvent
	public static void setup(final EntityAttributeCreationEvent event){
			event.put(DISCO_ZOMBIE.get(), DiscoZombie.createAttributes().build());
			event.put(ZOMBIE_DWARF.get(), ZombieDwarf.createAttributes().build());
			event.put(ZOMBIE_CHEF.get(), ZombieChef.createAttributes().build());
			event.put(ZOMBIE_CYBORG.get(), ZombieCyborg.createAttributes().build());
			event.put(ZOMBIE_HEROBRINE.get(), ZombieHerobrine.createAttributes().build());
			event.put(ZOMBIE_KING.get(), ZombieKing.createAttributes().build());
			event.put(ZOMBIE_KNIGHT.get(), ZombieKnight.createAttributes().build());
			event.put(ZOMBIE_MINER.get(), ZombieMiner.createAttributes().build());
			event.put(ZOMBIE_NOTCH.get(), ZombieNotch.createAttributes().build());
			event.put(ZOMBIE_PA.get(), ZombiePa.createAttributes().build());
			event.put(ZOMBIE_PIRATE.get(), ZombiePirate.createAttributes().build());
			event.put(NETHER_ZOMBIE.get(), DiscoZombie.createAttributes().build());
			event.put(SURVIVOR.get(), Survivor.createAttributes().build());
			event.put(ZOMBIE_CREEPER.get(), ZombieCreeper.createAttributes().build());
	}


}
