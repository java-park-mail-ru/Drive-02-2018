package project.services;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.models.AnswerModel;
import project.models.QuestionAndAnswer;
import project.models.QuestionModel;
import project.models.SetModel;
import project.rowmapper.ApiRowMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class SingleplayerService {

    private JdbcTemplate jdbcTemplate;

    SingleplayerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Integer createQuestion(QuestionModel question) {
        final String sql = "INSERT INTO questions(question, theme) VALUES (?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, question.getQuestion(), question.getTheme());
    }


    public void createAnswer(AnswerModel answer, Integer questionId) {
        final String sql = "INSERT INTO answers(answer, question_id, correct, answer_num) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, answer.getAnswer(), questionId, answer.getCorrect(), answer.getAnswerNum());
    }


    @Transactional(rollbackFor = Exception.class)
    public void init(List<SetModel> sets) {
        for (SetModel set : sets) {
            final Integer questionId = this.createQuestion(set.getQuestion());
            for (AnswerModel answer : set.getAnswers()) {
                this.createAnswer(answer, questionId);
            }
        }
    }


    public ArrayList<SetModel> getSet(Integer numberOfQuestions, String theme) {

        final String sql =
            "SELECT q.id AS question_id, answer, question, theme, a.answer_num FROM questions q "
            + "JOIN answers a ON q.id = a.question_id WHERE q.theme = ?::citext "
            + "LIMIT ?";
        //todo убрать константу - 4 ответа на вопрос
        final List<QuestionAndAnswer> questionAndAnswers =
            jdbcTemplate.query(sql, ApiRowMapper.getQuestionWithAnser(), theme, numberOfQuestions * 4);

        if (questionAndAnswers.isEmpty()) {
            throw new DataAccessException("Result is emty") { };
        }

        final LinkedHashMap<QuestionModel, ArrayList<AnswerModel>> questionsMap = new LinkedHashMap<>();

        //получили набор, в котором повторяются вопросы. Необходимо получить структуру: {вопрос:[ответы]}
        for (QuestionAndAnswer i : questionAndAnswers) {
            final ArrayList<AnswerModel> listOfAnswers
                = questionsMap.get(new QuestionModel(i.getQuestion(), i.getTheme(), i.getQuestionId()));

            if (listOfAnswers != null) {
                listOfAnswers.add(new AnswerModel(i.getAnswerNum(),  i.getAnswer(), i.getQuestionId()));
            } else {
                final ArrayList<AnswerModel> answers = new ArrayList<>();
                answers.add(new AnswerModel(i.getAnswerNum(),  i.getAnswer(), i.getQuestionId()));
                final QuestionModel question = new QuestionModel(i.getQuestion(), i.getTheme(), i.getQuestionId());
                questionsMap.put(question, answers);
            }
        }

        //у Jacksona-а не получается правильно сериализовать хэш-мапину, в отличие от List-а
        final ArrayList<SetModel> resultSet = new ArrayList<>();
        for (QuestionModel q : questionsMap.keySet()) {
            final  SetModel set = new SetModel(q);
            set.setAnswers(questionsMap.get(q));
            resultSet.add(set);
        }

        return resultSet;
    }


    public Boolean checkAnswer(Integer questionId, Integer answerNum) {
        final String sql = "SELECT correct FROM answers WHERE question_id = ? AND answer_num = ?";
        return jdbcTemplate.queryForObject(sql, Boolean.class, questionId, answerNum);
    }


    public void clear() {
        jdbcTemplate.execute("DELETE FROM questions");
        jdbcTemplate.execute("DELETE FROM answers");
    }

}
