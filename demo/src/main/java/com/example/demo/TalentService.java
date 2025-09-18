package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TalentService {
    
    private final TalentDAO talentDAO;

    public TalentService(TalentDAO talentDAO) {
        this.talentDAO = talentDAO;
    }

    public List<Talent> getAllTalents() {
        return talentDAO.findAll();
    }

    public Talent getTalentById(Long id) {
        return talentDAO.findById(id);
    }

    public Talent createTalent(Talent talent) {
        return talentDAO.save(talent);
    }

    public Talent updateTalent(Long id, Talent talent) {
        if (talentDAO.existsById(id)) {
            return talentDAO.update(id, talent);
        }
        return null;
    }

    public void deleteTalent(Long id) {
        talentDAO.deleteById(id);
    }
}


