package edu.taesu.myspring.userInfo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


@Repository
public class UserInfoDAO {

	@Autowired
	private SqlSession sqlSession;

	private static final String NS = UserInfoDAO.class.getPackage().getName() + ".";

	// 회원정보 조회
	public UserInfoVO selectUserInfo(final UserInfoVO vo) throws DataAccessException {
		return this.sqlSession.selectOne(NS + "selectUserInfo", vo);
	}

	// 회원정보 등록 (신규 회원가입)
	public int insertUserInfo(final UserInfoVO vo) throws DataAccessException {
		return this.sqlSession.insert(NS + "insertUserInfo", vo);
	}

	// 회원정보 수정
	public int updateUserInfo(final UserInfoVO vo) throws DataAccessException {
		return this.sqlSession.update(NS + "updateUserInfo", vo);
	}

	// 회원 삭제(탈퇴)
	public int deleteUserInfo(final UserInfoVO vo) throws DataAccessException {
		return this.sqlSession.delete(NS + "deleteUserInfo", vo);
	}

}
