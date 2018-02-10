package click.seichi.giganticlib.crafting.gui.slot

import click.seichi.giganticlib.crafting.gui.session.CraftSession
import click.seichi.giganticlib.gui.slot.StorageSlot
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * A class of slots which acts as a source in the craft session.
 *
 * @author kory33
 */
object CraftSourceSlot : StorageSlot() {

    override fun onItemSet(newItemStack: ItemStack?, event: InventoryClickEvent) = ItemSetReaction.ACCEPT.also {
        val session = event.inventory.holder as? CraftSession ?: return@also
        session.onCraftSourceUpdate()
    }

}