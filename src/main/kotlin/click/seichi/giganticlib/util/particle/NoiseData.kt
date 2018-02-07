package click.seichi.giganticlib.util.particle

import org.bukkit.util.Vector

/**
 * A class that acts as an immutable data of noise on the XYZ axis.
 *
 * @property size the size of noise
 *
 * @param innerSize the size of noise (only to set)
 * @param noiser the maker of noise
 *
 * @author unicroak
 */
data class NoiseData(
        private val innerSize: Vector,
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

    val size
        get() = innerSize.clone()

}