package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;

public class InsertDirectionController implements Handler {

    private Handler insertEndRequestReceiverController;

    public void setInsertEndRequestReceiverController(Handler insertEndRequestReceiverController) {
        this.insertEndRequestReceiverController = insertEndRequestReceiverController;
    }

    @Override
    public void handle(String[] query) {

        if (checkString(query)) {

            Warehouse reqNumber = getCommandType(query[2]);

            if (reqNumber.getNum() < 11 || reqNumber.getNum() > 13) {
                System.out.println("Incorrect type of model name.");
                return;
            }

            insertEndRequestReceiverController.handle(query);
        }
    }

    private boolean checkString(String[] strings) {

        if (!strings[1].toLowerCase().equals("into")) {
            System.out.println("Incorrect key: " + strings[1]);
            return false;
        }
        return true;
    }
}