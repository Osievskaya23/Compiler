public class Token extends LexicalAnalyzer {

    /**
     * Types for this lexical analyzer to analyze
     */
    public enum Type {
        KEY_WORD, LEFT_ROUND_PAREN, RIGHT_ROUND_PAREN, MATH_OPERATOR, LEFT_SQUARE_PAREN,
        RIGHT_SQEARE_PAREN, LEFT_FIGURE_PETERN, RIGHT_FIGURE_PATERN, LOGIC_OPERATOR, SEPARATOR,
        NUMBER, VARIABLE, CARET
    }

    public Type typ;
    public String value;
    public int position;

    /**
     * Token constructor
     * @param typ Token type
     * @param value Token value
     * @param position Token index
     */
    public Token(String value, Type typ, int position){
        this.typ = typ;
        this.value = value;
        this.position = position;
    }

    /**
     * Returns the token's value and lexical category
     */
    @Override
    public String toString(){
        return String.format("| %-10s%-7s%-20s |", this.value, "|", this.typ);
    }

    public Type getTyp() {
        return typ;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    public void setTyp(Type typ) {
        this.typ = typ;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
