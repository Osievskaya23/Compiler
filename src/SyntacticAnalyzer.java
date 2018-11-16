import java.util.ArrayList;

public class SyntacticAnalyzer {

    private ArrayList<Token> lexemList;
    private Token currentToken;
    private SynError currentError;
    private int position;

    /**
     * Kinds of exceptions
     */
    public enum SynError {
        KEY_WORD, OK, EXPECTED_OPERATOR, EXPECTED_SEMICOLON, EXPECTED_VARIABLE,
        EXPECTED_LEFT_PAREN, EXPECTED_RIGHT_PAREN
    }

    /**
     * Standart constructor
     */
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

    // install current token and position in the text
    private Token getNextLexem(){
        currentToken = lexemList.remove(0);
        position = currentToken.getPosition();
        return currentToken;
    }

    private boolean isFOR(Token token){
        if(!token.getValue().equals("for") ) {
            System.out.println("'for' is expected!");
            return false;
        }
        else return true;
    }

    private boolean isTO(Token token) {
        if(!token.getValue().equals("to") ) {
            System.out.println("'to' is expected!");
            return false;
        }
        else return true;
    }

    private boolean isDO(Token token) {
        if(!token.getValue().equals("do") ) {
            System.out.println("'do' is expected!");
            return false;
        }
        else return true;
    }

    private boolean isBegin(Token token) {
        if(!token.getValue().equals("begin") ) return false;
        else return true;
    }

    private boolean isEnd(Token token) {
        if(!token.getValue().equals("end") ) {
            System.out.println("'end is expected!");
            return false;
        }
        else return true;
    }

    private SynError assignment(){
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)) return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.SEPARATOR)) return SynError.EXPECTED_OPERATOR;
        if (!oneLexemRequire(getNextLexem(), Token.Type.MATH_OPERATOR)) return SynError.EXPECTED_OPERATOR;
        if (!twoLexemRequire(getNextLexem(), Token.Type.VARIABLE, Token.Type.NUMBER)) return SynError.EXPECTED_VARIABLE;
        return SynError.OK;
    }

    public SynError beginConstruction(){
        SynError checkConstructionEndresult;
        SynError checkNextConstructionResult;
        getNextLexem(); // remove 'begin'
        if (twoLexemRequire(lexemList.get(0), Token.Type.KEY_WORD, Token.Type.VARIABLE)) {
            checkNextConstructionResult = checkNextConstruction();
            if (checkNextConstructionResult != SynError.OK) return checkNextConstructionResult;
        }
        else return SynError.EXPECTED_VARIABLE;
        if (!oneLexemRequire(getNextLexem(), Token.Type.SEPARATOR)) return SynError.EXPECTED_SEMICOLON;
        if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) {
            checkConstructionEndresult = checkConstructionEnd();
            if (checkConstructionEndresult != SynError.OK) return checkConstructionEndresult;
        }
        else return SynError.KEY_WORD;
        return SynError.OK;
    }

    public SynError forConstruction(){
        SynError assignmentResult;
        SynError checkNextConstructionResult;
        SynError checkConstructionEndResult;
        getNextLexem(); //remove 'for'
        assignmentResult = assignment();
        if (assignmentResult != SynError.OK) return assignmentResult;
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
            checkNextConstructionResult = checkNextConstruction();
            if (checkNextConstructionResult != SynError.OK) return checkNextConstructionResult;
        } else return SynError.EXPECTED_VARIABLE;
        if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) {
            checkConstructionEndResult = checkConstructionEnd();
            if (checkConstructionEndResult != SynError.OK) return checkConstructionEndResult;
        } else return SynError.KEY_WORD;
        return SynError.OK;
    }

    private SynError checkConstructionEnd() {
        if (!isEnd(lexemList.get(0))) return SynError.KEY_WORD;
        else getNextLexem();
        if (!oneLexemRequire(getNextLexem(), Token.Type.SEPARATOR)) return SynError.EXPECTED_SEMICOLON;
        return SynError.OK;
    }

    private SynError checkNextConstruction() {
        SynError assignmentResult;
        if (lexemList.get(0).getTyp() == Token.Type.KEY_WORD) checkTheKeyWord();
        else {
            assignmentResult = assignment();
            if (assignmentResult != SynError.OK) return SynError.EXPECTED_VARIABLE;
        }
        return SynError.OK;
    }

    // check the for loop body construction. Is it KW or VAR?
    private SynError forBodyConstruction() {
        SynError checkTheKeyWordResult;
        SynError assignmentResult;
        if (lexemList.get(0).getTyp() == Token.Type.KEY_WORD) {
            checkTheKeyWordResult = checkTheKeyWord();
            if (checkTheKeyWordResult != SynError.OK) return checkTheKeyWordResult;
        } else {
            assignmentResult = assignment();
            if (assignmentResult != SynError.OK) return assignmentResult;
        }
        return SynError.OK;
    }

    // check what KW is and chose the next construction for analyze
    private void checkTheKeyWord() {
        if (isBegin(lexemList.get(0))) currentError = beginConstruction();
        else if (isFOR(lexemList.get(0))) currentError = forConstruction();
        return SynError.OK;
    }


    /**
     * First part, which contains key word.
     * @param tokens is a list of lexems in right order.
     * @return exception type
     */
    public SynError code(ArrayList<Token> tokens){
        lexemList = tokens;
        try {
            if (oneLexemRequire(lexemList.get(0), Token.Type.KEY_WORD)) { // is KW?
                checkTheKeyWord();
            } else currentError = SynError.KEY_WORD;
        } catch (java.lang.IndexOutOfBoundsException ex){
            currentError = SynError.EXPECTED_SEMICOLON;
        }
    }

    /**
     * Second part, which looks for key word conditions.
     * @param lexemList is a list of lexems in right order.
     * @return exception type
     */
    private SynError statement(ArrayList<Token> lexemList){
        SynError firstRuleResult;
        SynError secondRuleResult;
        firstRuleResult = firstRule(lexemList);
        if (firstRuleResult != SynError.OK){
            return firstRuleResult;
        }
        if (!oneLexemRequire(getNextLexem(), Token.Type.LOGIC_OPERATOR)){
            return SynError.EXPECTED_OPERATOR;
        }
        secondRuleResult = secondRule(lexemList);
        if (secondRuleResult != SynError.OK){
            return secondRuleResult;
        }
        return SynError.OK;
    }

    /**
     * Third part, which contains first key word condition.
     * @param lexemList is a list of lexems in right order.
     * @return exception type
     */
    private SynError firstRule(ArrayList<Token> lexemList){
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)){
            return SynError.EXPECTED_VARIABLE;
        }
        if(!oneLexemRequire(getNextLexem(), Token.Type.MATH_OPERATOR)){
            return SynError.EXPECTED_OPERATOR;
        }
        if(!oneLexemRequire(getNextLexem(), Token.Type.LOGIC_OPERATOR)){
            return SynError.EXPECTED_OPERATOR;
        }
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)){
            return SynError.EXPECTED_VARIABLE;
        }
        return SynError.OK;
    }

    /**
     * Third part, which contains second key word condition.
     * @param lexemList is a list of lexems in right order.
     * @return exception type
     */
    private SynError secondRule(ArrayList<Token> lexemList){
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)){
            return SynError.EXPECTED_VARIABLE;
        }
        if(!oneLexemRequire(getNextLexem(), Token.Type.MATH_OPERATOR)){
            return SynError.EXPECTED_OPERATOR;
        }
        if (!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)){
            return SynError.EXPECTED_VARIABLE;
        }
        if (!oneLexemRequire(getNextLexem(), Token.Type.LEFT_SQUARE_PAREN)){
            return SynError.EXPECTED_LEFT_PAREN;
        }
        if(!oneLexemRequire(getNextLexem(), Token.Type.VARIABLE)){
            return SynError.EXPECTED_VARIABLE;
        }
        if(!oneLexemRequire(getNextLexem(), Token.Type.RIGHT_SQEARE_PAREN)){
            return SynError.EXPECTED_RIGHT_PAREN;
        }
        return SynError.OK;
    }

    /**
     *
     * @param exception is exception type.
     * @return message for user about Exceptions.
     */
    public String getMessage(SynError exception){
        if (exception == SynError.OK) return "OK";
        return String.format("Syntactic error!\n\n%s ...\n\n%s is expected\nPosition: %s", currentToken.getValue(), exception, position);
    }
}
