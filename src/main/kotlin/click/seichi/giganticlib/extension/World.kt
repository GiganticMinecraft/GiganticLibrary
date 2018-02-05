package click.seichi.giganticlib.extension

import click.seichi.giganticlib.util.particle.NoiseData
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World

/**
 * Spawns the particles.
 *
 * @param particleType the type of particle to spawn
 * @param location the location to spawn
 * @param count the number of the particles
 * @param noiseData the data to add noise to the location
 * @param speed the extra data for this particle which depends on the particle used (normally speed)
 * @param data the extra data for this particle whose type depends on [Particle.dataType] (normally `null`)
 */
fun World.spawnParticle(
        particleType: Particle,
        location: Location,
        count: Int = 1,
        noiseData: NoiseData = NoiseData(0.0),
        speed: Double = 0.0,
        data: Any? = null
) = players.forEach { it.spawnParticle(particleType, location, count, noiseData, speed, data) }

/**
 * Spawns the colored particles.
 *
 * The type of this particle is determined [Particle.REDSTONE].
 *
 * @param color the color of particle to spawn
 * @param location the location to spawn
 * @param count the number of the particles
 * @param noiseData the data to add noise to the location
 */
fun World.spawnColoredParticle(
        color: Color,
        location: Location,
        count: Int = 1,
        noiseData: NoiseData = NoiseData(0.0)
) = players.forEach { it.spawnColoredParticle(color, location, count, noiseData) }