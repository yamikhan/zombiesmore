package me.yamikingg.zombiesmore.item;

import me.yamikingg.zombiesmore.ZombiesMore;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class DiscoGlassesMaterial {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, ZombiesMore.MODID);

    public static final Holder<ArmorMaterial> GLASSES = ARMOR_MATERIALS.register("glasses", () ->
            new ArmorMaterial(
                    // Defense values per slot (FEET, LEGS, CHEST, HEAD)
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS, 1);
                        map.put(ArmorItem.Type.LEGGINGS, 3);
                        map.put(ArmorItem.Type.CHESTPLATE, 2);
                        map.put(ArmorItem.Type.HELMET, 1);
                    }),
                    15, // enchantment value
                    SoundEvents.ARMOR_EQUIP_GENERIC, // equip sound (already a Holder<SoundEvent>)
                    () -> Ingredient.of(Items.GLASS), // repair ingredient
                    // Layer list for texture rendering
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.tryBuild(ZombiesMore.MODID,"glasses")
                            )
                    ),
                    0.0F, // toughness
                    0.0F  // knockback resistance
            )
    );
}