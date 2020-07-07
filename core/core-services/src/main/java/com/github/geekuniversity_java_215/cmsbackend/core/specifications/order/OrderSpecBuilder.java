package com.github.geekuniversity_java_215.cmsbackend.core.specifications.order;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.base.SpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderSpecDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("ConstantConditions")
@Slf4j
public class OrderSpecBuilder implements SpecBuilder<Order, OrderSpecDto> {

    public Specification<Order> build(OrderSpecDto orderSpecDto) {

        if (orderSpecDto == null) {
            return null;
        }

        Specification<Order> specA = Specification.where(null);

        OrderSpecDto s = orderSpecDto;

        final String idName = "id";
        final String priceName = "price";
        final String courier = "courier";
        final String client = "client";
        final String statusValue = "statusValue";
        //final String categoryName = "category";


        // FILTER BY ID
        if (s.getId() != null) {

            specA = specA.and(
                (root, query, builder) -> builder.equal(root.get(idName), s.getId()));
        }


        // BETWEEN PRICES
        if (s.getPriceMin() != null && s.getPriceMax() != null) {

//                specA = specA
//                    .and((root, query, builder) -> builder.lessThan(root.get(priceName), p.getPriceMax()))
//                    .and((root, query, builder) -> builder.greaterThanOrEqualTo(root.get(priceName), p.getPriceMin()));

            specA = specA.and(
                (root, query, builder) -> builder.between(root.get(priceName), s.getPriceMin(), s.getPriceMax()));


//                specA = specA.and(
//                        (root, query, builder) -> {
//                            return builder.between(root.get(priceName), p.getPriceMin(), p.getPriceMax());
//                        });
        }

        // PRICE LESS THAN MAX
        if (s.getPriceMin() == null && s.getPriceMax() != null) {

            specA = specA.and(
                (root, query, builder) -> {
                    //query.orderBy(builder.desc(root.get(priceName)));
                    return builder.lessThanOrEqualTo(root.get(priceName), s.getPriceMax());
                });
        }

        // PRICE GREATER THAN MIN
        if (s.getPriceMin() != null && s.getPriceMax() == null) {

            specA = specA.and(
                (root, query, builder) -> {
                    //query.orderBy(builder.desc(root.get(priceName)));
                    return builder.greaterThanOrEqualTo(root.get(priceName), s.getPriceMin());
                });
        }


//        // IN CATEGORY
//        if (s.getCategoryList().size() > 0) {
//
//            specA = specA.and(
//                (root, query, builder) -> {
//
//                    return builder.in(root.get(categoryName).get("id")).value(p.getCategoryList());
//                    //return builder.in(root.get("category.id")).value(p.getCategoryList());
//                });
//        }


        // FILTER BY COURIER
        if (s.getCourier() != null) {

            specA = specA.and(
                (root, query, builder) -> {
                    //query.orderBy(builder.desc(root.get(priceName)));
                    return builder.equal(root.get(courier).get(idName), s.getCourier().getId());
                });
        }

        // FILTER BY CLIENT
        if (s.getClient() != null) {

            specA = specA.and(
                (root, query, builder) -> {
                    //query.orderBy(builder.desc(root.get(priceName)));
                    return builder.equal(root.get(client).get(idName), s.getClient().getId());
                });
        }


        // FILTER BY STATUS
        if (s.getStatus() != null) {

            specA = specA.and(
                (root, query, builder) -> {
                    //query.orderBy(builder.desc(root.get(priceName)));
                    return builder.equal(root.get(statusValue), s.getStatus().getId());
                });
        }




        // DEFAULT SORT BY ID ASC
        if(s.getPriceOrderBy() == null) {

            specA = specA.and(
                (root, query, builder) -> {
                    query.orderBy(builder.asc(root.get(idName)));
                    //not good to return null but ...
                    return null;
                });

        }


        // SORT BY PRICE
        if(s.getPriceOrderBy() != null) {

            specA = specA.and(
                (root, query, builder) -> {
                    switch (s.getPriceOrderBy()){
                        case ASC:
                            query.orderBy(builder.asc(root.get(priceName)));
                            break;
                        case DESC:
                            query.orderBy(builder.desc(root.get(priceName)));
                            break;
                    }
                    // not good to return null but ...
                    return null;
                });
        }

        return specA;
    }



}



// Glitch code...

/*
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Specification<ProductN> build(Optional<ProductSpecDto> pSpecDtoOp) {

        AtomicReference<Specification<ProductN>> specA = new AtomicReference<>(Specification.where(null));


        pSpecDtoOp.ifPresent(p -> {

            //final String idName = "id";
            final String priceName = "price";
            final String categoryName = "category";

            // BETWEEN
            if (p.getPriceMin() != null && p.getPriceMax() != null) {

                specA.getAndUpdate(s -> s.and(


                        (root, query, builder) -> {
                            //query.orderBy(builder.desc(root.get(priceName)));
                            return builder.between(root.get(priceName), p.getPriceMin(), p.getPriceMax());
                        }));

            }

            // PRICE LESS THAN MAX
            if (p.getPriceMin() == null && p.getPriceMax() != null) {

                specA.getAndUpdate(s -> s.and(
                        (root, query, builder) -> {
                            //query.orderBy(builder.desc(root.get(priceName)));
                            return builder.lessThanOrEqualTo(root.get(priceName), p.getPriceMax());
                        }));
            }

            // PRICE GREATER THAN MIN
            if (p.getPriceMin() != null && p.getPriceMax() == null) {

                specA.getAndUpdate(s -> s.and(
                        (root, query, builder) -> {
                            //query.orderBy(builder.desc(root.get(priceName)));
                            return builder.greaterThanOrEqualTo(root.get(priceName), p.getPriceMin());
                        }
                ));
            }


            // IN CATEGORY
            if (p.getCategoryList().size() > 0) {

                specA.getAndUpdate(s -> s.and(
                        (root, query, builder) ->
                                builder.in(root.get(categoryName).get("id")).value(p.getCategoryList())
                ));
            }


            if (p.getPriceOrderBy() != null) {

                specA.getAndUpdate(s -> s.and(
                        (root, query, builder) -> {
                            switch (p.getPriceOrderBy()) {
                                case ASC:
                                    query.orderBy(builder.asc(root.get(priceName)));
                                    break;
                                case DESC:
                                    query.orderBy(builder.desc(root.get(priceName)));
                                    break;
                            }
                            //noinspection ConstantConditions
                            return null;
                        }
                ));
            }

        });

        return specA.get();

    }
    */
