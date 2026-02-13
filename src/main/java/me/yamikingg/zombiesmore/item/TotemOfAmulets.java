package me.yamikingg.zombiesmore.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import javax.annotation.Nullable;
import java.util.List;

public class TotemOfAmulets extends Item {
    public static final int MAX_USES = 1;
    private static final String TAG_USES = "uses";
    private static final int EFFECT_DURATION = 200; // 10 seconds

    public TotemOfAmulets(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int uses = getUses(stack);
        tooltip.add(Component.literal("Uses remaining: " + uses + "/" + MAX_USES).withStyle(
                uses > 0 ? net.minecraft.ChatFormatting.GREEN : net.minecraft.ChatFormatting.RED
        ));
        tooltip.add(Component.literal("Prevents death and teleports to world spawn").withStyle(net.minecraft.ChatFormatting.GRAY));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    public static int getUses(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_USES, 1)) {
            return MAX_USES;
        }
        return tag.getInt(TAG_USES);
    }

    public static void setUses(ItemStack stack, int uses) {
        stack.getOrCreateTag().putInt(TAG_USES, uses);
    }

    public static void consumeUse(Player player, ItemStack targetStack) {
        int uses = getUses(targetStack) - 1;
        setUses(targetStack, uses);

        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F + player.level().random.nextFloat() * 0.4F);

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack inventoryStack = player.getInventory().getItem(slot);
            if (inventoryStack == targetStack || ItemStack.matches(inventoryStack, targetStack)) {
                if (uses <= 0) {
                    player.getInventory().setItem(slot, ItemStack.EMPTY);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F + player.level().random.nextFloat() * 0.4F);
                } else {
                    player.getInventory().setItem(slot, targetStack.copy());
                }
                break;
            }
        }
    }

    public static void applyProtectionAndTeleport(Player player) {
        if (player.getHealth() < 1.0F) {
            player.setHealth(1.0F);
        }

        if (player.level() instanceof ServerLevel serverLevel) {
            BlockPos spawnPos = serverLevel.getSharedSpawnPos();

            int safeY = findSafeY(serverLevel, spawnPos.getX(), spawnPos.getZ());
            Vec3 spawnVec = new Vec3(spawnPos.getX() + 0.5, safeY, spawnPos.getZ() + 0.5);

            // Teleport player
            player.teleportTo(spawnVec.x, spawnVec.y, spawnVec.z);

            // Visual feedback: End portal particles
            for (int i = 0; i < 40; ++i) {
                double d0 = player.level().random.nextGaussian() * 0.5D;
                double d1 = player.level().random.nextGaussian() * 0.5D;
                double d2 = player.level().random.nextGaussian() * 0.5D;
                player.level().addParticle(
                        net.minecraft.core.particles.ParticleTypes.PORTAL,
                        spawnVec.x, spawnVec.y, spawnVec.z,
                        d0, d1, d2
                );
            }

            player.sendSystemMessage(Component.literal("✨ Teleported to world spawn!").withStyle(net.minecraft.ChatFormatting.AQUA));
        }

        // 3. Apply 10-second invulnerability (critical - spawn might be dangerous!)
        int duration = EFFECT_DURATION;
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 4, false, false, true)); // Resistance V = near invulnerability
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration, 1, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, duration, 1, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration, 0, false, true, true)); // Visual feedback

        // 4. Totem particles at new location
        for (int i = 0; i < 20; ++i) {
            double d0 = player.level().random.nextGaussian() * 0.02D;
            double d1 = player.level().random.nextGaussian() * 0.02D;
            double d2 = player.level().random.nextGaussian() * 0.02D;
            player.level().addParticle(
                    net.minecraft.core.particles.ParticleTypes.TOTEM_OF_UNDYING,
                    player.getX() + (player.level().random.nextFloat() * player.getBbWidth() * 2.0F) - player.getBbWidth(),
                    player.getY() + (player.level().random.nextFloat() * player.getBbHeight()),
                    player.getZ() + (player.level().random.nextFloat() * player.getBbWidth() * 2.0F) - player.getBbWidth(),
                    d0, d1, d2
            );
        }
    }

    // ✅ Find safe Y-coordinate above world spawn
    private static int findSafeY(ServerLevel level, int x, int z) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int spawnY = level.getSharedSpawnPos().getY();

        // Search upward first (avoid lava/water)
        for (int y = spawnY; y < level.getMaxBuildHeight() - 1; y++) {
            mutable.set(x, y, z);
            BlockState state = level.getBlockState(mutable);
            BlockState above = level.getBlockState(mutable.above());

            // Valid spawn spot: solid block below + air/water above
            if (state.isSolid() && (above.isAir() || above.getFluidState().is(Fluids.WATER))) {
                return y + 1; // Stand on top of solid block
            }
        }

        // Fallback: original spawn Y
        return Math.max(spawnY, level.getMinBuildHeight() + 2);
    }
}