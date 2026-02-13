package me.yamikingg.zombiesmore.events;

import me.yamikingg.zombiesmore.item.TotemOfAmulets;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TotemEventHandler {

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof ServerPlayer player)) return;
        if (player.level().isClientSide()) return;

        float damage = event.getAmount();
        float healthAfter = player.getHealth() + player.getAbsorptionAmount() - damage;

        // Only trigger for fatal hits
        if (healthAfter > 0.0F || player.isCreative() || player.isSpectator()) return;

        // Priority search: Offhand → Mainhand → Inventory
        int[] searchOrder = {40, 36 + player.getInventory().selected, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};

        for (int slot : searchOrder) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.isEmpty()) continue;


            String registryName = stack.getItem().builtInRegistryHolder().key().location().toString();
            if (!registryName.equals("zombiesmore_yamikingg:totem_of_amulets")) continue;

            int uses = TotemOfAmulets.getUses(stack);
            if (uses <= 0) continue;

            event.setCanceled(true);

            TotemOfAmulets.applyProtectionAndTeleport(player);
            TotemOfAmulets.consumeUse(player, stack);

            return;
        }
    }
}