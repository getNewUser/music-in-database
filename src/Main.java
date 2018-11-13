import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Main Window");
        MainWindow mainWindow = new MainWindow();
        mainFrame.setContentPane(mainWindow.getContentPane());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,600);
        mainFrame.setVisible(true);






    }
}
