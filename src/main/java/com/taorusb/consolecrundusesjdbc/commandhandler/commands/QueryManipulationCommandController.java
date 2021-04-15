package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;

public class QueryManipulationCommandController implements Handler {

    private Handler selectDirectionController;
    private Handler insertDirectionController;
    private Handler deleteDirectionController;
    private Handler updateDirectionController;

    public void setSelectDirectionController(Handler selectDirectionController) {
        this.selectDirectionController = selectDirectionController;
    }

    public void setInsertDirectionController(Handler insertDirectionController) {
        this.insertDirectionController = insertDirectionController;
    }

    public void setDeleteDirectionController(Handler deleteDirectionController) {
        this.deleteDirectionController = deleteDirectionController;
    }

    public void setUpdateDirectionController(Handler updateDirectionController) {
        this.updateDirectionController = updateDirectionController;
    }

    @Override
    public void handle(String[] query) {

        if (query.length <= 1) {
            System.out.println("Something is missing, try again.");
            return;
        }

        Warehouse reqNumber = getCommandType(query[0]);

        switch (reqNumber) {
            case SELECT:
                selectDirectionController.handle(query);
                break;
            case INSERT:
                insertDirectionController.handle(query);
                break;
            case DELETE:
                deleteDirectionController.handle(query);
                break;
            case UPDATE:
                updateDirectionController.handle(query);
                break;
            default:
                System.out.println("Incorrect command: " + query[0]);
        }
    }
}