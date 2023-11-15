package online.awet.springapi.features.resume.services;

import online.awet.springapi.features.resume.models.Skill;
import online.awet.springapi.features.resume.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    SkillRepository skillRepository;

    SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkill(Long id) {
        Optional<Skill> dbSkill = skillRepository.findById(id);
        return dbSkill.orElse(null);
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id , Skill updatedSkill) {
        Optional<Skill> dbSkill = skillRepository.findById(id);

        if (dbSkill.isPresent()) {

            if (updatedSkill.getName() != null && !updatedSkill.getName().isBlank()) {
                dbSkill.get().setName(updatedSkill.getName());
            }
            if (updatedSkill.getIcon() != null && !updatedSkill.getIcon().isBlank()) {
                dbSkill.get().setIcon(updatedSkill.getIcon());
            }
            if (updatedSkill.getImage() != null && !updatedSkill.getImage().isBlank()) {
                dbSkill.get().setImage(updatedSkill.getImage());
            }
            if (updatedSkill.getOrder() != null) {
                dbSkill.get().setOrder(updatedSkill.getOrder());
            }
            return skillRepository.save(dbSkill.get());
        }
        return null;
    }

    public Skill deleteSkill(Long id) {
        Optional<Skill> dbSkill = skillRepository.findById(id);
        if (dbSkill.isPresent()) {
            skillRepository.deleteById(id);
            return dbSkill.get();
        } else {
            return null;
        }
    }
}
