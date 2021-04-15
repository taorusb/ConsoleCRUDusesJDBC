package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;

public class DeleteDirectionController implements Handler {

    private Handler endRequestReceiverController;

    public void setEndRequestReceiverController(Handler endRequestReceiverController) {
        this.endRequestReceiverController = endRequestReceiverController;
    }

    @Override
    public void handle(String[] query) {

        if (query.length != 5) {
            System.out.println("Something is incorrect, please try again.");
            return;
        }

        if (checkString(query)) {

            Warehouse reqNumber = getCommandType(query[2]);

            if (reqNumber.getNum() < 11 || reqNumber.getNum() > 13) {
                System.out.println("Incorrect type of model name.");
                return;
            }

            endRequestReceiverController.handle(query);
        }
    }

    private boolean checkString(String[] strings) {

        if (!strings[1].toLowerCase().equals("from")) {
            System.out.println("Incorrect key: " + strings[1]);
            return false;
        }
        return true;
    }
}