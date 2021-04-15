package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;

public class UpdateDirectionController implements Handler {

    private Handler endRequestReceiverController;

    public void setEndRequestReceiverController(Handler endRequestReceiverController) {
        this.endRequestReceiverController = endRequestReceiverController;
    }

    @Override
    public void handle(String[] query) {

        if (query.length < 5 || query.length > 7) {
            System.out.println("Something is incorrect, please try again.");
            return;
        }

        int reqNumber = getCommandType(query[1]).getNum();

        if (reqNumber < 11 || reqNumber > 13) {
            System.out.println("Incorrect type of model name.");
            return;
        } else if (!query[2].toLowerCase().equals("where")) {
            System.out.println("Incorrect key: " + query[2]);
            return;
        }

        endRequestReceiverController.handle(query);
    }
}