package com.taorusb.consolecrundusesjdbc.controller;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.service.RegionService;

import java.util.List;
import java.util.NoSuchElementException;

import static com.taorusb.consolecrundusesjdbc.controller.Validator.*;

public class RegionController {

    private RegionService regionService;

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Region> showAll() {
        return regionService.getAllRegions();
    }

    public String addNewRegion(Region region) {

        try {
            regionService.saveRegion(region);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String updateRegion(Region region) {

        try {
            regionService.updateRegion(region);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String deleteRegion(long id) {

        try {
            regionService.deleteRegion(id);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }
}