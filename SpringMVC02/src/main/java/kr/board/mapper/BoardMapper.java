package kr.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.board.entity.Board;

@Mapper //Mybatis API
public interface BoardMapper {

	//CRUD 메서드 쓰면 됨
	
	public List<Board> getLists(); //sql을 가지고 있는 현클래스와 동일한 이름의 mapper.xml 파일을 생성해야함
	public void boardInsert(Board vo);
	
	public Board boardContent(int idx);	//상세보기
    
    public void boardDelete(int idx);
    
    public void boardUpdate(Board vo);
    
    //조회수 증가 메서드 - xml에 쓰지 않고 작성하는 방법
    @Update("update myboard set count=count+1 where idx=#{idx}")
    public void boardCount(int idx);
}
