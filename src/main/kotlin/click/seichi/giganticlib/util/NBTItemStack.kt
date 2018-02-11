package click.seichi.giganticlib.util

import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method
import kotlin.reflect.full.functions
import kotlin.reflect.jvm.javaMethod

/**
 * A class that provides reading/writing of NBT to [ItemStack].
 *
 * @param itemStack the source to read and write NBT
 *
 * @author unicroak
 */
class NBTItemStack(itemStack: ItemStack) {

    private var itemStack = itemStack
        get() = field.clone()

    private val nmsItemStack: Any
        get() = CRAFT_ITEM_STACK.getMethod("asNMSCopy", ItemStack::class.java).invoke(CRAFT_ITEM_STACK, itemStack)

    private val nbtTag: Any
        get() = nmsItemStack.javaClass.getMethod("getTag").invoke(nmsItemStack) ?: NBT_TAG_COMPOUND.newInstance()

    companion object {
        private val VERSION = Bukkit.getServer().javaClass.`package`.name.replace(".", ",").split(",".toRegex()).toTypedArray()[3]

        private val CRAFT_ITEM_STACK: Class<*> = Class.forName("org.bukkit.craftbukkit.$VERSION.inventory.CraftItemStack")
        private val NBT_TAG_COMPOUND: Class<*> = Class.forName("net.minecraft.server.$VERSION.NBTTagCompound")

        private val methodCacheMap = mutableMapOf<String, Method>()
    }

    private fun findMethodFromCache(methodName: String, method: Method): Method = methodCacheMap.getOrPut(methodName, { method })

    /**
     * Checks if this [NBTItemStack] contains NBT value which is corresponds the given [key].
     */
    fun has(key: String) = ("hasKey").let { methodName ->
        findMethodFromCache(methodName, NBT_TAG_COMPOUND.getMethod(methodName, String::class.java))
    }.invoke(nbtTag, key) as Boolean

    /**
     * Returns the value to which the given [key] corresponds
     */
    inline fun <reified T> read(key: String): T = read(key, T::class.java.simpleName)

    @Suppress("UNCHECKED_CAST")
    fun <T> read(key: String, typeName: String) = ("get$typeName").let { methodName ->
        findMethodFromCache(methodName, NBT_TAG_COMPOUND.getMethod(methodName, String::class.java))
    }.invoke(nbtTag, key) as T

    /**
     * Writes [value] which corresponds [key] in [itemStack].
     *
     * @return itself
     */
    fun write(key: String, value: Any): NBTItemStack {
        val nmsItemStack = this.nmsItemStack
        val nbtTag = this.nbtTag

        ("set${value::class.java.simpleName}").let { methodName ->
            findMethodFromCache(methodName, NBT_TAG_COMPOUND.kotlin.functions.first { it.name == methodName }.javaMethod!!)
        }.invoke(nbtTag, key, value)

        ("setTag").let { methodName ->
            findMethodFromCache(methodName, nmsItemStack::class.java.getMethod(methodName, NBT_TAG_COMPOUND))
        }.invoke(nmsItemStack, nbtTag)

        itemStack = ("asCraftMirror").let { methodName ->
            findMethodFromCache(methodName, CRAFT_ITEM_STACK.getMethod(methodName, nmsItemStack::class.java))
        }.invoke(CRAFT_ITEM_STACK, nmsItemStack) as ItemStack

        return this
    }

    /**
     * Returns [ItemStack] from this [NBTItemStack].
     *
     * @return [ItemStack]
     */
    fun toItemStack() = itemStack

}

/**
 * Returns [NBTItemStack] which is given [itemStack].
 */
fun nbtOf(itemStack: ItemStack) = NBTItemStack(itemStack)