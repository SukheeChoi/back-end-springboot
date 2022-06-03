package com.mycompany.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Image;
import com.mycompany.backend.service.Board2Service;
import com.mycompany.backend.service.ImageService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/board2")
public class Board2Controller {
	@Resource
	Board2Service board2Service;
	@Resource
	ImageService imageService;

	@PostMapping("/")
	public Board2 create(@RequestBody Board2 board, MultipartFile[] imagesArray) {
		log.info("실행");
		if (imagesArray != null) {
			log.info("imagesArray에 값이 넘어왔다~~~~");
		}
		// 사진을 제외한 게시물의 내용(제목, 메모, 작성자, 생성일, 조회수) 저장.
		board2Service.writeBoard(board);

		// 최대 3개까지 사진 저장.
		for (int i = 0; i < imagesArray.length; i++) {
			Image image = new Image();
			MultipartFile mf = imagesArray[i];
			image.setImgoname(mf.getOriginalFilename());
			image.setImgoname(new Date().getTime() + "-" + mf.getOriginalFilename());
			image.setImgtype(mf.getContentType());
			try {
				File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
				mf.transferTo(file);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			imageService.appendImage(image);

		}
		// 저장한 게시물정보 가져오기.(게시물+사진 각각 가져와서 전송해야 함.)
//    Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
		Board2 dbBoard = null;//////////
		return dbBoard;
	}

	@PutMapping("/")
	public Board2 update(@RequestBody Board2 board, @RequestBody MultipartFile[] imagesArray) {
		log.info("실행");

		// 사진을 제외한 게시물의 내용(제목, 메모, 작성자, 생성일, 조회수) 저장.
		board2Service.updateBoard(board);

		// 저장된 사진 최대 3개를 모두 삭제.
		imageService.deleteImageByBno(board.getBno());
		// 최대 3개까지 사진 저장.
		for (int i = 0; i < imagesArray.length; i++) {
			Image image = new Image();
			MultipartFile mf = image.getImg();
			image.setImgoname(mf.getOriginalFilename());
			image.setImgoname(new Date().getTime() + "-" + mf.getOriginalFilename());
			image.setImgtype(mf.getContentType());
			try {
				File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
				mf.transferTo(file);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			imageService.appendImage(image);

		}

//    Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
		Board2 dbBoard = null;//////////
		return dbBoard;
	}

	// 이미지 전체 이름만 준다고 생각
	@GetMapping("/images/{bno}")
	public ResponseEntity<Object> getImages(@PathVariable int bno) {
		log.info("실행");
		List<Image> list = imageService.getImages(bno);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("images", list);
		String json = jsonObject.toString();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(json);
	}

	// 이미지 하나씩 가져오는 메소드
	@GetMapping("/image/{ino}")
	public ResponseEntity<InputStreamResource> download(@PathVariable int ino) throws Exception {
		Image image = imageService.getImage(ino);
		String imageoname = image.getImgoname();
		if (imageoname == null)
			return null;

		imageoname = new String(imageoname.getBytes("UTF-8"), "ISO-8859-1");

		FileInputStream fis = new FileInputStream("C:/Temp/uploadfiles/" + image.getImgsname());
		InputStreamResource resource = new InputStreamResource(fis);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageoname + "\";")
				.header(HttpHeaders.CONTENT_TYPE, image.getImgtype()).body(resource);
	}

}
