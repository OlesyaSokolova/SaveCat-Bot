import consts.PhotosHandlerConsts;
import gameLogic.Game;
import gameLogic.PhotosHandler;
import gameLogic.WrongAnswerException;
import consts.BotConsts;
import consts.ButtonSigns;
import consts.GameConsts;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot
{
    public static void main(String[] args)
    {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try
        {
            Bot bot =  new Bot();
            telegramBotsApi.registerBot(bot);
            bot.initPhotos();
        }
        catch(TelegramApiRequestException ex)
        {
            ex.printStackTrace();
        }
    }

    private void initPhotos() {
        photos_= new PhotosHandler(GameConsts.MAX_ATTEMPTS, PhotosHandlerConsts.PHOTOS_FORMAT_JPG);
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        Message message = update.getMessage();
        if(message != null && message.hasText())
        {
            String messageText = message.getText();
            switch(messageText)
            {
                case ButtonSigns.START:
                {
                    sendNeededPhoto(message, PhotosHandlerConsts.GREETING_PHOTO);
                    sendMsg(message, BotConsts.GREETING);
                    break;
                }
                case ButtonSigns.RULES_BUTTON:
                {
                    sendNeededPhoto(message, PhotosHandlerConsts.RULES_PHOTO);
                    sendMsg(message, GameConsts.RULES);
                    break;
                }
                case ButtonSigns.NEW_GAME_BUTTON:
                {
                    endGame();
                    initPhotos();
                    sendNeededPhoto(message, PhotosHandlerConsts.NEW_GAME_PHOTO);
                    startGame(message);
                    break;
                }
                default:
                {
                    if(game_ != null)
                    {
                        String result = GameConsts.WRONG_ANSWER;
                        try
                        {
                            result = game_.checkUserAnswer(message.getText());
                        }
                        catch(WrongAnswerException ex)
                        {
                            sendMsg(message, ex.whatIsTheProblem());
                            break;
                        }
                        String photoToSend;
                        if(result.contains(GameConsts.WRONG_ANSWER))
                        {
                            photoToSend = String.valueOf(GameConsts.MAX_ATTEMPTS + PhotosHandlerConsts.PHOTO_SHIFT - game_.getLeftAttempts());
                        }
                        else if(result.contains(GameConsts.USER_LOSE))
                        {
                            photoToSend = PhotosHandlerConsts.GAMEOVER_PHOTO;
                        }
                        else if(result.contains(GameConsts.ALREADY_CHECKED_LETTER))
                        {
                            photoToSend = PhotosHandlerConsts.GAMEOVER_PHOTO;
                            sendMsg(message, result);
                            break;
                        }
                        else {
                            photoToSend = PhotosHandlerConsts.GUESSED_PHOTO;
                        }
                        sendNeededPhoto(message, photoToSend.toString());
                        sendMsg(message, result);
                    }
                    else {
                        sendMsg(message, BotConsts.NO_GAME_YET);
                    }
                    break;
                }
            }
        }
        if(game_.getGameStatus() == GameConsts.GAME_END)
        {
            endGame();
        }
    }

    public void setButtons(SendMessage sendMessage)
    {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton(ButtonSigns.RULES_BUTTON));
        keyboardFirstRow.add(new KeyboardButton(ButtonSigns.NEW_GAME_BUTTON));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
    public void sendMsg(Message message, String text)
    {
        SendMessage messageToSend = new SendMessage();
        messageToSend.enableMarkdown(true);
        messageToSend.setChatId(message.getChatId().toString());
        messageToSend.setText(text);
        try
        {
            if(game_ != null && game_.getGameStatus() == GameConsts.GAME_CONTINUING)
            {
                setButtons(messageToSend);
            }
            execute(messageToSend);
        }
        catch(TelegramApiException ex)
        {
            ex.printStackTrace();
        }
    }
    public void sendNeededPhoto(Message message, String neededPhoto)
    {
         SendPhoto photo = new SendPhoto();
         photo.setChatId(message.getChatId());
         String photoPath = photos_.getFilePath(neededPhoto);
         photo.setNewPhoto(new File(photoPath));
         try {
             sendPhoto(photo);
         } catch (TelegramApiException e) {
             e.printStackTrace();
         }
    }
    @Override
    public String getBotUsername() {
        return BotConsts.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConsts.TOKEN;
    }
    public void startGame(Message message)
    {
        if(game_ == null)
        {
            game_ = new Game();
        }

        String word = game_.newWordGame();
        sendMsg(message,GameConsts.GUESS + GameConsts.LINE_DELIMITER + word);
        sendMsg(message,GameConsts.ATTEMPTS_FOR_USER);
    }
    public void endGame()
    {
        game_ = null;
    }
    private Game game_;
    private PhotosHandler photos_;
}