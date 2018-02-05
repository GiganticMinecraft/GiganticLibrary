package click.seichi.giganticlib.gui

import click.seichi.giganticlib.gui.slot.ButtonSlot
import click.seichi.giganticlib.gui.slot.NormalSlot
import click.seichi.giganticlib.gui.slot.Slot
import click.seichi.giganticlib.gui.slot.StorageSlot
import click.seichi.giganticlib.util.collection.mapValuesNotNull
import org.bukkit.inventory.Inventory

/**
 * スロットのレイアウトを決定するクラスです。
 *
 * このクラスは、[inventoryParameter]が指定するインベントリ内の**全ての**スロットに対して
 * [Slot]を割り当てることを約束します。
 *
 * [defaultSlot]は既定値としての[Slot]インスタンスを与えます。
 *
 * @param inventoryParameter インベントリのサイズを示すデータ値
 * @param slotOverrides [defaultSlot]を上書きする対象のスロットIDからスロットへの[Map]
 */
abstract class SlotLayout(private val inventoryParameter: InventoryParameter, slotOverrides: Map<Int, Slot> = HashMap()) {
    /**
     * 何もセットされていない枠を埋めるためのデフォルトのスロット
     *
     * Minecraftデフォルトのスロットのような挙動をさせる場合は[NormalSlot]を使用してください。
     */
    abstract val defaultSlot: Slot

    private val slotMapping: Map<Int, Slot> = (0 until inventoryParameter.toSize()).associate { slotId ->
        slotId to (slotOverrides[slotId] ?: defaultSlot)
    }

    /** ストレージであるスロットのインデックスの集合 */
    private val storageIndices = slotMapping.filterValues { when(it) {
        // Slotのサブクラスが追加された際にコンパイルを落とすためにわざと明示的に書いています
        is StorageSlot -> true
        is ButtonSlot -> false
    } }.keys

    /**
     * ボタンのインデックスとそれに対応するスロットの組
     */
    private val buttons = slotMapping.mapValuesNotNull { _, slot -> slot as? ButtonSlot }

    fun applyTo(inventory: Inventory) =
            buttons.forEach { index, button -> inventory.setItem(index, button.icon.itemStack) }

    /**
     * 他のレイアウトをGUIインターフェースに安全に適用することができるかを指します。
     *
     * 「安全に適用できる」とは、対象のレイアウトとこのレイアウトが同じだけのスロット数を持ち、
     * 任意のインデックス `i` に対して
     *
     * `this[i] is StorageSlot (if and only if) another[i] is StorageSlot`
     *
     * が成り立つことを言います。
     *
     * [Slot]は[ButtonSlot]または[StorageSlot]であることが保証されるので、
     * 後半の条件は[storageIndices]の同値性を調べるのみで済みます。
     */
    fun isSafeToApply(another: SlotLayout) =
            (this.inventoryParameter.toSize() == another.inventoryParameter.toSize()) &&
            (this.storageIndices == another.storageIndices)

    /**
     * 対象スロットIDに存在する[Slot]を取得します。
     *
     * [slotId]が[inventoryParameter]で規定されたインベントリのサイズ外である場合は
     * [IndexOutOfBoundsException]が`throw`されます。
     *
     * @param slotId 取得する対象のスロットID
     */
    operator fun get(slotId: Int) = slotMapping[slotId] ?:
            throw IndexOutOfBoundsException("Target slot number is out of range!")

}