package com.taorusb.consolecrundusesjdbc.controller;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.service.RegionService;
import com.taorusb.consolecrundusesjdbc.service.WriterService;

import java.util.List;
import java.util.NoSuchElementException;

import static com.taorusb.consolecrundusesjdbc.controller.Validator.*;

public class WriterController {

    private WriterService writerService;
    private RegionService regionService;

    public void setWriterService(WriterService writerService) {
        this.writerService = writerService;
    }

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public List<Writer> showAll() {
        return writerService.getAllWriters();
    }

    public String addNewWriter(String firstName, String lastName, long regionId) {
        try {
            Region region = regionService.getById(regionId);
            writerService.saveWriter(new Writer(firstName, lastName, region));
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }

        return successful;
    }

    public String updateWriter(long id, String firstName, String lastName, long regionId) {

        try {
            Region region = regionService.getById(regionId);
            writerService.updateWriter(new Writer(id, firstName, lastName, region));
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }

    public String deleteWriter(long id) {

        try {
            writerService.deleteWriter(id);
        } catch (NoSuchElementException e) {
            return elementNotFoundError;
        }
        return successful;
    }
}