package click.seichi.giganticlib.crafting.gui.slot

import click.seichi.giganticlib.crafting.gui.session.CraftSession
import click.seichi.giganticlib.gui.slot.StorageSlot
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * A class of slots which acts as a destination in the craft session.
 *
 * @author kory33
 */
class CraftDestinationSlot : StorageSlot() {

    override fun onItemSet(newItemStack: ItemStack?, event: InventoryClickEvent): ItemSetReaction {
        if (event.currentItem.type == Material.AIR) return ItemSetReaction.REJECT

        val session = event.inventory.holder as? CraftSession ?: return ItemSetReaction.REJECT

        return when(event.action) {
            InventoryAction.PICKUP_SOME, InventoryAction.PICKUP_HALF, InventoryAction.PICKUP_ONE -> ItemSetReaction.ACCEPT.also {
                event.transferSlotItemToCursor()
                session.onCraftDestinationCollect()
            }

            InventoryAction.PICKUP_ALL, InventoryAction.COLLECT_TO_CURSOR -> ItemSetReaction.ACCEPT.also {
                session.onCraftDestinationCollect()
            }

            else -> ItemSetReaction.REJECT
        }
    }

    private fun InventoryClickEvent.transferSlotItemToCursor() {
        view.cursor = currentItem
        clickedInventory.clear(slot)
    }

}