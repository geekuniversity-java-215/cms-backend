package com.github.geekuniversity_java_215.cmsbackend.core.services.order;

import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.exceptions.InvalidLogicException;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.OrderRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


@Service
@Transactional
@Slf4j
public class OrderService extends BaseRepoAccessService<Order> {

    // alias
    interface OrderAct extends Function<Order,Order> {}


    List<OrderAct> createAct;
    List<OrderAct> editAct;
    List<OrderAct> assignAct;
    List<OrderAct> deliverAct;
    List<OrderAct> cancelAct;
    List<OrderAct> cancelBackAct;
    List<OrderAct> completeAct;
    List<OrderAct> closedAct;

    int mSize = OrderStatus.values().length;
    List<OrderAct>[][] m;

    // -----------------------------------------

    private final OrderRepository orderRepository;
    private final OrderLogic orderLogic;



    @Autowired
    public OrderService(OrderRepository orderRepository, OrderLogic orderLogic) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderLogic = orderLogic;
    }


    @PostConstruct
    private void postConstruct() {

        // Списки действий
        createAct     = Collections.singletonList(orderLogic::create);
        editAct       = Collections.singletonList(orderLogic::edit);
        assignAct     = Collections.singletonList(orderLogic::assign);
        deliverAct    = Collections.singletonList(orderLogic::deliver);
        cancelAct     = Collections.singletonList(orderLogic::cancel);
        cancelBackAct = Arrays.asList(orderLogic::cancel, orderLogic::back);
        completeAct   = Collections.singletonList(orderLogic::complete);
        closedAct     = Collections.singletonList(orderLogic::close);


        // строим матрицу решений (квадратная)

        //noinspection unchecked
        m = (List<OrderAct>[][]) Array.newInstance(List.class, mSize, mSize);


        int NUL = OrderStatus.NULL.getId();
        int NEW = OrderStatus.NEW.getId();
        int ASS = OrderStatus.ASSIGNED.getId();
        int TRA = OrderStatus.TRANSIT.getId();
        int CAN = OrderStatus.CANCELLED.getId();
        int MOD = OrderStatus.MODERATION.getId();  // модерация пока не прикручена
        int COM = OrderStatus.COMPLETED.getId();
        int CLO = OrderStatus.CLOSED.getId();

        //OLD  NEW    ACTION LIST
        m[NUL][NEW] = createAct;
        m[NEW][NEW] = editAct;
        m[NEW][ASS] = assignAct;
        m[ASS][TRA] = deliverAct;
        m[NEW][CAN] = cancelAct;
        m[ASS][CAN] = cancelAct;
        m[TRA][CAN] = cancelBackAct;
        m[TRA][COM] = completeAct;
        m[CAN][CLO] = closedAct;
        m[COM][CLO] = closedAct;
    }


    public Order accept(Order order) {
        throw new NotImplementedException();
    }



    @Override
    public Order save(Order order) {

        Order old;

        Long orderId = order.getId();
        if (order.getId() == null) {
            // фикс-защита
            order.setStatus(OrderStatus.NEW);

            // заглушка для логики
            old = new Order();
            old.setStatus(OrderStatus.NULL);
        }
        else {

            // отключаем от сессии новый заказ
            orderRepository.detach(order);

            // берем старый заказ и перезагружаем его из базы
            old = findByIdOrError(order.getId());
            orderRepository.refresh(old);
        }



        // смотрим, что у нас тут
        int oldOrd = old.getStatus().getId();
        int newOrd = order.getStatus().getId();


        // выполняем логику жизненного цикла заказа
        if (m[oldOrd][newOrd] != null) {
            // поочередно применяем назначенные действия к заказу
            for (OrderAct act : m[oldOrd][newOrd]) {
                order = act.apply(order);
            }
            // сохраняем все изменения
            order = orderRepository.save(order);
        }
        else {
            // Такая логика запрещена
            throw new InvalidLogicException("Invalid Order operation: " +
                "from " + old.getStatus() + " to " + order.getStatus());
        }

        return order;
    }
}
