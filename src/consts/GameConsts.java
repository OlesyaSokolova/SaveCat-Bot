package consts;
import com.vdurmont.emoji.EmojiParser;

public class GameConsts {
    public static final String emojiCatSmiling = EmojiParser.parseToUnicode(":smile_cat:");
    public static final int MAX_ATTEMPTS = 5;
    public static final String WRONG_ANSWER = "В этом слове нет такой буквы. Попробуй ещё раз!";
    public static final String RIGHT_ANSWER = "Супер!";
    public static final int NO_TNIS_LETTER_IN_WORD = -1;
    public static final String LINE_DELIMITER = "\n";
    public static final String UNKNOWN_LETTER = " - ";
    public static final int INDEX_SHIFT = 1;
    public static final int NO_MORE_ATTEMPTS = 0;
    public static final String ATTEMTS_LEFT = "Осталось попыток: ";
    public static final String ANSWER_REQUIREMENT = "Отправляй, пожалуйста, по одной букве.";
    public static final String BAD_ANSWER_FORMAT = "Такой вариант не может быть буквой: ";
    public static final String USER_LOSE = "Ты проиграл! Но, может, попробуешь ещё раз?";
    public static final String USER_WON = "Молодец! Ты выиграл!";
    public static final int ONE_LETTER = 1;
    public static final String ALREADY_CHECKED_LETTER = "Эту букву ты уже угадал!" + emojiCatSmiling;
    public static final int GAME_CONTINUING = 0;
    public static final int GAME_END = 1;
    public static final String RULES = "Я сижу на плоту посреди озера. " +
            "Есть слово, которое тебе нужно угадать. Отправляй по одной букве. " +
            "Каждый раз, когда ты ошибаешься, у плота исчезает одна доска. " +
            "Если ты не угадаешь слово за " + GameConsts.MAX_ATTEMPTS + " попыток, я упаду в воду! "+
            "Я, конечно, не утону, но плавать терпеть не могу! Так что постарайся!" + emojiCatSmiling;
    public static final String GUESS = "Угадай, что это:";
    public static final String ATTEMPTS_FOR_USER = "У тебя " + GameConsts.MAX_ATTEMPTS + " попыток.";
    public static final String SHOW_WORD = "Слово было: ";
}
