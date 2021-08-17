package gameLogic;

import consts.GameConsts;

import java.util.Arrays;

public class Game {
    public int getLeftAttempts()
    {
        return  this.attemptsCounter_;
    }

    public Game()
    {
        wordsHandler_ = new WordsHandler();

    }
    public String newWordGame()
    {
        updateWord();
        attemptsCounter_ = GameConsts.MAX_ATTEMPTS;
        gameStatus_ = GameConsts.GAME_CONTINUING;
        currentWord_ = wordsHandler_.chooseWord().toLowerCase();
        userWord_ = new String[currentWord_.length()];
        initUserWord();
        return wordsHandler_.getWordDescription(currentWord_);

    }
    public String checkUserAnswer(String answer) throws WrongAnswerException
    {
        if(answer.length() != GameConsts.ONE_LETTER)
        {
            throw new WrongAnswerException(answer, attemptsCounter_);
        }

        if(Arrays.asList(userWord_).contains(answer.toLowerCase()))
        {
            String usrWrd = new String();
            for(String elemenet: userWord_)
            {
                usrWrd +=elemenet;
            }
            return GameConsts.ALREADY_CHECKED_LETTER + GameConsts.LINE_DELIMITER + usrWrd;
        }
        String result = GameConsts.WRONG_ANSWER;
        int answerIndex = currentWord_.indexOf(answer.toLowerCase());
        if(answerIndex != GameConsts.NO_TNIS_LETTER_IN_WORD)
        {
            result = GameConsts.RIGHT_ANSWER;
            while(answerIndex != GameConsts.NO_TNIS_LETTER_IN_WORD)
            {
                userWord_[answerIndex] = answer.toLowerCase();
                answerIndex = currentWord_.indexOf(answer.toLowerCase(), answerIndex+ GameConsts.INDEX_SHIFT);
            }
        }
        else
        {
            attemptsCounter_--;
        }
        if(!Arrays.asList(userWord_).contains(GameConsts.UNKNOWN_LETTER))
        {
            result = GameConsts.USER_WON;
            gameStatus_ = GameConsts.GAME_END;
            return result + GameConsts.LINE_DELIMITER + currentWord_;
        }
        if(attemptsCounter_ == GameConsts.NO_MORE_ATTEMPTS)
        {
            result = GameConsts.USER_LOSE;
            gameStatus_ = GameConsts.GAME_END;
            return result + GameConsts.LINE_DELIMITER +GameConsts.SHOW_WORD + currentWord_;
        }
        String usrWrd = new String();
        for(String elemenet: userWord_)
        {
            usrWrd +=elemenet;
        }
        String attemptsLeft = GameConsts.ATTEMTS_LEFT + attemptsCounter_;
        return result + GameConsts.LINE_DELIMITER + usrWrd + GameConsts.LINE_DELIMITER + attemptsLeft;

    }
    private WordsHandler wordsHandler_;
    private String currentWord_;
    private int attemptsCounter_;
    private String[] userWord_;
    private int gameStatus_;
    private void initUserWord()
    {
        for(int i = 0; i < userWord_.length; i++)
        {
            userWord_[i] = GameConsts.UNKNOWN_LETTER;
        }
    }
    private void updateWord()
    {
        wordsHandler_.chooseWord();
    }
    public int getGameStatus() {
        return gameStatus_;
    }
}
