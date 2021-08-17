package gameLogic;

import consts.GameConsts;

public class WrongAnswerException extends Exception {
    private String incorrectUserAnswer_;
    private int attemptsCounter_;
    WrongAnswerException(String incorrectUserAnswer, int attemptsCounter)
    {
        attemptsCounter_ = attemptsCounter;
        incorrectUserAnswer_ = incorrectUserAnswer;
    }
    public String whatIsTheProblem()
    {
        String attemptsLeft = GameConsts.ATTEMTS_LEFT + attemptsCounter_;
       return GameConsts.BAD_ANSWER_FORMAT  + incorrectUserAnswer_
                + GameConsts.LINE_DELIMITER + GameConsts.ANSWER_REQUIREMENT
               + GameConsts.LINE_DELIMITER + attemptsLeft;
    }

}

