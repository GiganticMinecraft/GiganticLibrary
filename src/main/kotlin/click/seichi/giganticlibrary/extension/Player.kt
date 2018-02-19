package click.seichi.giganticlibrary.extension

import click.seichi.giganticlibrary.transmissible.Transmissible
import org.spongepowered.api.entity.living.player.Player

fun Player.sendMessage(transmissible: Transmissible, vararg arguments: Any) = transmissible.sendTo(this, *arguments)