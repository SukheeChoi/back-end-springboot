package com.mycompany.backend.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backend.dao.Board2Dao;
import com.mycompany.backend.dao.ImageDao;
import com.mycompany.backend.dto.Board;
import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Image;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

	@Resource
	private ImageDao imageDao;

  public void appendImage(Image image) {
		log.info("실행");
		imageDao.insert(image);
    
  }
}








