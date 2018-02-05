package click.seichi.giganticlib.gui.slot

import click.seichi.giganticlib.gui.Icon
import click.seichi.giganticlib.gui.slot.StorageSlot.ItemSetReaction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * インベントリ内のスロットを抽象化したクラスです。
 *
 * このクラスは`sealed`として宣言されており、サブクラスとして[ButtonSlot]と[StorageSlot]を持ちます。
 * 前者は「アイコンが固定された枠」、後者は「任意のアイテムを入れられる枠」としての意味合いを持ちます。
 *
 * このクラスが`sealed`である理由は、この二つのサブクラスのみでインベントリGUIを構築できるとの意図によるものです。
 *
 * @author unicroak, kory33
 */
sealed class Slot {

    abstract val reaction: (InventoryClickEvent) -> Unit

}

/**
 * ボタンとして機能するスロットです。
 *
 * 実装するクラスは、[icon]にボタンのアイコンとして機能する[Icon]を、
 * [action]にボタンがクリックされた際の副作用を定義する必要があります。
 *
 * [action]は[reaction]と似ていますが、[InventoryClickEvent.isCancelled]がどう変えられようと
 * [action]実行後に強制的に[InventoryClickEvent.isCancelled]が`true`に変えられることに注意してください。
 *
 * この仕様は、スロットが純粋にボタンとして動作するなら
 * クリックイベントはキャンセルされるべきであるという意図からのものです。
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
 * アイテムを内部に持つことを想定したスロットです。
 *
 * これを実装するクラスは[onItemSet]をアイテムがセットされたときの反応として定義してください。
 *
 * [ItemSetReaction]はアイテムがセットされたときのイベントに対する反応を内部的に表すEnumで、
 * [onItemSet]が[ItemSetReaction.REJECT]を返すと対応する[InventoryClickEvent]がキャンセルされます。
 */
abstract class StorageSlot: Slot() {

    /**
     * アイテムがセットされたときの反応です。
     *
     * @param newItemStack 新しくセットされる[ItemStack]、空になるなら`null`。
     * @return 対応するイベントに対する反応
     */
    abstract protected fun onItemSet(newItemStack: ItemStack?): ItemSetReaction

    override final val reaction = fun(event: InventoryClickEvent) {
        val reaction = onItemSet(event.cursor)

        if (reaction == ItemSetReaction.REJECT) {
            event.isCancelled = true
        }
    }

    protected enum class ItemSetReaction {
        ACCEPT, REJECT
    }

}