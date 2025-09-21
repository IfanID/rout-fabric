package respawn.outbase.rout;

import net.fabricmc.api.ClientModInitializer;
import respawn.outbase.rout.command.RoutCommands;
import respawn.outbase.rout.config.RoutConfig;

public class RoutClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Load the configuration from the file
		RoutConfig.load();

		// Register the commands
		RoutCommands.register();
	}
}