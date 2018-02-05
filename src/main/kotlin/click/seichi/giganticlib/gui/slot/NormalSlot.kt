package click.seichi.giganticlib.gui.slot

import click.seichi.giganticlib.gui.slot.StorageSlot.ItemSetReaction.ACCEPT
import org.bukkit.inventory.ItemStack

/**
 * バニラのインベントリの「いたって普通のスロット」です。
 *
 * 子のスロットは、普通のスロットと同じようにすべての[ItemStack]の出し入れを通します。
 *
 * @author kory33
 */
object NormalSlot: StorageSlot() {

    override fun onItemSet(newItemStack: ItemStack?) = ACCEPT

}