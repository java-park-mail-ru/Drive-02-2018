package project.services;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
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

    public void createQuestion(QuestionModel question) {
        final String sql = "INSERT INTO questions(question, theme) VALUES (?, ?)";
        jdbcTemplate.update(sql, question.getQuestion(), question.getTheme());
    }

    public void createAnswer(AnswerModel answer) {
        final String sql = "INSERT INTO answers(answer, question_id, correct) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, answer.getAnswer(), answer.getQuestionId(), answer.getCorrect());
    }





    public ArrayList<SetModel> getSet(Integer numberOfQuestions, String theme) {

        final String sql =
            "SELECT q.id AS question_id, a.id AS answer_id, answer, question, correct, theme FROM questions q " +
            "JOIN answers a ON q.id = a.question_id WHERE q.theme = ?::citext " +
            "LIMIT ?";
        //todo убрать константу - 4 ответа на вопрос
        final List<QuestionAndAnswer> questionAndAnswers =
            jdbcTemplate.query(sql, ApiRowMapper.getQuestionWithAnser, theme, numberOfQuestions * 4);

        final LinkedHashMap<QuestionModel, ArrayList<AnswerModel>> questionsMap = new LinkedHashMap<>();

        //получили набор, в котором повторяются вопросы. Необходимо получить структуру: {вопрос:[ответы]}
        for (QuestionAndAnswer i : questionAndAnswers) {
            final ArrayList<AnswerModel> listOfAnswers = questionsMap.get(new QuestionModel(i.getQuestion(), i.getTheme(), i.getQuestionId()));

            if (listOfAnswers != null) {
                listOfAnswers.add(new AnswerModel(i.getAnswerId(),  i.getAnswer(), i.getQuestionId(), i.getCorrect()));
            } else {
                final ArrayList<AnswerModel> answers = new ArrayList<>();
                answers.add(new AnswerModel(i.getAnswerId(),  i.getAnswer(), i.getQuestionId(), i.getCorrect()));
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

}
