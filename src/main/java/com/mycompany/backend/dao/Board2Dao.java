package com.mycompany.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backend.dto.Board2;
import com.mycompany.backend.dto.Pager;
@Mapper
public interface Board2Dao {
  public List<Board2> selectByPage(Pager pager);
  public int count();
  public Board2 selectByBno(int bno);
  public int selectCurrentBno();
  public int insert(Board2 board);
  public void update(Board2 board);
  public int deleteByBno(int bno);
  public int updateBhitcount(int bno);
}
