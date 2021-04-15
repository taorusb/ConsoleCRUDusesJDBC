package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;
import com.taorusb.consolecrundusesjdbc.view.ShowPost;
import com.taorusb.consolecrundusesjdbc.view.ShowRegion;
import com.taorusb.consolecrundusesjdbc.view.ShowWriter;

public class SelectEndRequestReceiverController implements Handler {

    private ShowWriter showWriter;
    private ShowPost showPost;
    private ShowRegion showRegion;

    public void setShowWriter(ShowWriter showWriter) {
        this.showWriter = showWriter;
    }

    public void setShowPost(ShowPost showPost) {
        this.showPost = showPost;
    }

    public void setShowRegion(ShowRegion showRegion) {
        this.showRegion = showRegion;
    }

    @Override
    public void handle(String[] query) {

        Warehouse reqNumber = getCommandType(query[3]);

        switch (reqNumber) {
            case WRITERS:
                if (query.length > 4) {
                    System.out.print("Some commands is needless.");
                    break;
                }
                showWriter.showAll();
                break;
            case POSTS:
                if (query.length < 6) {
                    System.out.print("Some commands is missing.");
                    break;
                }
                if (!checkArg(query)) {
                    break;
                }
                showPost.showByWriterId(getFromString(query[5]));
                break;
            case REGIONS:
                if (query.length > 4) {
                    System.out.print("Some command is missing.");
                    break;
                }
                showRegion.showAll();
                break;
        }
    }

    private String getFromString(String id) {
        int idLength = id.length();
        return id.substring(9, idLength);
    }

    private boolean checkArg(String[] arg) {

        if (!arg[4].equalsIgnoreCase("where")) {
            System.out.println("Incorrect command name: " + arg[4]);
            return false;
        } else if (!arg[5].toLowerCase().startsWith("writerid=")) {
            System.out.println("Incorrect argument name: " + arg[5]);
            return false;
        }
        return true;
    }
}