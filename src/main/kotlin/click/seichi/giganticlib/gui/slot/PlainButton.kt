package click.seichi.giganticlib.gui.slot

import click.seichi.giganticlib.gui.Icon
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * A class representing a button slot which does nothing upon a click.
 *
 * @author kory33
 */
class PlainButton(override val icon: Icon) : ButtonSlot() {

    override val action: (InventoryClickEvent) -> Unit = {}

}