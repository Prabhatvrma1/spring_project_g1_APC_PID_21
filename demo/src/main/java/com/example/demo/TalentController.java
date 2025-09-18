package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/talents")
@CrossOrigin(origins = "*")
public class TalentController {
    
    private final TalentService talentService;

    public TalentController(TalentService talentService) {
        this.talentService = talentService;
    }

    // Get all talents
    @GetMapping
    public List<Talent> getAllTalents() {
        return talentService.getAllTalents();
    }

    // Get talent by ID
    @GetMapping("/{id}")
    public Talent getTalent(@PathVariable Long id) {
        return talentService.getTalentById(id);
    }

    // Create new talent
    @PostMapping
    public Talent createTalent(@RequestBody Talent talent) {
        return talentService.createTalent(talent);
    }

    // Update talent
    @PutMapping("/{id}")
    public Talent updateTalent(@PathVariable Long id, @RequestBody Talent talent) {
        return talentService.updateTalent(id, talent);
    }

    // Delete talent
    @DeleteMapping("/{id}")
    public void deleteTalent(@PathVariable Long id) {
        talentService.deleteTalent(id);
    }
}
