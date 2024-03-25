package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final Long chatId;
    private String message;
    private LocalDateTime notificationDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationTask(Long chatId, String massage, LocalDateTime notificationDateTime) {
        this.chatId = chatId;
        this.message = massage;
        this.notificationDateTime = notificationDateTime;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getMassage() {
        return message;
    }

    public void setMassage(String massage) {
        this.message = massage;
    }

    public LocalDateTime getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDateTime notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(message, that.message) && Objects.equals(notificationDateTime, that.notificationDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, message, notificationDateTime);
    }
}
