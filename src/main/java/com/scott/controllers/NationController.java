package com.scott.controllers;

import com.scott.models.Nation;
import com.scott.repositories.NationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nations")
@CrossOrigin(origins="http://localhost:3000")
public class NationController {
    @Autowired
    NationRepository nationRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public Nation createNation(@RequestBody Nation nation){
        return nationRepository.save(nation);
    }
    @GetMapping
    public List<Nation> findAllNations(){
        return nationRepository.findAll();
    }
    @GetMapping("/{nationId}")
    public Nation findNationById(@PathVariable Long nationId){
        return nationRepository.findById(nationId).orElseThrow(()->new RuntimeException("Nation has not been found"));
    }

    @PutMapping("/{nationId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Nation updateNation(@PathVariable Long nationId, @RequestBody Nation nation){
        Nation updateN=this.findNationById(nationId);
        updateN.setName(nation.getName());
        //updateN.setPeople(nation.getPeople());
        //updateN.setCompetitions(nation.getCompetitions());
        //updateN.setNationalTeams(nation.getNationalTeams());
        return nationRepository.save(updateN);
    }

    @DeleteMapping("/{nationId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteNation(@PathVariable Long nationId){
        nationRepository.deleteById(nationId);
    }


}
