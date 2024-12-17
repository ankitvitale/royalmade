package com.royalmade.service;

import com.royalmade.entity.Lead;
import com.royalmade.entity.LeadLog;
import com.royalmade.repo.LeadLogRepository;
import com.royalmade.repo.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LeadLogService {
    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadLogRepository leadLogRepository;



}
