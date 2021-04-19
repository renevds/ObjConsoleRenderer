public class Pixel {
    String c = "  ";
    Double level;

    public void setC(String c, Double level) {
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
