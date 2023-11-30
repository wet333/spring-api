package online.awet.springapi.features.resume.controllers;

import jakarta.servlet.http.HttpServletRequest;
import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.core.exceptions.types.InvalidRequestDataException;
import online.awet.springapi.features.resume.models.Contact;
import online.awet.springapi.features.resume.services.ContactServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/resume/contacts")
public class ContactController {

    ContactServices contactServices;

    public ContactController(ContactServices contactServices) {
        this.contactServices = contactServices;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getAllContacts() {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        List<Contact> contacts = contactServices.getAllContacts();
        data.put("contacts", contacts);

        if (contacts != null) {
            String message = contacts.isEmpty() ? "Contact list is empty" : "Contact list";
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage(message);
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not get the contact list");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getContactById(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Contact dbContact = contactServices.getContactById(id);
        data.put("contact", dbContact);

        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact retrieved");
            response.setData(data);
        } else  {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Contact does not exists");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createContact(
            @RequestBody Contact contact, HttpServletRequest request
    ) throws InvalidRequestDataException {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        if (!contact.isValid()) {
            throw new InvalidRequestDataException("Fields [name, value] are required", request);
        }

        Contact dbContact = contactServices.createContact(contact);
        data.put("contact", dbContact);

        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact created");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not create the contact");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateContact(
            @PathVariable Long id, @RequestBody Contact contact, HttpServletRequest request
    ) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Contact dbContact = contactServices.updateContact(id, contact);
        data.put("contact", dbContact);

        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact updated");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not update the contact");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteContactById(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Contact dbContact = contactServices.deleteContact(id);
        data.put("contact", dbContact);

        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact deleted");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not delete the contact");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}