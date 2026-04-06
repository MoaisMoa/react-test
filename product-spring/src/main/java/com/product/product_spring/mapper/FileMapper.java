package com.product.product_spring.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.product.product_spring.dto.Files;

@Mapper
public interface FileMapper {
  List<Files> list();
  Files select(Long no);
  Files selectById(String id);
  int insert(Files files);
  int update(Files files);
  int updateById(Files files);
  int delete(Long no);
  int deleteById(String id);

  public List<Files> listByParent(Files file);
  public int deleteByParent(Files file);
  public int deleteFiles(String noList);
  public int deleteFilesById(String idList);
  public int deleteFileList(@Param("noList") List<Long> noList);
  public int deleteFileListById(@Param("idList") List<String> idList);
  public Files selectByType(Files file);
  public List<Files> listByType(Files file);
}
