package edu.taesu.myspring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{


   private static final String Login = "login";
   private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

       HttpSession httpSession = request.getSession();
       if(modelAndView != null){
           ModelMap modelMap = modelAndView.getModelMap();
           Object userVo = modelMap.get("user");

           if(userVo != null) {
               logger.info("new login session");
               httpSession.setAttribute(Login, userVo);
               httpSession.setAttribute("userId", modelMap.get("userId").toString());
               httpSession.setAttribute("userNm", modelMap.get("userNm").toString());

               response.sendRedirect("/user/home");
           }
       }

   }

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
           throws Exception {

       HttpSession httpSession = request.getSession();
       if (httpSession.getAttribute(Login) != null) {
           logger.info("clear login data before");
           httpSession.removeAttribute(Login);
       }

       return true;
   }
}


