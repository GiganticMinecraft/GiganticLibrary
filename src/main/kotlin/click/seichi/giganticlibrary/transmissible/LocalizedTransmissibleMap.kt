package click.seichi.giganticlibrary.transmissible

import java.util.*

/**
 * 1つの文字列に対し, 複数の言語へと翻訳された[Transmissible]を持つMap.
 *
 * @author unicroak
 */
class LocalizedTransmissibleMap {

    private val textMap = mutableMapOf<Locale, Transmissible>()

    fun getTransmissible(locale: Locale, default: Locale? = null) = textMap[locale]
            ?: default?.let { textMap[it] }
            ?: throw IllegalArgumentException()

}