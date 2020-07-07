package com.github.geekuniversity_java_215.cmsbackend.core.specifications.user;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.specifications.base.SpecBuilder;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserSpecDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("ConstantConditions")
@Slf4j
public class UserSpecBuilder implements SpecBuilder<User, UserSpecDto> {

    public Specification<User> build(UserSpecDto userSpecDto) {

        if (userSpecDto == null) {
            return null;
        }



        Specification<User> specA = Specification.where(null);

        UserSpecDto s = userSpecDto;

        final String idName = "id";
        final String courier = "courier";
        final String client = "client";
        //final String categoryName = "category";

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




        // DEFAULT SORT  BY ID ASC
        //if(s.getPriceOrderBy() == null) {

            specA = specA.and(
                (root, query, builder) -> {
                    query.orderBy(builder.asc(root.get(idName)));
                    //not good to return null but ...
                    return null;
                });

        //}

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