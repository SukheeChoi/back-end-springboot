package com.mycompany.backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Image;
import com.mycompany.backend.dto.Pager;
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

	@GetMapping("/{bno}")
	public Board2 read(@PathVariable int bno, @RequestParam(defaultValue = "false") boolean hit) {
		return board2Service.getBoard(bno, hit);
	}
	
	@GetMapping("/list")
	public Map<String, Object> list(@RequestParam(defaultValue="1") int pageNo) {
		log.info("실행");
		int totalRows = board2Service.getTotalBoardNum();
		Pager pager = new Pager(12, 10, totalRows, pageNo);
		List<Board2> list = board2Service.getBoards(pager);
		Map<String, Object> map = new HashMap<>();
		map.put("boards", list);
		map.put("pager", pager);
		return map;
	}

	@PostMapping("/")
//public Board2 create(Board2 board, MultipartHttpServletRequest mtfRequest) {
	public Board2 create(Board2 board, MultipartFile[] imagesArray) {
		log.info("실행");
// 사진을 제외한 게시물의 내용(제목, 메모, 작성자, 생성일, 조회수) 저장.
		board2Service.writeBoard(board);
		int bno = board2Service.selectBno();
		log.info("bno : " + bno);
//List<MultipartFile> imagesArray = mtfRequest.getFiles("File");
//log.info("imagesArray.size() : " + imagesArray.size());
//for(MultipartFile mf : imagesArray) {
//  Image image = new Image();
//  image.setImgoname(mf.getOriginalFilename());
//  image.setImgsname(new Date().getTime() + "-" + mf.getOriginalFilename());
//  image.setImgtype(mf.getContentType());
//  image.setBno(bno);
//  try {
//    File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
//    mf.transferTo(file);
//  } catch(Exception e) {
//    log.error(e.getMessage());
//  }
//  imageService.appendImage(image);
//}

// 최대 3개까지 사진 저장.
		for (int i = 0; i < imagesArray.length; i++) {
			MultipartFile mf = imagesArray[i];
			Image image = new Image();
			image.setBno(bno);
			image.setImgoname(mf.getOriginalFilename());
			image.setImgsname(new Date().getTime() + "-" + mf.getOriginalFilename());
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
//Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
		Board2 dbBoard = null;//////////
		return board;
	}

	@PutMapping("/")
	public Board2 update(Board2 board, MultipartFile[] imagesArray) {
		log.info("실행");

		// 사진을 제외한 게시물의 내용(제목, 메모, 작성자, 생성일, 조회수) 저장.
		board2Service.updateBoard(board);

		// 저장된 사진 최대 3개를 모두 삭제.
		imageService.deleteImageByBno(board.getBno());
		// 최대 3개까지 사진 저장.
		for (int i = 0; i < imagesArray.length; i++) {
			MultipartFile mf = imagesArray[i];
			Image image = new Image();
			image.setBno(board.getBno());
			image.setImgoname(mf.getOriginalFilename());
			image.setImgsname(new Date().getTime() + "-" + mf.getOriginalFilename());
			image.setImgtype(mf.getContentType());
			try {
				File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
				mf.transferTo(file);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			imageService.appendImage(image);
		}

//  Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
		Board2 dbBoard = null;
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

	@DeleteMapping("/{bno}")
	public Map<String, String> delete(@PathVariable int bno) {
		// bno에 해당하는 사진 모두 삭제.
		int imgResult = imageService.deleteImageByBno(bno);
		log.info("~~~~~~~~~~~~~~~~imgResult : " + imgResult);

		// bno에 해당하는 게시글의 내용 삭제.
		int boardResult = board2Service.removeBoard(bno);
		log.info("~~~~~~~~~~~~~~~~boardResult : " + boardResult);

		// 삭제 결과 응답하기.
		Map<String, String> map = new HashMap<>();
		if (boardResult + imgResult == 2) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}

}
