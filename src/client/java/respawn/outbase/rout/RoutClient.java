package respawn.outbase.rout;

import net.fabricmc.api.ClientModInitializer;
import respawn.outbase.rout.command.RoutCommands;
import respawn.outbase.rout.config.RoutConfig;
import respawn.outbase.rout.util.LangManager;

public class RoutClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Load the configuration from the file
		RoutConfig.load();

		// Load custom languages
		LangManager.loadLanguages();

		// Register the commands
		RoutCommands.register();
	}
}