package online.awet.springapi.features.resume.controllers;

import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.features.resume.models.Message;
import online.awet.springapi.features.resume.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resume/messages")
public class MessageController {

    private final MessageService messagesService;

    public MessageController(MessageService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Message>>> getAllMessages() {
        ApiResponse<List<Message>> response = new ApiResponse<List<Message>>();

        List<Message> messages = messagesService.getAllMessages();

        if (messages != null) {
            String message = messages.isEmpty() ? "Message list is empty" : "Message list";
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage(message);
            response.setData(messages);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not get the message list");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Message>> getMessageById(@PathVariable String id) {
        ApiResponse<Message> response = new ApiResponse<Message>();

        Message dbMessage = messagesService.getMessageById(id);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message retrieved");
            response.setData(dbMessage);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message does not exists");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Message>> createMessage(@RequestBody Message message) {
        ApiResponse<Message> response = new ApiResponse<Message>();

        if (!message.isValid()) {
            response.setReturnCode(ReturnCodes.BAD_REQUEST.getCode());
            response.setMessage("Fields [message] are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Message dbMessage = messagesService.createMessage(message);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message created");
            response.setData(dbMessage);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message could not be created");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Message>> updateMessage(@PathVariable String id, @RequestBody Message message) {
        ApiResponse<Message> response = new ApiResponse<Message>();

        if (!message.isValid()) {
            response.setReturnCode(ReturnCodes.BAD_REQUEST.getCode());
            response.setMessage("Fields [message] are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Message dbMessage = messagesService.updateMessage(id, message);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message updated");
            response.setData(dbMessage);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message could not be updated");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Message>> deleteMessage(@PathVariable String id) {
        ApiResponse<Message> response = new ApiResponse<Message>();

        Message dbMessage = messagesService.deleteMessage(id);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message deleted");
            response.setData(dbMessage);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message could not be deleted");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}