package com.royalmade.controller;

import com.royalmade.dto.*;
import com.royalmade.entity.Land;
import com.royalmade.entity.LandTransaction;
import com.royalmade.entity.Partner;
import com.royalmade.mapper.LandMapper;
import com.royalmade.service.LandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LandController {

    @Autowired
    LandService landService;


    @Autowired
    private LandMapper landMapper;



    @PostMapping("/create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> createLand(@RequestBody LandDto landRequestDto) {
        Land createdLand = landService.createLand(landRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLand);
    }

//    @PostMapping("/partnerpayment/{id}")
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<Partner> addPartnerPayment(@RequestBody PartnerpaymnetDto partnerpaymnetDto, @PathVariable Long id) {
//        Partner addpayment = landService.addPartnerPayment(partnerpaymnetDto, id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addpayment);
//    }

    @PostMapping("/addpayment/partner/{partnerId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<LandTransaction> addpayment(@RequestBody LandTransactionDto landTransactionDto, @PathVariable Long partnerId) {
        LandTransaction landTransaction = landService.addPayment(landTransactionDto, partnerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(landTransaction);
    }

    @PostMapping("/addpayment/purchaser/{purchaserId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<LandTransaction> addPaymentToPurchaser(
            @RequestBody LandTransactionDto landTransactionDto,
            @PathVariable Long purchaserId
    ) {
        LandTransaction landTransaction = landService.addPaymentToPurchaser(landTransactionDto, purchaserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(landTransaction);
    }



    @GetMapping("/getAllland")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<Land>> getAllLands() {
        List<Land> lands = landService.getAllLands();
        return ResponseEntity.ok(lands);
    }

    @GetMapping("/land/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> getLandById(@PathVariable Long id) {
        Land lands = landService.getLandById(id);
        ;
        return ResponseEntity.ok(lands);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> updateLand(@PathVariable Long id, @RequestBody LandDto landRequestDto) {
        Land updatedLand = landService.updateLand(id, landRequestDto);
        return ResponseEntity.ok(updatedLand);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deleteLand(@PathVariable Long id) {
        landService.deleteLand(id);
        return ResponseEntity.status(HttpStatus.OK).body("Land with ID " + id + " has been deleted successfully.");
    }


    @GetMapping("/land/{id}/partners-with-transactions")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<PartnerWithTransactionsDto>> getPartnersWithTransactions(@PathVariable Long id) {
        List<PartnerWithTransactionsDto> partners = landService.getPartnerTransactions(id);
        return ResponseEntity.ok(partners);
    }
    @GetMapping("/land/{id}/purchaser-with-transactions")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<PurchaserWithTransactionsDto> getPurchaserWithTransactions(@PathVariable Long id) {
        PurchaserWithTransactionsDto purchaser = landService.getPurchaserTransaction(id);
        if (purchaser == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no purchaser or transactions found
        }
        return ResponseEntity.ok(purchaser);
    }




    @GetMapping("/partner/{id}/transactions")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<PartnerWithTransactionsDto> getPartnerWithTransactions(@PathVariable Long id) {
        PartnerWithTransactionsDto partner = landService.getSinglePartnerTransactions(id);
        return ResponseEntity.ok(partner);
    }

    @DeleteMapping("/partner/transaction/{transactionId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> deletePartnerTransaction(@PathVariable Long transactionId) {
        landService.deleteSinglePartnerTransaction(transactionId);
        return ResponseEntity.ok("Transaction deleted successfully.");
    }

    @GetMapping("/land/{landId}/partners")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<PartnerWithTransactionsDto>> getPartnersByLand(@PathVariable Long landId) {
        List<PartnerWithTransactionsDto> partners = landService.getPartnersByLandId(landId);
        return ResponseEntity.ok(partners);
    }

    @GetMapping("/land/{id}/transactions")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<LandTransactionsResponseDto> getLandTransactions(@PathVariable Long id) {
        List<PartnerWithTransactionsDto> partners = landService.getPartnerTransactions(id);
        PurchaserWithTransactionsDto purchaser = landService.getPurchaserTransaction(id);
        LandTransactionsResponseDto response = new LandTransactionsResponseDto();
        response.setPartners(partners);
        response.setPurchaser(purchaser);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{landId}/partners")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Land> addPartnerToLand(@PathVariable Long landId, @RequestBody PartnerDto partnerDto) {
        Land updatedLand = landService.addPartnerToLand(landId, partnerDto);
        return ResponseEntity.ok(updatedLand);
    }

    //update partener payment
    @PutMapping("/UpdatePartner/payment/{transactionId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<LandTransaction> updatePayment(@RequestBody LandTransactionDto landTransactionDto, @PathVariable Long transactionId) {
        LandTransaction updatedTransaction = landService.updatePayment(landTransactionDto, transactionId);
        return ResponseEntity.ok(updatedTransaction);
    }

    // show the single Partner payment with single transation
    @GetMapping("/SinglePartnerPaymentById/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<LandTransactionDto> getPartnerTransactionWithId(@PathVariable Long id){
        LandTransactionDto partnerWithTransactionsDto=landService.getPartnerTransactionWithId(id);
        return ResponseEntity.ok(partnerWithTransactionsDto);
    }
}
