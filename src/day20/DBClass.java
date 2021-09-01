package day20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBClass {
	//본인PC면 localhost, 다른PC면 해당IP주소
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	//일반사용자 계정 아이디 비번
	private String id = "jieun";
	private String pwd = "1234";
	public DBClass() {
		//1.자바에서 오라클에 관련된 기능을 사용할 수 있게 기능을 등록하는 것(load한다)
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getList() {
		//2.데이터베이스 연결(con은 DB에 연결된 객체다)
		try {
			//sql하고 연결할거니까 sql에 있는 connection
			Connection con = DriverManager.getConnection(url, id, pwd);
			System.out.println("연결이 잘 이뤄졌습니다!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3.데이터베이스에 연결된 객체를 이용해서 명령어를 수행할 수 있는 객체를 얻어온다.
		//4.명령어를 수행할 수 있는 객체를 이용해서 명령어 수행
		//5.수행 결과를 저장한다.
	}
}
