package com.mycompany.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backend.dto.Image;

@Mapper
public interface ImageDao {
	public List<Image> selectByBno(int bno);
	public Image selectByIno(int ino);
	public int insert(Image image);
	public int deleteByBno(int bno);
	public int deleteByIno(int ino);
	
}
