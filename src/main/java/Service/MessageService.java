package Service;

import java.sql.SQLException;
import java.util.List;

import DAO.MessageDao;
import Model.Message;

public class MessageService {
    
    private MessageDao messageDao = new MessageDao();

    public Message createMessage(Message newMessage) throws SQLException {

        Message resultMessage = new Message();

        if (messageDao.insertNewMessage(newMessage)) {
            resultMessage = messageDao.selectMessage(newMessage.getPosted_by(), newMessage.getMessage_text(), newMessage.getTime_posted_epoch()); 
        }
        
        return resultMessage;
    }

    public List<Message> getAllMessages() throws SQLException {
        return messageDao.selectAllMessages();
    }

    public List<Message> getAllMessages(int postedId) throws SQLException {
        return messageDao.selectAllMessages(postedId);
    }

    public Message getMessageById(int messageId) throws SQLException {
        return messageDao.selectMessage(messageId);
    }

    public Message deleteMessageById(int messageId) throws SQLException {
        Message message = messageDao.selectMessage(messageId);
        if (message.getMessage_id() == 0) {
            return message;
        }

        if (messageDao.deleteMessage(messageId)) {
            return message;
        }

        return new Message();
    }

    public Message updateMessageById(int messageId, String messageText) throws SQLException {
        Message message = new Message();

        if (messageDao.selectMessage(messageId).getMessage_id()==0) {
            return message;
        }

        if (messageDao.updateMessage(messageId, messageText)) {
            message = messageDao.selectMessage(messageId);
        }

        return message;
    }
}
