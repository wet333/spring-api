package online.awet.springapi.features.resume.repository;

import online.awet.springapi.features.resume.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
}
