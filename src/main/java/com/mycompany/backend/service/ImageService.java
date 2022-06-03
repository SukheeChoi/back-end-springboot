package com.mycompany.backend.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backend.dao.ImageDao;
import com.mycompany.backend.dto.Image;

@Service
public class ImageService {

	@Resource
	private ImageDao imageDao;
	
	public List<Image> getImages(int bno) {
		return imageDao.selectByBno(bno);		
	}
	
}
