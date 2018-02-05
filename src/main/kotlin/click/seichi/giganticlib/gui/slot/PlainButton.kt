package click.seichi.giganticlib.gui.slot

import click.seichi.giganticlib.gui.Icon
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * 「何もしない」ようなボタンスロットのクラスです。
 * @author kory33
 */
class PlainButton(override val icon: Icon) : ButtonSlot() {

    override val action: (InventoryClickEvent) -> Unit = {}

}