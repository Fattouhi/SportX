package com.example.sportx.services.abir;

import com.example.sportx.DAO.abir.MessageDAO;
import com.example.sportx.models.abir.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private final MessageDAO messageDAO;
    private final NotificationService notificationService;

    public MessageService() throws SQLException {
        this.messageDAO = new MessageDAO();
        // Use ServiceFactory to get the NotificationService instance
        this.notificationService = ServiceFactory.getInstance().getNotificationService();
    }

    public void sendMessage(int senderId, int groupId, String content) throws SQLException {
        messageDAO.addMessage(senderId, groupId, content);
        notificationService.createNotificationForGroup(groupId, "New message in the group", "MESSAGE", senderId); // Pass the sender's ID (senderId)
    }

    public List<Message> getMessages(int groupId) throws SQLException {
        return messageDAO.getMessages(groupId);
    }
    public void sendVoiceMessage(int senderId, int groupId, String filePath) throws SQLException {
        messageDAO.addVoiceMessage(senderId, groupId, filePath);
    }

}