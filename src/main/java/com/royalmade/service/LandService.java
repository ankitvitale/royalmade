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
        land.setLandAddOnDate(landRequestDto.getLandAddOnDate());
        land.setAddress(address);
        land.setOwner(owner); // Assign saved owner
        land.setPurchaser(purchaser); // Assign saved purchaser
        land.setPartners(partners); // Assign saved partners

        // Save Land entity
        Land savedLand = landRepository.save(land);
        logger.info("Land saved successfully: {}", savedLand);

        return savedLand;
    }



    public List<Land> getAllLands() {

        return landRepository.findAll();
    }

    public Land getLandById(Long id) {
        return landRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Land not found with id: " + id));
    }

    public Land updateLand(Long landId, LandDto landRequestDto) {
        logger.info("Received request to update land with ID: {}", landId);

        Land existingLand = landRepository.findById(landId)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with ID: " + landId));

        if (landRequestDto.getAddress() == null) {
            throw new IllegalArgumentException("Address information is required.");
        }
        if (landRequestDto.getPurchaser() == null) {
            throw new IllegalArgumentException("Purchaser information is required.");
        }

        // Update Address
        Address address = existingLand.getAddress();
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

        // Update Purchaser
        Person purchaser = existingLand.getPurchaser();
        PurchaserDto purchaserDto = landRequestDto.getPurchaser();
        purchaser.setName(purchaserDto.getName());
        purchaser.setAddress(purchaserDto.getAddress());
        purchaser.setPhoneNumber(purchaserDto.getPhoneNumber());
        purchaser.setAadharNumber(purchaserDto.getAadharNumber());
        personRepository.save(purchaser);

        // Update Owner
        Person owner = existingLand.getOwner();
        OwnerDto ownerDto = landRequestDto.getOwner();
        owner.setName(ownerDto.getName());
        owner.setAddress(ownerDto.getAddress());
        owner.setPhoneNumber(ownerDto.getPhoneNumber());
        owner.setAadharNumber(ownerDto.getAadharNumber());
        personRepository.save(owner);

        // Update Partners
        if (landRequestDto.getPartners() != null) {
            partnerRepository.deleteAll(existingLand.getPartners());
            Set<Partner> updatedPartners = landRequestDto.getPartners().stream()
//                    .map(partnerDto -> {
//                        Partner partner = new Partner();
//                        partner.setName(partnerDto.getName());
//                        partner.setCity(partnerDto.getCity());
//                        partner.setPhoneNumber(partnerDto.getPhoneNumber());
//                        partner.setAmount(partnerDto.getAmount());
//                        partner.setPaymentDate(LocalDate.parse(partnerDto.getPaymentDate()));
//                        return partner;

                    .map(partnerDto -> {
                        Partner partner = new Partner();
                        partner.setName(partnerDto.getName());
                        partner.setCity(partnerDto.getCity());
                        partner.setPhoneNumber(partnerDto.getPhoneNumber());
                        partner.setAmount(partnerDto.getAmount());

                        if (partnerDto.getPaymentDate() != null && !partnerDto.getPaymentDate().isEmpty()) {
                            partner.setPaymentDate(LocalDate.parse(partnerDto.getPaymentDate()));
                        } else {
                            partner.setPaymentDate(null); // Handle missing date gracefully
                        }

                        return partner;
                    })
                    .collect(Collectors.toSet());
            partnerRepository.saveAll(updatedPartners);
            existingLand.setPartners(updatedPartners);
        }

        // Calculate sold amount safely
        double totalAmount = landRequestDto.getTotalAmount() != null ? landRequestDto.getTotalAmount() : 0;
        double agreementAmount = landRequestDto.getAgreementAmount() != null ? landRequestDto.getAgreementAmount() : 0;
        double tokenAmount = landRequestDto.getTokenAmount() != null ? landRequestDto.getTokenAmount() : 0;
        double soldAmount = totalAmount - (agreementAmount + tokenAmount);

        // Update Land entity
        existingLand.setArea(landRequestDto.getArea());
        existingLand.setTokenAmount(landRequestDto.getTokenAmount());
        existingLand.setAgreementAmount(landRequestDto.getAgreementAmount());
        existingLand.setTotalAmount(landRequestDto.getTotalAmount());
        existingLand.setLandAddOnDate(landRequestDto.getLandAddOnDate());
        existingLand.setAddress(address);
        existingLand.setOwner(owner);
        existingLand.setPurchaser(purchaser);

        Land updatedLand = landRepository.save(existingLand);
        logger.info("Land updated successfully: {}", updatedLand);

        return updatedLand;
    }


// old update code
//    public Land updateLand(Long id, LandDto landRequestDto) {
//        logger.info("Received request to update land with ID: {}", id);
//
//        // Fetch existing land record
//        Land existingLand = landRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Land not found with ID: " + id));
//
//        // Update Address
//        Address address = existingLand.getAddress();
//        if (landRequestDto.getAddress() != null) {
//            address.setLandmark(landRequestDto.getAddress().getLandmark());
//            address.setPincode(landRequestDto.getAddress().getPincode());
//            address.setCity(landRequestDto.getAddress().getCity());
//            address.setCountry(landRequestDto.getAddress().getCountry());
//            address.setState(landRequestDto.getAddress().getState());
//            address.setMuza(landRequestDto.getAddress().getMuza());
//            address.setKhno(landRequestDto.getAddress().getKhno());
//            address.setPlotno(landRequestDto.getAddress().getPlotno());
//            address.setPhno(landRequestDto.getAddress().getPhno());
//            addressRepository.save(address);
//        }
//
//        // Update Purchaser
//        if (landRequestDto.getPurchaser() != null) {
//            PurchaserDto purchaserDto = landRequestDto.getPurchaser();
//            Person purchaser = existingLand.getPurchaser();
//            purchaser.setName(purchaserDto.getName());
//            purchaser.setAddress(purchaserDto.getAddress());
//            purchaser.setPhoneNumber(purchaserDto.getPhoneNumber());
//            purchaser.setAadharNumber(purchaserDto.getAadharNumber());
//            personRepository.save(purchaser);
//        }

        // Update Owner

//        if (landRequestDto.getOwner() != null) {
//            updatedOwner.setId(existingLand.getOwner().getId()); // Retain existing ID
//            updatedOwner.setName(updatedOwner.getName() != null ? updatedOwner.getName() : existingLand.getOwner().getName());
//            updatedOwner.setAddress(updatedOwner.getAddress() != null ? updatedOwner.getAddress() : existingLand.getOwner().getAddress());
//            updatedOwner.setPhoneNumber(updatedOwner.getPhoneNumber() != null ? updatedOwner.getPhoneNumber() : existingLand.getOwner().getPhoneNumber());
//            personRepository.save(updatedOwner);  // Use merge() instead of save() if necessary
//            existingLand.setOwner(updatedOwner);
//        }
        // Update Partners
//        if (landRequestDto.getPartners() != null) {
//            Set<Partner> updatedPartners = landRequestDto.getPartners().stream()
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
//
//            // Remove old partners and add new ones
//            partnerRepository.deleteAll(existingLand.getPartners());
//            partnerRepository.saveAll(updatedPartners);
//            existingLand.setPartners(updatedPartners);
//        }
//
//        // Update Land entity
//        existingLand.setArea(landRequestDto.getArea());
//        existingLand.setTokenAmount(landRequestDto.getTokenAmount());
//        existingLand.setAgreementAmount(landRequestDto.getAgreementAmount());
//        existingLand.setTotalAmount(landRequestDto.getTotalAmount());
//
//        // Save updated entity
//        Land savedLand = landRepository.save(existingLand);
//        logger.info("Land updated successfully: {}", savedLand);
//
//        return savedLand;
//    }

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


    public Land addPartnerToLand(Long landId, PartnerDto partnerDto) {
        logger.info("Received request to add partner to land with ID: {}", landId);

        Land land = landRepository.findById(landId)
                .orElseThrow(() -> new EntityNotFoundException("Land not found with ID: " + landId));

        Partner partner = new Partner();
        partner.setName(partnerDto.getName());
        partner.setCity(partnerDto.getCity());
        partner.setPhoneNumber(partnerDto.getPhoneNumber());
      //  partner.setAmount(partnerDto.getAmount());
//       partner.setPaymentDate(LocalDate.parse(partnerDto.getPaymentDate()));
        partner.setLand(land);

        partnerRepository.save(partner);

        land.getPartners().add(partner);
        landRepository.save(land);

        logger.info("Partner added successfully to land ID: {}", landId);
        return land;
    }

    public LandTransaction updatePayment(LandTransactionDto landTransactionDto, Long transactionId) {
        LandTransaction landTransaction = landTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        landTransaction.setTransactionDate(landTransactionDto.getTransactionDate());
        landTransaction.setTransactionAmount(landTransactionDto.getTransactionAmount());
        landTransaction.setNote(landTransactionDto.getNote());
        landTransaction.setChange(landTransactionDto.getChange());
        landTransaction.setMadeBy(landTransactionDto.getMadeBy());
        landTransaction.setStatus(landTransactionDto.getStatus());

        return landTransactionRepository.save(landTransaction);
    }

    public LandTransactionDto getPartnerTransactionWithId(Long id) {
        LandTransaction transaction = landTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Land Transaction not found with ID: " + id));

        LandTransactionDto txDto = new LandTransactionDto();
        txDto.setId(transaction.getId());
        txDto.setTransactionDate(transaction.getTransactionDate());
        txDto.setTransactionAmount(transaction.getTransactionAmount());
        txDto.setNote(transaction.getNote());
        txDto.setChange(transaction.getChange());
        txDto.setMadeBy(transaction.getMadeBy());
        txDto.setStatus(transaction.getStatus());

        return txDto;

    }
}
