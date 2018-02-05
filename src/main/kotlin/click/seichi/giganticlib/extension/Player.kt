package click.seichi.giganticlib.extension

import click.seichi.giganticlib.util.particle.NoiseData
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

/**
 * Spawns the particles which can be seen only by this [Player].
 *
 * @param particleType the type of particle to spawn
 * @param location the location to spawn
 * @param count the number of particles
 * @param noiseData the data to add noise to the location
 * @param speed the extra data for this particle which depends on the particle used (normally speed)
 * @param data the extra data for this particle whose type depends on [Particle.dataType] (normally `null`)
 */
fun Player.spawnParticle(
        particleType: Particle,
        location: Location,
        count: Int = 1,
        noiseData: NoiseData = NoiseData(0.0),
        speed: Double = 0.0,
        data: Any? = null
) = (0 until count).forEach {
    spawnParticle(
            particleType,
            location.noisedWith(noiseData),
            1,
            0.0,
            0.0,
            0.0,
            speed,
            data
    )
}