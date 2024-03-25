package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotSender {
    private TelegramBot telegramBot;

    public TelegramBotSender(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void send(Long chatId, String message) {
        SendMessage sendingMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendingMessage);
    }
}
