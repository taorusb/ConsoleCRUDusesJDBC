package com.taorusb.consolecrundusesjdbc.view;

import com.taorusb.consolecrundusesjdbc.controller.RegionController;
import com.taorusb.consolecrundusesjdbc.model.Region;

import java.util.List;

import static com.taorusb.consolecrundusesjdbc.controller.Validator.*;
import static java.lang.Long.parseLong;

public class ShowRegion {

    private RegionController regionController;
    private static final String[] template = {"%-8s%-22s%n", "id", "name"};
    private List<Region> regionList;
    private Region container = new Region();

    public ShowRegion(RegionController regionController) {
        this.regionController = regionController;
    }

    public void showAll() {

        regionList = regionController.showAll();

        System.out.printf(template[0], template[1], template[2]);
        regionList.forEach(region -> System.out.printf(template[0], region.getId(), region.getName()));
        System.out.print("\n");
        regionList.clear();
    }

    public void addRegion(String name) {

        String result;
        if (checkString(name)) {

            result = regionController.addNewRegion(new Region(name));

            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }
            showAll();
        }
    }

    public void updateRegion(String id, String name) {

        String result;
        if (checkFields(id, name)) {

            container = new Region(parseLong(id), name);
            result = regionController.updateRegion(container);

            if (result.equals(elementNotFoundError)) {
                System.out.println(elementNotFoundError);
                return;
            }
            printRegion();
        }
    }

    public void deleteRegion(String id) {

        String result;
        if (!checkId(id)) {
            System.out.println(idError);
            return;
        }

        result = regionController.deleteRegion(parseLong(id));

        if (result.equals(elementNotFoundError)) {
            System.out.println(elementNotFoundError);
            return;
        }

        showAll();
    }

    private void printRegion() {
        System.out.printf(template[0], template[1], template[2]);
        System.out.printf(template[0], container.getId(), container.getName());
        System.out.print("\n");
    }

    private boolean checkFields(String id, String name) {

        if (!checkId(id)) {
            System.out.println(idError);
            return false;
        } else if (!checkString(name)) {
            System.out.println(nameError);
            return false;
        }
        return true;
    }
}