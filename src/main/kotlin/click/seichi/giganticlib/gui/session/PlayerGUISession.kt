package click.seichi.giganticlib.gui.session

import click.seichi.giganticlib.gui.SlotLayout
import click.seichi.giganticlib.gui.slot.StorageSlot
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent

/**
 * A class that represents a session of an interactive inventory GUI, which is uniquely associated with a [player].
 *
 * A subclass may override [beforeInventoryClose] to define an action following inventory-close event.
 *
 * @author kory33
 */
abstract class PlayerGUISession(private val player: Player,
                                initialLayout: SlotLayout): InventoryGUISession(initialLayout) {

    /**
     * An action executed just before the inventory close.
     *
     * @see [onInventoryClose]
     */
    open fun beforeInventoryClose(event: InventoryCloseEvent) {}

    override final fun onInventoryClose(event: InventoryCloseEvent) {
        beforeInventoryClose(event)

        inventory.filterIndexed { index, _ -> layout[index] is StorageSlot }
                .mapNotNull { itemStack ->
                    // addItem(itemStack)[0] will return overflown itemStack or null
                    itemStack?.let { player.inventory.addItem(it)[0] }
                }.forEach { overflownItemStack ->
            player.world.dropItemNaturally(player.location, overflownItemStack)
        }
    }
}