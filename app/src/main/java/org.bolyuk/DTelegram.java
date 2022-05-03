package org.bolyuk;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public class DTelegram extends Baser {
     private TelegramBot bot;

    public void init(String botToken){
        kovalski("Telegram Bot init...",2);
        bot = new TelegramBot(botToken);
    }

    public interface Getter {
        public int onRecieved(List<Update> updates);
        public void onException(TelegramException e);
    }

    public void sendText(String id, String text){
        Thread thread = new Thread( new Runnable() {
            public void run() {
                try {
                    kovalski("sending...",3);
                    bot.execute(new SendMessage(id, text));
                } catch (Exception e) {
                    kovalski("Sending error!: \n" + e.toString(),0);
                }
            }
        });
        thread.start();
    }

    public void setListener(Getter getter){
        bot.setUpdatesListener(
                new UpdatesListener() {
                    @Override
                    public int process(List<Update> updates) {
                        return getter.onRecieved(updates);
                    }
                }, new ExceptionHandler() {
                    @Override
                    public void onException(TelegramException e) {
                        getter.onException(e);
                    }
                }
        );
    }

    public void delListener(){
        bot.removeGetUpdatesListener();
    }

}
