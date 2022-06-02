package com.mycompany.backend.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
@RestController
@Log4j2
public class ErrorHandlerController implements ErrorController {
  @RequestMapping("/error")
  public ResponseEntity<String> error(HttpServletResponse response, HttpServletRequest request) {
    int status = response.getStatus();
    log.info("status : " + status);
    log.info("request : " + request);
    log.info("request.getHeader(\"referer\") : " + request.getHeader("referer"));
    if(status == 404) {
//      return ResponseEntity
//                          .status(HttpStatus.MOVED_PERMANENTLY) // 301에러.
//                          .location(URI.create("/")) // Redirect 에러처리.
//                          .body("");
      
      try { request.getRequestDispatcher("/").forward(request, response); } catch(Exception e) {}
      return null;

    } else {
      return ResponseEntity
                          .status(status)
                          .body("");
    }
  }
}
