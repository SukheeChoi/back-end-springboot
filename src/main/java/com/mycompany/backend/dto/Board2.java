package com.mycompany.backend.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Board2 {
	private int bno;
	private String btitle;
	private String bmemo;
	private String mid;
	private int bhitcount;
	private Date bdate;
	private MultipartFile[] imagesArray;
}