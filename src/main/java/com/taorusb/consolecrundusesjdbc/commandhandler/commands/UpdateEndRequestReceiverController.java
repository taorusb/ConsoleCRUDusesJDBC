package com.taorusb.consolecrundusesjdbc.commandhandler.commands;

import com.taorusb.consolecrundusesjdbc.commandhandler.Handler;
import com.taorusb.consolecrundusesjdbc.commandhandler.Warehouse;
import com.taorusb.consolecrundusesjdbc.view.ShowPost;
import com.taorusb.consolecrundusesjdbc.view.ShowRegion;
import com.taorusb.consolecrundusesjdbc.view.ShowWriter;

public class UpdateEndRequestReceiverController implements Handler {

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

        Warehouse reqNumber = getCommandType(query[1]);

        switch (reqNumber) {
            case WRITERS:
                if (query.length != 7) {
                    System.out.println("Something is incorrect, please try again.");
                    break;
                }
                checkWriterField(query[3], query[4], query[5], query[6]);
                break;
            case POSTS:
                if (query.length != 5) {
                    System.out.println("Something is incorrect, please try again.");
                    break;
                }
                checkPostField(query[3], query[4]);
                break;
            case REGIONS:
                if (query.length != 5) {
                    System.out.println("Something is incorrect, please try again..");
                    break;
                }
                checkRegionField(query[3], query[4]);
                break;
        }

    }

    private void checkWriterField(String id, String firstName, String lastName, String regionId) {

        int idLength = id.length();
        int firstNameLength = firstName.length();
        int lastNameLength = lastName.length();
        int regionIdLength = regionId.length();

        String arg1 = id.toLowerCase();
        String arg2 = firstName.toLowerCase();
        String arg3 = lastName.toLowerCase();
        String arg4 = regionId.toLowerCase();

        if (!arg1.startsWith("id=")) {
            System.out.println("invalid argument name: " + id);
            return;
        } else if (!arg2.startsWith("firstname=")) {
            System.out.println("invalid argument name: " + firstName);
            return;
        } else if (!arg3.startsWith("lastname=")) {
            System.out.println("invalid argument name: " + lastName);
            return;
        } else if (!arg4.startsWith("regionid=")) {
            System.out.println("invalid argument name: " + regionId.substring(0, 10));
            return;
        }

        arg1 = arg1.substring(3, idLength);
        arg2 = firstName.substring(10, firstNameLength);
        arg3 = lastName.substring(9, lastNameLength);
        arg4 = regionId.substring(9, regionIdLength);

        showWriter.updateWriter(arg1, arg2, arg3, arg4);
    }

    private void checkPostField(String id, String content) {

        int idLength = id.length();
        int contentLength = content.length();

        String arg1 = id.toLowerCase();
        String arg2 = content.toLowerCase();

        if (!arg1.startsWith("id=")) {
            System.out.println("invalid argument name: " + id);
            return;
        }
        if (!arg2.startsWith("content=")) {
            System.out.println("invalid argument name: " + content);
            return;
        }

        arg1 = arg1.substring(3, idLength);
        arg2 = content.substring(8, contentLength);

        showPost.updatePost(arg1, arg2);
    }

    private void checkRegionField(String id, String name) {

        int idLength = id.length();
        int nameLength = name.length();

        String arg1 = id.toLowerCase();
        String arg2 = name.toLowerCase();

        if (!arg1.startsWith("id=")) {
            System.out.println("invalid argument name: " + id);
            return;
        }
        if (!arg2.startsWith("name=")) {
            System.out.println("invalid argument name: " + name);
            return;
        }

        arg1 = arg1.substring(3, idLength);
        arg2 = name.substring(5, nameLength);

        showRegion.updateRegion(arg1, arg2);
    }
}
