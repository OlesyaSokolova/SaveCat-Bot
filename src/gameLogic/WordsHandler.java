package gameLogic;

import consts.WordsHandlerConsts;
import javafx.util.Pair;
import java.io.*;
import java.net.URL;
import java.util.HashMap;

public class WordsHandler
{

    HashMap<String, String> descMap_;
    Pair<String, String> wordWithDescPair_;
    public WordsHandler()
    {
        chooseRandomWord();
    }
    public String chooseWord() {

        return wordWithDescPair_.getKey();
    }
    public String getWordDescription(String word)
    {
        return wordWithDescPair_.getValue();
    }
    private void chooseRandomWord()
    {
        RandomAccessFile randomAccessFile = null;
        try
        {
            randomAccessFile = new RandomAccessFile(WordsHandlerConsts.WORDS_FILE, WordsHandlerConsts.READ );
        }
        catch (java.io.FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        String wordWithDescLine = readRandomString(randomAccessFile);
        wordWithDescPair_= parseLine(wordWithDescLine);
    }

    private String readRandomString(RandomAccessFile file)
    {
        long fileSize = 0;
        String randomLine = null;
        String res =  null;
        try
        {
            fileSize = file.length();
            long randomPos = (long) (Math.random() * fileSize);
            file.seek(randomPos);
            char c = (char) file.read();
            while (c != WordsHandlerConsts.END_DESC) {
                if (c == WordsHandlerConsts.FILE_DESCS_END) {
                    randomPos /= WordsHandlerConsts.RANDOM_POS_HALF;
                    file.seek(randomPos);
                }
                c = (char) file.read();
                randomPos++;
            }
            randomPos += WordsHandlerConsts.NEXT_LINE_SHIFT;
            file.seek(randomPos);
            String result = file.readLine();
            res = new String(result.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch(java.io.IOException ex)
        {
                ex.printStackTrace();
        }
            return res;
    }

    private Pair<String, String> parseLine(String randomLine)
    {
        randomLine = randomLine.substring(0, randomLine.length() - 1);
        String delimiter = WordsHandlerConsts.DESCRIPTION_DELIMITER;
        String[] wordAndDesc = randomLine.split(delimiter);
        return new  Pair<>(wordAndDesc[WordsHandlerConsts.WORD_INDEX], wordAndDesc[WordsHandlerConsts.DESC_INDEX]);
    }
    File getFileFromResources(String fileName)
    {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File " + fileName + " was not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
