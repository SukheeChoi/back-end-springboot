package com.mycompany.backend.dao;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface Board2Dao {
//  public List<Board2> selectByPage(Pager pager);
//  public int count();
//  public Board selectByBno(int bno);
//  public int insert(Board board);
  public int deleteByBno(int bno);
//  public int update(Board board);
//  public int updateBhitcount(int bno);
}
