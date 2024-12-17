package com.royalmade.service;

import com.royalmade.entity.OfferLatter;
import com.royalmade.entity.RelievingLatter;
import com.royalmade.repo.OfferLatterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferLatterService {

    @Autowired
    private OfferLatterRepository offerLatterRepository;
    public OfferLatter createOffterLatter(OfferLatter offerLatter) {

       return offerLatterRepository.save(offerLatter);
    }

    public List<OfferLatter> getAllOfferlater() {
        return offerLatterRepository.findAll();
    }

    public OfferLatter getOfferlaterByid(Long id) {
        return offerLatterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
    }

    public OfferLatter updateOfferlatter( OfferLatter offerLatter) {
        OfferLatter offerLatter1=offerLatterRepository.findById(offerLatter.getId()).orElseThrow(()-> new RuntimeException("Offerlatter is is not found"+offerLatter.getId()));
      //  offerLatter1.setId(offerLatter.getId());
        offerLatter1.setName(offerLatter.getName());
        offerLatter1.setEmail(offerLatter.getEmail());
        offerLatter1.setAddress(offerLatter.getAddress());
        offerLatter1.setPhoneNumber(offerLatter.getPhoneNumber());
        offerLatter1.setCureentDate(offerLatter.getCureentDate());
        offerLatter1.setJoiningDate(offerLatter.getJoiningDate());
        offerLatter1.setPosition(offerLatter.getPosition());
        offerLatter1.setCtc(offerLatter.getCtc());
        return offerLatterRepository.save(offerLatter1);
    }

    public void deleteOfferlatter(Long id) {
        OfferLatter offerLatter1=offerLatterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer Latter not found with ID: " + id));
        offerLatterRepository.delete(offerLatter1);
    }
}
