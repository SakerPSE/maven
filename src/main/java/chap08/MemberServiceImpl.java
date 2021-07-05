package chap08;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao dao;
	
	@Override
	public List<MemberVo> selectList() {
		return dao.selectList();
	}

	@Override
	public MemberVo login(MemberVo vo) {
		return dao.login(vo);
	}

	@Override
	public MemberVo mypage(int mno) {
		return dao.selectOne(mno);
	}

	@Override
	public int update(MemberVo vo) {
		return dao.update(vo);
	}
}
