package click.seichi.giganticlib.crafting.gui.session

import click.seichi.giganticlib.crafting.InventoryRecipe
import click.seichi.giganticlib.crafting.gui.slot.CraftDestinationSlot
import click.seichi.giganticlib.crafting.gui.slot.CraftSourceSlot
import click.seichi.giganticlib.extension.gui.filterTypedSlots
import click.seichi.giganticlib.gui.SlotLayout
import click.seichi.giganticlib.gui.session.PlayerGUISession
import org.bukkit.entity.Player

/**
 * A class of crafting session which is associated with a player.
 *
 * @param player a player to whom the session will be associated
 * @param initialLayout base layout of the session inventory
 * @param recipe recipe to which the session aims to craft
 *
 * @author kory33
 */
abstract class PlayerCraftGUISession(player: Player,
                                     initialLayout: SlotLayout,
                                     private val recipe: InventoryRecipe)
    : PlayerGUISession(player, initialLayout), CraftSession {

    override fun onCraftSourceUpdate() {
        val inputs = layout.filterTypedSlots<CraftSourceSlot>().associate { it.index to inventory.getItem(it.index) }
        val destination = layout.filterTypedSlots<CraftDestinationSlot>().first().index

        val yieldResult = recipe.yieldWith(inputs)

        if (yieldResult != null) {
            inventory.setItem(destination, yieldResult)
        } else {
            inventory.clear(destination)
        }
    }

    override fun onCraftDestinationCollect() {
        layout.filterTypedSlots<CraftSourceSlot>().forEach { (index, _) ->
            inventory.getItem(index).amount--
        }
    }

}