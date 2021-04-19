public class Pixel {
    String c = "  ";
    Float level;

    public void setC(String c, Float level) {
        if(this.level == null || this.level <= level){
            this.c = c;
            this.level = level;
        }
    }

    public void reset(){
        this.c = "  ";
        level = null;
    }

    @Override
    public String toString() {
        return c;
    }

}
