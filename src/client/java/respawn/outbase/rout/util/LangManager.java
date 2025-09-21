package respawn.outbase.rout.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import respawn.outbase.rout.RoutClient;
import respawn.outbase.rout.config.RoutConfig;

public class LangManager {

    private static final Gson GSON = new Gson();
    private static final Map<String, Map<String, String>> translations = new HashMap<>();
    private static final Map<String, String> fallback = new HashMap<>();

    public static void loadLanguages() {
        List<String> langFiles = new ArrayList<>();
        langFiles.add("en_us");
        langFiles.add("id_id");

        for (String langCode : langFiles) {
            try (InputStream stream = RoutClient.class.getResourceAsStream("/assets/rout/lang/" + langCode + ".json")) {
                if (stream != null) {
                    Type type = new TypeToken<Map<String, String>>() {}.getType();
                    Map<String, String> langMap = GSON.fromJson(new InputStreamReader(stream), type);
                    translations.put(langCode, langMap);
                    if (langCode.equals("en_us")) {
                        fallback.putAll(langMap);
                    }
                }
            } catch (Exception e) {
                System.err.println("Gagal memuat file bahasa: " + langCode);
                e.printStackTrace();
            }
        }
    }

    public static String get(String key) {
        Map<String, String> langMap = translations.get(RoutConfig.language);
        if (langMap != null && langMap.containsKey(key)) {
            return langMap.get(key);
        }
        // Fallback ke Bahasa Inggris jika kunci tidak ditemukan
        if (fallback.containsKey(key)) {
            return fallback.get(key);
        }
        // Fallback ke kunci itu sendiri jika tidak ditemukan juga
        return key;
    }

    public static String get(String key, Object... args) {
        return String.format(get(key), args);
    }
    
    public static List<String> getAvailableLanguages() {
        return new ArrayList<>(translations.keySet());
    }
}
