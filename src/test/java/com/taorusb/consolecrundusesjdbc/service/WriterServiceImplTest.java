package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Region;
import com.taorusb.consolecrundusesjdbc.model.Role;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.repository.impl.WriterRepositoryImpl;
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

public class WriterServiceImplTest {

    WriterServiceImpl writerService = new WriterServiceImpl();
    WriterRepositoryImpl writerRepository = WriterRepositoryImpl.getInstance();
    Supplier<Connection> connectionSupplier = mock(Supplier.class);
    Connection connection = mock(Connection.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    @Before
    public void setUp() throws Exception {
        writerRepository.setConnectionSupplier(connectionSupplier);

        when(connectionSupplier.get()).thenReturn(connection);

        writerService.setWriterRepository(writerRepository);
    }

    @Test
    public void getById() throws SQLException {

        when(connection.prepareStatement(any(), anyInt(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.getRow()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> writerService.getById(1L));

        when(resultSet.getRow()).thenReturn(1);
        setRsActionForGetById();
        Writer writer = new Writer(1L,"Ivan", "Ivanov", new Region(1L));
        writer.setRole(Role.USER);

        Writer fromRepo = writerService.getById(1L);

        assertEquals(writer, fromRepo);
        assertEquals(4, fromRepo.getPosts().size());
    }

    @Test
    public void saveWriter() throws SQLException {

        Writer writer = new Writer(1L, "Ivan", "Ivanov", new Region(1L));
        writer.setRole(Role.USER);

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        assertEquals(writer, writerService.saveWriter(writer));
    }

    @Test
    public void updateWriter() throws SQLException {

        Writer writer = new Writer(1L, "Ivan", "Ivanov", new Region(1L));

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> writerService.updateWriter(writer));

        when(preparedStatement.executeUpdate()).thenReturn(1);
        assertEquals(writer, writerService.updateWriter(writer));
    }

    @Test
    public void deleteWriter() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> writerService.deleteWriter(1L));
    }

    @Test
    public void getAllWriters() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        setRsActionForFindAll();

        assertEquals(3, writerService.getAllWriters().size());
    }

    private void setRsActionForGetById() throws SQLException {

        when(resultSet.getLong("p.id")).thenReturn(1L);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("region_id")).thenReturn(1L);

        when(resultSet.getLong("w.id")).thenReturn(1L);
        when(resultSet.getString("first_name")).thenReturn("Ivan");
        when(resultSet.getString("last_name")).thenReturn("Ivanov");
        when(resultSet.getString("role")).thenReturn("USER");

    }

    private void setRsActionForFindAll() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("w.id")).thenReturn(1L);
        when(resultSet.getString("role")).thenReturn("USER");
        when(resultSet.getLong("p.id")).thenReturn(0L);
    }
}