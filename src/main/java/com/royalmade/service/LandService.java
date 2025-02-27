package com.royalmade.service;

import com.royalmade.dto.*;
import com.royalmade.entity.*;
import com.royalmade.entity.enumeration.TransactionChange;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.mapper.LandMapper;
import com.royalmade.repo.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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
    private LandTransactionRepository landTransactionRepository;

    @Autowired
    private LandMapper landMapper;



    private static final Logger logger = LoggerFactory.getLogger(LandService.class);

    public Land createLand(LandDto landRequestDto) {
        logger.info("Received request to create land: {}", landRequestDto);

        if (landRequestDto.getAddress() == null) {
            logger.error("Address information is missing.");
            throw new IllegalArgumentException("Address information is required.");
        }

        if (landRequestDto.getPurchaser() == null) {
            logger.error("Purchaser information is missing.");
            throw new IllegalArgumentException("Purchaser information is required.");
        }

        // Convert Address DTO to Entity
        Address address = new Address();
        address.setLandmark(landRequestDto.getAddress().getLandmark());
        address.setPincode(landRequestDto.getAddress().getPincode());
        address.setCity(landRequestDto.getAddress().getCity());
        address.setCountry(landRequestDto.getAddress().getCountry());
        address.setState(landRequestDto.getAddress().getState());
        address.setMuza(landRequestDto.getAddress().getMuza());
        address.setKhno(landRequestDto.getAddress().getKhno());
        address.setPlotno(landRequestDto.getAddress().getPlotno());
        address.setPhno(landRequestDto.getAddress().getPhno());

        // Convert Purchaser DTO to Entity and Save
        PurchaserDto purchaserDto = landRequestDto.getPurchaser();
        Person purchaser = new Person();
        purchaser.setName(purchaserDto.getName());
        purchaser.setAddress(purchaserDto.getAddress());
        purchaser.setPhoneNumber(purchaserDto.getPhoneNumber());
        purchaser.setAadharNumber(purchaserDto.getAadharNumber());

        personRepository.save(purchaser); // Save Purchaser before assigning it to Land

        // Convert Owner DTO to Entity and Save
        OwnerDto ownerDto = landRequestDto.getOwner();
        Person owner = new Person();
        owner.setName(ownerDto.getName());
        owner.setAddress(ownerDto.getAddress());
        owner.setPhoneNumber(ownerDto.getPhoneNumber());
        owner.setAadharNumber(ownerDto.getAadharNumber());

        personRepository.save(owner); // Save Owner before assigning it to Land

        // Convert Partners DTOs to Partner Entities and Save
        Set<Partner> partners = new HashSet<>();
        if (landRequestDto.getPartners() != null) {
            partners = landRequestDto.getPartners().stream()
                    .map(partnerDto -> {
                        Partner partner = new Partner();
                        partner.setName(partnerDto.getName());
                        partner.setCity(partnerDto.getCity());
                        partner.setPhoneNumber(partnerDto.getPhoneNumber());
                        partner.setAmount(partnerDto.getAmount());
                        partner.setPaymentDate(LocalDate.parse(partnerDto.getPaymentDate()));
                        return partner;
                    })
                    .collect(Collectors.toSet());

            partnerRepository.saveAll(partners); // Save partners before assigning to Land
        }

        // Save Address before assigning to Land
        addressRepository.save(address);

        // Calculate sold amount safely
        double totalAmount = landRequestDto.getTotalAmount() != null ? landRequestDto.getTotalAmount() : 0;
        double agreementAmount = landRequestDto.getAgreementAmount() != null ? landRequestDto.getAgreementAmount() : 0;
        double tokenAmount = landRequestDto.getTokenAmount() != null ? landRequestDto.getTokenAmount() : 0;
        double soldAmount = totalAmount - (agreementAmount + tokenAmount);

        // Create and populate Land entity
        Land land = new Land();
        land.setArea(landRequestDto.getArea());
        land.setTokenAmount(landRequestDto.getTokenAmount());
        land.setAgreementAmount(landRequestDto.getAgreementAmount());
        land.setTotalAmount(landRequestDto.getTotalAmount());
        land.setAddress(address);
        land.setOwner(owner); // Assign saved owner
        land.setPurchaser(purchaser); // Assign saved purchaser
        land.setPartners(partners); // Assign saved partners

        // Save Land entity
        Land savedLand = landRepository.save(land);
        logger.info("Land saved successfully: {}", savedLand);

        return savedLand;
    }


//    public Land createLand(LandDto landRequestDto) {
//        logger.info("Received request to create land: {}", landRequestDto);
//
//        if (landRequestDto.getAddress() == null) {
//            logger.error("Address information is missing.");
//            throw new IllegalArgumentException("Address information is required.");
//        }
//
//        if (landRequestDto.getPurchaser() == null) {
//            logger.error("Purchaser information is missing.");
//            throw new IllegalArgumentException("Purchaser information is required.");
//        }
//
//        // Convert Address DTO to Entity
//        Address address = new Address();
//        address.setLandmark(landRequestDto.getAddress().getLandmark());
//        address.setPincode(landRequestDto.getAddress().getPincode());
//        address.setCity(landRequestDto.getAddress().getCity());
//        address.setCountry(landRequestDto.getAddress().getCountry());
//        address.setState(landRequestDto.getAddress().getState());
//        address.setMuza(landRequestDto.getAddress().getMuza());
//        address.setKhno(landRequestDto.getAddress().getKhno());
//        address.setPlotno(landRequestDto.getAddress().getPlotno());
//        address.setPhno(landRequestDto.getAddress().getPhno());
//
//        // Convert Purchaser DTO to Entity
//        PurchaserDto purchaserDto = landRequestDto.getPurchaser();
//        Person purchaser = new Person();
//        purchaser.setName(purchaserDto.getName());
//        purchaser.setAddress(purchaserDto.getAddress());
//        purchaser.setPhoneNumber(purchaserDto.getPhoneNumber());
//        purchaser.setAadharNumber(purchaserDto.getAadharNumber());
//
//        OwnerDto ownerDto = landRequestDto.getOwner();
//        Person owner = new Person();
//        owner.setName(ownerDto.getName());
//        owner.setAddress(ownerDto.getAddress());
//        owner.setPhoneNumber(ownerDto.getPhoneNumber());
//        owner.setAadharNumber(ownerDto.getAadharNumber());
//
//        // Convert Partners DTOs to Partner Entities (Ensure list is not null)
//        Set<Partner> partners = new HashSet<>();
//        if (landRequestDto.getPartners() != null) {
//            partners = landRequestDto.getPartners().stream()
//                    .map(partnerDto -> {
//                        Partner partner = new Partner();
//                        partner.setName(partnerDto.getName());
//                        partner.setCity(partnerDto.getCity());
//                        partner.setPhoneNumber(partnerDto.getPhoneNumber());
//                        partner.setAmount(partnerDto.getAmount());
//                        partner.setPaymentDate(LocalDate.parse(partnerDto.getPaymentDate()));
//                        return partner;
//                    })
//                    .collect(Collectors.toSet());
//        }
//
//        // Save related entities
//        addressRepository.save(address);
//        personRepository.save(purchaser);
//        partnerRepository.saveAll(partners);
//
//        // Calculate sold amount safely
//        double totalAmount = landRequestDto.getTotalAmount() != null ? landRequestDto.getTotalAmount() : 0;
//        double agreementAmount = landRequestDto.getAgreementAmount() != null ? landRequestDto.getAgreementAmount() : 0;
//        double tokenAmount = landRequestDto.getTokenAmount() != null ? landRequestDto.getTokenAmount() : 0;
//        double soldAmount = totalAmount - (agreementAmount + tokenAmount);
//
//        // Create and populate Land entity
//        Land land = new Land();
//        land.setArea(landRequestDto.getArea());
//        land.setTokenAmount(landRequestDto.getTokenAmount());
//        land.setAgreementAmount(landRequestDto.getAgreementAmount());
//        land.setTotalAmount(landRequestDto.getTotalAmount());
//        land.setAddress(address);
//        land.setOwner(owner);
//        land.setPurchaser(purchaser);
//        land.setPartners(partners);
//
//        // Save Land entity
//        Land savedLand = landRepository.save(land);
//        logger.info("Land saved successfully: {}", savedLand);
//
//        return savedLand;
//    }



//    public Land createLand(LandRequestDto landRequestDto) {
//        // Map DTO to Entity
//        Address address = landRequestDto.getAddress();
//        Person person = landRequestDto.getPurchaser();
//        Person person1 = landRequestDto.getOwner();
//        Set<Partner> partners = landRequestDto.getPartners();
//
//        // Save related entities
//        addressRepository.save(address);
//        personRepository.save(person);
//        personRepository.save(person1);
//        partnerRepository.saveAll(partners);
//        double soldAmount = landRequestDto.getTotalAmount() - (landRequestDto.getAgreementAmount() + landRequestDto.getTokenAmount());
//
//        // Create and save Land entity
//        Land land = landMapper.toLand(landRequestDto);
//        land.setAddress(address);
//        land.setOwner(person1);
//        land.setPurchaser(person);
//        land.setPartners(partners);
//
//        return landRepository.save(land);
//    }

    public List<Land> getAllLands() {

        return landRepository.findAll();
    }

    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Land not found with id: " + id));
    }


    //    @Transactional
//    public Land updateLand(Long id, LandRequestDto landRequestDto) {
//        // Fetch the existing Land entity
//        Land existingLand = landRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Land not found with ID: " + id));
//        // Update Address
//        Address updatedAddress = landRequestDto.getAddress();
//        if (updatedAddress != null) {
//            if (updatedAddress.getId() != null) {
//                // Existing address, attach it to the persistence context
//                Address existingAddress = addressRepository.findById(updatedAddress.getId())
//                        .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + updatedAddress.getId()));
//                existingAddress.setLandmark(updatedAddress.getLandmark() != null ? updatedAddress.getLandmark() : existingAddress.getLandmark());
//                existingAddress.setPincode(updatedAddress.getPincode() != null ? updatedAddress.getPincode() : existingAddress.getPincode());
//                existingAddress.setCity(updatedAddress.getCity() != null ? updatedAddress.getCity() : existingAddress.getCity());
//                existingAddress.setState(updatedAddress.getState() != null ? updatedAddress.getState() : existingAddress.getState());
//                existingAddress.setCountry(updatedAddress.getCountry() != null ? updatedAddress.getCountry() : existingAddress.getCountry());
//                existingAddress.setMuza(updatedAddress.getMuza() != null ? updatedAddress.getMuza() : existingAddress.getMuza());
//                existingAddress.setKhno(updatedAddress.getKhno() != null ? updatedAddress.getKhno() : existingAddress.getKhno());
//                existingAddress.setPhno(updatedAddress.getPhno() != null ? updatedAddress.getPhno() : existingAddress.getPhno());
//                existingAddress.setPlotno(updatedAddress.getPlotno() != null ? updatedAddress.getPlotno() : existingAddress.getPlotno());
//                addressRepository.save(existingAddress);
//                existingLand.setAddress(existingAddress); // Associate with the updated address
//            } else {
//                // New address, save it
//                addressRepository.save(updatedAddress);
//                existingLand.setAddress(updatedAddress);
//            }
//        }
//        // Update Purchaser
//        Person updatedPurchaser = landRequestDto.getPurchaser();
//        if (updatedPurchaser != null) {
//            updatedPurchaser.setId(existingLand.getPurchaser().getId()); // Retain existing ID
//            updatedPurchaser.setName(updatedPurchaser.getName() != null ? updatedPurchaser.getName() : existingLand.getPurchaser().getName());
//            updatedPurchaser.setAddress(updatedPurchaser.getAddress() != null ? updatedPurchaser.getAddress() : existingLand.getPurchaser().getAddress());
//            updatedPurchaser.setPhoneNumber(updatedPurchaser.getPhoneNumber() != null ? updatedPurchaser.getPhoneNumber() : existingLand.getPurchaser().getPhoneNumber());
//            personRepository.save(updatedPurchaser);  // You can use merge() instead of save() if it's detached
//            existingLand.setPurchaser(updatedPurchaser);
//        }
//
//// Update Owner
//        Person updatedOwner = landRequestDto.getOwner();
//        if (updatedOwner != null) {
//            updatedOwner.setId(existingLand.getOwner().getId()); // Retain existing ID
//            updatedOwner.setName(updatedOwner.getName() != null ? updatedOwner.getName() : existingLand.getOwner().getName());
//            updatedOwner.setAddress(updatedOwner.getAddress() != null ? updatedOwner.getAddress() : existingLand.getOwner().getAddress());
//            updatedOwner.setPhoneNumber(updatedOwner.getPhoneNumber() != null ? updatedOwner.getPhoneNumber() : existingLand.getOwner().getPhoneNumber());
//            personRepository.save(updatedOwner);  // Use merge() instead of save() if necessary
//            existingLand.setOwner(updatedOwner);
//        }
//
//
//// Update Partners
//        Set<Partner> updatedPartners = landRequestDto.getPartners();
//        if (updatedPartners != null && !updatedPartners.isEmpty()) {
//            // Clear existing partners and replace with new ones
//            partnerRepository.deleteAll(existingLand.getPartners());
//            updatedPartners.forEach(partner -> {
//                if (partner.getId() == null) {
//                    // New Partner: Save directly
//                    partnerRepository.save(partner);
//                } else {
//                    // Existing Partner: Update fields
//                    Partner existingPartner = partnerRepository.findById(partner.getId())
//                            .orElseThrow(() -> new ResourceNotFoundException("Partner not found with ID: " + partner.getId()));
//                    existingPartner.setName(partner.getName() != null ? partner.getName() : existingPartner.getName());
//                    existingPartner.setCity(partner.getCity() != null ? partner.getCity() : existingPartner.getCity());
//                    existingPartner.setPhoneNumber(partner.getPhoneNumber() != null ? partner.getPhoneNumber() : existingPartner.getPhoneNumber());
//               //     existingPartner.setAmount(partner.getAmount() != null ? partner.getAmount() : existingPartner.getAmount());
//                    existingPartner.setPaymentDate(partner.getPaymentDate() != null ? partner.getPaymentDate() : existingPartner.getPaymentDate());
//                    partnerRepository.save(existingPartner);
//                }
//            });
//            existingLand.setPartners(updatedPartners);
//        }
//
//
////        // Update other fields of the Land entity
//        existingLand.setArea(landRequestDto.getArea());
//        existingLand.setTokenAmount(landRequestDto.getTokenAmount());
//        existingLand.setAgreementAmount(landRequestDto.getAgreementAmount());
//        existingLand.setTotalAmount(landRequestDto.getTotalAmount());
////
//        // Calculate and set soldAmount
//        double soldAmount = landRequestDto.getTotalAmount() -
//                (landRequestDto.getAgreementAmount() + landRequestDto.getTokenAmount());
//        // existingLand.setSoldAmount(soldAmount);
//
//        // Save and return the updated Land entity
//        return landRepository.save(existingLand);
//    }


    public Land updateLand(Long id, LandDto landRequestDto) {
        logger.info("Received request to update land with ID: {}", id);

        // Fetch existing land record
        Land existingLand = landRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with ID: " + id));

        // Update Address
        Address address = existingLand.getAddress();
        if (landRequestDto.getAddress() != null) {
            address.setLandmark(landRequestDto.getAddress().getLandmark());
            address.setPincode(landRequestDto.getAddress().getPincode());
            address.setCity(landRequestDto.getAddress().getCity());
            address.setCountry(landRequestDto.getAddress().getCountry());
            address.setState(landRequestDto.getAddress().getState());
            address.setMuza(landRequestDto.getAddress().getMuza());
            address.setKhno(landRequestDto.getAddress().getKhno());
            address.setPlotno(landRequestDto.getAddress().getPlotno());
            address.setPhno(landRequestDto.getAddress().getPhno());
            addressRepository.save(address);
        }

        // Update Purchaser
        if (landRequestDto.getPurchaser() != null) {
            PurchaserDto purchaserDto = landRequestDto.getPurchaser();
            Person purchaser = existingLand.getPurchaser();
            purchaser.setName(purchaserDto.getName());
            purchaser.setAddress(purchaserDto.getAddress());
            purchaser.setPhoneNumber(purchaserDto.getPhoneNumber());
            purchaser.setAadharNumber(purchaserDto.getAadharNumber());
            personRepository.save(purchaser);
        }

        // Update Partners
        if (landRequestDto.getPartners() != null) {
            Set<Partner> updatedPartners = landRequestDto.getPartners().stream()
                    .map(partnerDto -> {
                        Partner partner = new Partner();
                        partner.setName(partnerDto.getName());
                        partner.setCity(partnerDto.getCity());
                        partner.setPhoneNumber(partnerDto.getPhoneNumber());
                        partner.setAmount(partnerDto.getAmount());
                        partner.setPaymentDate(LocalDate.parse(partnerDto.getPaymentDate()));
                        return partner;
                    })
                    .collect(Collectors.toSet());

            // Remove old partners and add new ones
            partnerRepository.deleteAll(existingLand.getPartners());
            partnerRepository.saveAll(updatedPartners);
            existingLand.setPartners(updatedPartners);
        }

        // Update Land entity
        existingLand.setArea(landRequestDto.getArea());
        existingLand.setTokenAmount(landRequestDto.getTokenAmount());
        existingLand.setAgreementAmount(landRequestDto.getAgreementAmount());
        existingLand.setTotalAmount(landRequestDto.getTotalAmount());

        // Save updated entity
        Land savedLand = landRepository.save(existingLand);
        logger.info("Land updated successfully: {}", savedLand);

        return savedLand;
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

//    public Partner addPartnerPayment(PartnerpaymnetDto partnerpaymnetDto, Long id) {
//        Land land = landRepository.findById(id).orElseThrow(() -> new RuntimeException("land " + id + "not found"));
//        Partner partner = new Partner();
//        partner.setName(partnerpaymnetDto.getName());
//        partner.setCity(partnerpaymnetDto.getCity());
//        partner.setPhoneNumber(partnerpaymnetDto.getPhoneNumber());
//       // partner.setAmount(partnerpaymnetDto.getAmount());
//        partner.setPaymentDate(partnerpaymnetDto.getPaymentDate());
//        partner.setLand(land);
//        return partnerRepository.save(partner);
//    }

    public LandTransaction addPayment(LandTransactionDto landTransactionDto, Long partnerId) {
        // Fetch Partner by ID
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new RuntimeException("Partner not found"));
//        // Create LandTransaction
        LandTransaction landTransaction = new LandTransaction();
        landTransaction.setTransactionDate(landTransactionDto.getTransactionDate());
        landTransaction.setTransactionAmount(landTransactionDto.getTransactionAmount());
        landTransaction.setNote(landTransactionDto.getNote());
        landTransaction.setChange(landTransactionDto.getChange());
        landTransaction.setMadeBy(landTransactionDto.getMadeBy());
        landTransaction.setStatus(landTransactionDto.getStatus());
        landTransaction.setPartner(partner);
        //   landTransaction.setPerson(person);

        // Save and return
        return landTransactionRepository.save(landTransaction);
    }


//    public List<PartnerWithTransactionsDto> getPartnerTransactions(Long landId) {
//        // Fetch all partners for the given land ID
//        List<Partner> partners = partnerRepository.findAllByLandId(landId);
//
//        // Fetch all transactions associated with the land ID
//        List<LandTransaction> landTransactions = landTransactionRepository.findAllByPartnerLandId(landId);
//
//        // Create a map to hold partner transactions
//        Map<Long, PartnerWithTransactionsDto> partnerTransactions = new HashMap<>();
//
//        // Map each partner, including those without transactions
//        for (Partner partner : partners) {
//            PartnerWithTransactionsDto partnerWithTransactionsDto = new PartnerWithTransactionsDto();
//            partnerWithTransactionsDto.setId(partner.getId());
//            partnerWithTransactionsDto.setName(partner.getName());
//            partnerWithTransactionsDto.setCity(partner.getCity());
//            partnerWithTransactionsDto.setPhoneNumber(partner.getPhoneNumber());
////            partnerWithTransactionsDto.setAmount(partner.getAmount()); // Default amount
//            partnerWithTransactionsDto.setAmount(Double.parseDouble(partner.getAmount()));
//            partnerWithTransactionsDto.setPaymentDate(partner.getPaymentDate() != null ? partner.getPaymentDate().toString() : null);
//            partnerWithTransactionsDto.setLandTransactions(new ArrayList<>()); // Initialize empty transaction list
//            partnerWithTransactionsDto.setTotal(0.0); // Default total
//            partnerTransactions.put(partner.getId(), partnerWithTransactionsDto);
//        }
//
//        // Process transactions and update the corresponding partner DTOs
//        for (LandTransaction transaction : landTransactions) {
//            Partner partner = transaction.getPartner();
//            PartnerWithTransactionsDto currentPartnerDto = partnerTransactions.get(partner.getId());
//
////            // Calculate the amount based on transaction change type
////            double amountToChange = transaction.getChange() == TransactionChange.DEBIT
////                    ? -transaction.getTransactionAmount() // Subtract amount for DEBIT
////                    : transaction.getTransactionAmount(); // Add amount for CREDIT
////
////            currentPartnerDto.setAmount(currentPartnerDto.getAmount() + amountToChange); // Update partner's amount
//
//            // Map transaction details
//            LandTransactionDto txDto = new LandTransactionDto();
//            txDto.setId(transaction.getId());
//            txDto.setTransactionDate(transaction.getTransactionDate());
//            txDto.setTransactionAmount(transaction.getTransactionAmount());
//            txDto.setNote(transaction.getNote());
//            txDto.setChange(transaction.getChange());
//            txDto.setMadeBy(transaction.getMadeBy());
//            txDto.setStatus(transaction.getStatus());
//            currentPartnerDto.getLandTransactions().add(txDto);
//
//
//
//            // Update total transaction amount by considering DEBIT and CREDIT transactions properly
//            double transactionTotal = currentPartnerDto.getLandTransactions().stream()
//                    .mapToDouble(tx -> tx.getChange() == TransactionChange.DEBIT
//                            ? -tx.getTransactionAmount() // Subtract for DEBIT
//                            : tx.getTransactionAmount()) // Add for CREDIT
//                    .sum();
//
//            // Set total by adding the amount and total of transactions
//            currentPartnerDto.setTotal(transactionTotal);
//        }

 //       return partnerTransactions.values().stream().toList();
 //   }
 public List<PartnerWithTransactionsDto> getPartnerTransactions(Long landId) {
     List<LandTransaction> landTransactions = landTransactionRepository.findAllByPartnerLandId(landId);

     Map<Long, PartnerWithTransactionsDto> partnerTransactions = new HashMap<>();
     for (LandTransaction transaction : landTransactions) {
         Partner partner = transaction.getPartner();
         if (!partnerTransactions.containsKey(partner.getId())) {
             PartnerWithTransactionsDto partnerWithTransactionsDto = new PartnerWithTransactionsDto();
             partnerWithTransactionsDto.setId(partner.getId());
             partnerWithTransactionsDto.setName(partner.getName());
             partnerWithTransactionsDto.setCity(partner.getCity());
             partnerWithTransactionsDto.setPhoneNumber(partner.getPhoneNumber());
             partnerWithTransactionsDto.setAmount(Double.parseDouble(partner.getAmount()));
             partnerWithTransactionsDto.setPaymentDate(partner.getPaymentDate().toString());
             partnerWithTransactionsDto.setLandTransactions(new ArrayList<>());
             partnerWithTransactionsDto.setTotal(0.0); // Initialize the total field

             partnerTransactions.put(partner.getId(), partnerWithTransactionsDto);
         }

         double amountToChange = transaction.getChange() == TransactionChange.DEBIT ? transaction.getTransactionAmount() * -1 : transaction.getTransactionAmount();
         PartnerWithTransactionsDto currentPartnerDto = partnerTransactions.get(partner.getId());

         // Update the amount
   //      currentPartnerDto.setAmount(currentPartnerDto.getAmount() + amountToChange);

         // Add transaction details
         LandTransactionDto txDto = new LandTransactionDto();
         txDto.setId(transaction.getId());
         txDto.setTransactionDate(transaction.getTransactionDate());
         txDto.setTransactionAmount(transaction.getTransactionAmount());
         txDto.setNote(transaction.getNote());
         txDto.setChange(transaction.getChange());
         txDto.setMadeBy(transaction.getMadeBy());
         txDto.setStatus(transaction.getStatus());
         currentPartnerDto.getLandTransactions().add(txDto);

         // Update the total: sum of amount and all transaction amounts
         double transactionTotal = currentPartnerDto.getLandTransactions().stream()
                 .mapToDouble(LandTransactionDto::getTransactionAmount)
                 .sum();
     //    currentPartnerDto.setTotal(currentPartnerDto.getAmount() + transactionTotal);
         currentPartnerDto.setTotal(currentPartnerDto.getTotal() + transactionTotal);
     }

     System.out.println(partnerTransactions);
     return partnerTransactions.values().stream().toList();
 }


//    public PurchaserWithTransactionsDto getPurchaserTransaction(Long landId) {
//        Land land = landRepository.findById(landId)
//                .orElseThrow(() -> new IllegalArgumentException("Land not found"));
//        Person purchaser = land.getPurchaser();
//
//        if (purchaser == null) {
//            return null; // No purchaser found for the land
//        }
//
//        // Fetch all transactions for the purchaser
//        List<LandTransaction> transactions = landTransactionRepository.findAllByPerson_Id(purchaser.getId());
//
//        // Initialize DTO and populate fields
//        PurchaserWithTransactionsDto dto = new PurchaserWithTransactionsDto();
//        dto.setId(purchaser.getId());
//        dto.setName(purchaser.getName());
//        dto.setPhoneNumber(purchaser.getPhoneNumber());
//        dto.setAddress(purchaser.getAddress());
//        dto.setAadharNumber(purchaser.getAadharNumber());
//
//        // Calculate total transaction amount
//        double totalAmount = transactions.stream()
//                .mapToDouble(tx -> tx.getTransactionAmount())
//                .sum();
//        dto.setTotal(totalAmount);
//
//        // Map transactions to DTO
//        dto.setLandTransactions(transactions.stream().map(tx -> {
//            LandTransactionDto txDto = new LandTransactionDto();
//            txDto.setId(tx.getId());
//            txDto.setTransactionDate(tx.getTransactionDate());
//            txDto.setTransactionAmount(tx.getTransactionAmount());
//            txDto.setNote(tx.getNote());
//            txDto.setChange(tx.getChange());
//            txDto.setMadeBy(tx.getMadeBy());
//            txDto.setStatus(tx.getStatus());
//            return txDto;
//        }).toList());
//
//        return dto;
//    }


    public PurchaserWithTransactionsDto getPurchaserTransaction(Long landId) {
        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new IllegalArgumentException("Land not found"));
        Person purchaser = land.getPurchaser();

        if (purchaser == null) {
            return null; // No purchaser found for the land
        }

        // Fetch all transactions for the purchaser
        List<LandTransaction> transactions = landTransactionRepository.findAllByPerson_Id(purchaser.getId());

        // Initialize DTO and populate fields
        PurchaserWithTransactionsDto dto = new PurchaserWithTransactionsDto();
        dto.setId(purchaser.getId());
        dto.setName(purchaser.getName());
        dto.setPhoneNumber(purchaser.getPhoneNumber());
        dto.setAddress(purchaser.getAddress());
        dto.setAadharNumber(purchaser.getAadharNumber());




        double totalAmount = transactions.stream()
                .map(tx -> tx.getChange() == TransactionChange.DEBIT ? -tx.getTransactionAmount() : tx.getTransactionAmount())
                .reduce(0.0, Double::sum);

        dto.setTotal(totalAmount);

        // Map transactions to DTO
        dto.setLandTransactions(transactions.stream().map(tx -> {
            LandTransactionDto txDto = new LandTransactionDto();
            txDto.setId(tx.getId());
            txDto.setTransactionDate(tx.getTransactionDate());
            txDto.setTransactionAmount(tx.getTransactionAmount());
            txDto.setNote(tx.getNote());
            txDto.setChange(tx.getChange());
            txDto.setMadeBy(tx.getMadeBy());
            txDto.setStatus(tx.getStatus());
            return txDto;
        }).toList());

        return dto;
    }


    public LandTransaction addPaymentToPurchaser(LandTransactionDto landTransactionDto, Long purchaserId) {
        // Fetch Purchaser by ID
         Person purchaser = personRepository.findById(purchaserId)
                .orElseThrow(() -> new RuntimeException("Purchaser not found"));

        // Create LandTransaction
        LandTransaction landTransaction = new LandTransaction();
        landTransaction.setTransactionDate(landTransactionDto.getTransactionDate());
        landTransaction.setTransactionAmount(landTransactionDto.getTransactionAmount());
        landTransaction.setNote(landTransactionDto.getNote());
        landTransaction.setChange(landTransactionDto.getChange());
        landTransaction.setMadeBy(landTransactionDto.getMadeBy());
        landTransaction.setStatus(landTransactionDto.getStatus());
        landTransaction.setPerson(purchaser); // Set the Purchaser

        // Save and return
        return landTransactionRepository.save(landTransaction);
    }




//    public PartnerWithTransactionsDto getSinglePartnerTransactions(Long partnerId) {
//        // Fetch partner details
//        Partner partner = partnerRepository.findById(partnerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Partner not found with id: " + partnerId));
//
//        // Fetch all transactions associated with this partner
//        List<LandTransaction> landTransactions = landTransactionRepository.findAllByPartnerId(partnerId);
//
//        // Create PartnerWithTransactionsDto
//        PartnerWithTransactionsDto partnerWithTransactionsDto = new PartnerWithTransactionsDto();
//        partnerWithTransactionsDto.setId(partner.getId());
//        partnerWithTransactionsDto.setName(partner.getName());
//        partnerWithTransactionsDto.setCity(partner.getCity());
//        partnerWithTransactionsDto.setPhoneNumber(partner.getPhoneNumber());
//     //   partnerWithTransactionsDto.setAmount(Double.parseDouble(partner.getAmount()));
//        partnerWithTransactionsDto.setPaymentDate(partner.getPaymentDate() != null ? partner.getPaymentDate().toString() : null);
//        partnerWithTransactionsDto.setLandTransactions(new ArrayList<>()); // Initialize empty list
//        partnerWithTransactionsDto.setTotal(0.0); // Default total
//
//        // Process transactions and update the DTO
//        for (LandTransaction transaction : landTransactions) {
//            // Calculate the amount based on transaction change type
//            double amountToChange = transaction.getChange() == TransactionChange.DEBIT
//                    ? -transaction.getTransactionAmount() // Subtract amount for DEBIT
//                    : transaction.getTransactionAmount(); // Add amount for CREDIT
//
//         //   partnerWithTransactionsDto.setAmount(partnerWithTransactionsDto.getAmount() + amountToChange);
//            partnerWithTransactionsDto.setTotal(partnerWithTransactionsDto.getTotal() + amountToChange);
//
//            // Map transaction details
//            LandTransactionDto txDto = new LandTransactionDto();
//            txDto.setId(transaction.getId());
//            txDto.setTransactionDate(transaction.getTransactionDate());
//            txDto.setTransactionAmount(transaction.getTransactionAmount());
//            txDto.setNote(transaction.getNote());
//            txDto.setChange(transaction.getChange());
//            txDto.setMadeBy(transaction.getMadeBy());
//            txDto.setStatus(transaction.getStatus());
//            partnerWithTransactionsDto.getLandTransactions().add(txDto);
//        }
//
//        // Calculate total transaction amount
//        double transactionTotal = partnerWithTransactionsDto.getLandTransactions().stream()
//                .mapToDouble(tx -> tx.getChange() == TransactionChange.DEBIT
//                        ? -tx.getTransactionAmount() // Subtract for DEBIT
//                        : tx.getTransactionAmount()) // Add for CREDIT
//                .sum();
//
//        // Set total by adding the amount and total transactions
//   //     partnerWithTransactionsDto.setTotal(partnerWithTransactionsDto.getAmount() + transactionTotal);
//        partnerWithTransactionsDto.setTotal(partnerWithTransactionsDto.getTotal() + transactionTotal);
//
//        return partnerWithTransactionsDto;
//    }


    public PartnerWithTransactionsDto getSinglePartnerTransactions(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new IllegalArgumentException("Partner not found with id: " + partnerId));

        List<LandTransaction> transactions = landTransactionRepository.findAllByPartnerId(partnerId);

        PartnerWithTransactionsDto dto = new PartnerWithTransactionsDto();
        dto.setId(partner.getId());
        dto.setName(partner.getName());
        dto.setCity(partner.getCity());
        dto.setPhoneNumber(partner.getPhoneNumber());
        dto.setPaymentDate(partner.getPaymentDate() != null ? partner.getPaymentDate().toString() : null);

        double totalAmount = transactions.stream()
                .mapToDouble(tx -> tx.getChange() == TransactionChange.DEBIT
                        ? -tx.getTransactionAmount()
                        : tx.getTransactionAmount())
                .sum();

        dto.setTotal(totalAmount);

        dto.setLandTransactions(transactions.stream().map(tx -> {
            LandTransactionDto txDto = new LandTransactionDto();
            txDto.setId(tx.getId());
            txDto.setTransactionDate(tx.getTransactionDate());
            txDto.setTransactionAmount(tx.getTransactionAmount());
            txDto.setNote(tx.getNote());
            txDto.setChange(tx.getChange());
            txDto.setMadeBy(tx.getMadeBy());
            txDto.setStatus(tx.getStatus());
            return txDto;
        }).toList());

        return dto;
    }

    public void deleteSinglePartnerTransaction(Long transactionId) {
        // Fetch the transaction by ID
        LandTransaction transaction = landTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        // Delete the transaction
        landTransactionRepository.delete(transaction);
    }

    public List<PartnerWithTransactionsDto> getPartnersByLandId(Long landId) {
        System.out.println("Fetching partners and transactions for landId: " + landId);

        // Fetch all partners for the given landId
        List<Partner> partners = partnerRepository.findAllByLandId(landId);
        System.out.println("Total partners found: " + partners.size());

        // Fetch all transactions related to this landId
        List<LandTransaction> landTransactions = landTransactionRepository.findAllByPartnerLandId(landId);
        System.out.println("Total transactions found: " + landTransactions.size());

        Map<Long, PartnerWithTransactionsDto> partnerTransactions = new HashMap<>();

        // Initialize DTOs for all partners
        for (Partner partner : partners) {
            partnerTransactions.computeIfAbsent(partner.getId(), id -> {
                PartnerWithTransactionsDto dto = new PartnerWithTransactionsDto();
                dto.setId(partner.getId());
                dto.setName(partner.getName());
                dto.setCity(partner.getCity());
                dto.setPhoneNumber(partner.getPhoneNumber());
                dto.setPaymentDate(partner.getPaymentDate() != null ? partner.getPaymentDate().toString() : null);
                dto.setTotal(0.0);
                dto.setLandTransactions(new ArrayList<>());
                return dto;
            });
        }

        // Process transactions and map them to the correct partner
        for (LandTransaction transaction : landTransactions) {
            Partner partner = transaction.getPartner();
            if (partner == null) {
                System.out.println("Skipping transaction ID " + transaction.getId() + " because partner is null.");
                continue;
            }

            PartnerWithTransactionsDto dto = partnerTransactions.get(partner.getId());

            double amountToChange = transaction.getChange() == TransactionChange.DEBIT
                    ? -transaction.getTransactionAmount()
                    : transaction.getTransactionAmount();

            dto.setTotal(dto.getTotal() + amountToChange);

            LandTransactionDto txDto = new LandTransactionDto();
            txDto.setId(transaction.getId());
            txDto.setTransactionDate(transaction.getTransactionDate());
            txDto.setTransactionAmount(transaction.getTransactionAmount());
            txDto.setNote(transaction.getNote());
            txDto.setChange(transaction.getChange());
            txDto.setMadeBy(transaction.getMadeBy());
            txDto.setStatus(transaction.getStatus());

            dto.getLandTransactions().add(txDto);
        }

        return new ArrayList<>(partnerTransactions.values());
    }


}
