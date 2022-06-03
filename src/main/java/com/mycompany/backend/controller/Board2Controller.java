package com.mycompany.backend.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Image;
import com.mycompany.backend.service.Board2Service;
import com.mycompany.backend.service.ImageService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/board2")
public class Board2Controller{
  @Resource
  Board2Service board2Service;
  @Resource
  ImageService imageService;
	
  @PostMapping("/")
  public Board2 create(Board2 board, MultipartHttpServletRequest mtfRequest) {
//    public Board2 create(Board2 board) {
//    public Board2 create(Board2 board, MultipartFile[] imagesArray) {
    log.info("실행");
//    if(imagesArray != null) {
//      log.info("imagesArray에 값이 넘어왔다~~~~");
//    }
    // 사진을 제외한 게시물의 내용(제목, 메모, 작성자, 생성일, 조회수) 저장.
    board2Service.writeBoard(board);
    int bno = board2Service.selectBno();
    log.info("bno : " + bno);
    
    //
    List<MultipartFile> imagesArray = mtfRequest.getFiles("File");
    log.info("imagesArray.size() : " + imagesArray.size());
    for(MultipartFile mf : imagesArray) {
      Image image = new Image();
      image.setImgoname(mf.getOriginalFilename());
      image.setImgsname(new Date().getTime() + "-" + mf.getOriginalFilename());
      image.setImgtype(mf.getContentType());
      image.setBno(bno);
      try {
        File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
        mf.transferTo(file);
      } catch(Exception e) {
        log.error(e.getMessage());
      }
      imageService.appendImage(image);
    }
    
    // 최대 3개까지 사진 저장.
//    for(int i=0; i<imagesArray.length; i++) {
//      MultipartFile mf = imagesArray[i];
//      Image image = new Image();
//      image.setBno(bno);
//      image.setImgoname(mf.getOriginalFilename());
//      image.setImgsname(new Date().getTime() + "-" + mf.getOriginalFilename());
//      image.setImgtype(mf.getContentType());
//      try {
//        File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
//        mf.transferTo(file);
//      } catch(Exception e) {
//        log.error(e.getMessage());
//      }
//      imageService.appendImage(image);
//      
//    }
    // 저장한 게시물정보 가져오기.(게시물+사진 각각 가져와서 전송해야 함.)
//    Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
    Board2 dbBoard = null;//////////
    return board;
  }
  
  @PutMapping("/")
  public Board2 update(@RequestBody Board2 board, @RequestBody MultipartFile[] imagesArray) {
    log.info("실행");

    // 사진을 제외한 게시물의 내용(제목, 메모, 작성자, 생성일, 조회수) 저장.
    board2Service.updateBoard(board);
    
    // 저장된 사진 최대 3개를 모두 삭제.
    imageService.deleteImageByBno(board.getBno());
    // 최대 3개까지 사진 저장.
    for(int i=0; i<imagesArray.length; i++) {
      Image image = new Image();
      MultipartFile mf = image.getImg();
      image.setImgoname(mf.getOriginalFilename());
      image.setImgoname(new Date().getTime() + "-" + mf.getOriginalFilename());
      image.setImgtype(mf.getContentType());
      try {
        File file = new File("/Users/choisukhee/Osstem/temp/uploadfiles/" + image.getImgsname());
        mf.transferTo(file);
      } catch(Exception e) {
        log.error(e.getMessage());
      }
      imageService.appendImage(image);
      
    }
    
//    Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
    Board2 dbBoard = null;
    return dbBoard;
  }
  
}
