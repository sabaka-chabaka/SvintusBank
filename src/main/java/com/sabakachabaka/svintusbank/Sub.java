package com.sabakachabaka.svintusbank;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Sub {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        BankCommands.register(event.getDispatcher());
    }
}