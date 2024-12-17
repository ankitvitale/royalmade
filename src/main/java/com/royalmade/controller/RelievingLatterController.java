package com.royalmade.controller;

import com.royalmade.entity.RelievingLatter;
import com.royalmade.service.RelievingLatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RelievingLatterController {

    @Autowired
    private RelievingLatterService relievingLatterService;

    @PostMapping("/createRelievinglatter")
    public RelievingLatter createRelievinglatter(@RequestBody RelievingLatter relievingLatter){
        return relievingLatterService.createRelievinglatter(relievingLatter);
    }

    @GetMapping("/getAllRelievingLatter")
    public List<RelievingLatter> getAllRelievingLatter(){
        return relievingLatterService.getAllRelievingLatter();
    }

    @GetMapping("/getAllRelievingLatterbyid/{id}")
    public RelievingLatter getAllRelievingLatterbyid(@PathVariable long id){
        return  relievingLatterService.getAllRelievingLatterbyid(id);
    }

    @PutMapping("/updateRelievingLatter/{id}")
   public RelievingLatter updateRelievingLatter(@PathVariable Long id,RelievingLatter relievingLatter){
        relievingLatter.setId(id);
        RelievingLatter updateRelievingLatter=relievingLatterService.updateRelievingLatter(relievingLatter);
        return updateRelievingLatter;
   }
    @DeleteMapping("/deleteRelievingLatter/{id}")
    public ResponseEntity<String> deleteRelievingLatter(@PathVariable Long id) {
        relievingLatterService.deleteRelievingLatter(id);
        return ResponseEntity.ok("Salary slip deleted successfully");
    }
}
