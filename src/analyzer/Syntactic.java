import java.util.ArrayList;

public class SyntacticAnalyzer {

    private ArrayList<Token> lexemList;
    private Token currentToken;
    private SynError currentError = SynError.OK;
    private int position;

    /** Kinds of exceptions */
    public enum SynError {
        KEY_WORD, OK, EXPECTED_OPERATOR, EXPECTED_SEMICOLON, EXPECTED_VARIABLE,
        EXPECTED_LEFT_PAREN, EXPECTED_RIGHT_PAREN
    }

    /** Standart constructor */
    SyntacticAnalyzer(){
    }

    /**
     * Check the lexema's type
     * @param lexema need check
     * @param expectedType is type, we need
     * @return true if lexema's type is expected
     */
    private boolean oneLexemRequire(Token lexema, Token.Type expectedType){
        return lexema.getTyp() == expectedType;
    }

    /**
     * Check the lexema's type
     * @param lexema need check
     * @param firstExpectedType is first type, we need
     * @param secondExpectedType is second type, we need
     * @return true if lexema's type is one of the expected
     */
    private boolean twoLexemRequire(Token lexema, Token.Type firstExpectedType, Token.Type secondExpectedType){
        return lexema.getTyp() == firstExpectedType || lexema.getTyp() == secondExpectedType;
    }

    /**
     * Check the lexema's type
     * @param lexema need check
     * @param firstExpectedType is first type, we need
     * @param secondExpectedType is second type, we need
     * @param thirdExpectedType is thirdtype, we need
     * @return true if lexema's type is one of the expected
     */
    private boolean threeLexemRequire(Token lexema, Token.Type firstExpectedType, Token.Type secondExpectedType,
                                      Token.Type thirdExpectedType){
        return lexema.getTyp() == firstExpectedType || lexema.getTyp() == secondExpectedType ||
                lexema.getTyp() == thirdExpectedType;
    }

    /** Install current token and current position of the text */
    private Token getNextLexem(){
        currentToken = lexemList.remove(0);
        position = currentToken.getPosition();
        return currentToken;
    }

    /** Check is it "for" token name */
    private boolean isFOR(Token token){
        if(!token.getValue().equals("for") ) {
            System.out.println("'for' is expected!");
            return false;
        }
        else return true;
    }

    /** Check is it "to" token name */
    private boolean isTO(Token token) {
        if(!token.getValue().equals("to") ) {
            System.out.println("'to' is expected!");
            return false;
        }
        else return true;
    }

    /** Check is it "do" token name */
    private boolean isDO(Token token) {
        if(!token.getValue().equals("do") ) {
            System.out.println("'do' is expected!");
            return false;
        }
        else return true;
    }

    /** Check is it "begin" token name */
    private boolean isBegin(Token token) {
        return token.getValue().equals("begin");
    }

    /** Check is it "end" token name */
    private boolean isEnd(Token token) {
        if(!token.getValue().equals("end") ) {
            System.out.println("'end is expected!");
            return false;
        }
        else return true;
    }

    /** Check next construction: <variable> <operator> <variable | number> */
    private SynError assignment(){
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.SEPARATOR)) return SynError.EXPECTED_OPERATOR;
        if (!oneLexemRequire(getNextLexem(), Token.Type.MATH_OPERATOR)) return SynError.EXPECTED_OPERATOR;
        if (!twoLexemRequire(getNextLexem(), Token.Type.VARIABLE, Token.Type.NUMBER)) return SynError.EXPECTED_VARIABLE;
        return SynError.OK;
    }

    /** <begin> <body> <key word[end]> <semicolon> */
    private SynError beginConstruction(){
        getNextLexem(); // remove 'begin'
        if (twoLexemRequire(lexemList.get(0), Token.Type.KEY_WORD, Token.Type.VARIABLE)) {
            currentError = checkNextConstruction();
            if (currentError != SynError.OK) return currentError;
        } else return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.SEPARATOR)) return SynError.EXPECTED_SEMICOLON;
        if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) {
            if (isEnd(lexemList.get(0))) {
                currentError = checkEndConstruction();
                if (currentError != SynError.OK) return  currentError;
            } else return SynError.KEY_WORD;
        } else return SynError.KEY_WORD;
        return SynError.OK;
    }

    /** <key word[for]> <assignment> <key word[to]> <variable> <key word[do]> <body> <checkEndConstruction> */
    private SynError forConstruction(){
        getNextLexem(); //remove 'for'
        currentError = assignment();
        if (currentError != SynError.OK) return currentError;
        if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) {
            if (!isTO(lexemList.get(0))) return SynError.KEY_WORD;
            else getNextLexem(); // remove'to'
        } else return SynError.KEY_WORD;
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) {
            if (!isDO(lexemList.get(0))) return SynError.KEY_WORD;
            else getNextLexem(); // remove 'do'
        } else return SynError.KEY_WORD;
        if (twoLexemRequire(lexemList.get(0), Token.Type.KEY_WORD, Token.Type.VARIABLE)) {
            currentError = checkNextConstruction();
            if (currentError != SynError.OK) return currentError;
        } else return SynError.EXPECTED_VARIABLE;
        if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) {
            if (isEnd(lexemList.get(0))) {
                currentError = checkEndConstruction();
                if (currentError != SynError.OK) return  currentError;
            } else return SynError.KEY_WORD;
        } else return SynError.KEY_WORD;
        return SynError.OK;
    }

    /** <end> <semicolon> */
    private SynError checkEndConstruction() {
        getNextLexem();
        if (!oneLexemRequire(getNextLexem(), Token.Type.SEPARATOR)) return SynError.EXPECTED_SEMICOLON;
        return SynError.OK;
    }

    /** <firstCondition> <logic operator> <secondCondition> */
    private SynError whileConstructions(){
        currentError = firstCondition();
        if (currentError != SynError.OK) return currentError;
        if (!oneLexemRequire(getNextLexem(), Token.Type.LOGIC_OPERATOR)) return SynError.EXPECTED_OPERATOR;
        currentError = secondCondition();
        if (currentError != SynError.OK) return currentError;
        return SynError.OK;
    }

    /** <variable> <math operator> <logic operator> <variable> */
    private SynError firstCondition(){
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if(!oneLexemRequire(getNextLexem(), Token.Type.MATH_OPERATOR)) return SynError.EXPECTED_OPERATOR;
        if(!oneLexemRequire(getNextLexem(), Token.Type.LOGIC_OPERATOR)) return SynError.EXPECTED_OPERATOR;
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        return SynError.OK;
    }

    /** <variable> <math operator> <variable> <left square paren> <variable> <right square paren> */
    private SynError secondCondition(){
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.MATH_OPERATOR)) return SynError.EXPECTED_OPERATOR;
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.LEFT_SQUARE_PAREN)) return SynError.EXPECTED_LEFT_PAREN;
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.RIGHT_SQEARE_PAREN)) return SynError.EXPECTED_RIGHT_PAREN;
        return SynError.OK;
    }

   /** Check the construction body. Are there some other constructions inside*/
    private SynError checkNextConstruction() {
        if (lexemList.get(0).getTyp() == Token.Type.KEY_WORD) {
            currentError = checkTheKeyWord();
            if (currentError != SynError.OK) return currentError;
        }
        else {
            currentError = assignment();
            if (currentError != SynError.OK) return currentError;
        }
        return SynError.OK;
    }

    /** Chose the next construction for analyze by the key word */
    private SynError checkTheKeyWord() {
        if (isBegin(lexemList.get(0))) {
            currentError = beginConstruction();
            if (currentError != SynError.OK) return currentError;
        }
        else if (isFOR(lexemList.get(0))) {
            currentError = forConstruction();
            if (currentError != SynError.OK) return currentError;
        }
        return SynError.OK;
    }

    /** Check the first construction */
    SynError code(ArrayList<Token> tokens){
        lexemList = tokens;
        try {
            if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) { // is KW?
                 currentError = checkTheKeyWord();
                if (currentError != SynError.OK) return currentError;
            } else return SynError.KEY_WORD;
        } catch (java.lang.IndexOutOfBoundsException ex){
            return SynError.EXPECTED_SEMICOLON;
        }
        return SynError.OK;
    }

    /** Output exception message */
    String getMessage(SynError exception){
        if (exception == SynError.OK) return "OK";
        return String.format("Syntactic error! %s\n\n... %s ...\n\nPosition: %s", exception, currentToken.getValue(), position);
    }
}
