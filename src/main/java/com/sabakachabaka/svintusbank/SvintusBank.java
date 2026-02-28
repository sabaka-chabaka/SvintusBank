package com.sabakachabaka.svintusbank;

import com.sabakachabaka.svintusbank.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("svintusbank")
public class SvintusBank
{
    public static final String MOD_ID = "svintusbank";
    public static final ItemGroup ITEM_GROUP = new BankGroup("svintusbank");

    public SvintusBank() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }

    public static class BankGroup extends ItemGroup {
        public BankGroup(String label) { super(label); }

        @Override
        public ItemStack makeIcon(){
            return ModItems.BANK_CARD.get().getDefaultInstance();
        }
    }
}
