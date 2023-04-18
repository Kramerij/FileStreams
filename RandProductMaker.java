import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductMaker extends JFrame {
    ArrayList<String> lines = new ArrayList<>();
    Product product = new Product();
    String pName, pDesc, pID, fCost;
    Double pCost;
    int recordCountNum;
    JPanel mainPnl, productDataPnl, buttonPnl;
    JTextField name, desc, ID, cost;
    JTextArea recordCount;


    public RandProductMaker() {
        mainPnl = new JPanel(new BorderLayout());
        createProductPnl();
        createProductButtonPnl();
        add(mainPnl);
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        mainPnl.add(productDataPnl, BorderLayout.NORTH);
        mainPnl.add(buttonPnl, BorderLayout.SOUTH);

    }
    JLabel recordCountLabel;
    JButton addProductButton;
    long pos = 1;
    public void createProductButtonPnl() {
        recordCount = new JTextArea("");
        recordCount.setText(String.valueOf(recordCountNum));
        recordCountLabel = new JLabel("record count");
        addProductButton = new JButton("Add Product");
        buttonPnl = new JPanel(new GridLayout(3, 1));
        addProductButton.addActionListener((ActionEvent ae) -> {

            pName = name.getText();
            formatName();
            pDesc = desc.getText();
            formatDescription();
            pID = ID.getText();
            formatID();
            fCost = cost.getText();
            formatCost();

            lines.add(pName + pDesc + pID + fCost );
            productData();
            recordCountNum++;
            recordCount.setText(String.valueOf(recordCountNum));
            //saveProductData(pos,pName, pDesc, pID, fCost );
            pos = pos + 124;
            resetFeilds();
        });
        buttonPnl.add(recordCountLabel); buttonPnl.add(recordCount); buttonPnl.add(addProductButton);
    }

    public void createProductPnl() {
        productDataPnl = new JPanel(new GridLayout(2, 2));
        name = new JTextField("name");
        desc = new JTextField("description");
        ID = new JTextField("ID");
        cost = new JTextField("Cost");
        productDataPnl.add(name);
        productDataPnl.add(desc);
        productDataPnl.add(ID);
        productDataPnl.add(cost);
    }

    public void formatName() {
        pName = String.format("%-35s", pName);
        product.setName(pName);
        System.out.printf(product.name);
    }

    public void formatDescription() {
        pDesc = String.format("%-75s", pDesc);
        product.setDescription(pDesc);
        System.out.printf(product.description);
    }

    public void formatID() {
        pID = String.format("%-6s", pID);
        product.setID(pID);
        System.out.printf(product.ID);
    }

    public void formatCost() {
        fCost = String.format("%8s", Double.valueOf(fCost));
        pCost = Double.valueOf(fCost);
        System.out.println(fCost);
    }
    public void resetFeilds(){
        name.setText("name");
        desc.setText("description");
        ID.setText("ID");
        cost.setText("cost");
    }
public void productData(){


        File workingDirectory = new File(System.getProperty("user.dir"));
    Path file = Paths.get(workingDirectory.getPath() + "\\src\\ProductDataRAN.txt");
    try
    {

        OutputStream out =
                new BufferedOutputStream(Files.newOutputStream(file, CREATE));
        BufferedWriter writer =
                new BufferedWriter(new OutputStreamWriter(out));

        for(String rec : lines )
        {
            writer.write(rec, 0, rec.length());
            writer.newLine();

        }
        writer.close();

    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
}
    private static void saveProductData(long pos, String pName, String pDesc, String pID, String fCost) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("ProductDataRAN.txt", "rw");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            file.getFilePointer(); // move filePointer to pos
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            file.write(String.format("%35s", pName).getBytes(StandardCharsets.UTF_8));
            file.write(String.format("%75s", pDesc).getBytes(StandardCharsets.UTF_8));
            file.write(String.format("%6s", pID).getBytes(StandardCharsets.UTF_8));
            file.write(String.format("%8s", fCost).getBytes(StandardCharsets.UTF_8));
            //file.writeDouble(pCost);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
