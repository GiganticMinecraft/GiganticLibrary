package click.seichi.giganticlib.gui.slot

import click.seichi.giganticlib.gui.slot.StorageSlot.ItemSetReaction.ACCEPT
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * Represents a "normal" slot in a vanilla-chest inventory.
 *
 * This slot accepts every in and out transaction of [ItemStack].
 *
 * @author kory33
 */
object NormalSlot: StorageSlot() {

    override fun onItemSet(newItemStack: ItemStack?, event: InventoryClickEvent) = ACCEPT

}