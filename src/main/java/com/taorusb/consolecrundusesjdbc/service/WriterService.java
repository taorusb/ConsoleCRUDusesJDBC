package com.taorusb.consolecrundusesjdbc.service;

import com.taorusb.consolecrundusesjdbc.model.Writer;

import java.util.List;

public interface WriterService {

    Writer getById(Long id);

    Writer saveWriter(Writer writer);

    Writer updateWriter(Writer writer);

    void deleteWriter(Long id);

    List<Writer> getAllWriters();
}
