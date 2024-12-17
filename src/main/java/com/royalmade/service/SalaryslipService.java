package com.royalmade.service;

import com.royalmade.entity.Salaryslip;
import com.royalmade.repo.SalaryslipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryslipService {
    @Autowired
    private SalaryslipRepository salaryslipRepository;
    public Salaryslip createsalarySlip(Salaryslip salaryslip) {
        return salaryslipRepository.save(salaryslip);
    }

    public List<Salaryslip> getAllsalarySlip() {
        return salaryslipRepository.findAll();
    }


    public Salaryslip getlsalarySlip(Long id) {
        return salaryslipRepository.findById(id).orElseThrow(()-> new RuntimeException("Id Not Found"+id));
    }
    public void deleteSalarySlip(Long id) {
        Salaryslip salaryslip = salaryslipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary slip not found with ID: " + id));
        salaryslipRepository.delete(salaryslip);
    }

    public Salaryslip updateSalaryslip(Salaryslip salaryslip) {
        Salaryslip salaryslip1= salaryslipRepository.findById(salaryslip.getId()).orElseThrow(()-> new RuntimeException("Salary slip ID Not Found"+salaryslip.getId()));
        salaryslip.setEmployeeName(salaryslip1.getEmployeeName());
        salaryslip.setBranchName(salaryslip1.getBranchName());
        salaryslip.setTodayDate(salaryslip1.getTodayDate());
        salaryslip.setSalaryFrom(salaryslip1.getSalaryFrom());
        salaryslip.setSalaryTo(salaryslip1.getSalaryTo());
        salaryslip.setStatus(salaryslip1.getStatus());
        return salaryslipRepository.save(salaryslip);
    }
}
