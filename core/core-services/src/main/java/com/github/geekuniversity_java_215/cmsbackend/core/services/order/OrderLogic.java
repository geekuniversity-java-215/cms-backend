package com.github.geekuniversity_java_215.cmsbackend.core.services.order;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;


@Service
@Transactional
@Slf4j
public class OrderLogic {

    private final OrderRepository orderRepository;

    public OrderLogic(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }



    public Order create(Order order) {

        log.info("Order: CREATE");

        // SELECT FOR UPDATE требует существующую строку в БД
        // order = orderRepository.save(order);

        // SELECT FOR UPDATE блокируем строки в OrderN, ProductN, OrderItem, StorageItem
        // блокировки, как я понял - reentrant, на своем lock ты повторно не вешаешься
        // если работаешь в одной транзакции(или в продленной транзакции)
        // orderRepository.lockByOrder(order);

        // ToDo: Тут добавить логику с финансами, при создании заказа
        return order;
    }




    public Order edit(Order order) {

        log.info("Order: EDIT");

        // блокируемся нах
        // orderRepository.lockByOrder(order);

        // берем старый заказ (по 2 разу, возьмет из кеша, т.к. одна транзакция)
//        Long orderId = order.getId();
//        Order old = orderRepository.findById(order.getId()).orElseThrow(() ->
//                new ValidationException("OrderN not exists:\n" + orderId));

        //order = orderRepository.save(order);

        // ToDo:  Тут добавить логику с финансами, при изменении заказа
        return order;
    }


    public Order assign(Order order) {

        log.info("Order: ASSIGN");

        // ToDo:  Тут добавить логику с назначением заказа
        return order;
    }



    public Order deliver(Order order) {

        log.info("Order: DELIVERING");

        // ToDo:  Тут добавить логику с началом выполнения заказа

        return order;
    }




    public Order cancel(Order order) {

        log.info("Order: CANCEL");

        // ToDo:  Тут добавить логику с отменой заказа
        //  У заказа может быть различный статус - вплоть до TRANSIT
        return order;
    }



    public Order back(Order order) {

        log.info("Order: BACK");

        // ToDo:  Тут добавить логику с возвратом заказа отправителю
        return order;
    }



    public Order complete(Order order) {

        log.info("Order: COMPLETE");

        // ToDo:  Тут добавить логику с успешным завершением доставки заказа
        return order;
    }


    public Order close(Order order) {

        log.info("Order: CLOSE");

        // ToDo:  Тут добавить логику с завершением заказа, переводом средств
        //  Заказ мог быть завершен как успешно, так и нет
        return order;
    }


}
