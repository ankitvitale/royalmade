package com.royalmade.mapper;

import com.royalmade.dto.MeterialDto.VendorPaymentResponse;
import com.royalmade.entity.VendorPayment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorPaymentMapper {
    VendorPaymentResponse toResponse(VendorPayment payment);
}
