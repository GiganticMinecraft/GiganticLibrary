package click.seichi.giganticlib.gui

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author unicroak
 */
data class Icon(
        private val material: Material,
        private val damage: Int = 0,
        private val amount: Int = 1,
        private val isEnchanted: Boolean = false,
        private val name: String? = null,
        private val lore: List<String> = listOf()
) {
    val itemStack: ItemStack = ItemStack(material, amount, damage.toShort())
        get() = field.clone()

    init {
        val itemMeta = itemStack.itemMeta.clone()

        if (isEnchanted) {
            itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true)
            itemMeta.itemFlags += ItemFlag.HIDE_ENCHANTS
        }

        name?.let { itemMeta.displayName = name }

        if (lore.isNotEmpty()) {
            itemMeta.lore = lore
        }

        itemStack.itemMeta = itemMeta
    }
}