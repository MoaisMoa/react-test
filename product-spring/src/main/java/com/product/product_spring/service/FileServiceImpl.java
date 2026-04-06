package com.product.product_spring.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import com.product.product_spring.dto.Files;
import com.product.product_spring.mapper.FileMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final ResourceLoader resourceLoader;

    @Value("${upload.path}")
    private String uploadPath;

    
    @Override
    public List<Files> list() {
        return fileMapper.list();
    }

    @Override
    public Files select(Long no) {
        return fileMapper.select(no);
    }
    
    @Override
    public Files selectById(String id) {
        return fileMapper.selectById(id);
    }

    

    @Override
    public boolean insert(Files files) {
        int result = fileMapper.insert(files);
        return result > 0;
    }
    
    @Override
    public boolean update(Files files) {
        int result = fileMapper.update(files);
        return result > 0;
    }

    @Override
    public boolean updateById(Files files) {
        int result = fileMapper.updateById(files);
        return result > 0;
    }


    public boolean delete(Files files) {
        if(files == null) {
            return false;
        }
        String filePath = files.getFilePath();
        File deleteFile = new File(filePath);

        if(!deleteFile.exists()) {
            return false;
        }

        boolean deleted = deleteFile.delete();
        return deleted;
    }

    @Override
    public boolean delete(Long no) {
        Files files = fileMapper.select(no);
        delete(files);
        int result = fileMapper.delete(no);
        return result > 0;
    }

    @Override
    public boolean deleteById(String id) {
        Files files = fileMapper.selectById(id);
        delete(files);
        int result = fileMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean upload(Files file) throws Exception {
        boolean result = false;
        MultipartFile multipartFile = file.getData();
        if(multipartFile == null || multipartFile.isEmpty()) {
            return false;
        }

        String originName = multipartFile.getOriginalFilename();
        long fileSize = multipartFile.getSize();
        byte[] fileData = multipartFile.getBytes();
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        String filePath = uploadPath + "/" + fileName;
        File uploadFile = new File(filePath);
        FileCopyUtils.copy(fileData, uploadFile);

        file.setOriginName(originName);
        file.setFileName(fileName);
        file.setFilePath(filePath);
        file.setFileSize(fileSize);
        result = fileMapper.insert(file) > 0;
        return result;
    }

    @Override
    public int upload(List<Files> fileList) throws Exception {
        int result = 0;
        if (fileList == null || fileList.isEmpty()) {
            return result;
        }

        for(Files file : fileList){
            result += (upload(file)?1:0);
        }
        return result;
    }

    @Override
    public List<Files> listByParent(Files file) {
        return fileMapper.listByParent(file);
    }

    @Override
    public List<Files> listByType(Files file) {
        return fileMapper.listByType(file);
    }

    @Override
    public int deleteByParent(Files file) {
        List<Files> fileList = fileMapper.listByParent(file);

        for(Files deleteFile : fileList) {
            delete(deleteFile);
        }

        return fileMapper.deleteByParent(file);
    }

    @Override
    public int deleteFiles(String noList) {
        if(noList == null || noList.isEmpty()) return 0;
        
        int count = 0;
        String[] nos = noList.split(",");
        for(String noStr : nos){
            Long no = Long.parseLong(noStr);
            count += (delete(no) ? 1:0);
        }
        return count;
    }    
    
    @Override
    public int deleteFilesByID(String idList) {
        if(idList == null || idList.isEmpty()) return 0;

        int count = 0;
        String[] ids = idList.split(",");
        for(String id : ids){
            count += (deleteById(id) ? 1:0);
        }
        return count;
    }

    @Override
    public int deleteFileList(List<Long> noList) {
        if(noList == null || noList.isEmpty()) return 0;
        for(Long no : noList){
            Files file = select(no.longValue());
            delete(file);
        }
        int count = fileMapper.deleteFileList(noList);
        return count;
    }

    @Override
    public int deleteFileListById(List<String> idList) {
        if(idList == null || idList.isEmpty()) return 0;
        for(String id : idList) {
            Files file = selectById(id);
            delete(file);
        }
        int count = fileMapper.deleteFileListById(idList);
        return count;
    }
    
    @Override
    public Files selectByType(Files file) {
        return fileMapper.selectByType(file);
    }

    @Override
    public boolean download(String id, HttpServletResponse response) throws Exception {
        Files file = selectById(id);
        if (file == null || file.getFilePath() == null) {
            return false;
        }

        File downloadFile = new File(file.getFilePath());
        if (!downloadFile.exists()) {
            return false;
        }

        String fileName = file.getOriginName() != null ? file.getOriginName() : downloadFile.getName();
        String encodedName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedName);
        response.setContentLengthLong(downloadFile.length());

        try (FileInputStream fis = new FileInputStream(downloadFile);
             ServletOutputStream sos = response.getOutputStream()) {
            return FileCopyUtils.copy(fis, sos) > 0;
        }
    }
    
    @Override
    public boolean thumbnail(String id, HttpServletResponse response) throws Exception {
        Files file = selectById(id);
        String filePath = file != null ? file.getFilePath() : null;

        File imgFile;
        Resource resource = resourceLoader.getResource("classpath:static/img/no-image.png");
        if( filePath == null || !(imgFile = new File(filePath)).exists() ) {
        imgFile = resource.getFile();
        filePath = imgFile.getPath();
        }

        // 확장자 
        // C:/upload/2026.02.06-강아지.png
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
        String mimeType = MimeTypeUtils.parseMimeType("image/" + ext).toString();
        MediaType mType = MediaType.valueOf(mimeType);

        if( mType == null ) {
        // 이미지 타입이 아닌 경우
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        imgFile = resource.getFile();
        } else {
        // 이미지 타입
        response.setContentType(mType.toString());
        }

        FileInputStream fis = new FileInputStream(imgFile);     // 파일 입력
        ServletOutputStream sos = response.getOutputStream();   // 파일 출력
        int result = FileCopyUtils.copy(fis, sos);              // 파일 전송
        return result > 0;
    }








}
