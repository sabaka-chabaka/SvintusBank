package com.sabakachabaka.svintusbank.items;

import com.sabakachabaka.svintusbank.SvintusBank;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sabakachabaka.svintusbank.blocks.ModBlocks.ATM_BLOCK;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =  DeferredRegister.create(ForgeRegistries.ITEMS, SvintusBank.MOD_ID);

    public static final RegistryObject<Item> BANK_CARD = ITEMS.register("bank_card", () -> new BankCardItem(new Item.Properties()));

    public static final RegistryObject<Item> BILL_50 = ITEMS.register("bill_50", () -> new BillItem(50));
    public static final RegistryObject<Item> BILL_100 = ITEMS.register("bill_100", () -> new BillItem(100));
    public static final RegistryObject<Item> BILL_500 = ITEMS.register("bill_500", () -> new BillItem(500));
    public static final RegistryObject<Item> BILL_1000 = ITEMS.register("bill_1000", () -> new BillItem(1000));
    public static final RegistryObject<Item> BILL_5000 = ITEMS.register("bill_5000", () -> new BillItem(5000));

    public static final RegistryObject<Item> ATM_ITEM = ITEMS.register("atm",
            () -> new BlockItem(ATM_BLOCK.get(), new Item.Properties().tab(SvintusBank.ITEM_GROUP)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
