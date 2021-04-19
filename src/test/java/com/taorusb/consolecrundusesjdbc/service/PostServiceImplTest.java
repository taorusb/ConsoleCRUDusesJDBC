package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Post;
import com.taorusb.consolecrundusesjdbc.model.Writer;
import com.taorusb.consolecrundusesjdbc.repository.impl.PostRepositoryImpl;
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

public class PostServiceImplTest {

    PostServiceImpl postService = new PostServiceImpl();
    PostRepositoryImpl postRepository = PostRepositoryImpl.getInstance();
    Supplier<Connection> connectionSupplier = mock(Supplier.class);
    Connection connection = mock(Connection.class);
    PreparedStatement preparedStatement = mock(PreparedStatement.class);
    ResultSet resultSet = mock(ResultSet.class);

    @Before
    public void setUp() throws Exception {
        postRepository.setConnectionSupplier(connectionSupplier);
        when(connectionSupplier.get()).thenReturn(connection);

        postService.setPostRepository(postRepository);
    }

    @Test
    public void getById() throws SQLException {

        when(connection.prepareStatement(any(), anyInt(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.getRow()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> postService.getById(1L));

        when(resultSet.getRow()).thenReturn(1);
        setRsActionForGetById();
        Post post = new Post(1L, "Some-content", "12/04/2021", "-", new Writer());

        assertEquals(post, postService.getById(1L));
    }

    @Test
    public void updatePost() throws SQLException {

        Post post = new Post(1L, "Some", "12/11/2020", "-", new Writer(1L));
        when(connection.prepareStatement(any())).thenReturn(preparedStatement);


        when(connection.prepareStatement(any(), anyInt(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.getRow()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> postService.updatePost(post));

        when(resultSet.getRow()).thenReturn(1);

        assertEquals(post, postService.savePost(post));
    }

    @Test
    public void savePost() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);
        Post post = new Post(1L, "Some", "12/11/2020", "-", new Writer(1L));

        assertEquals(post, postService.savePost(post));
    }

    @Test
    public void deletePost() throws SQLException {

        when(connection.prepareStatement(any())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);
        assertThrows(NoSuchElementException.class, () -> postService.deletePost(1L));
    }

    private void setRsActionForGetById() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("content")).thenReturn("Some-content");
        when(resultSet.getString("created")).thenReturn("12/04/2021");
        when(resultSet.getString("updated")).thenReturn("-");
    }

    private void setRsActionForFindAll() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong(anyInt())).thenReturn(1L);
    }
}