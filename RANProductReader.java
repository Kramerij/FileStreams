import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class RANProductReader extends JFrame{
    JPanel mainPnl, displayPnl;
    JTextArea displayFindings;
    JTextField filterBy;
    String filterLetters;
    final int FIELDS_LENGTH = 4;

    String id, name, description;
    double cost;

    public RANProductReader() {
        mainPnl = new JPanel(new BorderLayout());
        add(mainPnl);
        createDisplay();
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        mainPnl.add(displayPnl);

    }
    JButton beginFilter;
    public void createDisplay(){
        displayPnl = new JPanel(new BorderLayout());
        beginFilter = new JButton("Find");
        beginFilter.addActionListener((ActionEvent ae) -> {
            filterLetters = filterBy.getText();
            filter();
                });
        filterBy = new JTextField("input what you're looking for");
        displayFindings = new JTextArea(10,35);
        displayFindings.setFont(new Font("Courier New", Font.PLAIN, 12));
        displayPnl.add(displayFindings, BorderLayout.CENTER);
        displayPnl.add(filterBy, BorderLayout.NORTH);
        displayPnl.add(beginFilter, BorderLayout.SOUTH);
    }
    ArrayList<String> filteredLines = new ArrayList<>();

    public void filter(){
        Stream<String> lines;
        String appendLines;
        try {
            lines = Files.lines(Path.of("ProductDataRAN.txt"));
            lines
                    .filter(str -> str.toLowerCase().contains(filterLetters))
                    .forEach(s -> filteredLines.add(s));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        displayFindings.append("ID      Name       Description               Cost\n");
        displayFindings.append("===================================================\n");
        String[] fields;
        for (String a : filteredLines) {
            fields = a.split("\\s{2,75}", 0);

            if (fields.length == FIELDS_LENGTH) {
                id = fields[2].trim();
                name = fields[0].trim();
                description = fields[1].trim();
                cost = Double.parseDouble(fields[3].trim());
                appendLines = String.format("\n%-8s%-10s%-25s%-6s", id, name, description, cost);
                displayFindings.append(String.format("\n%-8s%-10s%-25s%-6s", id, name, description, cost));

                //displayFindings.append(appendLines);
            } else {

            }
        }

    }
}
