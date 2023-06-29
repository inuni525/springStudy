package kr.board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;

@Controller
public class MemberController {

	@Autowired
	MemberMapper memberMapper;
	
	@RequestMapping("/memJoin.do")
	public String memJoin() {
		return "member/join";
	}
	
	//아이디 사용가능 유무
	@RequestMapping("/memRegisterCheck.do")
	public @ResponseBody int memRegisterCheck(@RequestParam("memID") String memID) {
		
		Member m = memberMapper.registerCheck(memID);
		if(m!=null || memID.equals("")) {
			return 0;	//이미 존재하는 회원, 입력불가
		}
		
		return 1;	//사용가능한 아이디
	}
	
	//회원가입 처리
	@RequestMapping("/memRegister.do")				//회원가입 성공시 로그인을 위해
	public String memRegister(Member m, String memPassword1, String memPassword2, RedirectAttributes rttr, HttpSession session) {
																				//성공 여부 메시지를 보내기 위함.
		//누락 정보 여부 확인
		if(m.getMemID()==null || m.getMemID().equals("") ||
		   memPassword1==null || memPassword1.equals("") ||
		   memPassword2==null || memPassword2.equals("") ||
		   m.getMemName()==null || m.getMemName().equals("") ||	
		   m.getMemAge()==0 ||	/*나이 입력하지 않을 시에는 jsp에서 goInsert() 함수를 통해 검사함*/
		   m.getMemGender()==null || m.getMemGender().equals("") ||
		   m.getMemEmail()==null || m.getMemEmail().equals("")) {
			
		   // 누락메세지를 가지고 가기? =>객체바인딩(Model, HttpServletRequest, HttpSession) 할수 없음.

		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
		   return "redirect:/memJoin.do";  // ${msgType} , ${msg}
		}
		
		//비밀번호 동일 체크
		if(!memPassword1.equals(memPassword2)) {
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
		   return "redirect:/memJoin.do";  // ${msgType} , ${msg}
		}		
		
		m.setMemProfile(""); // 사진이 없다는 의미 ""
		
		// 회원을 테이블에 저장하기
		int result=memberMapper.register(m);
		if(result==1) { // 회원가입 성공 메세지
		   rttr.addFlashAttribute("msgType", "성공 메세지");
		   rttr.addFlashAttribute("msg", "회원가입에 성공했습니다.");
		   
		   // 회원가입이 성공하면=>로그인처리하기
		   session.setAttribute("m", m); // ${!empty m}	=> 이름이 달라야하나?
		   return "redirect:/";
		}else {
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
		   return "redirect:/memJoin.do";
		}		
	}
	
	//로그아웃 처리
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate();	//세션을 무효화
		return "redirect:/";
	}
	
	//로그인 화면으로 이동
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		return "member/memLoginForm";	//memLoginForm.jsp
	}
	
	//로그인 기능 구현
	@RequestMapping("/memLogin.do")
	public String memLogin(Member m, RedirectAttributes rttr, HttpSession session) {
		if(m.getMemID()==null || m.getMemID().equals("")||
		   m.getMemPassword()==null || m.getMemPassword().equals("")) {
			
			rttr.addFlashAttribute("msgType", "실패 메시지");
			rttr.addFlashAttribute("msg", "아이디와 비밀번호를 확인하세요.");
			return "redirect:/memLoginForm.do";
		}
		Member mvo = memberMapper.memLogin(m);
		if(mvo!=null) {	//로그인 성공
			
			rttr.addFlashAttribute("msgType", "성공 메시지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다.");
			session.setAttribute("m", mvo);
			return "redirect:/";	//메인
			
		}else {	//로그인 실패
			
			rttr.addFlashAttribute("msgType", "실패 메시지");
			rttr.addFlashAttribute("msg", "아이디와 비밀번호를 확인하세요.");
			return "redirect:/memLoginForm.do";	//메인
			
		}
	}
	
	// 회원정보수정 화면으로 이동
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		return "member/memUpdateForm";
	}
	
	//회원정보 수정
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member m, RedirectAttributes rttr,
	 String memPassword1, String memPassword2, HttpSession session) {
		if(m.getMemID()==null || m.getMemID().equals("") ||
		   memPassword1==null || memPassword1.equals("") ||
		   memPassword2==null || memPassword2.equals("") ||
		   m.getMemName()==null || m.getMemName().equals("") ||	
		   m.getMemAge()==0 ||
		   m.getMemGender()==null || m.getMemGender().equals("") ||
		   m.getMemEmail()==null || m.getMemEmail().equals("")) {
			
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");
		   return "redirect:/memUpdateForm.do";  // ${msgType} , ${msg}
		}
		if(!memPassword1.equals(memPassword2)) {
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다.");
		   return "redirect:/memUpdateForm.do";  // ${msgType} , ${msg}
		}		
		// 회원을 수정저장하기
		int result=memberMapper.memUpdate(m);
		if(result==1) { // 수정성공 메세지
		   rttr.addFlashAttribute("msgType", "성공 메세지");
		   rttr.addFlashAttribute("msg", "회원정보 수정에 성공했습니다.");
		   
		   // 회원수정이 성공하면=>로그인처리하기
		   session.setAttribute("m", m); // ${!empty m}
		   return "redirect:/";
		}else {
		   rttr.addFlashAttribute("msgType", "실패 메세지");
		   rttr.addFlashAttribute("msg", "회원정보 수정에 실패했습니다.");
		   return "redirect:/memUpdateForm.do";
		}
	}
	
	//회원의 사진등록 화면
	@RequestMapping("/memImageForm.do")
	public String memImageForm() {
		return "member/memImageForm";	//memImageForm.jsp
	}
	
	//회원 사진이미지 업로드(upload, DB도 저장)
	@RequestMapping("/memImageUpdate.do")
	public String memImageUpdate(HttpServletRequest request, RedirectAttributes rttr, HttpSession session) {
		//업로드하려면 업로드 폴더에 업로드가 되어야함 
		//	=> 파일업로드 API가 필요 : 3가지의 API가 있음
		// cos.jar, commons-fileupload, 서블릿 3.0 이상
		
		//1.일단 변수 먼저 생성
		MultipartRequest multi = null;
		//2.사진사이즈
		int fileMaxSize = 40*1024*1024; //10MB
		//3.경로설정 = /resources/upload의 실제경로를 가져오려면 리퀘스트 객체를 이용
		// 왜? 이클립스는 우리의 프로젝트를 별도의 폴더로 관리를 해서 우리가 아는 경로가 아님(백업본을 이클립스가 관리하고 있기 때문)
		String savePath = request.getRealPath("resources/upload");
		//파일업로드는 트라이-캐치써야함
		try {
			//객체 생성					 파라미터를 읽어들이기 위해 리퀘스트 객체가 필요
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
			//이미지 업로드가 되는 것임 = ㄴ이 생성자가 알아서 해줌									//ㄴ저장경로		ㄴ사이즈	  ㄴ인코딩		ㄴ동일한 이름의 파일이 있을경우 리네임해주는 클래스
		} catch (Exception e) {
			//용량문제로 업로드 실패할 경우
			e.printStackTrace();
			
			//모달창을 띄워줄 것
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "파일의 크기는 10MB를 넘을 수 없습니다.");
			return "redirect:/memImageForm.do";
			
			//모달창을 띄우지 못하고 파일업로드로 인한 과부하로 톰캣이 인터넷을 끊어버림
			//해결방법1 : Servers/Tomcat/server.xml에서 63번째줄에 maxSwallowSize="-1"을 작성해줌
															//ㄴ기본값(2MB)을 넘어가면 톰캣이 멈추니까 제한해제하는 역할 = 대용량파일 업로드가 가능해짐
			//해결방법2 :ajax로 가능
		}
		
		//데이터베이스 테이블에 회원이미지를 업데이트
		
		//multi에 request로 받아왔으니까
		String memID = multi.getParameter("memID");	//히든으로 넘겼던 아이디 받아옴
		String newProfile="";
		
		//세이브해둔 파일정보 가져와보기	= 파일과의 연결
		File file = multi.getFile("memProfile");
					//ㄴ업로드한 파일을 전부 알고있음
		//File file은 업로드한 파일을 가르키는 객체가 되었음
		
		if(file != null) {//업로드가 된 상태 (.png, .jpg, .gif 이미지파일만 업로드되어야함)
			//이미지파일인지 여부를 체크 -> 이미지 파일이 아니면 삭제하자
			//파일의 이름을 꺼내오면 될 것
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1);
																	//ㄴ가장뒤에있는 "."을 찾아 그 뒤의 값을 가져옴 = 확장자
			//확장자가 대소문자 일수도 있음
			ext = ext.toUpperCase();	//PNG, JPG, GIF
			if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")) {
				//정상적인 업로드 = 새로 업로드된 이미지(new->1.PNG), 현재 DB에 있는 이미지(old->PNG)
				//현재이미지를 삭제하고 새로 업로드된 이미지를 저장해야함	
				//1.현재DB에 있는 이미지 가져오기
				String oldProfile = memberMapper.getMember(memID).getMemProfile();
				//현재DB에 이미지가 존재하는지 확인여부 체크
				File oldFile = new File(savePath+"/"+oldProfile);	
				if(oldFile.exists()) {	//현재 이미지가 존재하면 삭제 = 왜? 새로운 이미지를 업로드 하기 위해서
					oldFile.delete();
				}
				newProfile=file.getName();
				
			}else {	
				//이미지 파일이 아닌 경우
				if(file.exists()) {	//임의로 삭제할수도 있으니 파일이 존재하는지 체크
					file.delete();	//이미지 파일이 아니므로 업로드된것을 삭제
				}
				//오류 메시지 모달창
				rttr.addFlashAttribute("msgType", "실패 메세지");
				rttr.addFlashAttribute("msg", "이미지파일만 업로드 가능합니다.");
				return "redirect:/memImageForm.do";
				
			}
			
		}
		//새로운 이미지를 업데이트
		Member m = new Member();
		m.setMemID(memID);
		m.setMemProfile(newProfile);
		memberMapper.memProfileUpdate(m);	//이미지 업데이트 성공
		
		//새로운 이미지가 담겨진 세션으로 저장해야함 = 이미지가 없었거나 예전이미지의 세션이니까
		Member mvo = memberMapper.getMember(memID);
		session.setAttribute("m", mvo);
		
		//업데이트 성공 모달창
		rttr.addFlashAttribute("msgType", "성공 메세지");
		rttr.addFlashAttribute("msg", "이미지가 변경되었습니다.");
		return "redirect:/";
	}
	
	
	
}
