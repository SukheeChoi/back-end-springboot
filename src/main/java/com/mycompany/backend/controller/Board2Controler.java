package com.mycompany.backend.controller;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Image;
import com.mycompany.backend.service.Board2Service;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/board2")
public class Board2Controler{
  @Resource
  Board2Service board2Service;
	
  @PostMapping("/")
  public Board2 create(@RequestBody Board2 board, @RequestBody Image[] imagesArray) {
    log.info("실행");
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
      board2Service.appendImage(image);
      
    }
//    Board2 dbBoard = board2Service.getBoard(board.getBno(), false);
    Board2 dbBoard = null;//////////
    return dbBoard;
  }
  
}
