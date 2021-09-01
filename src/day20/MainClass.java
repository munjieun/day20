package day20;

import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int num, age;
		String id, name;
		DBClass db = new DBClass();
		while(true) {
			System.out.println("1.모든데이터보기 2.검색 3.저장 4.삭제 5.수정");
			num = input.nextInt();
			switch(num) {
			case 1 :
				//데이터베이스의 모든 데이터를 가져와서 보여준다.
				db.getList();
				break;
			case 2 : break;
			case 3 : break;
			case 4 : break;
			case 5 : break;
			}
		}
	}
}
