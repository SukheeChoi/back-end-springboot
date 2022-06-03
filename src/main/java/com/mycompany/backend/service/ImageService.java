package com.mycompany.backend.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backend.dao.ImageDao;
import com.mycompany.backend.dto.Image;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ImageService {

	@Resource
	private ImageDao imageDao;

	public List<Image> getImages(int bno) {
		return imageDao.selectByBno(bno);
	}

	public Image getImage(int ino) {
		return imageDao.selectByIno(ino);
	}
	
	public void appendImage(Image image) {
		log.info("실행");
		imageDao.insert(image);
	}

}
