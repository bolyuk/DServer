package com.atr.dserver.dclasses;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class DTelegram extends DBClass {
    static TelegramBot bot;

    public static void init(String botToken){
        log("Telegram Bot init...");
        bot = new TelegramBot(botToken);
    }

    public static void sendText(String id, String text){
        Thread thread = new Thread( new Runnable() {
            public void run() {
                try {
                    log("sending...");
                    bot.execute(new SendMessage(id, text));
                } catch (Exception e) {
                    log("Sending error!: \n" + e.toString());
                }
            }
        });
        thread.start();
    }

}
