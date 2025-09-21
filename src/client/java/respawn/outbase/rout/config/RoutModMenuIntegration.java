package respawn.outbase.rout.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import respawn.outbase.rout.screen.RoutConfigScreen;

public class RoutModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return RoutConfigScreen::create;
    }
}
