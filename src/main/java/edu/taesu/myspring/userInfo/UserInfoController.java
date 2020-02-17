package edu.taesu.myspring.userInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.taesu.myspring.ResultVO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;

@Controller
public class UserInfoController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoService UserInfoService;

	// 로그인 초기화면
	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String selectLoginForm() throws Exception {
		return "/user/login";
	}

	// 로그인
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public void getUserInfo(UserInfoVO vo, HttpSession httpSession, Model model) {

		UserInfoVO userInfo = UserInfoService.selectUserInfo(vo);

		if(vo == null || !BCrypt.checkpw(vo.getUserPw(), userInfo.getUserPw())) {
			return;
		}

		model.addAttribute("user", userInfo);
	}


	// 회원등록 초기화면
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String insertUserInfoForm() throws Exception {
		return "/user/register";
	}

	// 회원등록
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String insertUserInfo(UserInfoVO vo, RedirectAttributes redirectAttributes) {

		String hashedPw = BCrypt.hashpw(vo.getUserPw(), BCrypt.gensalt());						// pw hash 암호화
		vo.setUserPw(hashedPw);																	// hashed password 세팅
		UserInfoService.insertUserInfo(vo);
		redirectAttributes.addFlashAttribute("msg", this.getSuccessCode().get("msg"));

		return "/user/login";
	}

	// 회원정보 상세 초기화면
	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	public String updateUserInfo(HttpSession session, Model model, UserInfoVO vo) throws Exception{

        model.addAttribute("login", session.getAttribute("login"));

		return "/user/info";
	}

	// 회원정보 수정
	@RequestMapping(value = "/user/info/modify", method = { RequestMethod.POST })
	@ResponseBody
	public ResultVO updateUserInfo(Model model, UserInfoVO vo, ResultVO rvo) {

		try {
			int result = UserInfoService.updateUserInfo(vo);
			if(result > 0){
				rvo.setResultCd("0000");
                rvo.setResultMsg("성공하였습니다.");
			}
		} catch (Throwable e) {
			logger.warn(ExceptionUtils.getStackTrace(e));
		}

		return rvo;
	}

	private Map<String, Object> getSuccessCode() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		//JsonObject result = new JsonObject();
		map.put("code", "1");
		map.put("msg", "success");
		return map;
	}

	private Map<String, String> getFailCode(String msg) {
		Map<String, String> result = new HashMap<String, String>();

		//JsonObject result = new JsonObject();
		result.put("code", "-1");
		result.put("msg", "fail");
		return result;
	}
}
