package project.controllers;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.models.AnswerModel;
import project.models.ErrorModel;
import project.models.QuestionModel;
import project.services.SingleplayerService;



@RestController
public class SingleplayerController {

    private SingleplayerService singleplayerService;

    public SingleplayerController(SingleplayerService singleplayerService) {
        this.singleplayerService = singleplayerService;
    }

    @PostMapping(value = "/question/create", produces = "application/json")
    public ResponseEntity createQuestion(@RequestBody QuestionModel question) {
        try {
            singleplayerService.createQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(question);
        } catch (DataAccessException e) {
            final ErrorModel error = new ErrorModel("Question wasn't create");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping(value = "/answer/create", produces = "application/json")
    public ResponseEntity createAnswer(@RequestBody AnswerModel answer) {
        try {
            singleplayerService.createAnswer(answer);
            return ResponseEntity.status(HttpStatus.CREATED).body(answer);
        } catch (DataAccessException e) {
            final ErrorModel error = new ErrorModel("Answer wasn't create");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping(value = "/set/get", produces = "application/json")
    public ResponseEntity getSet(@RequestParam(value = "questions") Integer size,
                                 @RequestParam(value = "theme") String theme) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(singleplayerService.getSet(size, theme));
        } catch (DataAccessException e) {
            final ErrorModel error = new ErrorModel("Requset url should be like /set/get?questions=4&theme=html");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

}
