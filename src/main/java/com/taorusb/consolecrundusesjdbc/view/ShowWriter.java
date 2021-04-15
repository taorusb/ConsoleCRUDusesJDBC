package com.taorusb.consolecrundusesjdbc.view;

import com.taorusb.consolecrundusesjdbc.controller.WriterController;
import com.taorusb.consolecrundusesjdbc.model.Writer;

import java.util.List;

import static com.taorusb.consolecrundusesjdbc.controller.Validator.*;
import static java.lang.Long.parseLong;

public class ShowWriter {

    private static final String[] template = {"%-8s%-16s%-16s%-16s%-16s%-8s%n", "id", "firstName", "lastName", "regionId", "postCount", "role"};
    private WriterController writerController;
    private List<Writer> container;

    public ShowWriter(WriterController writerController) {
        this.writerController = writerController;
    }

    public void showAll() {
        container = writerController.showAll();
        printWriters();
        container.clear();
    }

    public void addWriter(String firstName, String lastName, String regionId) {

        String result;
        if (checkFields(firstName, lastName) || checkId(regionId)) {

            result = writerController.addNewWriter(firstName, lastName, parseLong(regionId));

            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }

            showAll();
        }
    }

    public void updateWriter(String id, String firstName, String lastName, String regionId) {

        String result;
        if (checkId(id) || checkId(regionId) || checkFields(firstName, lastName)) {

            result = writerController.updateWriter(parseLong(id), firstName, lastName, parseLong(regionId));

            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }

            showAll();
        }
    }

    public void deleteWriter(String id) {

        String result;
        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        result = writerController.deleteWriter(parseLong(id));

        if (result.equals(elementNotFoundError)) {
            System.out.println(elementNotFoundError);
            return;
        }

        showAll();
    }

    private void printWriters() {
        System.out.printf(template[0], template[1], template[2], template[3], template[4], template[5], template[6]);
        container
                .forEach(x -> System.out.printf
                        (template[0], x.getId(), x.getFirstName(), x.getLastName(), x.getRegion().getId(), x.getPostsCount(), x.getRole().name()));
        System.out.print("\n");
    }

    private boolean checkFields(String firstName, String lastName) {
        if (!checkString(firstName) || !checkString(lastName)) {
            System.out.println(nameError);
            return false;
        }
        return true;
    }
}