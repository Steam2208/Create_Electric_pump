package com.electricpump;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.Set;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ElectricPumpMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ElectricPumpBlockEntity>> ELECTRIC_PUMP =
        BLOCK_ENTITIES.register("electric_pump", ModBlockEntities::createBlockEntityType);

    @SuppressWarnings("unchecked")
    private static BlockEntityType<ElectricPumpBlockEntity> createBlockEntityType() {
        try {
            Class<?> supplierClass = Class.forName("net.minecraft.world.level.block.entity.BlockEntityType$BlockEntitySupplier");
            Object supplier = Proxy.newProxyInstance(
                ElectricPumpMod.class.getClassLoader(),
                new Class<?>[]{supplierClass},
                (proxy, method, args) -> {
                    if ("create".equals(method.getName())) {
                        return new ElectricPumpBlockEntity((BlockPos) args[0], (BlockState) args[1]);
                    }
                    return null;
                }
            );
            Constructor<BlockEntityType> ctor = BlockEntityType.class.getDeclaredConstructor(
                supplierClass,
                Set.class,
                com.mojang.datafixers.types.Type.class
            );
            ctor.setAccessible(true);
            return (BlockEntityType<ElectricPumpBlockEntity>) (Object) ctor.newInstance(
                supplier,
                Set.of(ModBlocks.ELECTRIC_PUMP.get()),
                null
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate ElectricPump BlockEntityType", e);
        }
    }
}
