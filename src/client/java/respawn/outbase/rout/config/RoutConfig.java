package respawn.outbase.rout.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.fabricmc.loader.api.FabricLoader;

public class RoutConfig {

    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("rout.properties");
    private static final Properties properties = new Properties();

    // Nilai default jika file konfigurasi tidak ada atau error
    public static String language = "id_id";
    public static String commandAlias = "r";

    /**
     * Memuat konfigurasi dari file rout.properties.
     * Jika file tidak ada, file akan dibuat dengan format yang diinginkan.
     */
    public static void load() {
        try {
            if (!Files.exists(CONFIG_FILE)) {
                createDefaultConfigFile();
            }

            // Muat properti dari file
            try (InputStream input = Files.newInputStream(CONFIG_FILE)) {
                properties.load(input);
            }

            // Ambil nilai dari properti, gunakan nilai default jika tidak ditemukan
            language = properties.getProperty("language", language);
            commandAlias = properties.getProperty("commandAlias", commandAlias);

        } catch (IOException e) {
            System.err.println("Gagal memuat/membuat file konfigurasi Rout. Menggunakan nilai default.");
            // Jika terjadi error, nilai default yang sudah diatur akan digunakan.
        }
    }

    /**
     * Menyimpan nilai konfigurasi saat ini ke file rout.properties,
     * dengan tetap mempertahankan format dan komentar asli.
     */
    public static void save() {
        try {
            String content = "# Konfigurasi Mod Rout\n" +
                             "#\n" +
                             "language: " + language + "\n" +
                             "# language: Mengatur bahasa untuk mod (misal: en_us, id_id).\n" +
                             "#\n" +
                             "commandAlias: " + commandAlias + "\n" +
                             "# commandAlias: Mengatur alias untuk perintah /rout.\n";
            Files.write(CONFIG_FILE, content.getBytes());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan konfigurasi Rout: " + e.getMessage());
        }
    }

    /**
     * Membuat file konfigurasi default dengan format yang telah ditentukan.
     */
    private static void createDefaultConfigFile() throws IOException {
        String defaultConfig = "# Konfigurasi Mod Rout\n" +
                               "#\n" +
                               "language: id_id\n" +
                               "# language: Mengatur bahasa untuk mod (misal: en_us, id_id).\n" +
                               "#\n" +
                               "commandAlias: r\n" +
                               "# commandAlias: Mengatur alias untuk perintah /rout.\n";
        Files.write(CONFIG_FILE, defaultConfig.getBytes());
    }

    // Blok statis untuk memuat konfigurasi saat kelas pertama kali diakses
    static {
        load();
    }
}