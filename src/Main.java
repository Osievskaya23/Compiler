import java.util.ArrayList;

public class Main {

    public static SyntacticAnalyzer syntacticAnalyzer = new SyntacticAnalyzer();
    public static Parser parser = new Parser();

    public static void main(String[] args) {
        parser.parser();
        parser.outTableOfLexemas();
        // Output the result of syntactic analyze
        /*tokens = lexer.sortToken(tokens);
        variableTypeFinder();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getTyp() == Token.Type.VARIABLE){
                if (tokens.get(i).getTyp() == Token.Type.KEY_WORD){

                }
                variableList.add(new Variable(tokens.get(i). getValue()));
            }
        }*/
        /*tokens = lexer.sortToken(tokens);
        System.out.println(syntaxer.getMessage(syntaxer.code(tokens)));*/
    }

   /* private static void variableTypeFinder() {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getTyp() == Token.Type.VARIABLE && tokens.get(i-1).getTyp() == Token.Type.KEY_WORD){

            }
        }
    }*/


}
