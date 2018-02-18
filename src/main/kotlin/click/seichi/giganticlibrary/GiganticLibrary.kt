package click.seichi.giganticlibrary

import com.google.inject.Inject
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.plugin.Plugin
import java.util.logging.Logger

/**
 * @author unicroak
 */
@Plugin(
        id = "giganticlibrary",
        name = "GiganticLibrary"
)
class Gigalopolis {

    @Inject
    private lateinit var logger: Logger

    @Listener
    fun onServerStart(event: GameStartedServerEvent) {
    }

}