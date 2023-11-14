package online.awet.springapi.features.resume.repository;

import online.awet.springapi.features.resume.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
