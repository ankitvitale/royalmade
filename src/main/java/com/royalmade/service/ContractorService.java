package com.royalmade.service;

import com.royalmade.dto.AllContractorResponseDto;
import com.royalmade.dto.ContractorDto;
import com.royalmade.dto.ContractorInstallmentDto;
import com.royalmade.dto.ContractorResponseDto;
import com.royalmade.entity.Contractor;
import com.royalmade.entity.ContractorInstallment;
import com.royalmade.entity.Expense;
import com.royalmade.entity.Project;
import com.royalmade.entity.enumeration.ExpensePayStatus;
import com.royalmade.exception.ResourceNotFoundException;
import com.royalmade.repo.ContractorInstallmentRepository;
import com.royalmade.repo.ContractorRepository;
import com.royalmade.repo.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractorService {

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private ContractorInstallmentRepository contractorInstallmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContractorService.class);

    @Autowired
    private ProjectRepository projectRepository;
    public Contractor addContractor(ContractorDto contractorDto) {
        Contractor contractor = new Contractor();
        contractor.setContractorName(contractorDto.getContractorName());
        contractor.setSideName(contractorDto.getSideName());
        contractor.setType(contractorDto.getType());
        contractor.setAddedOn(contractorDto.getAddedOn());
        contractor.setTotal(contractorDto.getTotal());
        contractor.setContractorPaidAmount(contractorDto.getContractorPaidAmount());
        contractor.setReamingAmount(contractorDto.getReamingAmount());

//        if (contractorDto.getProjectId() != null) {
//            Project project = projectRepository.findById(contractorDto.getProjectId().getId())
//                    .orElseThrow(() -> new EntityNotFoundException("Project not found"));
//            contractor.setProject(project);
//        }

        return contractorRepository.save(contractor);
    }

    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }

//    public Contractor createContractor(Long projectId, ContractorDto contractorDto) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
//        Contractor contractor = new Contractor();
//        contractor.setContractorName(contractorDto.getContractorName());
//        contractor.setSideName(contractorDto.getSideName());
//        contractor.setType(contractorDto.getType());
//        contractor.setTotal(contractorDto.getTotal());
//        contractor.setContractorPaidAmount(contractorDto.getContractorPaidAmount());
//        contractor.setReamingAmount(contractorDto.getReamingAmount());
//        contractor.setAddedOn(contractorDto.getAddedOn());
//        contractor.setProject(project);
//        return contractorRepository.save(contractor);
//    }

//    public Contractor createContractor(Long projectId, ContractorDto contractorDto) {
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
//
//        // Ensure values are not null before calculations
//        double total = contractorDto.getTotal() != null ? contractorDto.getTotal() : 0.0;
//        double contractorPaidAmount = contractorDto.getContractorPaidAmount() != null ? contractorDto.getContractorPaidAmount() : 0.0;
//        double remainingAmount = total - contractorPaidAmount; // Fixed variable name
//
//        Contractor contractor = new Contractor();
//        contractor.setContractorName(contractorDto.getContractorName());
//        contractor.setSideName(contractorDto.getSideName());
//        contractor.setType(contractorDto.getType());
//        contractor.setTotal(total);  // Ensuring non-null value
//        contractor.setContractorPaidAmount(contractorPaidAmount);
//        contractor.setReamingAmount(remainingAmount); // Corrected method call
//        contractor.setAddedOn(contractorDto.getAddedOn());
//        contractor.setProject(project);
//
//        return contractorRepository.save(contractor);
//
//
//    }

    public Contractor createContractor(Long projectId, ContractorDto contractorDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        double total = contractorDto.getTotal() != null ? contractorDto.getTotal() : 0.0;
        double contractorPaidAmount = contractorDto.getContractorPaidAmount() != null ? contractorDto.getContractorPaidAmount() : 0.0;
        double remainingAmount = total - contractorPaidAmount;

        Contractor contractor = new Contractor();
        contractor.setContractorName(contractorDto.getContractorName());
        contractor.setSideName(contractorDto.getSideName());
        contractor.setType(contractorDto.getType());
        contractor.setTotal(total);
        contractor.setContractorPaidAmount(contractorPaidAmount);
        contractor.setReamingAmount(remainingAmount);
        contractor.setAddedOn(contractorDto.getAddedOn());
        contractor.setProject(project);

        contractor = contractorRepository.save(contractor);

        // If contractorPaidAmount is provided, create an installment entry
        if (contractorPaidAmount > 0) {
            ContractorInstallment installment = new ContractorInstallment();
            installment.setContractor(contractor);
            installment.setAmount(contractorPaidAmount);
            installment.setContractorPayDate(LocalDate.now()); // Set the current date
            installment.setContractorPayStatus(ExpensePayStatus.valueOf("UPI")); // Default status, modify as needed
      //      installment.setRemainingAmount(remainingAmount > 0 ? remainingAmount : 0.0);

            contractorInstallmentRepository.save(installment);
        }

        return contractor;
    }


    public Contractor addContractorInstallment(Long id, List<ContractorInstallmentDto> contractorInstallments) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contractor id not found"));

        List<ContractorInstallment> installments = new ArrayList<>();

        for (ContractorInstallmentDto installmentDto : contractorInstallments) {
            ContractorInstallment installment = new ContractorInstallment();
            installment.setContractor(contractor);
            installment.setContractorPayDate(installmentDto.getContractorPayDate());
            installment.setAmount(installmentDto.getAmount());
            installment.setRemark(installmentDto.getRemark());
            installment.setContractorPayStatus(installmentDto.getContractorPayStatus());

            // Ensure amount is not null before using it
            double amount = installment.getAmount() != null ? installment.getAmount() : 0.0;

            contractor.setContractorPaidAmount(contractor.getContractorPaidAmount() + amount);
            contractor.setReamingAmount(contractor.getReamingAmount() - amount); // Fixed typo

            installments.add(installment);
        }

        contractor.getContractorInstallments().addAll(installments);
        contractorInstallmentRepository.saveAll(installments);

        return contractorRepository.save(contractor);
    }


//    public List<ContractorInstallmentDto> getContractorInstallments(Long id) {
//        Contractor contractor = contractorRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Contractor ID " + id + " Not Found"));
//
//        return contractor.getContractorInstallments().stream()
//                .map(installment -> new ContractorInstallmentDto(
//                        installment.getId(),
//                        installment.getContractorPayDate(),
//                        installment.getAmount(),
//                        installment.getRemark(),
//                        installment.getContractorPayStatus()
//                ))
//                .collect(Collectors.toList());
//    }


    public ContractorResponseDto getContractorById(Long id) {
        Contractor contractor = contractorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contractor ID " + id + " Not Found"));

        List<ContractorInstallmentDto> installmentDtos = contractor.getContractorInstallments().stream()
                .map(installment -> new ContractorInstallmentDto(
                        installment.getId(),
                        installment.getContractorPayDate(),
                        installment.getAmount(),
                        installment.getRemark(),
                        installment.getContractorPayStatus(),
                        contractor.getReamingAmount() // Adding RemainingAmount field
                ))
                .collect(Collectors.toList());


        return new ContractorResponseDto(
                contractor.getId(),
                contractor.getContractorName(),
                contractor.getSideName(),
                contractor.getType(),
                contractor.getAddedOn(),
                contractor.getTotal(),
                contractor.getContractorPaidAmount(),
                contractor.getReamingAmount(), // Fixed typo
                installmentDtos
        );
    }


public List<AllContractorResponseDto> getContractorByProject(Long projectId) {
    List<Contractor> contractors = contractorRepository.findByProjectId(projectId);
    return contractors.stream().map(AllContractorResponseDto::fromEntity).collect(Collectors.toList());
}

    public Contractor deleteContractor(Long id) {
        Contractor contractor=contractorRepository.findById(id).orElseThrow(()->  new ResourceNotFoundException("Contractor not found with id: " + id));
     for(ContractorInstallment installment:contractor.getContractorInstallments()){
         contractorInstallmentRepository.delete(installment);
     }
     contractorRepository.deleteById(id);
     return contractor;
    }
}
