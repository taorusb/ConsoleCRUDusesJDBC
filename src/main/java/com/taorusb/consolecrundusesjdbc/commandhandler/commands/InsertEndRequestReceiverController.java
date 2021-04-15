package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;
import com.taorusb.consolecrundusesjdbc.view.ShowPost;
import com.taorusb.consolecrundusesjdbc.view.ShowRegion;
import com.taorusb.consolecrundusesjdbc.view.ShowWriter;

public class InsertEndRequestReceiverController implements Handler {

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

        Warehouse reqNumber = getCommandType(query[2]);

        switch (reqNumber) {
            case WRITERS:
                if (query.length != 6) {
                    System.out.println("Something is incorrect, please try again.");
                    break;
                }
                checkWriterFields(query[3], query[4], query[5]);
                break;
            case POSTS:
                if (query.length != 5) {
                    System.out.println("Something is incorrect, please try again.");
                    break;
                }
                checkPostFields(query[3], query[4]);
                break;
            case REGIONS:
                if (query.length != 4) {
                    System.out.println("Something is incorrect, please try again.");
                    break;
                }
                checkRegionFields(query[3]);
                break;
        }
    }

    private void checkWriterFields(String firstName, String lastName, String regionId) {

        int firstNameLength = firstName.length();
        int lastNameLength = lastName.length();
        int regionIdLength = regionId.length();

        String arg1 = firstName.toLowerCase();
        String arg2 = lastName.toLowerCase();
        String arg3 = regionId.toLowerCase();

        if (!arg1.startsWith("firstname=")) {
            System.out.println("invalid argument name: " + firstName.substring(0, 11));
            return;
        } else if (!arg2.startsWith("lastname=")) {
            System.out.println("invalid argument name: " + lastName.substring(0, 10));
            return;
        } else if (!arg3.startsWith("regionid=")) {
            System.out.println("invalid argument name: " + regionId.substring(0, 10));
            return;
        }

        arg1 = firstName.substring(10, firstNameLength);
        arg2 = lastName.substring(9, lastNameLength);
        arg3 = regionId.substring(9, regionIdLength);

        showWriter.addWriter(arg1, arg2, arg3);
    }

    private void checkPostFields(String writerId, String content) {

        int idLength = writerId.length();
        int postContentLength = content.length();

        String arg1 = writerId.toLowerCase();
        String arg2 = content.toLowerCase();

        if (!arg1.startsWith("writerid=")) {
            System.out.println("invalid argument name: " + writerId);
            return;
        }
        if (!arg2.startsWith("content=")) {
            System.out.println("invalid argument name: " + content);
            return;
        }

        arg1 = arg1.substring(9, idLength);
        arg2 = content.substring(8, postContentLength);

        showPost.addPost(arg1, arg2);
    }

    private void checkRegionFields(String name) {

        int nameLength = name.length();

        String arg1 = name.toLowerCase();

        if (!arg1.startsWith("name=")) {
            System.out.println("invalid argument name: " + name);
            return;
        }

        arg1 = name.substring(5, nameLength);

        showRegion.addRegion(arg1);
    }
}