public class Variable {

    private Token token;
    private String type;

    Variable(Token token, String type){
        this.token = token;
        this.type = type;
    }

    Variable(Token token){
        this.token = token;
        this.type = null;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken(){
        return this.token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
