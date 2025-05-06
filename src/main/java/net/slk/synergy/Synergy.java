package net.slk.synergy;

import net.fabricmc.api.ModInitializer;
import net.slk.synergy.item.SynergyItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Synergy implements ModInitializer {
	public static final String MOD_ID = "synergy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SynergyItems.initialize();
	}
}