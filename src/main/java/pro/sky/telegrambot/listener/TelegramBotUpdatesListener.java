package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;
import pro.sky.telegrambot.service.TelegramBotSender;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private static final String GREETING_TEXT = "welcome";

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final Pattern INCOMING_MESSAGE_PATTERN =Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private final DateTimeFormatter NOTIFICATION_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final String SUCCESSFULLY_SAVED_MASSAGE = "THE NOT HAS BEEN SAVED";

    private TelegramBotSender telegramBotSender;
    
    private NotificationTaskRepository notificationTaskRepository;
    
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(TelegramBotSender telegramBotSender, TelegramBot telegramBot , NotificationTaskRepository notificationTaskRepository) {
        this.telegramBotSender = telegramBotSender;
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String messageText = update.message().text();
            Long chatId = update.message().chat().id();

            if (messageText.equals("/start")) {
                telegramBotSender.send(chatId,GREETING_TEXT);
            }else{
             Matcher matcher = INCOMING_MESSAGE_PATTERN.matcher(messageText);
             if (matcher.matches()){
                 logger.info("get a new message" + messageText);

                 String rawDateTime = matcher.group(1);
                 String notificationText = matcher.group(3);

                 NotificationTask notificationTask = new NotificationTask(
                         chatId,
                         notificationText,
                         LocalDateTime.parse(rawDateTime, NOTIFICATION_DATE_TIME_FORMAT)
                 );

                 notificationTaskRepository.save(notificationTask);

                 telegramBotSender.send(chatId, SUCCESSFULLY_SAVED_MASSAGE);

             }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
