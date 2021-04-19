package com.taorusb.consolecrundusesjdbc.controller;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.service.RegionService;

import java.util.List;
import java.util.NoSuchElementException;

public class RegionController {

    private RegionService regionService;

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Region> showAll() {
        return regionService.getAllRegions();
    }

    public Region addNewRegion (ResponseStatus responseStatus, String name) {

        Region region = null;
        try {
            region = regionService.saveRegion(new Region(name));
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return region;
    }

    public Region updateRegion(ResponseStatus responseStatus, long id, String name) {

        Region region = null;
        try {
            region = regionService.updateRegion(new Region(id, name));
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
        return region;
    }

    public void deleteRegion(ResponseStatus responseStatus, long id) {

        try {
            regionService.deleteRegion(id);
            responseStatus.setSuccessful();
        } catch (NoSuchElementException e) {
            responseStatus.setElementNotFoundStatus();
        }
    }
}