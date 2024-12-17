package com.royalmade.controller;

import com.royalmade.entity.OfferLatter;
import com.royalmade.service.OfferLatterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OfferLatterController {

    @Autowired
    private OfferLatterService offerLatterService;

    @PostMapping("/createOfferLatter")
    public OfferLatter createOffterLatter(@RequestBody OfferLatter offerLatter){
        return offerLatterService.createOffterLatter(offerLatter);
    }

    @GetMapping("/getOfferlatter")
    public List<OfferLatter> getAllOfferlater(){

        return offerLatterService.getAllOfferlater();

    }
    @GetMapping("/singleOfferlatter/{id}")
    public OfferLatter getOfferlaterbyid(@PathVariable Long id){
        return offerLatterService.getOfferlaterByid(id);
    }

    @PutMapping("/updateOfferlatter/{id}")
    public OfferLatter updateOfferlatter(@PathVariable Long id,@RequestBody OfferLatter offerLatter){
        offerLatter.setId(id);
          OfferLatter updateOfferlatter= offerLatterService.updateOfferlatter(offerLatter);
        return  updateOfferlatter;
    }

    @DeleteMapping("/deleteOfferlatter/{id}")
    public ResponseEntity<String> deleteOfferlatter(@PathVariable Long id) {
        offerLatterService.deleteOfferlatter(id);
        return ResponseEntity.ok("delete Offer latter successfully");
    }
}