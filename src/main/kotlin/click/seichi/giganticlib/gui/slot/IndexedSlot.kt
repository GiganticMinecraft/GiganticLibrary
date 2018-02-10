package click.seichi.giganticlib.gui.slot

/**
 * A slot which is associated with its index.
 */
data class IndexedSlot<out T: Slot>(val index: Int, val slot: T)