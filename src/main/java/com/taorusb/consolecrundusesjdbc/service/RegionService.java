package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Region;

import java.util.List;

public interface RegionService {

    Region getById(Long id);

    Region updateRegion(Region region);

    Region saveRegion(Region region);

    void deleteRegion(Long id);

    List<Region> getAllRegions();

}
