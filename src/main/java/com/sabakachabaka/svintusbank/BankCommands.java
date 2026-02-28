package com.sabakachabaka.svintusbank;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sabakachabaka.svintusbank.items.BankCardItem;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

public class BankCommands {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("pay_card")
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg(1.0))
                                .executes(context -> {
                                    return transferMoney(
                                            context.getSource(),
                                            EntityArgument.getPlayer(context, "target"),
                                            DoubleArgumentType.getDouble(context, "amount")
                                    );
                                }))));
    }

    private static int transferMoney(CommandSource source, PlayerEntity target, double amount) throws CommandSyntaxException {
        ServerPlayerEntity sender = source.getPlayerOrException();
        ItemStack senderCard = sender.getMainHandItem();

        // 1. Проверка карты в руке отправителя
        if (!(senderCard.getItem() instanceof BankCardItem)) {
            source.sendFailure(new StringTextComponent("§cВы должны держать банковскую карту в основной руке!"));
            return 0;
        }

        // 2. Проверка баланса
        double senderBalance = BankCardItem.getBalance(senderCard);
        if (senderBalance < amount) {
            source.sendFailure(new StringTextComponent("§cНедостаточно свинтусов на карте!"));
            return 0;
        }

        // 3. Поиск карты у получателя (в инвентаре)
        ItemStack targetCard = ItemStack.EMPTY;
        for (int i = 0; i < target.inventory.getContainerSize(); i++) {
            ItemStack stack = target.inventory.getItem(i);
            if (stack.getItem() instanceof BankCardItem) {
                targetCard = stack;
                break;
            }
        }

        if (targetCard.isEmpty()) {
            source.sendFailure(new StringTextComponent("§cУ игрока " + target.getScoreboardName() + " нет банковской карты в инвентаре!"));
            return 0;
        }

        // 4. Проведение транзакции
        BankCardItem.setBalance(senderCard, senderBalance - amount);
        BankCardItem.setBalance(targetCard, BankCardItem.getBalance(targetCard) + amount);

        // Уведомления
        source.sendSuccess(new StringTextComponent("§aВы перевели " + amount + " свинтусов игроку " + target.getScoreboardName()), true);
        target.displayClientMessage(new StringTextComponent("§aИгрок " + sender.getScoreboardName() + " перевел вам " + amount + " свинтусов на карту!"), false);

        return 1;
    }
}
