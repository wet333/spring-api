package online.awet.springapi.features.resume.controllers;

import jakarta.servlet.http.HttpServletRequest;
import online.awet.springapi.core.ApiResponse;
import online.awet.springapi.core.ReturnCodes;
import online.awet.springapi.core.exceptions.types.InvalidRequestDataException;
import online.awet.springapi.features.resume.models.Skill;
import online.awet.springapi.features.resume.services.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/resume/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getAllSkills() {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        List<Skill> skills = skillService.getAllSkills();
        data.put("skills", skills);

        if (skills != null) {
            String message = skills.isEmpty() ? "Skill list is empty" : "Skill list";
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage(message);
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Could not get the skill list");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> getSkillById(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Skill dbSkill = skillService.getSkill(id);
        data.put("skill", dbSkill);

        if (dbSkill != null) {
            response.setReturnCode(ReturnCodes.OK.getCode());
            response.setMessage("Skill retrieved");
            response.setData(data);
        } else  {
            response.setReturnCode(ReturnCodes.RESOURCE_NOT_FOUND.getCode());
            response.setMessage("Skill does not exists");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> createSkill(
            @RequestBody Skill skill, HttpServletRequest request
    ) throws InvalidRequestDataException {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        if (!skill.isValid()) {
            throw new InvalidRequestDataException("Fields [name] are required", request);
        }

        Skill createdSkill = skillService.createSkill(skill);
        data.put("skill", createdSkill);

        if (skill.getName() != null) {
            response.setReturnCode(ReturnCodes.CREATE_OK.getCode());
            response.setMessage("New skill created!");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.CREATE_ERROR.getCode());
            response.setMessage("Skill could not be created");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> updateSkill(
            @PathVariable Long id, @RequestBody Skill newSkill, HttpServletRequest request
    ) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Skill oldSkill = skillService.updateSkill(id, newSkill);
        data.put("skill", oldSkill);

        if (oldSkill != null) {
            response.setReturnCode(ReturnCodes.UPDATE_OK.getCode());
            response.setMessage("Skill updated");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.UPDATE_ERROR.getCode());
            response.setMessage("Skill could not be updated");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable Long id) {
        ApiResponse response = new ApiResponse();
        Map<String, Object> data = new HashMap<>();

        Skill dbSkill = skillService.deleteSkill(id);
        data.put("skill", dbSkill);

        if (dbSkill != null) {
            response.setReturnCode(ReturnCodes.DELETE_OK.getCode());
            response.setMessage("Skill deleted");
            response.setData(data);
        } else {
            response.setReturnCode(ReturnCodes.DELETE_ERROR.getCode());
            response.setMessage("The skill does not exists or could not be deleted");
            response.setData(null);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
