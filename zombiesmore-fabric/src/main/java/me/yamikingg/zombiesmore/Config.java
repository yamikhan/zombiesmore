package me.yamikingg.zombiesmore;

import org.apache.logging.log4j.Logger;

/**
 * Fabric config handler
 * For Fabric mods, configuration is typically handled via Mod Menu and ClothConfig
 * This is a basic implementation that stores boolean values
 */
public class Config {
	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_UPDATES = "updates";
	public static final String CATEGORY_MISC = "misc";

	public static boolean OLD_DWARF_ZOMBIE_MODEL = false;

	public static void loadConfig(Logger logger) {
		logger.info("Loading ZombiesMore config...");
		// For basic Fabric support without external libs
		// Users will need to install Mod Menu + Cloth Config for GUI config
	}
}