package click.seichi.giganticlibrary.transmissible

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

/**
 * 標準的な[TransmissibleSendingProtocol]の列挙.
 *
 * @author unicroak
 */
enum class TransmissibleSendingProtocols(override val sending: (Player, Text) -> Unit) : TransmissibleSendingProtocol {

    CHAT({ player, text -> player.sendMessage(text) }),

    ACTION_BAR({ player, text -> player.sendMessage(org.spongepowered.api.text.chat.ChatTypes.ACTION_BAR, text) }),

    ;

}