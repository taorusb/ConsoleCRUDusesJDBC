package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.repository.impl.RegionRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RegionServiceImplTest {

    RegionServiceImpl regionService = new RegionServiceImpl();
    RegionRepositoryImpl regionRepository = RegionRepositoryImpl.getInstance();
    Supplier<Connection> connectionSupplier = mock(Supplier.class);
    Connection connection = mock(Connection.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    @Before
    public void setUp() throws Exception {
        regionRepository.setConnectionSupplier(connectionSupplier);
        when(connectionSupplier.get()).thenReturn(connection);

        regionService.setRegionRepository(regionRepository);
    }

    @Test
    public void getById() throws SQLException {

        when(connection.prepareStatement(any(), anyInt(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.getRow()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> regionService.getById(1L));

        when(resultSet.getRow()).thenReturn(1);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("name");

        Region region = new Region(1L, "name");

        assertEquals(region, regionService.getById(1L));
    }

    @Test
    public void updateRegion() throws SQLException {

        Region region = new Region(1L, "name");

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> regionService.updateRegion(region));

        when(preparedStatement.executeUpdate()).thenReturn(1);
        assertEquals(region, regionService.updateRegion(region));
    }

    @Test
    public void saveRegion() throws SQLException {
        Region region = new Region(1L, "name");
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        assertEquals(region, regionService.saveRegion(region));
    }

    @Test
    public void deleteRegion() throws SQLException {
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> regionService.deleteRegion(1L));
    }

    @Test
    public void getAllRegions() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1L);

        assertEquals(3, regionService.getAllRegions().size());
    }
}