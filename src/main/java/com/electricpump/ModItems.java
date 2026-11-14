package com.electricpump;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ElectricPumpMod.MOD_ID);

    public static final DeferredItem<BlockItem> ELECTRIC_PUMP =
        ITEMS.registerSimpleBlockItem("electric_pump", ModBlocks.ELECTRIC_PUMP);
}