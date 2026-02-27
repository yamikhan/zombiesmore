package me.yamikingg.zombiesmore;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;

public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_UPDATES = "updates";
    public static final String CATEGORY_MISC = "misc";

    public static ModConfigSpec COMMON_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;

    public static ModConfigSpec.BooleanValue oldDwarfZombieModel;
    public static ModConfigSpec.BooleanValue checkUpdates;
    public static ModConfigSpec.BooleanValue showErrors;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

        CLIENT_BUILDER.comment("Settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        setupMobsConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.comment("Misc").push(CATEGORY_MISC);
        CLIENT_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupMobsConfig(ModConfigSpec.Builder COMMON_BUILDER, ModConfigSpec.Builder CLIENT_BUILDER) {
        oldDwarfZombieModel = CLIENT_BUILDER
                .comment("Changes the Zombie Dwarf model to a standard zombie model.\n Default: false\n Note: This doesn't change his collision box")
                .define("oldZombieDwarfModel", false);
    }

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

    public static void loadConfig(ModConfig config) {
        ZombiesMore.LOGGER.debug("Loading config file {}", config.getFileName());

        // The config is automatically loaded by NeoForge
        // You can access values directly from the ModConfigSpec

        ZombiesMore.LOGGER.debug("Loaded config file {}", config.getFileName());
    }
}