package com.royalmade.service;

import com.royalmade.dto.LandInfoDto;
import com.royalmade.dto.LandRequestDto;
import com.royalmade.dto.LandResponseDto;
import com.royalmade.entity.Address;
import com.royalmade.entity.Land;
import com.royalmade.entity.Partner;
import com.royalmade.entity.Person;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.mapper.LandMapper;
import com.royalmade.repo.AddressRepository;
import com.royalmade.repo.LandRepository;
import com.royalmade.repo.PartnerRepository;
import com.royalmade.repo.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LandService {
    @Autowired
    private LandRepository landRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private LandMapper landMapper;

    public Land createLand(LandRequestDto landRequestDto) {
        // Map DTO to Entity
        Address address = landRequestDto.getAddress();
        Person person = landRequestDto.getPurchaser();
        Person person1=landRequestDto.getOwner();
        Set<Partner> partners = landRequestDto.getPartners();

        // Save related entities
        addressRepository.save(address);
        personRepository.save(person);
        personRepository.save(person1);
        partnerRepository.saveAll(partners);
        double soldAmount = landRequestDto.getTotalAmount() - (landRequestDto.getAgreementAmount() + landRequestDto.getTokenAmount());

        // Create and save Land entity
        Land land = landMapper.toLand( landRequestDto);
        land.setAddress(address);
        land.setOwner(person1);
        land.setPurchaser(person);
        land.setPartners(partners);

        return landRepository.save(land);
    }
    public List<Land> getAllLands() {

        return landRepository.findAll();
    }
    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Land not found with id: " + id));
    }


//    @Transactional
    public Land updateLand(Long id, LandRequestDto landRequestDto) {
        // Fetch the existing Land entity
        Land existingLand = landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Land not found with ID: " + id));
        // Update Address
        Address updatedAddress = landRequestDto.getAddress();
        if (updatedAddress != null) {
            if (updatedAddress.getId() != null) {
                // Existing address, attach it to the persistence context
                Address existingAddress = addressRepository.findById(updatedAddress.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + updatedAddress.getId()));
                existingAddress.setLandmark(updatedAddress.getLandmark() != null ? updatedAddress.getLandmark() : existingAddress.getLandmark());
                existingAddress.setPincode(updatedAddress.getPincode() != null ? updatedAddress.getPincode() : existingAddress.getPincode());
                existingAddress.setCity(updatedAddress.getCity() != null ? updatedAddress.getCity() : existingAddress.getCity());
                existingAddress.setState(updatedAddress.getState() != null ? updatedAddress.getState() : existingAddress.getState());
                existingAddress.setCountry(updatedAddress.getCountry() != null ? updatedAddress.getCountry() : existingAddress.getCountry());
                addressRepository.save(existingAddress);
                existingLand.setAddress(existingAddress); // Associate with the updated address
            } else {
                // New address, save it
                addressRepository.save(updatedAddress);
                existingLand.setAddress(updatedAddress);
            }
        }
// Update Purchaser
        Person updatedPurchaser = landRequestDto.getPurchaser();
        if (updatedPurchaser != null) {
            updatedPurchaser.setId(existingLand.getPurchaser().getId()); // Retain existing ID
            updatedPurchaser.setName(updatedPurchaser.getName() != null ? updatedPurchaser.getName() : existingLand.getPurchaser().getName());
            updatedPurchaser.setEmail(updatedPurchaser.getEmail() != null ? updatedPurchaser.getEmail() : existingLand.getPurchaser().getEmail());
            updatedPurchaser.setPhoneNumber(updatedPurchaser.getPhoneNumber() != null ? updatedPurchaser.getPhoneNumber() : existingLand.getPurchaser().getPhoneNumber());
            personRepository.save(updatedPurchaser);  // You can use merge() instead of save() if it's detached
            existingLand.setPurchaser(updatedPurchaser);
        }

// Update Owner
        Person updatedOwner = landRequestDto.getOwner();
        if (updatedOwner != null) {
            updatedOwner.setId(existingLand.getOwner().getId()); // Retain existing ID
            updatedOwner.setName(updatedOwner.getName() != null ? updatedOwner.getName() : existingLand.getOwner().getName());
            updatedOwner.setEmail(updatedOwner.getEmail() != null ? updatedOwner.getEmail() : existingLand.getOwner().getEmail());
            updatedOwner.setPhoneNumber(updatedOwner.getPhoneNumber() != null ? updatedOwner.getPhoneNumber() : existingLand.getOwner().getPhoneNumber());
            personRepository.save(updatedOwner);  // Use merge() instead of save() if necessary
            existingLand.setOwner(updatedOwner);
        }


// Update Partners
        Set<Partner> updatedPartners = landRequestDto.getPartners();
        if (updatedPartners != null && !updatedPartners.isEmpty()) {
            // Clear existing partners and replace with new ones
            partnerRepository.deleteAll(existingLand.getPartners());
            updatedPartners.forEach(partner -> {
                if (partner.getId() == null) {
                    // New Partner: Save directly
                    partnerRepository.save(partner);
                } else {
                    // Existing Partner: Update fields
                    Partner existingPartner = partnerRepository.findById(partner.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Partner not found with ID: " + partner.getId()));
                    existingPartner.setName(partner.getName() != null ? partner.getName() : existingPartner.getName());
                    existingPartner.setEmail(partner.getEmail() != null ? partner.getEmail() : existingPartner.getEmail());
                    existingPartner.setPhoneNumber(partner.getPhoneNumber() != null ? partner.getPhoneNumber() : existingPartner.getPhoneNumber());
                    existingPartner.setAmount(partner.getAmount() != null ? partner.getAmount() : existingPartner.getAmount());
                    partnerRepository.save(existingPartner);
                }
            });
            existingLand.setPartners(updatedPartners);
        }


//        // Update other fields of the Land entity
        existingLand.setArea(landRequestDto.getArea());
        existingLand.setTokenAmount(landRequestDto.getTokenAmount());
        existingLand.setAgreementAmount(landRequestDto.getAgreementAmount());
        existingLand.setTotalAmount(landRequestDto.getTotalAmount());
//
        // Calculate and set soldAmount
        double soldAmount = landRequestDto.getTotalAmount() -
                (landRequestDto.getAgreementAmount() + landRequestDto.getTokenAmount());
       // existingLand.setSoldAmount(soldAmount);

        // Save and return the updated Land entity
        return landRepository.save(existingLand);
    }
    public void deleteLand(Long id) {
        // Fetch the existing Land entity by ID
        Land existingLand = landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Land not found with ID: " + id));

        // Remove all relationships to avoid constraint violations
        existingLand.setAddress(null);
        existingLand.setPurchaser(null);
        existingLand.setOwner(null);
        existingLand.getPartners().clear();

        // Save the updated Land entity to detach relationships
        landRepository.save(existingLand);

        // Now delete the Land entity
        landRepository.delete(existingLand);
    }

}
