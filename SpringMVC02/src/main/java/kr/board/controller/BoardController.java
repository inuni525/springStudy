package kr.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {
//	
//	@Autowired
//	BoardMapper boardMapper;	//BoardMapper라는 객체를 만들어서 사용해야하므로
//	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
//	
//	@RequestMapping("/boardList.do")
//	public @ResponseBody/*JSON형식으로 변환하려면 이 어노테이션을 써야함*/ List<Board> boardList(){	//객체타입을 리턴한다는 의미
//		//이 어노테이션이 있으면 Spring내부에서 JSON이 자동으로 동작함 [객체를->JSON 데이터포맷으로 변환]
//		List<Board> list = boardMapper.getLists();
//		
//		return list;	//객체를 리턴한다는 것은 JSON 데이터 형식으로 변환(API)해서 리턴하겠다. 는 의미	
//						// 즉, Board라는 객체를 JSON 데이터포맷을 통해 배열의 형태로 데이터를 받는다.
//						// key(column): value 이 형식으로 JSON 배열 형태로 만들어짐
//						// 다양한 디바이스에서 페이지를 제공해야하므로 JSON값이 request URI 를 통해 전달된다
//						
//	}
//	
//	@RequestMapping("/boardInsert.do")
//	public @ResponseBody/*리턴할 필요가 없으므로 리스폰스바디가 넘겨줌 리턴값이 없어도*/ void boardInsert(Board vo) {
//		boardMapper.boardInsert(vo);	//등록성공
//	}
//	
//	@RequestMapping("/boardDelete.do")
//	public @ResponseBody void boardInsert(int idx) {
//		boardMapper.boardDelete(idx);
//	}
//	
//	@RequestMapping("/boardUpdate.do")
//	public @ResponseBody void boardUpdate(Board vo) {
//		boardMapper.boardUpdate(vo);
//	}
//	
//	@RequestMapping("/boardContent.do")
//	public @ResponseBody Board boardContent(int idx) {
//		Board vo = boardMapper.boardContent(idx);
//		
//		return vo; //vo->JSON 으로 바껴서 전달될것
//	}
//	
//	@RequestMapping("/boardCount.do")
//	public @ResponseBody Board boardCount(int idx) {
//		boardMapper.boardCount(idx);	//조회수를 증가시키고
//		Board vo = boardMapper.boardContent(idx);	//조회수가 수정이된 값을 가져옴
//		return vo;
//	}
//	
}
