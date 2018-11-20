public class Variable {

    private String name;
    private String type;

    Variable(String name, String type){
        this.name = name;
        this.type = type;
    }

    Variable(String name){
        this.name = name;
        this.type = null;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
