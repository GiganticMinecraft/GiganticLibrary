package click.seichi.giganticlib.listener

import click.seichi.giganticlib.gui.session.InventoryGUISession
import click.seichi.giganticlib.listener.InventoryGUIListener.isInstanceAlreadyListening
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * A default implementation of a listener which conveys events to the [InventoryGUISession].
 *
 * Do check for return value of [isInstanceAlreadyListening]
 * before registering the listener because it cannot be registered twice.
 *
 * @author unicroak, kory33
 */
object InventoryGUIListener : Listener {

    private val InventoryEvent.guiSession
        get() = inventory?.holder as? InventoryGUISession

    private val InventoryClickEvent.clickedGuiSession
        get() = clickedInventory?.holder as? InventoryGUISession

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) = event.clickedGuiSession?.let { session ->
        session.getBoundReaction(event.slot)(event)
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) = event.guiSession?.onInventoryClose(event)

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) = event.guiSession?.onInventoryOpen(event)

    /**
     * Returns true if an instance of this class is already registered to the bukkit's event system.
     */
    fun isInstanceAlreadyListening() = InventoryClickEvent.getHandlerList().registeredListeners.any {
        it.listener is InventoryGUIListener
    }

}