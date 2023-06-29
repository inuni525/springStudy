package kr.board.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//web.xml

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
									//ㄴ추상메서드를 상속받았음 -> 오버라이드 해주자
	
	//필터설정 마우스오른쪽->소스->오버라이드임플리멘트->겟서블릿필터스
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[]{encodingFilter};
	}
	
	
	@Override
	protected Class<?>[] getRootConfigClasses() {	//환경설정1
		// TODO Auto-generated method stub
		return new Class[] {RootConfig.class	};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {	//환경설정2
		// TODO Auto-generated method stub
		return new Class[] {ServletConfig.class	};
	}

	@Override
	protected String[] getServletMappings() {	//맵핑부분
		// TODO Auto-generated method stub
		return new String[] { "/" };	//프론트컨트롤러
	}

	
	
}
