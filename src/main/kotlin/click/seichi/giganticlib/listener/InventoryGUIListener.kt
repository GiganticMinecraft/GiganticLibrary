package click.seichi.giganticlib.listener

import click.seichi.giganticlib.gui.session.InventoryGUISession
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * A default implementation of a listener which conveys events to the [InventoryGUISession].
 *
 * Note that this class is most likely to cause a conflict between multiple plugins
 * when used without the package relocation.
 *
 * @author unicroak, kory33
 */
class InventoryGUIListener : Listener {

    private val InventoryEvent.guiSession
        get() = inventory?.holder as? InventoryGUISession

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) = event.guiSession?.let { session ->
        session.getBoundReaction(event.slot)(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) = event.guiSession?.onInventoryClose(event)

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) = event.guiSession?.onInventoryOpen(event)

}