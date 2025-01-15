package com.royalmade.service;

import com.royalmade.repo.AlotmentLetterRepository;
import org.springframework.stereotype.Service;

import com.royalmade.entity.AlotmentLetter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AlotmentLetterService {

    @Autowired
    private AlotmentLetterRepository alotmentLetterRepository;

    public List<AlotmentLetter> getAllAlotmentLetters() {
        return alotmentLetterRepository.findAll();
    }

    public AlotmentLetter getAlotmentLetterById(Long id) {
        return alotmentLetterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alotment Letter not found with ID: " + id));
    }

    public AlotmentLetter createAlotmentLetter(AlotmentLetter alotmentLetter) {
        return alotmentLetterRepository.save(alotmentLetter);
    }

    public AlotmentLetter updateAlotmentLetter(Long id, AlotmentLetter alotmentLetter) {
        AlotmentLetter existingAlotmentLetter = getAlotmentLetterById(id);
        existingAlotmentLetter.setApartmentName(alotmentLetter.getApartmentName());
        existingAlotmentLetter.setKhno(alotmentLetter.getKhno());
        existingAlotmentLetter.setMouzeNo(alotmentLetter.getMouzeNo());
        existingAlotmentLetter.setSheetNo(alotmentLetter.getSheetNo());
        existingAlotmentLetter.setCitySurveyNo(alotmentLetter.getCitySurveyNo());
        existingAlotmentLetter.setName(alotmentLetter.getName());
        existingAlotmentLetter.setTotalamount(alotmentLetter.getTotalamount());
        existingAlotmentLetter.setTotalamountword(alotmentLetter.getTotalamountword());
        existingAlotmentLetter.setAgreementDate(alotmentLetter.getAgreementDate());
        existingAlotmentLetter.setSqmtrs(alotmentLetter.getSqmtrs());
        existingAlotmentLetter.setSqft(alotmentLetter.getSqft());
        return alotmentLetterRepository.save(existingAlotmentLetter);
    }

    public void deleteAlotmentLetter(Long id) {
        alotmentLetterRepository.deleteById(id);
    }
}
