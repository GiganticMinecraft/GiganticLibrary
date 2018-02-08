package click.seichi.giganticlib.gui.session

import click.seichi.giganticlib.gui.InventoryParameter
import click.seichi.giganticlib.gui.SlotLayout
import click.seichi.giganticlib.gui.slot.ButtonSlot
import click.seichi.giganticlib.gui.slot.StorageSlot
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * A class that represents a session of an interactive inventory GUI.
 *
 * This class internally has a [layout] and a session inventory as a state.
 *
 * When a new [SlotLayout] is set on [layout], the internal inventory gets updated
 * and the resulting difference will be broadcasted to all the players viewing this inventory.
 *
 * A subclass may override [onInventoryOpen] and [onInventoryClose]
 * to define an action following inventory-open and inventory-close respectively.
 *
 * @author kory33
 */
abstract class InventoryGUISession(initialLayout: SlotLayout): InventoryHolder {

    abstract val title: String

    open val inventoryParameter = InventoryParameter.Type(InventoryType.CHEST)

    /**
     * A layout that represents the arrangement of slots.
     * Any new value set to this field will make inventory re-render itself.
     *
     * When the old layout says `false` on [SlotLayout.isSafeToApply],
     * an exception is thrown as it indicates that [ButtonSlot] may overwrite some [StorageSlot].
     */
    protected var layout: SlotLayout = initialLayout
        set(newLayout) {
            if (!field.isSafeToApply(newLayout)) {
                throw IllegalStateException("Given layout is not safe to apply.")
            }

            field = newLayout.also { it.applyTo(sessionInventory) }
        }

    private val sessionInventory: Inventory by lazy {
        inventoryParameter.createInventoryWith(this, title)
    }

    /**
     * Returns a reaction bound to a slot of specified [slotId]
     * @param slotId index of the slot
     * @return reaction bound to the slot
     */
    fun getBoundReaction(slotId: Int) = layout[slotId].reaction

    /**
     * Executes an action on inventory-open.
     */
    open fun onInventoryOpen(event: InventoryOpenEvent) {}

    /**
     * Executes an action on inventory-close.
     */
    open fun onInventoryClose(event: InventoryCloseEvent) {}

    override fun getInventory() = sessionInventory

}