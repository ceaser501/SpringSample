package edu.taesu.mySpring.testTable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TestTableService {
	@Autowired
	private TestTableDAO testTableDAO;

	public List<TestTableVO> getTestTableList(final TestTableVO vo) throws DataAccessException {
		return testTableDAO.getTestTableList(vo);
	}

	public void insertTestTable(final TestTableVO vo) throws DataAccessException {
		testTableDAO.insertTestTable(vo);
	}

	public void updateTestTable(final TestTableVO vo) throws DataAccessException {
		testTableDAO.updateTestTable(vo);
	}

	public void deleteTestTable(final TestTableVO vo) throws DataAccessException {
		testTableDAO.deleteTestTable(vo);
	}
}