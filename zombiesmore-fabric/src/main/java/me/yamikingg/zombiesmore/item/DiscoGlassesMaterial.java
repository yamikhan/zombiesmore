package me.yamikingg.zombiesmore.item;

import me.yamikingg.zombiesmore.ZombiesMoreFabric;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DiscoGlassesMaterial {

    public static final Holder<ArmorMaterial> GLASSES = register(
            "glasses",
            new EnumMap<>(Map.of(
                    ArmorItem.Type.HELMET,     1,
                    ArmorItem.Type.CHESTPLATE, 3,
                    ArmorItem.Type.LEGGINGS,   2,
                    ArmorItem.Type.BOOTS,      1
            )),
            15,
            SoundEvents.ARMOR_EQUIP_GENERIC.value(),
            0.0F,
            0.0F,
            Ingredient.of(Items.GLASS)
    );

    private static Holder<ArmorMaterial> register(
            String name,
            EnumMap<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            net.minecraft.sounds.SoundEvent equipSound,
            float toughness,
            float knockbackResistance,
            Ingredient repairIngredient
    ) {
        ResourceLocation id = new ResourceLocation(ZombiesMoreFabric.MODID, name);

        ArmorMaterial material = new ArmorMaterial(
                defense,
                enchantmentValue,
                Holder.direct(equipSound),
                () -> repairIngredient,
                List.of(new ArmorMaterial.Layer(id)),
                toughness,
                knockbackResistance
        );

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, id, material);
    }

    public static void register() {
    }
}