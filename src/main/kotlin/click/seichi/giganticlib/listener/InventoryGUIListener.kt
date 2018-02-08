package click.seichi.giganticlib.listener

import click.seichi.giganticlib.gui.session.InventoryGUISession
import click.seichi.giganticlib.listener.InventoryGUIListener.Companion.isInstanceAlreadyListening
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * A default implementation of a listener which conveys events to the [InventoryGUISession].
 *
 * Do check for return value of [isInstanceAlreadyListening] because multiple registration causes
 * multiple event notification to the session instance.
 *
 * When [isInstanceAlreadyListening] is `true`, the constructor throws an exception
 * to ensure that no multiple plugins register instances of this class.
 *
 * Do not try to register the listener except from the main thread as it would break the single registration principle.
 *
 * @author unicroak, kory33
 */
class InventoryGUIListener : Listener {

    init {
        if (isInstanceAlreadyListening()) {
            throw IllegalStateException("The listener is already registered!")
        }
    }

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

    companion object {
        /**
         * Returns true if an instance of this class is already registered to the bukkit's event system.
         */
        fun isInstanceAlreadyListening() = InventoryClickEvent.getHandlerList().registeredListeners.any {
            it.listener is InventoryGUIListener
        }
    }

}