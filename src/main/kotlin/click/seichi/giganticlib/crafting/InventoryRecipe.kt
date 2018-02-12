package click.seichi.giganticlib.crafting

import org.bukkit.inventory.ItemStack

/**
 * An abstracted interface of a recipe constructed on an inventory.
 *
 * @author kory33
 */
interface InventoryRecipe {

    /**
     * Yields the result of the recipe based on the [inputs].
     *
     * When the given input is wrong or insufficient to yield [ItemStack], returns `null`
     *
     * @param inputs a mapping of [ItemStack]s from [Int] representing a slot id.
     * @return result of the craft, `null` if the input is wrong or insufficient.
     */
    fun yieldWith(inputs: Map<Int, ItemStack?>): ItemStack?

}