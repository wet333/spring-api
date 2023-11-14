package online.awet.springapi.features.resume.controllers;

import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.features.resume.models.Skill;
import online.awet.springapi.features.resume.services.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resume/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Skill>>> getAllSkills() {
        ApiResponse<List<Skill>> response = new ApiResponse<>();

        List<Skill> skills = skillService.getAllSkills();
        if (skills != null) {
            String message = skills.isEmpty() ? "Skill list is empty" : "Skill list";
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage(message);
            response.setData(skills);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not get the skill list");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> getSkillById(@PathVariable Long id) {
        ApiResponse<Skill> response = new ApiResponse<>();

        Skill dbSkill = skillService.getSkill(id);
        if (dbSkill != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Skill retrieved");
            response.setData(dbSkill);
        } else  {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Skill does not exists");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Skill>> createSkill(@RequestBody Skill skill) {
        ApiResponse<Skill> response = new ApiResponse<>();

        if (!skill.isValid()) {
            response.setReturnCode(ReturnCodes.BAD_REQUEST.getCode());
            response.setMessage("Fields [name] are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        try {
            if (skill.getName() != null) {
                Skill createdSkill = skillService.createSkill(skill);
                response.setReturnCode(ReturnCodes.CREATE_OK.getCode());
                response.setMessage("New skill created!");
                response.setData(createdSkill);
            }
        } catch (Exception e) {
            response.setReturnCode(ReturnCodes.CREATE_ERROR.getCode());
            response.setMessage("Skill could not be created");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> updateSkill(@PathVariable Long id, @RequestBody Skill newSkill) {
        ApiResponse<Skill> response = new ApiResponse<>();

        if (!newSkill.isValid()) {
            response.setReturnCode(ReturnCodes.BAD_REQUEST.getCode());
            response.setMessage("Fields [name] are required");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Skill oldSkill = skillService.updateSkill(id, newSkill);

        if (oldSkill != null) {
            response.setReturnCode(ReturnCodes.UPDATE_OK.getCode());
            response.setMessage("Skill updated");
            response.setData(oldSkill);
        } else {
            response.setReturnCode(ReturnCodes.UPDATE_ERROR.getCode());
            response.setMessage("Skill could not be updated");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Skill>> deleteSkill(@PathVariable Long id) {
        ApiResponse<Skill> response = new ApiResponse<>();

        Skill dbSkill = skillService.deleteSkill(id);
        if (dbSkill != null) {
            response.setReturnCode(ReturnCodes.DELETE_OK.getCode());
            response.setMessage("Skill deleted");
            response.setData(dbSkill);
        } else {
            response.setReturnCode(ReturnCodes.DELETE_ERROR.getCode());
            response.setMessage("The skill does not exists or could not be deleted");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
