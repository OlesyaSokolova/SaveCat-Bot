package gameLogic;

import consts.PhotosHandlerConsts;

import java.util.ArrayList;
import java.util.HashMap;

public class PhotosHandler {
    private HashMap<String, String> photos_;
    private int filesNumber_;
    private String filesFormat_;
    public PhotosHandler(int neededFilesNumber, String format)
    {
        filesNumber_ = neededFilesNumber;
        filesFormat_ = format;
        photos_ = new HashMap<>();
        createPhotosTable();
    }
   private void createPhotosTable()
   {
       String pathBegining = PhotosHandlerConsts.PATH_FROM_CONTENT;
        for(int i = 1; i <= filesNumber_; i++)
        {
            photos_.put(String.valueOf(i), pathBegining + i + filesFormat_);
        }
       photos_.put(PhotosHandlerConsts.GREETING_PHOTO, PhotosHandlerConsts.PATH_FROM_CONTENT + PhotosHandlerConsts.GREETING_PHOTO + filesFormat_);
       photos_.put(PhotosHandlerConsts.NEW_GAME_PHOTO, PhotosHandlerConsts.PATH_FROM_CONTENT + String.valueOf(1) + filesFormat_);
       photos_.put(PhotosHandlerConsts.RULES_PHOTO, PhotosHandlerConsts.PATH_FROM_CONTENT + String.valueOf(1)  + filesFormat_);
       photos_.put(PhotosHandlerConsts.GAMEOVER_PHOTO, PhotosHandlerConsts.PATH_FROM_CONTENT + PhotosHandlerConsts.GAMEOVER_PHOTO + filesFormat_);
       photos_.put(PhotosHandlerConsts.GUESSED_PHOTO, PhotosHandlerConsts.PATH_FROM_CONTENT + PhotosHandlerConsts.GUESSED_PHOTO + filesFormat_);
   }
   public String getFilePath(String neededPhoto)
   {
       return photos_.get(neededPhoto);
   }
}
