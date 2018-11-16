import java.io.*;
import java.util.ArrayList;

public class LexicalAnalyzer extends Main{

    /** Array, what contain token lexem from the "text.txt" file and information about it */
    public ArrayList<Token> tokens = new ArrayList<>();

    /** Array, what contains key words from the "key words.txt" file */
    public ArrayList<String> keyWords = new ArrayList<>();

    /** Text to by analyzed*/
    public String inputText;

    /**
     * Default constructor
     */
    public LexicalAnalyzer(){
    }

    /**
     * Oppening the file "text.txt"
     * Read text to be analyzed
     * @return text to be analyzed
     */
    public String getInputText (){
        try{
            inputText = "";
            FileInputStream fstream = new FileInputStream("C:\\Users\\38066\\IdeaProjects\\SP_lab3_Osievskaya\\src\\text.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) !=  null){
                inputText += ("\n" + strLine);
            }
            inputText = inputText.substring(1);
            fstream.close();
        } catch (IOException ex) {
            System.out.println("IOException!");
        }
        return inputText;
    }

    /**
     * Oppening the file "key words.txt"
     * Read key words
     * @return list of key words
     */
    public ArrayList<String> getKeyWords (){
        try{
            FileInputStream fstream = new FileInputStream("C:\\Users\\38066\\IdeaProjects\\SP_lab3_Osievskaya\\src\\key words.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) !=  null){
                keyWords.add(strLine);
            }
            fstream.close();
        } catch (IOException ex) {
            System.out.println("IOException!");
        }
        return keyWords;
    }

    /**
     * Array sort by position in the text
     * @param tokens is ArrayList of Tokens for sorting
     * @return sorted tokens
     */
    public ArrayList<Token> sortToken(ArrayList<Token> tokens){
        int length = tokens.size();
        for(int i = 0; i < length - 1; i++){
            for (int j = 0; j < length - i - 1; j++) {
                if (tokens.get(j).getPosition() > tokens.get(j+1).getPosition()) {
                    Token tmp = tokens.get(j+1);
                    tokens.set(j+1, tokens.get(j));
                    tokens.set(j, tmp);
                }
            }
        }
        return tokens;
    }

}
