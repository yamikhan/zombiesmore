package me.yamikingg.zombiesmore.item;

import me.yamikingg.zombiesmore.ZombiesMore;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;

public class DiscoGlassesMaterial {

    public static final Holder<ArmorMaterial> HOLDER = Holder.direct(
            new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.HELMET, 1);
                        map.put(ArmorItem.Type.CHESTPLATE, 3);
                        map.put(ArmorItem.Type.LEGGINGS, 2);
                        map.put(ArmorItem.Type.BOOTS, 1);
                    }),
                    15,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.of(Items.GLASS),
                    List.of(
                            new ArmorMaterial.Layer(
                                    ResourceLocation.fromNamespaceAndPath(ZombiesMore.MODID, "glasses")
                            )
                    ),
                    0.0F,
                    0.0F
            )
    );
}