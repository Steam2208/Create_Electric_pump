package com.electricpump;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ElectricPumpMod.MOD_ID);

    public static final DeferredBlock<ElectricPumpBlock> ELECTRIC_PUMP = BLOCKS.register(
        "electric_pump",
        () -> new ElectricPumpBlock(
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .sound(SoundType.COPPER)
                .strength(3.0F)
                .requiresCorrectToolForDrops()
                .noOcclusion()
        )
    );
}