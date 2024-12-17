package com.royalmade.mapper;

import com.royalmade.dto.ExpenseDto;
import com.royalmade.entity.Expense;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ExpenseMapper {

    public abstract ExpenseDto toExpanseDto(Expense expense);
}
