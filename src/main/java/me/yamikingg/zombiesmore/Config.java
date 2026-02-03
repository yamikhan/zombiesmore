package me.yamikingg.zombiesmore;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_UPDATES = "updates";
    public static final String CATEGORY_MISC = "misc";

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue oldDwarfZombieModel;
    public static ForgeConfigSpec.BooleanValue checkUpdates;
    public static ForgeConfigSpec.BooleanValue showErrors;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Settings").push(CATEGORY_GENERAL);
        CLIENT_BUILDER.pop();

        setupMobsConfig(COMMON_BUILDER, CLIENT_BUILDER);

        CLIENT_BUILDER.comment("Misc").push(CATEGORY_MISC);
        CLIENT_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupMobsConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
        oldDwarfZombieModel = CLIENT_BUILDER
                .comment("Changes the Zombie Dwarf model to a standard zombie model.\n Default: false\n Note: This doesn't change his collision box")
                .define("oldZombieDwarfModel", false);
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
        // ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        ZombiesMore.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        ZombiesMore.LOGGER.debug("Built TOML config for {}", path.toString());
        configData.load();
        ZombiesMore.LOGGER.debug("Loaded TOML config file {}", path.toString());
        spec.setConfig(configData);
    }
}