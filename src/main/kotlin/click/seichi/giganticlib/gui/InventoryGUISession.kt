package click.seichi.giganticlib.gui

import click.seichi.giganticlib.gui.slot.ButtonSlot
import click.seichi.giganticlib.gui.slot.StorageSlot
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * インタラクティブなインベントリGUIのセッションを表すクラスです。
 * このクラスは`protected`な内部状態として、GUIとして機能する内部インベントリ内のボタン配置を与える[layout]を持ちます。
 *
 * [layout]に新しいインスタンスが代入されるたびに内部インベントリが更新され、
 * Bukkitの仕様によりこのインベントリを見ているプレーヤー全員に対してGUIの再描画が行われます。
 *
 * 派生クラスは、[onInventoryOpen]と[onInventoryClose]をGUIインベントリ開閉時のアクションとして定義することができます。
 *
 * @author kory33
 */
abstract class InventoryGUISession(initialLayout: SlotLayout): InventoryHolder {

    abstract val title: String

    open val inventoryParameter = InventoryParameter.Type(InventoryType.CHEST)

    /**
     * インベントリのボタンのレイアウトを保持するデータです。
     * このフィールドに新しいインスタンスが代入されるたびにインベントリGUIの更新が行われます。
     *
     * 既存のレイアウトの[SlotLayout.isSafeToApply]が`false`を返す場合、
     * ([ButtonSlot]が[StorageSlot]を上書きする危険性があるため)代入時にエラーが投げられます。
     */
    private var layout: SlotLayout = initialLayout
        set(newLayout) {
            if (!field.isSafeToApply(newLayout)) {
                throw IllegalStateException("Given layout is not safe to apply.")
            }

            field = newLayout.also { it.applyTo(sessionInventory) }
        }

    private val sessionInventory: Inventory by lazy {
        inventoryParameter.createInventoryWith(this, title)
    }

    /**
     * スロット番号から、関連付けられた反応を取得します。
     * @param slotId Menu内のslot番号
     * @return 指定IDのスロットに関連付けられた反応を取得します。
     */
    fun getBoundReaction(slotId: Int) = layout[slotId].reaction

    /**
     * メニューのインベントリが開かれたときのアクションを実行します
     */
    abstract fun onInventoryOpen(event: InventoryOpenEvent)

    /**
     * メニューのインベントリが閉じられたときのアクションを実行します
     */
    abstract fun onInventoryClose(event: InventoryCloseEvent)

    override fun getInventory() = sessionInventory

}