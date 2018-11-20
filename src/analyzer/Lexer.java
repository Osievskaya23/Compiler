import java.io.*;
import java.util.ArrayList;

public class Lexer extends Main{

    /** Array, what contain token lexem from the "text.txt" file and information about it */
    public ArrayList<Token> tokens = new ArrayList<>();

    /** Array, what contains key words from the "key words.txt" file */
    private ArrayList<String> keyWords = new ArrayList<>();

    /** Array, what contains key words (types of  variables from the "variable type.txt" file */
    private ArrayList<String> variableTypes = new ArrayList<>();

    /** Text to by analyzed*/
    private String inputText;

    /** Default constructor */
    Lexer(){
    }

    /**
     * Open "text.txt"
     * Read text to be analyzed. Split in words and write them down into the tokens list
     * @return text to be analyzed
     */
    String getInputText (){
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
     * Open "key words.txt"
     * Read key words and write them down into the keyWords list
     * @return list of key words
     */
    ArrayList<String> getKeyWords (){
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
     * Open "variable type.txt"
     * Read types of variables and write them down into the variableTypes list
     * @return list of key words
     */
    ArrayList<String> getVariableTypes(){
        try{
            FileInputStream fstream = new FileInputStream("C:\\Users\\38066\\IdeaProjects\\" +
                    "SP_lab3_Osievskaya\\src\\variable type.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) !=  null){
                variableTypes.add(strLine);
            }
            fstream.close();
        } catch (IOException ex) {
            System.out.println("IOException!");
        }
        return variableTypes;
    }

    /**
     * Array sort by position in the text
     * @param tokens is ArrayList of Tokens for sorting
     * @return sorted tokens
     */
    ArrayList<Token> sortToken(ArrayList<Token> tokens){
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
