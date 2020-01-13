package test.majiang.community.demo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.majiang.community.demo.Mapper.QueationMapper;
import test.majiang.community.demo.Mapper.UserMapper;
import test.majiang.community.demo.dao.Question;
import test.majiang.community.demo.dao.User;
import test.majiang.community.demo.dto.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QueationMapper queationMapper;

    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> listQuestion() {
        List<QuestionDTO> questionDTOList =  new ArrayList<>();
        List<Question> questions = queationMapper.list();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);//使用spring工具类复制属性
            User user = userMapper.findUserById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;
    }
}
