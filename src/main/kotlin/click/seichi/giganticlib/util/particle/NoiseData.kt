package click.seichi.giganticlib.util.particle

import org.bukkit.util.Vector

/**
 * A class representing data of noise on a XYZ axis.
 *
 * @param size the size of noise
 * @param noiser the maker of noise
 *
 * @author unicroak
 */
data class NoiseData(
        val size: Vector,
        val noiser: (Double) -> Double = defaultNoiser
) {

    companion object {
        private val defaultNoiser: (Double) -> Double = { size ->
            size * Math.random() * (Math.random().takeIf { it < 0.5 }?.let { 1.0 } ?: -1.0)
        }
    }

    /**
     * @param sizeUniformly the size of noises on the XYZ axis
     * @param noiser the maker of noise
     */
    constructor(
            sizeUniformly: Double,
            noiser: (Double) -> Double = defaultNoiser
    ) : this(Vector(sizeUniformly, sizeUniformly, sizeUniformly), noiser)

}