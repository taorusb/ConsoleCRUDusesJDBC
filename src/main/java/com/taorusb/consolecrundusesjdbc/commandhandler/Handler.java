package com.taorusb.consolecrundusesjdbc.commandhandler;


public interface Handler {

    void handle(String[] query);

    default Warehouse getCommandType(String s) {
        Warehouse key;
        try {
            key = Warehouse.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            key = Warehouse.ERROR;
        }
        return key;
    }
}