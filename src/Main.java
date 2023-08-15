import ProjectCore.Menu;

public class Main {
    public static void main(String[] args) {
        try {
            Menu.start();
        } catch (Exception ex) {
            System.err.print(ex.getMessage());
        }
    }
}
