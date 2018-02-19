package click.seichi.giganticlibrary.transmissible

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

/**
 * [Transmissible]の送信手段を示すインターフェース.
 *
 * @author unicroak
 */
interface TransmissibleSendingProtocol {

    /** [Transmissible]の送信手段 */
    val sending: (Player, Text) -> Unit

}