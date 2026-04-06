package com.product.product_spring.service;

import java.util.List;

import com.product.product_spring.dto.Files;

import jakarta.servlet.http.HttpServletResponse;

public interface FileService {
    List<Files> list();
    Files select(Long no);
    Files selectById(String id);
    boolean insert(Files entity);
    boolean update(Files entity);
    boolean updateById(Files entity);
    boolean delete(Long no);
    boolean deleteById(String id);

    public boolean upload(Files file) throws Exception;
    public int upload(List<Files> fileList) throws Exception;
    public boolean download(String id, HttpServletResponse response) throws Exception;
    public boolean thumbnail(String id, HttpServletResponse response) throws Exception;
    public List<Files> listByParent(Files file);
    public int deleteByParent(Files file);
    public int deleteFiles(String noList);
    public int deleteFilesByID(String idList);
    public int deleteFileList(List<Long> noList);
    public int deleteFileListById(List<String> idList);
    public Files selectByType(Files file);
    public List<Files> listByType(Files file);
}