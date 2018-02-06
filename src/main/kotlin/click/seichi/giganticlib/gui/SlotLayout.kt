package click.seichi.giganticlib.gui

import click.seichi.giganticlib.gui.slot.ButtonSlot
import click.seichi.giganticlib.gui.slot.NormalSlot
import click.seichi.giganticlib.gui.slot.Slot
import click.seichi.giganticlib.gui.slot.StorageSlot
import click.seichi.giganticlib.util.collection.mapValuesNotNull
import org.bukkit.inventory.Inventory

/**
 * A class that determines the positioning of slots.
 *
 * This class promises to allocate a [Slot] instance to *all* the slots in the inventory
 * whose size is determined by [inventoryParameter].
 *
 * @param inventoryParameter a data value which specifies the inventory size
 * @param slotOverrides a map containing entries, each representing a slot override.
 */
abstract class SlotLayout(private val inventoryParameter: InventoryParameter, slotOverrides: Map<Int, Slot> = HashMap()) {
    /**
     * A default [Slot] instance which is used in filling up the non-overriden slots.
     *
     * If you want a slot which acts like a one in a vanilla chest, use [NormalSlot].
     */
    abstract val defaultSlot: Slot

    private val slotMapping: Map<Int, Slot> = (0 until inventoryParameter.toSize()).associate { slotId ->
        slotId to (slotOverrides[slotId] ?: defaultSlot)
    }

    /** Indices of slots that act as a storage */
    private val storageIndices = slotMapping.filterValues { when(it) {
        // This clause is explicitly written to fail the compile when another [Slot] subclass is added.
        is StorageSlot -> true
        is ButtonSlot -> false
    } }.keys

    /**
     * Set of mappings from a button index to a [Slot] instance
     */
    private val buttons = slotMapping.mapValuesNotNull { _, slot -> slot as? ButtonSlot }

    fun applyTo(inventory: Inventory) =
            buttons.forEach { index, button -> inventory.setItem(index, button.icon.itemStack) }

    /**
     * Judges if another [SlotLayout] can safely substitute the layout described by this object.
     *
     * Here, "safely substitute" means that the target layout has the same amount of slots as this layout has,
     * and that the following predicate is satisfied:
     *
     * ```this[i] is StorageSlot (if and only if) another[i] is StorageSlot```
     *
     * A [Slot] is guaranteed to be either a [ButtonSlot] or a [StorageSlot].
     * hence the predicate is equivalent to [storageIndices] being equal.
     */
    fun isSafeToApply(another: SlotLayout) =
            (this.inventoryParameter.toSize() == another.inventoryParameter.toSize()) &&
            (this.storageIndices == another.storageIndices)

    /**
     * Returns a [Slot] which is set to the given index.
     *
     * If [slotId] exceeds the boundary described by [inventoryParameter],
     * [IndexOutOfBoundsException] will be thrown.
     *
     * @param slotId target slot id
     */
    operator fun get(slotId: Int) = slotMapping[slotId] ?:
            throw IndexOutOfBoundsException("Target slot number is out of range!")

}