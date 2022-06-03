package com.mycompany.backend.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backend.dto.Board2;
@Mapper
public interface Board2Dao {
//  public List<Board2> selectByPage(Pager pager);
//  public int count();
//  public Board selectByBno(int bno);
  public int insert(Board2 board);
  public void update(Board2 board);
  public int deleteByBno(int bno);
//  public int updateBhitcount(int bno);
}
