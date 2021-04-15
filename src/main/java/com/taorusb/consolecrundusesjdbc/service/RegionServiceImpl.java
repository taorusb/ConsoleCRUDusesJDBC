package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.repository.RegionRepository;

import java.util.List;

public class RegionServiceImpl implements RegionService {

    private RegionRepository regionRepository;

    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region getById(Long id) {
        return regionRepository.getById(id);
    }

    @Override
    public Region updateRegion(Region region) {
        return regionRepository.update(region);
    }

    @Override
    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    @Override
    public void deleteRegion(Long id) {
        regionRepository.deleteById(id);
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }
}