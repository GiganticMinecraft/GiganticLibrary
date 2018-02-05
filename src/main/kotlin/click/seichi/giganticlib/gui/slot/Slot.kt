package click.seichi.giganticlib.gui.slot

import click.seichi.giganticlib.gui.Icon
import click.seichi.giganticlib.gui.slot.StorageSlot.ItemSetReaction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * An abstraction of a slot in an inventory.
 *
 * This class is declared to be `sealed` and has [ButtonSlot] and [StorageSlot] as its subclasses.
 * The former represents a button on which an icon is fixed,
 * and the latter stands for a slot in which an item can be put.
 *
 * The reason this class is `sealed` follows the proposition that
 * any inventory GUI can be built by a certain combination of these two types of slots.
 *
 * @author unicroak, kory33
 */
sealed class Slot {

    abstract val reaction: (InventoryClickEvent) -> Unit

}

/**
 * A slot that acts like a button.
 *
 * A subclass implementing this class has to define:
 *  * [icon] as an icon of the button
 *  * [action] as a side effect caused by the button click
 *
 * [action] might look to be similar to [reaction]. However, note that [InventoryClickEvent.isCancelled] will be set to
 * `true` no matter to what value you set this in [action].
 *
 * This behaviour follows an idea that *if a slot acts as a pure button, the click event should be cancelled*.
 *
 * @author kory33
 */
abstract class ButtonSlot : Slot() {

    abstract val icon: Icon

    abstract val action: (InventoryClickEvent) -> Unit

    override final val reaction = { event: InventoryClickEvent ->
        action(event)

        event.isCancelled = true
    }

}

/**
 * A slot which can hold an [ItemStack].
 *
 * A subclass implementing this class has to define [onItemSet] as a reaction for an [ItemStack] being set to the slot.
 *
 * [ItemSetReaction] is an internal enumeration of the reaction for incoming [ItemStack].
 * When [onItemSet] returns [ItemSetReaction.REJECT], the corresponding [InventoryClickEvent] will be cancelled.
 *
 * @author kory33
 */
abstract class StorageSlot: Slot() {

    /**
     * A reaction for an [ItemStack] being set to the slot
     *
     * @param newItemStack an incoming [ItemStack]. `null` if the slot is going to be empty.
     * @param event the event which describes a transaction of [ItemStack]
     * @return reaction to the [InventoryClickEvent]
     */
    abstract protected fun onItemSet(newItemStack: ItemStack?, event: InventoryClickEvent): ItemSetReaction

    override final val reaction = fun(event: InventoryClickEvent) {
        val reaction = onItemSet(event.cursor, event)

        event.isCancelled = reaction == ItemSetReaction.REJECT
    }

    protected enum class ItemSetReaction {
        ACCEPT, REJECT
    }

}