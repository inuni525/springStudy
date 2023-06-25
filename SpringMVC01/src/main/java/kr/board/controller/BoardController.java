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

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {

	@Autowired	/* BoardMapper 라는 객체를 메모리에서 찾음 - 그리고 연결됨 
				- BoardMapper 와 연결되어있는 sqlsession에 만들어진 메서드를 실행해서 db까지 연동이 되는 것임
	*/
	private BoardMapper mapper;
	// HandlerMapping
	// 클라이언트 : 게시판 리스트 좀 보여줘
	@RequestMapping("/boardList.do")
	public String boardList(Model model) {
		List<Board> list=mapper.getLists();
		model.addAttribute("list", list);
		return "boardList"; // /WEB-INF/views/boardList.jsp -> forward
	}
	
	/* =@RequestMapping을 써도 됨 */
	@GetMapping/* a태그로 서버요청할때 쓰는 어노테이션*/("/boardForm.do")
	public String boardForm() {
		return "boardForm";	//forward 만 해주면 됨
	}
	
	@PostMapping("boardInsert.do")
	public String boardInsert(Board vo) {
		/*세개의 파라미터가 인서트로 전달 받아야함 
		  = 파라미터 수집 = Board를 써야함 
		  = 이름이 똑같아야 자동으로 스프링이 수집해줌 */
		
		//수집된 vo를 db에 저장될 메서드를 mapper에 만들어줘야함
		mapper.boardInsert(vo);	//등록
		
		return "redirect:/boardList.do"; //redirect
	}
	
	@GetMapping("/boardContent.do")
	public String boardContent(@RequestParam("idx") int idx, Model model) { 
								// idx라는 정보 하나만 받아올때는 @RequestParam을 쓰기도한다
								// 넘어오는 파라미터의 이름과 받는 변수의 이름이 같으면 RequestParam 생략가능
		Board vo = mapper.boardContent(idx);
		
		//조회수 증가 만들어주기
		mapper.boardCount(idx);
		
		model.addAttribute("vo",vo);
		return "boardContent"; // boardContent.jsp
	}
	
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) {
								// /로 경로처럼 idx를 전달할 경우에는 @PathVariable을 사용하여 전달받음
		mapper.boardDelete(idx);
		return "redirect:/boardList.do";
	}
	
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		Board vo = mapper.boardContent(idx);
		//가지고 있는거니까 전에 만들어놓은 db활용
		
		model.addAttribute("vo",vo);
		
		return "boardUpdate";	//boardUpdate.jsp
	}
	
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) {	// idx, title, content
		mapper.boardUpdate(vo);	//수정
		return "redirect:/boardList.do";
	}
	
	
}
