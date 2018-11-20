import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static Lexer lexer = new Lexer();

    /** Standart constructor */
    Parser(){}

    /** Reading the text to be analyzed from the file */
    private String inputText = lexer.getInputText();

    /** Initialize tokens list */
    private ArrayList<Token> tokens = lexer.tokens;

    /** Creating the list with key words */
    private ArrayList<String> keyWords = lexer.getKeyWords();

    /** Variable list */
    private ArrayList<Variable> variableList;

    /** Looking for lexemas from the text */
    public ArrayList<Token> parser(){
        //Creating Pattern objects
        Pattern rID = Pattern.compile("[a-zA-Z_0-9]+");
        Pattern rID1 = Pattern.compile("[a-zA-Z0-9]+");
        Pattern rLeftRoundParen = Pattern.compile("\\(");
        Pattern rRightRoundParen = Pattern.compile("\\)");
        Pattern rMath = Pattern.compile("[/*+>=<]+");
        Pattern rLeftSquareParen = Pattern.compile("\\[");
        Pattern rRightSquareParen = Pattern.compile("]");
        Pattern rLeftFigureParen = Pattern.compile("\\{");
        Pattern rRightFigureParen = Pattern.compile("\\{");
        Pattern rLogicOperator = Pattern.compile("[|&+-]+");
        Pattern rCaret = Pattern.compile("\\^");
        Pattern rSeparator = Pattern.compile("[,.!?;:]+");
        Pattern rNumber = Pattern.compile("[0-9]+");

        //Creating Matcher objects
        Matcher mID = rID.matcher(inputText);
        Matcher mID1 = rID1.matcher(inputText);
        Matcher mMath = rMath.matcher(inputText);
        Matcher mLeftRoundParen = rLeftRoundParen.matcher(inputText);
        Matcher mRightRoundParen = rRightRoundParen.matcher(inputText);
        Matcher mLeftSquareParen = rLeftSquareParen.matcher(inputText);
        Matcher mRightSquareParen = rRightSquareParen.matcher(inputText);
        Matcher mLeftFigureParen = rLeftFigureParen.matcher(inputText);
        Matcher mRightFigureParen = rRightFigureParen.matcher(inputText);
        Matcher mLogicOperator = rLogicOperator.matcher(inputText);
        Matcher mCaret = rCaret.matcher(inputText);
        Matcher mSeparator = rSeparator.matcher(inputText);
        Matcher mNumber = rNumber.matcher(inputText);

        //Finding new tokens and adding them to the tokens list.
        while (mID.find()) {
            checkTheVariable(inputText.substring(mID.start(), mID.end()), mID.start());
        }
        while (mMath.find()) {
            Token token = new Token(inputText.substring(mMath.start(), mMath.end()), Token.Type.MATH_OPERATOR,
                    mMath.start());
            tokens.add(token);
        }
        while (mLeftRoundParen.find()) {
            Token token = new Token(inputText.substring(mLeftRoundParen.start(), mLeftRoundParen.end()),
                    Token.Type.LEFT_ROUND_PAREN, mLeftRoundParen.start());
            tokens.add(token);
        }
        while (mRightRoundParen.find()) {
            Token token = new Token(inputText.substring(mRightRoundParen.start(), mRightRoundParen.end()),
                    Token.Type.RIGHT_ROUND_PAREN, mRightRoundParen.start());
            tokens.add(token);
        }
        while (mLeftSquareParen.find()) {
            Token token = new Token(inputText.substring(mLeftSquareParen.start(), mLeftSquareParen.end()),
                    Token.Type.LEFT_SQUARE_PAREN, mLeftSquareParen.start());
            tokens.add(token);
        }
        while (mRightSquareParen.find()) {
            Token token = new Token(inputText.substring(mRightSquareParen.start(), mRightSquareParen.end()),
                    Token.Type.RIGHT_SQEARE_PAREN, mRightSquareParen.start());
            tokens.add(token);
        }
        while (mLeftFigureParen.find()) {
            Token token = new Token(inputText.substring(mLeftFigureParen.start(), mLeftFigureParen.end()),
                    Token.Type.LEFT_FIGURE_PETERN, mLeftFigureParen.start());
            tokens.add(token);
        }
        while (mRightFigureParen.find()) {
            Token token = new Token(inputText.substring(mRightFigureParen.start(), mRightFigureParen.end()),
                    Token.Type.RIGHT_FIGURE_PATERN, mRightFigureParen.start());
            tokens.add(token);
        }
        while (mLogicOperator.find()) {
            Token token = new Token(inputText.substring(mLogicOperator.start(), mLogicOperator.end()),
                    Token.Type.LOGIC_OPERATOR, mLogicOperator.start());
            tokens.add(token);
        }
        while (mCaret.find()) {
            Token token = new Token(inputText.substring(mCaret.start(), mCaret.end()),
                    Token.Type.CARET, mCaret.start());
            tokens.add(token);
        }
        while (mSeparator.find()) {
            Token token = new Token(inputText.substring(mSeparator.start(), mSeparator.end()),
                    Token.Type.SEPARATOR, mSeparator.start());
            tokens.add(token);
        }
        return tokens;
    }

    /** Check, does the variable begins with digit */
    private void checkTheVariable(String var, int start) {
        String regex = "\\d+";
        if (var.substring(0, 1).matches(regex)){
            if (var.matches(regex)) {
                Token token = new Token(var, Token.Type.NUMBER, start);
                tokens.add(token);
            } else System.out.println("Error! \nPosition: " + start + "\nVariable " + var + " couldn't start with digit.");
        } else {
            // check key word or variable
            if (keyWords.contains(var)) {
                Token token = new Token(var, Token.Type.KEY_WORD, start);
                tokens.add(token);
            } else {
                Token token = new Token(var, Token.Type.VARIABLE, start);
                tokens.add(token);
            }
        }
    }

    /** Output the result of lexical analyze */
    public void outTableOfLexemas(){
        System.out.println("-----------------------------------------");
        System.out.printf("| %-10s%-7s%-20s |", "Lexem", "|", "Lexem type");
        System.out.println("\n-----------------------------------------");
        lexer.sortToken(tokens);
        for (Token t: tokens){
            System.out.println(t.toString());
        }
        System.out.println("-----------------------------------------");
    }
}
