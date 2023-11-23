package online.awet.springapi.features.resume.controllers;

import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.features.resume.models.Contact;
import online.awet.springapi.features.resume.services.ContactServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resume/contacts")
@PreAuthorize("hasRole('ADMIN')")
public class ContactController {

    ContactServices contactServices;

    public ContactController(ContactServices contactServices) {
        this.contactServices = contactServices;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Contact>>> getAllContacts() {
        ApiResponse<List<Contact>> response = new ApiResponse<>();

        List<Contact> contacts = contactServices.getAllContacts();
        if (contacts != null) {
            String message = contacts.isEmpty() ? "Contact list is empty" : "Contact list";
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage(message);
            response.setData(contacts);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not get the contact list");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contact>> getContactById(@PathVariable Long id) {
        ApiResponse<Contact> response = new ApiResponse<>();

        Contact dbContact = contactServices.getContactById(id);
        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact retrieved");
            response.setData(dbContact);
        } else  {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Contact does not exists");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Contact>> createContact(@RequestBody Contact contact) {
        ApiResponse<Contact> response = new ApiResponse<>();

        if (!contact.isValid()) {
            response.setReturnCode(ReturnCodes.BAD_REQUEST.getCode());
            response.setMessage("Fields [name, value] are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Contact dbContact = contactServices.createContact(contact);
        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact created");
            response.setData(dbContact);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not create the contact");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Contact>> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        ApiResponse<Contact> response = new ApiResponse<>();

        Contact dbContact = contactServices.updateContact(id, contact);
        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact updated");
            response.setData(dbContact);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not update the contact");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Contact>> deleteContactById(@PathVariable Long id) {
        ApiResponse<Contact> response = new ApiResponse<>();

        Contact dbContact = contactServices.deleteContact(id);
        if (dbContact != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Contact deleted");
            response.setData(dbContact);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not delete the contact");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}