import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    private File selectedFile;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        JFrame frame = new JFrame("Jmenu example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JTextArea textArea = new JTextArea();
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.setVisible(true);

        frame.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int val = jFileChooser.showOpenDialog(frame);
                if (val == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jFileChooser.getSelectedFile();
                    try (
                            BufferedReader bfr = new BufferedReader(new FileReader(selectedFile))) {

                        StringBuilder text = new StringBuilder();
                        String line;

                        while ((line = bfr.readLine()) != null) {
                            text.append(line).append("\n");
                        }

                        textArea.setText(text.toString());
                    } catch (Exception e1) {
                        System.out.println("Error.. Try again");
                    }
                }
            }
        });

        frame.add(scrollPane);

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try (BufferedWriter bfw = new BufferedWriter(new FileWriter(selectedFile))) {
                        String text = textArea.getText();
                        bfw.write(text);
                        bfw.flush();
                        System.out.println("File saved successfully..");
                    } catch (Exception p) {
                        System.out.println("There was an error saving the file.");
                    }
                } else {
                    System.out.println("No selected file!");
                }
            }
        });
    }
}

