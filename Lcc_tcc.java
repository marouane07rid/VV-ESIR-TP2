public class Lcc_tcc {
    
    public boolean fct1() {
        return fct2();
    }

    public boolean fct2() {
        return fct3() && true;
    }

    public boolean fct3() {
        return false;
    }
}
