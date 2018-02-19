package click.seichi.giganticlibrary.extension

import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColor
import org.spongepowered.api.text.format.TextColors
import org.spongepowered.api.text.format.TextStyle
import org.spongepowered.api.text.format.TextStyles

fun Text.Builder.append(text: String, color: TextColor = TextColors.WHITE, style: TextStyle = TextStyles.NONE): Text.Builder =
        this.append(Text.builder(text).color(color).style(style).build())