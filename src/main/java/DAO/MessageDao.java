package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDao {
    
    private Connection connection = ConnectionUtil.getConnection();

    public Boolean insertNewMessage(Message newMessage) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)");
        ps.setInt(1, newMessage.getPosted_by());
        ps.setString(2, newMessage.getMessage_text());
        ps.setLong(3, newMessage.getTime_posted_epoch());

        int result = ps.executeUpdate();

        if (result > 0) {
            return true;
        }

        return false;
    }

    public Message selectMessage(int postedBy, String messageText, Long timePostedEpoch) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM message WHERE posted_by = ? AND message_text = ? AND time_posted_epoch = ?");
        ps.setInt(1, postedBy);
        ps.setString(2, messageText);
        ps.setLong(3, timePostedEpoch);

        ResultSet rs = ps.executeQuery();

        Message message = new Message();

        while(rs.next()) {
            message.setMessage_id(rs.getInt("message_id"));
            message.setPosted_by(rs.getInt("posted_by"));
            message.setMessage_text(rs.getString("message_text"));
            message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
        }

        return message;
    }

    public Message selectMessage(int messageId) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM message WHERE message_id = ?");
        ps.setInt(1, messageId);

        ResultSet rs = ps.executeQuery();

        Message message = new Message();

        while(rs.next()) {
            message.setMessage_id(rs.getInt("message_id"));
            message.setPosted_by(rs.getInt("posted_by"));
            message.setMessage_text(rs.getString("message_text"));
            message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
        }

        return message;
    }

    public List<Message> selectAllMessages() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM message");

        ResultSet rs = ps.executeQuery();

        List<Message> messages = new LinkedList<>();

        while(rs.next()) {
            Message message = new Message();

            message.setMessage_id(rs.getInt("message_id"));
            message.setPosted_by(rs.getInt("posted_by"));
            message.setMessage_text(rs.getString("message_text"));
            message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

            messages.add(message);
        }

        return messages;
    }

    public List<Message> selectAllMessages(int postedId) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM message WHERE posted_by = ?");
        ps.setInt(1, postedId);

        ResultSet rs = ps.executeQuery();

        List<Message> messages = new LinkedList<>();

        while(rs.next()) {
            Message message = new Message();

            message.setMessage_id(rs.getInt("message_id"));
            message.setPosted_by(rs.getInt("posted_by"));
            message.setMessage_text(rs.getString("message_text"));
            message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));

            messages.add(message);
        }

        return messages;
    }

    public Boolean deleteMessage(int messageId) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM message WHERE message_id = ?");
        ps.setInt(1, messageId);

        int result = ps.executeUpdate();

        if (result > 0) {
            return true;
        }

        return false;
    }

    public Boolean updateMessage(int messageId, String messageText) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("UPDATE message SET message_text = ? WHERE message_id = ?");
        ps.setString(1, messageText);
        ps.setInt(2, messageId);

        int result = ps.executeUpdate();

        if (result > 0) {
            return true;
        }

        return false;
    }
}
