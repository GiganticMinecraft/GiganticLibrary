package click.seichi.giganticlib.extension

import click.seichi.giganticlib.util.particle.NoiseData
import org.bukkit.Location

/**
 * Returns the new location which is added the noise using [noiseData].
 *
 * @param noiseData the data to add noise to the location
 */
fun Location.noisedWith(noiseData: NoiseData) = clone().add(
        noiseData.noiser.invoke(noiseData.size.x),
        noiseData.noiser.invoke(noiseData.size.y),
        noiseData.noiser.invoke(noiseData.size.z)
)