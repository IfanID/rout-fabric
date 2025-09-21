package respawn.outbase.rout.screen;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import respawn.outbase.rout.config.RoutConfig;
import respawn.outbase.rout.util.LangManager;

public class RoutConfigScreen {

    // Enum untuk pilihan bahasa
    public enum Language {
        EN_US("en_us", "English"),
        ID_ID("id_id", "Indonesia");

        private final String code;
        private final String displayName;

        Language(String code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        @Override
        public String toString() {
            return displayName;
        }

        public static Language fromCode(String code) {
            for (Language lang : values()) {
                if (lang.getCode().equalsIgnoreCase(code)) {
                    return lang;
                }
            }
            return ID_ID; // Default
        }
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal(LangManager.get("title.rout.config")));

        // Saat seluruh layar disimpan, simpan file konfigurasi
        builder.setSavingRunnable(RoutConfig::save);

        ConfigCategory general = builder.getOrCreateCategory(Text.literal(LangManager.get("category.rout.general")));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // Entri Pemilih Bahasa
        general.addEntry(entryBuilder.startEnumSelector(Text.literal(LangManager.get("option.rout.language")), Language.class, Language.fromCode(RoutConfig.language))
                .setDefaultValue(Language.ID_ID)
                .setSaveConsumer(newValue -> {
                    RoutConfig.language = newValue.getCode();
                    // Muat ulang layar untuk menampilkan teks baru
                    MinecraftClient.getInstance().setScreen(create(parent));
                })
                .build());


        // Entri Alias Perintah
        general.addEntry(entryBuilder.startStrField(Text.literal(LangManager.get("option.rout.commandAlias")), RoutConfig.commandAlias)
                .setDefaultValue("r")
                .setTooltip(
                    Text.literal(LangManager.get("tooltip.rout.commandAlias")),
                    Text.literal(LangManager.get("tooltip.rout.commandAlias.restart")).formatted(Formatting.YELLOW)
                )
                .setSaveConsumer(newValue -> RoutConfig.commandAlias = newValue)
                .build());

        return builder.build();
    }
}
