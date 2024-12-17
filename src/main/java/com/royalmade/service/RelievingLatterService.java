package com.royalmade.service;

import com.royalmade.entity.RelievingLatter;
import com.royalmade.entity.Salaryslip;
import com.royalmade.repo.RelievingLatterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RelievingLatterService {
    @Autowired
    private RelievingLatterRepository relievingLatterRepository;
    public RelievingLatter createRelievinglatter(RelievingLatter relievingLatter) {

        return relievingLatterRepository.save(relievingLatter);
    }

    public List<RelievingLatter> getAllRelievingLatter() {
        return relievingLatterRepository.findAll();
    }

    public RelievingLatter getAllRelievingLatterbyid(long id) {
        return relievingLatterRepository.findById(id).orElseThrow(()-> new RuntimeException("Id is not found "+id));
    }

    public RelievingLatter updateRelievingLatter(RelievingLatter relievingLatter) {
        RelievingLatter relievingLatter1=relievingLatterRepository.findById(relievingLatter.getId()).orElseThrow(()-> new RuntimeException("ID is not found "+relievingLatter.getId()));
        relievingLatter.setEmployeeName(relievingLatter1.getEmployeeName());
        relievingLatter.setCurrentDate(relievingLatter1.getCurrentDate());
        relievingLatter.setResignationDate(relievingLatter1.getResignationDate());
        relievingLatter.setLastworkingdate(relievingLatter1.getLastworkingdate());
        relievingLatter.setDesignation(relievingLatter1.getDesignation());
        relievingLatter.setDeparment(relievingLatter1.getDeparment());
        relievingLatter.setLocation(relievingLatter1.getLocation());
        return relievingLatterRepository.save(relievingLatter);
    }

    public void deleteRelievingLatter(Long id) {
        RelievingLatter relievingLatter1=relievingLatterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary slip not found with ID: " + id));
        relievingLatterRepository.delete(relievingLatter1);
    }
}
