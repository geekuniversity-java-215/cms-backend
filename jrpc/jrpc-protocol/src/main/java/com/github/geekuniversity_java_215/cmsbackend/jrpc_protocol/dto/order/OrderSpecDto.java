package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderSpecDto {

    public enum OrderBy {ASC,DESC}

    // null - не сортировать
    private OrderBy priceOrderBy;

    // null - без нижней границы по цене
    private BigDecimal priceMin;

    // null - без верхней границы по цене
    private BigDecimal priceMax;

    // Если пустой список - то поиск будет осуществляться по всем категориям
    @NotNull
    private List<Long> categoryList = new ArrayList<>();

    // null - выдать сразу все товары, иначе по сколько отдавать
    private Integer limit;

    // это недо-pagination, полноценной нету
}
