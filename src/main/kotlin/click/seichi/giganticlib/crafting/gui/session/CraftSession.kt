package click.seichi.giganticlib.crafting.gui.session

import org.bukkit.inventory.InventoryHolder

/**
 * An abstracted interface of a craft session.
 */
interface CraftSession : InventoryHolder {

    /**
     * reacts to the craft source update.
     */
    fun onCraftSourceUpdate()

    /**
     * reacts to the item collection at the craft destination.
     */
    fun onCraftDestinationCollect()

}