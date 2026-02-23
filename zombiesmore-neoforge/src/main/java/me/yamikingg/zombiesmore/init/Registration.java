package me.yamikingg.zombiesmore.init;

import me.yamikingg.zombiesmore.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import me.yamikingg.zombiesmore.ZombiesMore;
import me.yamikingg.zombiesmore.item.DiscoGlassesMaterial;

import java.util.function.Supplier;

@EventBusSubscriber(modid = ZombiesMore.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Registration {

	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ZombiesMore.MODID);
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, ZombiesMore.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, ZombiesMore.MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ZombiesMore.MODID);

	public static void init(IEventBus modEventBus) {
		ENTITIES.register(modEventBus);
		SOUNDS.register(modEventBus);
		ITEMS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
	}

	public static final DeferredItem<Item> DISCO_GLASSES = ITEMS.register("disco_glasses",
			() -> new ArmorItem(DiscoGlassesMaterial.GLASSES, ArmorItem.Type.HELMET, new Item.Properties()));

	public static final Supplier<SoundEvent> HURT_SURVIVOR = SOUNDS.register("hurt_survivor",
			() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZombiesMore.MODID, "hurt_survivor")));

	public static final Supplier<SoundEvent> HURT_SURVIVOR_FEMALE = SOUNDS.register("hurt_survivor_female",
			() -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ZombiesMore.MODID, "hurt_survivor_female")));

	public static final Supplier<EntityType<Survivor>> SURVIVOR = ENTITIES.register(Survivor.NAME,
			() -> EntityType.Builder.of(Survivor::new, MobCategory.CREATURE).sized(0.6f, 1.95F).build(Survivor.NAME));

	public static final DeferredItem<Item> SURVIVOR_SPAWN_EGG = ITEMS.register(Survivor.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(SURVIVOR, -9611202, -12053227, new Item.Properties()));

	public static final Supplier<EntityType<DiscoZombie>> DISCO_ZOMBIE = ENTITIES.register(DiscoZombie.NAME,
			() -> EntityType.Builder.of(DiscoZombie::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(DiscoZombie.NAME));

	public static final DeferredItem<Item> DISCO_SPAWN_EGG = ITEMS.register(DiscoZombie.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(DISCO_ZOMBIE, -3342388, -16777216, new Item.Properties()));

	public static final Supplier<EntityType<ZombieChef>> ZOMBIE_CHEF = ENTITIES.register(ZombieChef.NAME,
			() -> EntityType.Builder.of(ZombieChef::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieChef.NAME));

	public static final DeferredItem<Item> CHEF_SPAWN_EGG = ITEMS.register(ZombieChef.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_CHEF, -3342388, -6750208, new Item.Properties()));

	public static final Supplier<EntityType<ZombieCyborg>> ZOMBIE_CYBORG = ENTITIES.register(ZombieCyborg.NAME,
			() -> EntityType.Builder.of(ZombieCyborg::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieCyborg.NAME));

	public static final DeferredItem<Item> CYBORG_SPAWN_EGG = ITEMS.register(ZombieCyborg.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_CYBORG, -10066330, -6750208, new Item.Properties()));

	public static final Supplier<EntityType<ZombieHerobrine>> ZOMBIE_HEROBRINE = ENTITIES.register(ZombieHerobrine.NAME,
			() -> EntityType.Builder.of(ZombieHerobrine::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieHerobrine.NAME));

	public static final DeferredItem<Item> HEROBRINE_SPAWN_EGG = ITEMS.register(ZombieHerobrine.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_HEROBRINE, -16751104, -16751002, new Item.Properties()));

	public static final Supplier<EntityType<ZombieKing>> ZOMBIE_KING = ENTITIES.register(ZombieKing.NAME,
			() -> EntityType.Builder.of(ZombieKing::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieKing.NAME));

	public static final DeferredItem<Item> KING_SPAWN_EGG = ITEMS.register(ZombieKing.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_KING, -16751104, -3355648, new Item.Properties()));

	public static final Supplier<EntityType<ZombieKnight>> ZOMBIE_KNIGHT = ENTITIES.register(ZombieKnight.NAME,
			() -> EntityType.Builder.of(ZombieKnight::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieKnight.NAME));

	public static final DeferredItem<Item> KNIGHT_SPAWN_EGG = ITEMS.register(ZombieKnight.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_KNIGHT, -16738048, -10066330, new Item.Properties()));

	public static final Supplier<EntityType<ZombieMiner>> ZOMBIE_MINER = ENTITIES.register(ZombieMiner.NAME,
			() -> EntityType.Builder.of(ZombieMiner::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieMiner.NAME));

	public static final DeferredItem<Item> MINER_SPAWN_EGG = ITEMS.register(ZombieMiner.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_MINER, -6750208, -13434676, new Item.Properties()));

	public static final Supplier<EntityType<ZombieNotch>> ZOMBIE_NOTCH = ENTITIES.register(ZombieNotch.NAME,
			() -> EntityType.Builder.of(ZombieNotch::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombieNotch.NAME));

	public static final DeferredItem<Item> NOTCH_SPAWN_EGG = ITEMS.register(ZombieNotch.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_NOTCH, -11851502, -16738048, new Item.Properties()));

	public static final Supplier<EntityType<ZombiePa>> ZOMBIE_PA = ENTITIES.register(ZombiePa.NAME,
			() -> EntityType.Builder.of(ZombiePa::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombiePa.NAME));

	public static final DeferredItem<Item> PA_SPAWN_EGG = ITEMS.register(ZombiePa.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_PA, -10066330, -16737997, new Item.Properties()));

	public static final Supplier<EntityType<ZombiePirate>> ZOMBIE_PIRATE = ENTITIES.register(ZombiePirate.NAME,
			() -> EntityType.Builder.of(ZombiePirate::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(ZombiePirate.NAME));

	public static final DeferredItem<Item> PIRATE_SPAWN_EGG = ITEMS.register(ZombiePirate.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_PIRATE, -16738048, -10092544, new Item.Properties()));

	public static final Supplier<EntityType<ZombieDwarf>> ZOMBIE_DWARF = ENTITIES.register(ZombieDwarf.NAME,
			() -> EntityType.Builder.of(ZombieDwarf::new, MobCategory.MONSTER).sized(0.6f, 1.7f).build(ZombieDwarf.NAME));

	public static final DeferredItem<Item> DWARF_SPAWN_EGG = ITEMS.register(ZombieDwarf.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_DWARF, -3355444, -16738048, new Item.Properties()));

	public static final Supplier<EntityType<NetherZombie>> NETHER_ZOMBIE = ENTITIES.register(NetherZombie.NAME,
			() -> EntityType.Builder.of(NetherZombie::new, MobCategory.MONSTER).sized(0.6f, 1.95F).build(NetherZombie.NAME));

	public static final DeferredItem<Item> NETHER_ZOMBIE_SPAWN_EGG = ITEMS.register(NetherZombie.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(NETHER_ZOMBIE, -10092544, -13434880, new Item.Properties()));

	public static final Supplier<EntityType<ZombieCreeper>> ZOMBIE_CREEPER = ENTITIES.register(ZombieCreeper.NAME,
			() -> EntityType.Builder.of(ZombieCreeper::new, MobCategory.MONSTER).sized(0.6f, 1.51f).build(ZombieCreeper.NAME));

	public static final DeferredItem<Item> ZOMBIE_CREEPER_SPAWN_EGG = ITEMS.register(ZombieCreeper.NAME + "_spawn_egg",
			() -> new DeferredSpawnEggItem(ZOMBIE_CREEPER, -16724992, -16724788, new Item.Properties()));

	public static final Supplier<CreativeModeTab> ITEM_GROUP = CREATIVE_MODE_TABS.register("item_group",
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

	@SubscribeEvent
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
		event.put(NETHER_ZOMBIE.get(), DiscoZombie.createAttributes().build());
		event.put(SURVIVOR.get(), Survivor.createAttributes().build());
		event.put(ZOMBIE_CREEPER.get(), ZombieCreeper.createAttributes().build());
	}
}