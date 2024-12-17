package com.royalmade.service;

import com.royalmade.entity.Lead;
import com.royalmade.entity.LeadLog;


import com.royalmade.repo.LeadLogRepository;
import com.royalmade.repo.LeadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadLogRepository leadLogRepository;
    public Lead addNewLead(Lead lead) {
        return leadRepository.save(lead);
    }

    public Lead addLogsToLead(Long id, List<LeadLog> leadLogs) {
        // Find the lead by ID or throw an exception if not found
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
        // Set the lead for each LeadLog
        for (LeadLog log : leadLogs) {
            log.setLead(lead); // Ensure Lead is set in each log
        }
        // Add the logs to the lead
        lead.getLeadLogs().addAll(leadLogs);
        // Update the lead's status based on the latest LeadLog status
        if (!leadLogs.isEmpty()) {
            LeadLog latestLog = leadLogs.get(leadLogs.size() - 1); // Get the latest added log
            lead.setStatus(latestLog.getStatus()); // Update the lead status
        }
        // Save the lead and the logs
        leadLogRepository.saveAll(leadLogs);
        return lead; // Return the updated lead
    }

    public Lead getLeadById(Long id) {
        return leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));
    }

    public List<Lead> getAllLeads() {
        return leadRepository.findAll();
    }

    public boolean deleteLead(Long id) {
        Optional<Lead> lead = leadRepository.findById(id);
        if (lead.isPresent()) {
            // First, delete all LeadLogs associated with the Lead
            leadLogRepository.deleteByLeadId(id); // Assuming you have this method in your LeadLog repository

            // Then, delete the Lead
            leadRepository.delete(lead.get());
            return true;
        }
        return false;
    }

    public Lead updateLead(Long id, Lead updatedLead) {
        // Find the existing lead by ID or throw an exception if not found
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with ID: " + id));

        // Update the lead details
        lead.setName(updatedLead.getName());
        lead.setJobTitle(updatedLead.getJobTitle());
        lead.setCompanyName(updatedLead.getCompanyName());
        lead.setEmail(updatedLead.getEmail());
        lead.setPhoneNumber(updatedLead.getPhoneNumber());
        lead.setFoundOn(updatedLead.getFoundOn());
        lead.setStatus(updatedLead.getStatus());

        // Update the lead logs if provided
        if (updatedLead.getLeadLogs() != null && !updatedLead.getLeadLogs().isEmpty()) {
            // Set the lead reference for each LeadLog and add them to the lead's logs
            for (LeadLog log : updatedLead.getLeadLogs()) {
                log.setLead(lead);  // Set the lead in each log
            }
            lead.getLeadLogs().clear();  // Clear the existing logs
            lead.getLeadLogs().addAll(updatedLead.getLeadLogs());  // Add the updated logs

            // Update the lead's status based on the latest LeadLog status
            LeadLog latestLog = updatedLead.getLeadLogs().get(updatedLead.getLeadLogs().size() - 1);  // Get the latest log
            lead.setStatus(latestLog.getStatus());  // Update the lead's status
        }

        // Save the updated lead and its logs (if any)
        leadLogRepository.saveAll(updatedLead.getLeadLogs());
        leadRepository.save(lead);

        return lead;  // Return the updated lead
    }


}