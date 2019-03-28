package edu.netcracker.backend.controller;

import edu.netcracker.backend.message.response.SpaceportDTO;
import edu.netcracker.backend.model.Spaceport;
import edu.netcracker.backend.service.SpaceportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
public class SpaceportController {

    private final SpaceportService spaceportService;

    @Autowired
    public SpaceportController(SpaceportService spaceportService){
        this.spaceportService = spaceportService;
    }

    @GetMapping("spaceports")
    public List<SpaceportDTO> getAllPlanets(@RequestParam("planetId") Integer planetId){
        return spaceportService.findSpaceportsOfPlanet(planetId);
    }
}
