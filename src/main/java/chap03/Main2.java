package chap03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {
	private static AnnotationConfigApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException{
		ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		// new hong@gmail.com 홍길동 1234 1234(" "띄어쓰기 할 것)
		// change hong@gmail.com 1234 5678 (" "띄어쓰기 할 것)
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.println("명령어를 입력해 주세요.");
			String cmd = reader.readLine();
			if (cmd.equals("exit")) {
				System.out.println("종료합니다.");
				break;
			} else if (cmd.startsWith("new")) {
				processNewCommand(cmd.split(" "));
			} else if (cmd.startsWith("change")) {
				processChangeCommand(cmd.split(" "));
			} else if (cmd.equals("list")) {
				// hong@gmail.com 홍길동 비밀번호 날짜
				Map<String, Member> map = ctx.getBean("listSvc",MemberListService.class).selectList();
				for(String key : map.keySet()) {
//					System.out.println(key); //키캆인 이메일이 출려됨.
					Member m = map.get(key); // .get(key) 키에 매핑된 밸류를 리턴함.
					System.out.println(m.getEmail() + "\t" + m.getName() + "\t" + m.getPassword() + "\t" + m.getRegisterDateTime());
				}
			} else if (cmd.startsWith("info")) {
				if(cmd.split(" ").length != 2) {
					return;
				}
				Member member = ((MemberInfoService) ctx.getBean("infoSvc")).selectOne(cmd.split(" ")[1]);
				if (member == null) {
					System.out.println("등록되지 않은 이메일입니다.");
				} else {
					System.out.println("id:"+member.getId() + " email:"+member.getEmail() + " name:"+member.getName() + " Date:"+member.getRegisterDateTime());
				}
			}
		}
	}
	private static void processNewCommand(String[] arg) {
		if (arg.length != 5) {
			return; // 중지
		}
		MemberRegisterService regSvc = ctx.getBean("regSvc", MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest(); // 값을 담아둠
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmpassword(arg[4]);
		
		if (!req.isPasswordEqualToConfirmPassword()) { // !req 이므로 isPasswordEqualToConfirmPassword()가 false 이면 true가 되는 거임
			System.out.println("비밀번호가 일치하지 않습니다."); 
			return;
		}
		try {
		regSvc.regist(req);
		} catch (DuplicateMemberException e) {
			System.out.println("이메일 중복");
		}
		System.out.println("등록완료");
	}
	private static void processChangeCommand(String[] arg) {
		if (arg.length != 4) {
			return; // 길이가 4면 중지
		}
		ChangePasswordService pwdSvc = ctx.getBean("pwdSvc", ChangePasswordService.class);
		try {
		pwdSvc.changePassword(arg[1], arg[2], arg[3]);
		System.out.println("비밀번호 변경");
		} catch(MemberNotFoundException e) {
			System.out.println("회원이 존재하지 않습니다.");
		} catch(WrongIdPasswordException e) {
			System.out.println("이메일과 비밀번호가 일치하지 않습니다.");
		}
	}
	
}
