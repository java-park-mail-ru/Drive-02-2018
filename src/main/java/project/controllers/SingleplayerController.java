package project.controllers;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.models.ErrorModel;
import project.models.SetModel;
import project.services.SingleplayerService;

import java.util.List;


@RestController
public class SingleplayerController {

    private SingleplayerService singleplayerService;

    public SingleplayerController(SingleplayerService singleplayerService) {
        this.singleplayerService = singleplayerService;
    }


    @GetMapping(value = "/set/get", produces = "application/json")
    public ResponseEntity getSet(@RequestParam(value = "questions") Integer size,
                                 @RequestParam(value = "theme") String theme) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(singleplayerService.getSet(size, theme));
        } catch (DataAccessException e) {
            final ErrorModel error = new ErrorModel("Requset url should be like /set/get?questions=4&theme=html");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping(value = "/answer/check", produces = "application/json")
    public ResponseEntity checkAnswer(@RequestParam(value = "question") Integer questionId,
                                 @RequestParam(value = "answer") Integer answerNum) {
        try {
            final Integer correct = singleplayerService.getCorrectAnswer(questionId);
            final StringBuilder str = new StringBuilder();
            str.append(correct.equals(answerNum) ? "{\"correct\": true" : "{\"correct\": false");
            str.append(", \"correctAnswer\": ");
            str.append(correct.toString());
            str.append("}");
            return ResponseEntity.status(HttpStatus.OK).body(str.toString());
        } catch (DataAccessException e) {
            final ErrorModel error = new ErrorModel("Requset url should be like /answer/check?question=4&answer=1"
                    + " and query parameteres must be valid");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping(value = "/clear", produces = "application/json")
    public ResponseEntity clear() {
        try {
            this.singleplayerService.clear();
            return ResponseEntity.status(HttpStatus.OK).body("All is clear, i hope u isn't a crimainal.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(value = "/init", produces = "application/json")
    public ResponseEntity init(@RequestBody List<SetModel> sets) {
        try {
            this.singleplayerService.clear();
            this.singleplayerService.init(sets);
            return ResponseEntity.status(HttpStatus.OK).body("Succesful init");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
