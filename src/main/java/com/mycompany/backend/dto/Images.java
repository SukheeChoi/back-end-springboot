package com.mycompany.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Images {
	private int ino;
	private int bno;
	private String imgoname;
	private String imgsname;
	private String imgtype;
	private MultipartFile img;
}
