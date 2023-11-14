package online.awet.springapi.features.resume.services;

import online.awet.springapi.features.resume.models.Contact;
import online.awet.springapi.features.resume.repository.ContactsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServices {

    ContactsRepository contactsRepository;

    public ContactServices(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    public List<Contact> getAllContacts() {
        return contactsRepository.findAll();
    }

    public Contact getContactById(Long id) {
        Optional<Contact> dbContact = contactsRepository.findById(id);
        return dbContact.orElse(null);
    }

    public Contact createContact(Contact newContact) {
        return contactsRepository.save(newContact);
    }

    public Contact updateContact(Long id, Contact newContact) {
        Optional<Contact> dbContact = contactsRepository.findById(id);

        if (dbContact.isPresent()) {

            if (newContact.getName() != null && !newContact.getName().isBlank()) {
                dbContact.get().setName(newContact.getName());
            }
            if (newContact.getValue() != null && !newContact.getValue().isBlank()) {
                dbContact.get().setValue(newContact.getValue());
            }
            if (newContact.getDescription() != null && !newContact.getDescription().isBlank()) {
                dbContact.get().setDescription(newContact.getDescription());
            }
            return contactsRepository.save(dbContact.get());
        }
        return null;
    }

    public Contact deleteContact(Long id) {
        Optional<Contact> dbContact = contactsRepository.findById(id);
        if (dbContact.isPresent()) {
            contactsRepository.deleteById(id);
            return dbContact.get();
        } else {
            return null;
        }
    }
}
