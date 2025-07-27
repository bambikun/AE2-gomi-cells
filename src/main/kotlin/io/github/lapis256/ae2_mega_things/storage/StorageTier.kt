package io.github.lapis256.ae2_mega_things.storage

import appeng.core.definitions.AEItems
import gripe._90.megacells.item.MEGAItems
import net.minecraft.world.level.ItemLike


sealed class StorageTier(
    val bytes: Long,
    val affix: String,
    val component: ItemLike,
    val idleDrain: Double
) {
    companion object {
        fun calcKilobytes(index: Int) = when (index) {
            in 1..5 -> 1L shl (2 * (index - 1)) // Use Long type
            else -> throw IllegalArgumentException("Index out of range")
        }
    }

    open class Standard(index: Int, component: ItemLike) : StorageTier(
        calcKilobytes(index) * 1024L,
        "${calcKilobytes(index)}k",
        component,
        0.5 * index
    )

    open class Mega(index: Int, component: ItemLike) : StorageTier(
        calcKilobytes(index) * 1_000_000L,
        "${calcKilobytes(index)}m",
        component,
        2.5 + 0.5 * index
    )

    open class Custom(bytes: Long, affix: String, component: ItemLike, idleDrain: Double) : StorageTier(
        bytes,
        affix,
        component,
        idleDrain
    )

    // 標準 tiers
    object SIZE1K : Custom(1L, "1", AEItems.CELL_COMPONENT_1K)
    object SIZE4K : Custom(2147483647L, "2b" AEItems.CELL_COMPONENT_4K)
    object SIZE16K : Custom(9223372036854775807L, "max", AEItems.CELL_COMPONENT_16K)
    object SIZE64K : Standard(4, AEItems.CELL_COMPONENT_64K)
    object SIZE256K : Standard(5, AEItems.CELL_COMPONENT_256K)

    // Mega tiers
    object SIZE1M : Mega(1, MEGAItems.CELL_COMPONENT_1M)
    object SIZE4M : Mega(2, MEGAItems.CELL_COMPONENT_4M)
    object SIZE16M : Mega(3, MEGAItems.CELL_COMPONENT_16M)
    object SIZE64M : Mega(4, MEGAItems.CELL_COMPONENT_64M)
    object SIZE256M : Mega(5, MEGAItems.CELL_COMPONENT_256M)
}
