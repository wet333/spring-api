package online.awet.springapi.features.resume.controllers;

import jakarta.servlet.http.HttpServletRequest;
import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.core.exceptions.types.InvalidRequestDataException;
import online.awet.springapi.features.resume.models.Message;
import online.awet.springapi.features.resume.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/resume/messages")
public class MessageController {

    private final MessageService messagesService;

    public MessageController(MessageService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> getAllMessages() {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        List<Message> messages = messagesService.getAllMessages();
        data.put("messages", messages);

        if (messages != null) {
            String message = messages.isEmpty() ? "Message list is empty" : "Message list";
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage(message);
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not get the message list");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> getMessageById(@PathVariable String id) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Message dbMessage = messagesService.getMessageById(id);
        data.put("message", dbMessage);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message retrieved");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message does not exists");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> createMessage(
            @RequestBody Message message, HttpServletRequest request
    ) throws InvalidRequestDataException {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        if (!message.isValid()) {
            throw new InvalidRequestDataException("Fields [message] are required", request);
        }

        Message dbMessage = messagesService.createMessage(message);
        data.put("message", dbMessage);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message created");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message could not be created");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateMessage(
            @PathVariable String id, @RequestBody Message message, HttpServletRequest request
    ) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Message dbMessage = messagesService.updateMessage(id, message);
        data.put("message", dbMessage);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message updated");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message could not be updated");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable String id) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Message dbMessage = messagesService.deleteMessage(id);
        data.put("message", dbMessage);

        if (dbMessage != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Message deleted");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Message could not be deleted");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}