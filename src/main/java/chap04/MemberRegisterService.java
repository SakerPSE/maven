package chap04;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberRegisterService {
	@Autowired
	private MemberDao memberDao;
	
//	public MemberRegisterService(MemberDao memberDao) {
//		this.memberDao = memberDao;
//	}
	
	public Long regist(RegisterRequest req) {
		 Member member  = memberDao.selectByEmail(req.getEmail());
		 if (member != null) {
			 throw new DuplicateMemberException("이메일 중복:" + req.getEmail());
		 }
		 Member newMember = new Member( //member가 null이면 실행
				 req.getEmail(), 
				 req.getPassword(), 
				 req.getName(), 
				 LocalDateTime.now());
		 memberDao.insert(newMember);
		 return newMember.getId(); // 중복이 안되면 map에다가 new member를 추가하고 아이디를 리턴하는 흐름.
	}
}
