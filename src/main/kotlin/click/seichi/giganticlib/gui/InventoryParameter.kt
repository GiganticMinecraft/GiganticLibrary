package click.seichi.giganticlib.gui

import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * A class which is a direct sum of [InventoryType] and [Int] (representing inventory size).
 *
 * @author kory33
 */
sealed class InventoryParameter {

    abstract fun createInventoryWith(inventoryHolder: InventoryHolder, title: String): Inventory
    abstract fun toSize(): Int

    class Size(private val size: Int): InventoryParameter() {

        override fun createInventoryWith(inventoryHolder: InventoryHolder, title: String): Inventory
                = Bukkit.createInventory(inventoryHolder, this.size, title)

        override fun toSize() = size

    }

    class Type(private val type: InventoryType): InventoryParameter() {

        override fun createInventoryWith(inventoryHolder: InventoryHolder, title: String): Inventory
                = Bukkit.createInventory(inventoryHolder, this.type, title)

        override fun toSize() = type.defaultSize

    }

}