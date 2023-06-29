package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@RequestMapping("/board")	// URI + 전송방식을 사용하기 위해서 경로에 통일성을 주어 간략화

@RestController	//모든 객체를 JSON 응답을 하겠다.
public class BoardRestController { //ajax 통신용 클래스임 = 즉, ResponseBody 생략가능
	//ResponseBody = ajax 통신
	
	//BoardController 에서 옮겨보자
	//@Autowired
	@Autowired
	BoardMapper boardMapper;	//BoardMapper라는 객체를 만들어서 사용해야하므로
	
	//@RequestMapping("/boardList.do")
	@GetMapping("/all")
	public  List<Board> boardList(){	//객체타입을 리턴한다는 의미
		List<Board> list = boardMapper.getLists();
		
		return list;
						
	}
	
	//@RequestMapping("/boardInsert.do")	//기존
	@PostMapping("/new")					//URI+POST
	public  void boardInsert(Board vo) {
		boardMapper.boardInsert(vo);	//등록성공
	}
	
	//@RequestMapping("/boardDelete.do")
	//public  void boardInsert(int idx) {
	@DeleteMapping("/{idx}")
	public  void boardInsert(@PathVariable("idx")/**/ int idx) {
		boardMapper.boardDelete(idx);
	}
	
	//@RequestMapping("/boardUpdate.do")
	//public  void boardUpdate(Board vo) {
	@PutMapping("/update")
	public  void boardUpdate(@RequestBody/*JSON으로 바뀐 객체를 받기 위해서 씀*/ Board vo) {
		boardMapper.boardUpdate(vo);
	}
	
	@GetMapping("/{idx}")
	public  Board boardContent(@PathVariable("idx") int idx) {
		Board vo = boardMapper.boardContent(idx);
		
		return vo; //vo->JSON 으로 바껴서 전달될것
	}
	
	@PutMapping("/count/{idx}")
	public  Board boardCount(@PathVariable("idx") int idx) {
		boardMapper.boardCount(idx);	//조회수를 증가시키고
		Board vo = boardMapper.boardContent(idx);	//조회수가 수정이된 값을 가져옴
		return vo;
	}
}
