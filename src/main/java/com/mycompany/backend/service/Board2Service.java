package com.mycompany.backend.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backend.dao.Board2Dao;
import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Pager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Board2Service {

	@Resource
	private Board2Dao board2Dao;
	
	public List<Board2> getBoards(Pager pager) {
		log.info("실행");
		return board2Dao.selectByPage(pager);
	}
  public List<Board2> getBoardsByMid(Pager pager, String mid) {
    log.info("실행");
    return board2Dao.selectByPageNMid(pager, mid);
  }
//	
//	public Board getBoard(int bno, boolean hit) {
//		log.info("실행");
//		if(hit) {
//			board2Dao.updateBhitcount(bno);
//		}
//		return board2Dao.selectByBno(bno);
//	}
//	
	public int getTotalBoardNum() {
		log.info("실행");
		return board2Dao.count();
	}
	
  public int getTotalBoardNumByMid(String mid) {
    log.info("실행");
    return board2Dao.countByMid(mid);
  }
//	
//	public void writeBoard(Board board) {
//		log.info("실행");
//		board2Dao.insert(board);
//	}
//	
//	public void updateBoard(Board board) {
//		log.info("실행");
//		board2Dao.update(board);
//	}
//	
	
	public Board2 getBoard(int bno, boolean hit) {
		if(hit) {
			board2Dao.updateBhitcount(bno);
		}
		return board2Dao.selectByBno(bno);
	}
	
	public int writeBoard(Board2 board) {
		log.info("실행");
		return board2Dao.insert(board);
	}
	
	public int selectBno() {
	  log.info("실행");
	  return board2Dao.selectCurrentBno();
	}

	public void updateBoard(Board2 board) {
		log.info("실행");
		board2Dao.update(board);
	}

  public int removeBoard(int bno) {
    log.info("실행");
    int result = board2Dao.deleteByBno(bno);
    return result;
  }

  
}
