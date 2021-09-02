package day20;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	public ArrayList<StudentDTO> getList() {
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		try {
			//2.데이터베이스 연결(con은 DB에 연결된 객체다)
			//sql하고 연결할거니까 sql에 있는 connection
			Connection con = DriverManager.getConnection(url, id, pwd);
			System.out.println("연결이 잘 이뤄졌습니다!!");
			
			//3.데이터베이스에 연결된 객체를 이용해서 명령어를 수행할 수 있는 객체를 얻어온다.
			String sql = "select * from newst";
			//sql명령어를 넣어주면 실행해줄 수 있는 실행 객체를 얻어온다.
			PreparedStatement ps = con.prepareStatement(sql);//명령어 전송
			
			//4.명령어를 수행할 수 있는 객체를 이용해서 명령어 수행(executeQuery)
			//5.수행 결과를 저장한다.(ResultSet)
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				/*
				System.out.println("id : " + rs.getString("id"));
				System.out.println("name : " + rs.getString("name"));
				System.out.println("age : " + rs.getInt("age"));
				System.out.println("---------------");
				*/
				//이 클래스에서는 데이터베이스 관련된 기능만 처리할거라 여기서 출력안함
				StudentDTO dto = new StudentDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				
				list.add(dto);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public StudentDTO searchST(String id) {
		//select * from newst where Id = '222'; 222자리에 대신 들어가는 거니까 작은따옴표는 그대로 들어가야한다.
		String sql = "select * from newst where Id = '" + id + "'";
		StudentDTO dto = null;
		try {
			//1.db연결
			//System.out.println(url);
			//System.out.println(id);
			//System.out.println(pwd);
			//입력받는 변수 명을 바꾸거나 this를 사용해서 넣어준다.
			//오류가 발생할 때는 의심가는 값을 일단 다 출력해본다.
			Connection con = DriverManager.getConnection(url, this.id, pwd);
			System.out.println("연결 확인!");
			//2.명령어(쿼리문)전송 객체 생성
			PreparedStatement ps = con.prepareStatement(sql);
			//3.전송 후 결과값 저장
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				//사용자가 없는 값을 입력할 경우 객체가 생성되지 않도록해서 dto가 null일때는 찾고자하는 값이 없다는 것을 알려준다.
				dto = new StudentDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	public int saveData(String userId, String userName, int userAge) {
		//insert into newst values('aaa', '김개똥', 33);
		//String sql = "insert into newst values('" + userId + "', '" + userName + "', " + userAge + ")";
		//위처럼 하면 번거로우니까 ?를 넣어서 나중에 값을 넣어준다.
		String sql = "insert into newst values(?, ?, ?)"; 
		int result = 0;
		try {
			//1.데이터베이스 연결 객체 얻어오기
			Connection con = DriverManager.getConnection(url, id, pwd);
			//2.쿼리문 전송객체 얻어오기
			PreparedStatement ps = con.prepareStatement(sql);
			//3.?자리 채우기
			//첫번째 ?에는 userID, 두번째 ?에는 userName, 세번째 ?에는 userAge
			ps.setString(1, userId);
			ps.setString(2, userName);
			ps.setInt(3, userAge);
			//4.쿼리문 전송(실행)
			//select 쿼리문일 때, executeQuery를 사용한다.
			//executeQuery도 기본키 중복이 걸러지지만 rs로만 값을 받아와서 따로 메시지 처리를 할 수 없다.
			//ResultSet rs = ps.executeQuery();
			//select를 제외한 다른 쿼리문은 executeUpdate()를 사용한다.
			//executeUpdate는 int형태의 값을 돌려준다. 성공은 1, 실패는 0 또는 에러
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int deleteData(String userId) {
		int result = 0;
		//delete from newst where id = '111';
		String sql = "delete from newst where id = '"+ userId +"'";
		//String sql = "delete from newst where id = ?";
		try {
			Connection con = DriverManager.getConnection(url, id, pwd);
			PreparedStatement ps = con.prepareStatement(sql);
			//ps.setString(1, userId); sql ?쿼리문일 때 사용
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int updateData(String userId, String userName, int userAge) {
		//update newst set name = '오오오', age =35 where id = '333';
		int result = 0;
		String sql = "update newst set name = ?, age = ? where id = ?";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DriverManager.getConnection(url, id, pwd);
			ps = con.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setInt(2, userAge);
			ps.setString(3, userId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			try {
				if(ps != null) ps.close();
				if(con != null) con.close(); //데이터가 너무 쌓이지 않게 종료한다.
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
