package respawn.outbase.rout.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import respawn.outbase.rout.config.RoutConfig;

import java.util.Optional;
import java.util.stream.Collectors;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class RoutCommands {

    private static final String COMMAND_NAME = "rout";

    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(RoutCommands::registerRoutCommand);
    }

    private static void registerRoutCommand(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        // Define the execution logic in a shared variable
        Command<FabricClientCommandSource> executionLogic = context -> {
            Optional<ModContainer> modContainerOpt = FabricLoader.getInstance().getModContainer(COMMAND_NAME);

            if (modContainerOpt.isPresent()) {
                ModMetadata meta = modContainerOpt.get().getMetadata();
                context.getSource().sendFeedback(Text.literal("§a--- Informasi Mod " + meta.getName() + " ---"));
                context.getSource().sendFeedback(Text.literal("§fVersi: §e" + meta.getVersion().getFriendlyString()));
                context.getSource().sendFeedback(Text.literal("§fDeskripsi: §7" + meta.getDescription()));

                String authors = meta.getAuthors().stream()
                        .map(Person::getName)
                        .collect(Collectors.joining(", "));
                context.getSource().sendFeedback(Text.literal("§fPembuat: §b" + authors));

                context.getSource().sendFeedback(Text.literal("§fLisensi: §d" + meta.getLicense()));
                context.getSource().sendFeedback(Text.literal("§8Perintah yang digunakan: " + context.getInput()));
            } else {
                context.getSource().sendError(Text.literal("Tidak dapat menemukan informasi untuk mod '" + COMMAND_NAME + "'."));
            }
            return 1;
        };

        // Register the main command
        dispatcher.register(literal(COMMAND_NAME).executes(executionLogic));

        // Register the alias from the config file
        dispatcher.register(literal(RoutConfig.commandAlias).executes(executionLogic));
    }
}
