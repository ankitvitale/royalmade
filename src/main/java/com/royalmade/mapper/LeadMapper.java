//package com.royalmade.mapper;
//
//import com.royalmade.dto.LeadLogResponseDto;
//import com.royalmade.dto.LeadResponseDto;
//import com.royalmade.entity.Lead;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//public class LeadMapper {
//
//    public LeadResponseDto toLeadResponseDto(Lead lead) {
//        List<LeadLogResponseDto> leadLogs = lead.getLeadLogs().stream()
//                .map(log -> new LeadLogResponseDto(log.getId(), log.getLogDate(), log.getStatus()))
//                .collect(Collectors.toList());
//
//        return new LeadResponseDto(
//                lead.getId(),
//                lead.getName(),
//                lead.getJobTitle(),
//                lead.getCompanyName(),
//                lead.getEmail(),
//                lead.getPhoneNumber(),
//                lead.getFoundOn(),
//                lead.getStatus(),
//                leadLogs
//        );
//    }
//}
