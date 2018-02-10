package click.seichi.giganticlib.extension.gui

import click.seichi.giganticlib.gui.SlotLayout
import click.seichi.giganticlib.gui.slot.IndexedSlot
import click.seichi.giganticlib.gui.slot.Slot

inline fun <reified T: Slot> SlotLayout.filterTypedSlots() = this.mapNotNull { (index, slot) ->
    if (slot is T) IndexedSlot(index, slot)
    else null
}