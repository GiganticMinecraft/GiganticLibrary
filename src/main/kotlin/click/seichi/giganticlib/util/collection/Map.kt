package click.seichi.giganticlib.util.collection

/**
 * Returns a [Map] containing all mappings whose values are not null.
 *
 * The returned map preserves the entry iteration order of the original map.
 */
@Suppress("UNCHECKED_CAST")
fun <K, V> Map<K, V?>.filterNotNullValues() = filterValues { it != null } as Map<K, V>

/**
 * Returns a new map with entries having the keys of this map and the non-`null` values
 * obtained by applying the [transform] function to each entry in this [Map].
 *
 * Any entry whose value is mapped to `null` by [transform] will be removed from the new map.
 *
 * The returned map preserves the entry iteration order of the original map.
 */
fun <K, V, R> Map<K, V>.mapValuesNotNull(transform: (K, V) -> R?)
        = mapValues { (k, v) -> transform(k, v) }.filterNotNullValues()