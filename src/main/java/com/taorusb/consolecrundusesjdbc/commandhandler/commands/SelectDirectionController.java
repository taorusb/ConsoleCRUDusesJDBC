package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;

public class SelectDirectionController implements Handler {

    private Handler selectEndRequestReceiverController;

    public void setSelectEndRequestReceiverController(Handler selectEndRequestReceiverController) {
        this.selectEndRequestReceiverController = selectEndRequestReceiverController;
    }

    @Override
    public void handle(String[] query) {

        if (query.length < 4 || query.length > 6) {
            System.out.println("Something is incorrect, try again.");
            return;
        }

        if (checkString(query)) {
            Warehouse reqNumber = getCommandType(query[3]);

            if (reqNumber.getNum() < 11 || reqNumber.getNum() > 13) {
                System.out.println("Incorrect type of model name.");
                return;
            }

            selectEndRequestReceiverController.handle(query);
        }
    }

    private boolean checkString(String[] strings) {

        if (!strings[1].toLowerCase().equals("all")) {
            System.out.println("Incorrect key: " + strings[1]);
            return false;
        } else if (!strings[2].toLowerCase().equals("from")) {
            System.out.println("Incorrect key: " + strings[2]);
            return false;
        }
        return true;
    }
}