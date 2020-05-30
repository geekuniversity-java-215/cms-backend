package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base;

public class HandlerName {

    // classic jpa CRUD
    private static final String findById = "findById";
    private static final String findAllById = "findAllById";
    private static final String findAll = "findAll";
    private static final String findFirst = "findFirst";

    private static final String save = "save";
    private static final String delete = "delete";
    private static final String put = "put";
    private static final String remove = "remove";

    // common
    private static final String getCurrent = "getCurrent";


    // calculator specific
    private static final String add ="add";
    private static final String sub ="sub";
    private static final String mul ="mul";
    private static final String div ="div";

    //geodata specific
    private static final String getRoute = "getRoute";

    //user specific
    private static final String findByUsername = "findByUsername";
    private static final String addRoles = "addRoles";
    //private static final String makeClient = "makeClient";
    //private static final String makeCourier = "makeCourier";

    // order courier specific
    public static final String findNew = "findNew";
    public static final String accept = "accept";
    public static final String execute = "execute";
    public static final String complete = "complete";




    // CALCULATOR -------------------------------------------------
    public static class calculator {
        public static final String path = "calculator.actions";

        public static final String add = HandlerName.add;
        public static final String sub = HandlerName.sub;
        public static final String mul = HandlerName.mul;
        public static final String div = HandlerName.div;
    }

    // USER --------------------------------------------------------

    public static class user {

        public static final String path = "cmsapp.core.user";

        public static final String findById = HandlerName.findById;
        public static final String findAllById = HandlerName.findAllById;
        public static final String findAll = HandlerName.findAll;
        public static final String findFirst = HandlerName.findFirst;
        public static final String save = HandlerName.save;
        public static final String delete = HandlerName.delete;

        public static final String findByUsername = HandlerName.findByUsername;
        public static final String getCurrent = HandlerName.getCurrent;

        //public static final String addRoles = HandlerName.addRoles;
        //public static final String makeClient = HandlerName.makeClient;
        //public static final String makeCourier = HandlerName.makeCourier;

    }

    // CLIENT --------------------------------------------------------


    public static class client {

        public static final String path = "cmsapp.core.client";
        public static final String findById = HandlerName.findById;
        public static final String save = HandlerName.save;
        public static final String findByUsername = HandlerName.findByUsername;
        public static final String getCurrent = HandlerName.getCurrent;
    }



    // COURIER --------------------------------------------------------


    public static class courier {

        public static final String path = "cmsapp.core.courier";
        public static final String findById = HandlerName.findById;
        public static final String save = HandlerName.save;
        public static final String findByUsername = HandlerName.findByUsername;
        public static final String getCurrent = HandlerName.getCurrent;
    }





    // ORDER -------------------------------------------------------

    public static class order {

        public static class manager {
            public static final String path = "cmsapp.core.order.manager";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findFirst = HandlerName.findFirst;
            public static final String save = HandlerName.save;
            public static final String delete = HandlerName.delete;

        }

        public static class courier {
            public static final String path = "cmsapp.core.order.courier";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findFirst = HandlerName.findFirst;
            public static final String findNew = HandlerName.findNew;
            //public static final String save = HandlerName.save;
            //public static final String delete = HandlerName.delete;
            public static final String accept = HandlerName.accept;
            public static final String execute = HandlerName.execute;
            public static final String complete = HandlerName.complete;

        }

        public static class client {
            public static final String path = "cmsapp.core.order.client";

            public static final String findById = HandlerName.findById;
            public static final String findAllById = HandlerName.findAllById;
            public static final String findAll = HandlerName.findAll;
            public static final String findFirst = HandlerName.findFirst;
            public static final String save = HandlerName.save;
            public static final String delete = HandlerName.delete;

        }
    }


//    public static class geodata {
//        public static final String path = "geodata";
//
//        public static final String getRoute = HandlerName.getRoute;
//    }


   /*

    public static class OrderN {
        public static final String path = "shop.entities.order";

        public static final String findById = HandlerName.findById;
        public static final String save = HandlerName.save;
        public static final String delete = HandlerName.delete;
    }


    public static class ProductN {
        public static final String path = "shop.entities.product";

        public static final String findById = HandlerName.findById;
        public static final String findAllById = HandlerName.findAllById;
        public static final String findAll = HandlerName.findAll;
        public static final String findFirst = HandlerName.findFirst;
        public static final String save = HandlerName.save;
        public static final String delete = HandlerName.delete;
    }



    public static class StorageN {
        public static final String path = "shop.entities.storage";

        public static final String findByProductId = "findByProductId";
        public static final String findAllByProductId = "findAllByProductId";
        public static final String findAll = HandlerName.findAll;
        public static final String save = HandlerName.save;     // save ProductItem
        public static final String put =    HandlerName.put;    // increase product count
        public static final String remove = HandlerName.remove; // decrease product count
        public static final String delete = HandlerName.delete; // remove ProductItem
    }

    */

}
