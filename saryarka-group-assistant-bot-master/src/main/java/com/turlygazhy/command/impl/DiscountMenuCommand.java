package com.turlygazhy.command.impl;

import com.turlygazhy.Bot;
import com.turlygazhy.command.Command;
import com.turlygazhy.dao.impl.ListDao;
import com.turlygazhy.entity.Discount;
import com.turlygazhy.entity.ListData;
import com.turlygazhy.entity.Message;
import com.turlygazhy.entity.MessageElement;
import org.telegram.telegrambots.api.methods.ParseMode;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eshu on 22.06.2017.
 */
public class DiscountMenuCommand extends Command {
    @Override
    public boolean execute(Update update, Bot bot) throws SQLException, TelegramApiException {
        if(update.getMessage()!= null){
        long chatId             = update.getMessage().getChatId();
        Message message         = messageDao.getMessage(messageId);
        SendMessage sendMessage = message.getSendMessage().setReplyMarkup(keyboardMarkUpDao.select(message.getKeyboardMarkUpId())).setChatId(chatId);
        bot.sendMessage(sendMessage);
        return true;
        }
        if(update.getCallbackQuery()!=null){
            String chose = update.getCallbackQuery().getData();
            if(chose.equals(buttonDao.getButtonText(97))){
                getDiscounts(bot,update, "Restaurants","Рестораны");
                return true;
            }
            if(chose.equals(buttonDao.getButtonText(98))){
                getDiscounts(bot,update, "Hotels", "Отели");
                return true;
            }
            if(chose.equals(buttonDao.getButtonText(99))){
                getDiscounts(bot,update, "BusinessSchools", "Бизнес-школы");
                return true;
            }

        }

        return true;
    }

    private void getDiscounts(Bot bot, Update update, String discountsType, String textWithDiscounts) throws SQLException, TelegramApiException {
        ListDao listDao = factory.getListDao("DISCOUNTS");
        ArrayList<Discount> discountArrayList = listDao.getDiscounts(discountsType, true);
        if(discountArrayList.isEmpty()){
            SendMessage sendMessage = new SendMessage().setText("К сожалению ничего не найдено")
                    .setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()));
            bot.sendMessage(sendMessage);
        }
        else{
            ReplyKeyboard keyboard                = getDiscountViaButtons(discountArrayList);
            SendMessage sendMessage               = new SendMessage().setChatId(String.valueOf(update.getCallbackQuery().getFrom().getId()))
                    .setText(textWithDiscounts).setReplyMarkup(keyboard);
            bot.sendMessage(sendMessage);}
    }


    private ReplyKeyboard getDiscountViaButtons(ArrayList<Discount> discountArrayList){
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row;

        for (Discount discount : discountArrayList) {
            row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            String buttonText = discount.getName() + " (" + discount.getDiscount() +")";
            button.setText(buttonText);
            button.setCallbackData("get_discount" + ":" + discount.getId());
            row.add(button);
            rows.add(row);
        }
        keyboard.setKeyboard(rows);
        return keyboard;

    }
}
