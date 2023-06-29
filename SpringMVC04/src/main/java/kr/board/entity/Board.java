package kr.board.entity;

import lombok.Data;

@Data	//setter, getter 자동생성 api = Lombok API
public class Board {

	private int idx;
	private String memID;	//회원ID
	private String title;
	private String content;
	private String writer;
	private String indate;
	private int count;
	
}
