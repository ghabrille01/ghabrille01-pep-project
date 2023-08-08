package Controller;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("register", ctx -> {

            try {

                Account newAccount = objectMapper.readValue(ctx.body(), Account.class);

                if (newAccount.getUsername().isBlank() || newAccount.getPassword().isBlank() || newAccount.getPassword().length() < 4) {
                    ctx.status(400);
                    return;
                }

                Account resultAccount = accountService.registerAccount(newAccount);

                if (resultAccount.getUsername().isBlank() || resultAccount.getAccount_id() == 0) {
                    ctx.status(400);
                } else {
                    ctx.status(200).json(resultAccount);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }

        });

        app.post("login", ctx -> {

            try {

                Account newAccount = objectMapper.readValue(ctx.body(), Account.class);

                if (newAccount.getUsername().isBlank() || newAccount.getPassword().isBlank() || newAccount.getPassword().length() < 4) {
                    ctx.status(401);
                    return;
                }

                Account resultAccount = accountService.loginAccount(newAccount);

                if (resultAccount.getUsername().isBlank() || resultAccount.getAccount_id() == 0) {
                    ctx.status(401);
                } else {
                    ctx.status(200).json(resultAccount);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(401);
            }

        });

        app.post("messages", ctx -> {

            try {

                Message newMessage = objectMapper.readValue(ctx.body(), Message.class);

                if (newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() > 254 ) {
                    ctx.status(400);
                    return;
                }

                Message result = messageService.createMessage(newMessage);

                if (result.getMessage_text().isBlank() || result.getMessage_id() == 0) {
                    ctx.status(400);
                } else {
                    ctx.status(200).json(result);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }

        });

        app.get("messages", ctx -> {
            
            try {
                List<Message> messages = messageService.getAllMessages();

                if (messages.size() > 0) {
                    ctx.status(200).json(messages);
                } else {
                    ctx.status(200).json(new LinkedList<>());
                }
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }
        });

        app.get("messages/{message_id}", ctx -> {
            
            try {
                Message result = messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id")));

                if (result.getMessage_id()==0) {
                    ctx.status(200);
                } else {
                    ctx.status(200).json(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }
        });

        app.delete("messages/{message_id}", ctx -> {

            try {
                Message result = messageService.deleteMessageById(Integer.parseInt(ctx.pathParam("message_id")));
                
                if (result.getMessage_id()==0) {
                    ctx.status(200);
                } else {
                    ctx.status(200).json(result);
                }
            
            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }
        });

        app.patch("messages/{message_id}", ctx -> {

            try {
                Message newMessage = objectMapper.readValue(ctx.body(), Message.class);

                if (newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() > 254 ) {
                    ctx.status(400);
                    return;
                }

                Message result = messageService.updateMessageById(Integer.parseInt(ctx.pathParam("message_id")), newMessage.getMessage_text());

                if (result.getMessage_id() == 0) {
                    ctx.status(400);
                } else {
                    ctx.status(200).json(result);
                }

            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }
        });

        app.get("accounts/{account_id}/messages", ctx -> {
            try {
                List<Message> messages = messageService.getAllMessages(Integer.parseInt(ctx.pathParam("account_id")));

                if (messages.size() > 0) {
                    ctx.status(200).json(messages);
                } else {
                    ctx.status(200).json(new LinkedList<>());
                }

            } catch (Exception e) {
                e.printStackTrace();
                ctx.status(400);
            }
        });

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}