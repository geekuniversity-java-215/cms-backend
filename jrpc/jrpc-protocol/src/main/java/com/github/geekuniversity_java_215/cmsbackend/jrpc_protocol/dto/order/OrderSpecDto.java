package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
public class OrderSpecDto extends AbstractSpecDto {

    public enum OrderBy {ASC,DESC}

    private Long id;
    // null - не сортировать
    private OrderBy priceOrderBy;

    // null - без нижней границы по цене
    private BigDecimal priceMin;

    // null - без верхней границы по цене
    private BigDecimal priceMax;

    // интервал времени
    private Instant from;
    private Instant to;

    private OrderStatus status;


//    // Если пустой список - то поиск будет осуществляться по всем категориям
//    @NotNull
//    private List<Long> categoryList = new ArrayList<>();

    // null - выдать сразу все заказы, иначе по сколько отдавать
    private Integer limit;

    // принадлежность курьеру
    private CourierDto courier;

    // принадлежность клиенту
    private ClientDto client;
}
