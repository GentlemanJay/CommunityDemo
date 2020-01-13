package test.majiang.community.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import test.majiang.community.demo.Mapper.UserMapper;
import test.majiang.community.demo.service.QuestionService;
import test.majiang.community.demo.dao.User;
import test.majiang.community.demo.dto.QuestionDTO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 用户每次登陆首页时，先获取所有cookie信息，找到先前存入cookie中的token值，
     * 再根据该唯一的token值从user表中查询是否存在该用户
     * @param request
     * @return
     */

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        //首页显示问题列表
        List<QuestionDTO> questionDTOList = questionService.listQuestion();
        model.addAttribute("questionList",questionDTOList);

        return "index";
    }

}
