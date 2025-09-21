package respawn.outbase.rout.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public class RoutConfig {

    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("rout.properties");
    private static final Properties properties = new Properties();

    public static String commandAlias;

    public static void load() {
        try {
            if (Files.exists(CONFIG_FILE)) {
                properties.load(Files.newInputStream(CONFIG_FILE));
            }
            commandAlias = properties.getProperty("commandAlias", "r");
            save(); // Save to write defaults and comments
        } catch (IOException e) {
            System.err.println("Gagal memuat konfigurasi Rout: " + e.getMessage());
        }
    }

    public static void save() {
        try {
            properties.setProperty("commandAlias", commandAlias);
            properties.store(Files.newOutputStream(CONFIG_FILE), " Konfigurasi Mod Rout\n\n commandAlias: Mengatur alias untuk perintah /rout.");
        }
        catch (IOException e) {
            System.err.println("Gagal menyimpan konfigurasi Rout: " + e.getMessage());
        }
    }
}
