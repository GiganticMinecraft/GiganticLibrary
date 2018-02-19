package click.seichi.giganticlibrary.transmissible

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

/**
 * [Player]に対し伝達可能な文字列を示すクラス.
 *
 * @author unicroak
 */
class Transmissible(private val protocol: TransmissibleSendingProtocol, private val transmission: (List<*>) -> Text) {

    fun sendTo(destination: Player, vararg arguments: Any) = protocol.sending(destination, transmission.invoke(listOf(*arguments)))

}
