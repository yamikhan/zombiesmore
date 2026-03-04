package me.yamikingg.zombiesmore.init;

import me.yamikingg.zombiesmore.entity.*;
import me.yamikingg.zombiesmore.item.DiscoGlassesMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import me.yamikingg.zombiesmore.ZombiesMore;


public class Registration {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ZombiesMore.MODID);
	private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ZombiesMore.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ZombiesMore.MODID);
	private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ZombiesMore.MODID);

	public static void init(IEventBus modEventBus) {
		ENTITIES.register(modEventBus);
		SOUNDS.register(modEventBus);
		ITEMS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
	}

	public static final RegistryObject<SoundEvent> HURT_SURVIVOR = SOUNDS.register("hurt_survivor", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiesMore.MODID, "hurt_survivor")));
	public static final RegistryObject<SoundEvent> HURT_SURVIVOR_FEMALE = SOUNDS.register("hurt_survivor_female", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ZombiesMore.MODID, "hurt_survivor_female")));

	public static final RegistryObject<EntityType<Survivor>> SURVIVOR = ENTITIES.register(Survivor.NAME, () -> EntityType.Builder.of(Survivor::new, MobCategory.CREATURE).sized(0.6f, 1.95F).build(Survivor.NAME));

	public static final RegistryObject<Item> SURVIVOR_SPAWN_EGG = ITEMS.register(Survivor.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(SURVIVOR, -9611202, -12053227, new Item.Properties()));

	public static final RegistryObject<EntityType<DiscoZombie>> DISCO_ZOMBIE = ENTITIES.register(DiscoZombie.NAME, () -> EntityType.Builder.of(DiscoZombie::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(DiscoZombie.NAME));
	public static final RegistryObject<Item> DISCO_SPAWN_EGG = ITEMS.register(DiscoZombie.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(DISCO_ZOMBIE, -3342388, -16777216, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieChef>> ZOMBIE_CHEF = ENTITIES.register(ZombieChef.NAME, () -> EntityType.Builder.of(ZombieChef::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieChef.NAME));
	public static final RegistryObject<Item> CHEF_SPAWN_EGG = ITEMS.register(ZombieChef.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_CHEF, -3342388, -6750208, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieCyborg>> ZOMBIE_CYBORG = ENTITIES.register(ZombieCyborg.NAME, () -> EntityType.Builder.of(ZombieCyborg::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieCyborg.NAME));
	public static final RegistryObject<Item> CYBORG_SPAWN_EGG = ITEMS.register(ZombieCyborg.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_CYBORG, -10066330, -6750208, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieHerobrine>> ZOMBIE_HEROBRINE = ENTITIES.register(ZombieHerobrine.NAME, () -> EntityType.Builder.of(ZombieHerobrine::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieHerobrine.NAME));
	public static final RegistryObject<Item> HEROBRINE_SPAWN_EGG = ITEMS.register(ZombieHerobrine.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_HEROBRINE, -16751104, -16751002, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieKing>> ZOMBIE_KING = ENTITIES.register(ZombieKing.NAME, () -> EntityType.Builder.of(ZombieKing::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieKing.NAME));
	public static final RegistryObject<Item> KING_SPAWN_EGG = ITEMS.register(ZombieKing.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_KING, -16751104, -3355648, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieKnight>> ZOMBIE_KNIGHT = ENTITIES.register(ZombieKnight.NAME, () -> EntityType.Builder.of(ZombieKnight::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieKnight.NAME));
	public static final RegistryObject<Item> KNIGHT_SPAWN_EGG = ITEMS.register(ZombieKnight.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_KNIGHT, -16738048, -10066330, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieMiner>> ZOMBIE_MINER = ENTITIES.register(ZombieMiner.NAME, () -> EntityType.Builder.of(ZombieMiner::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieMiner.NAME));
	public static final RegistryObject<Item> MINER_SPAWN_EGG = ITEMS.register(ZombieMiner.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_MINER, -6750208, -13434676, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieNotch>> ZOMBIE_NOTCH = ENTITIES.register(ZombieNotch.NAME, () -> EntityType.Builder.of(ZombieNotch::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieNotch.NAME));
	public static final RegistryObject<Item> NOTCH_SPAWN_EGG = ITEMS.register(ZombieNotch.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_NOTCH, -11851502, -16738048, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombiePa>> ZOMBIE_PA = ENTITIES.register(ZombiePa.NAME, () -> EntityType.Builder.of(ZombiePa::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombiePa.NAME));
	public static final RegistryObject<Item> PA_SPAWN_EGG = ITEMS.register(ZombiePa.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_PA, -10066330, -16737997, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombiePirate>> ZOMBIE_PIRATE = ENTITIES.register(ZombiePirate.NAME, () -> EntityType.Builder.of(ZombiePirate::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombiePirate.NAME));
	public static final RegistryObject<Item> PIRATE_SPAWN_EGG = ITEMS.register(ZombiePirate.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_PIRATE, -16738048, -10092544, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieDwarf>> ZOMBIE_DWARF = ENTITIES.register(ZombieDwarf.NAME, () -> EntityType.Builder.of(ZombieDwarf::new, MobCategory.MONSTER).sized(0.6f, 1.7f).build(ZombieDwarf.NAME));
	public static final RegistryObject<Item> DWARF_SPAWN_EGG = ITEMS.register(ZombieDwarf.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_DWARF, -3355444, -16738048, new Item.Properties()));

	public static final RegistryObject<EntityType<NetherZombie>> NETHER_ZOMBIE = ENTITIES.register(NetherZombie.NAME, () -> EntityType.Builder.of(NetherZombie::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(NetherZombie.NAME));
	public static final RegistryObject<Item> NETHER_ZOMBIE_SPAWN_EGG = ITEMS.register(NetherZombie.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(NETHER_ZOMBIE, -10092544, -13434880, new Item.Properties()));

	public static final RegistryObject<EntityType<ZombieCreeper>> ZOMBIE_CREEPER = ENTITIES.register(ZombieCreeper.NAME, () -> EntityType.Builder.of(ZombieCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.51f).build(ZombieCreeper.NAME));
	public static final RegistryObject<Item> ZOMBIE_CREEPER_SPAWN_EGG = ITEMS.register(ZombieCreeper.NAME + "_spawn_egg", () -> new ForgeSpawnEggItem(ZOMBIE_CREEPER, -16724992, -16724788, new Item.Properties()));

	public static final RegistryObject<Item> DISCO_GLASSES = ITEMS.register("disco_glasses",
			() -> new ArmorItem(
					DiscoGlassesMaterial.HOLDER,
					ArmorItem.Type.HELMET,
					new Item.Properties()
			)
	);

	public static final RegistryObject<CreativeModeTab> ITEM_GROUP = CREATIVE_MODE_TABS.register("item_group",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup." + ZombiesMore.MODID))
					.icon(() -> new ItemStack(DISCO_GLASSES.get()))
					.displayItems((parameters, output) -> {
						output.accept(DISCO_GLASSES.get());
						output.accept(SURVIVOR_SPAWN_EGG.get());
						output.accept(DISCO_SPAWN_EGG.get());
						output.accept(CHEF_SPAWN_EGG.get());
						output.accept(CYBORG_SPAWN_EGG.get());
						output.accept(HEROBRINE_SPAWN_EGG.get());
						output.accept(KING_SPAWN_EGG.get());
						output.accept(KNIGHT_SPAWN_EGG.get());
						output.accept(MINER_SPAWN_EGG.get());
						output.accept(NOTCH_SPAWN_EGG.get());
						output.accept(PA_SPAWN_EGG.get());
						output.accept(PIRATE_SPAWN_EGG.get());
						output.accept(DWARF_SPAWN_EGG.get());
						output.accept(NETHER_ZOMBIE_SPAWN_EGG.get());
						output.accept(ZOMBIE_CREEPER_SPAWN_EGG.get());
					})
					.build()
	);

	public static void setup(final EntityAttributeCreationEvent event) {
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
		event.put(NETHER_ZOMBIE.get(), NetherZombie.createAttributes().build());
		event.put(SURVIVOR.get(), Survivor.createAttributes().build());
		event.put(ZOMBIE_CREEPER.get(), ZombieCreeper.createAttributes().build());
	}
}