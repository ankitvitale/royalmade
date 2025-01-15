package com.royalmade.controller;

import com.royalmade.entity.RelievingLatter;
import com.royalmade.service.RelievingLatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RelievingLatterController {

    @Autowired
    private RelievingLatterService relievingLatterService;

    @PostMapping("/createRelievinglatter")
    @PreAuthorize("hasRole('Admin')")
    public RelievingLatter createRelievinglatter(@RequestBody RelievingLatter relievingLatter){
        return relievingLatterService.createRelievinglatter(relievingLatter);
    }

    @GetMapping("/getAllRelievingLatter")
    @PreAuthorize("hasRole('Admin')")

    public List<RelievingLatter> getAllRelievingLatter(){
        return relievingLatterService.getAllRelievingLatter();
    }

    @GetMapping("/getAllRelievingLatterbyid/{id}")
    @PreAuthorize("hasRole('Admin')")

    public RelievingLatter getAllRelievingLatterbyid(@PathVariable long id){
        return  relievingLatterService.getAllRelievingLatterbyid(id);
    }

    @PutMapping("/updateRelievingLatter/{id}")
    @PreAuthorize("hasRole('Admin')")
    public RelievingLatter updateRelievingLatter(@PathVariable Long id, @RequestBody RelievingLatter relievingLatter) {
        relievingLatter.setId(id);
        return relievingLatterService.updateRelievingLatter(relievingLatter);
    }
    @DeleteMapping("/deleteRelievingLatter/{id}")
    @PreAuthorize("hasRole('Admin')")

    public ResponseEntity<String> deleteRelievingLatter(@PathVariable Long id) {
        relievingLatterService.deleteRelievingLatter(id);
        return ResponseEntity.ok("Salary slip deleted successfully");
    }
}
