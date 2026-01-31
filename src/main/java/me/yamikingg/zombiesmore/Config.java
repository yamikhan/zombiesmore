package me.yamikingg.zombiesmore;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

@Mod.EventBusSubscriber
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

//        showErrors = CLIENT_BUILDER
//                .comment("Show the mod errors when loaded").define("misc.errors", false);


//        CLIENT_BUILDER.pop();

//        CLIENT_BUILDER.comment("Update Checker Settings").push(CATEGORY_UPDATES);

//        setupUpdatesConfig(COMMON_BUILDER, CLIENT_BUILDER);

//        CLIENT_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }


    private static void setupMobsConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {

        oldDwarfZombieModel = CLIENT_BUILDER
                .comment("Changes the Zombie Dwarf model to a standard zombie model.\n Default: false\n Note: This don't change his collision box")
                .define("oldZombieDwarfModel", false);

    }



//   private static void setupUpdatesConfig(ForgeConfigSpec.Builder COMMON_BUILDER, ForgeConfigSpec.Builder CLIENT_BUILDER) {
//
//      checkUpdates = CLIENT_BUILDER
//                .comment(" true = check for updates, false = don't check for updates.\n Default: true.")
//                .define("check_updates.updates", true);
//
//    }

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



    @SubscribeEvent
    public static void onWorldLoad(final WorldEvent.Load event) {
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(ZombiesMore.MODID + "-client.toml"));
//        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(ZombiesMore.MODID + ".toml"));
    }

}
