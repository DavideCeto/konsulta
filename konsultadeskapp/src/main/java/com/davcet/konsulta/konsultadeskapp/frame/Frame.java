package com.davcet.konsulta.konsultadeskapp.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Frame extends JFrame {
  private static final long serialVersionUID = 7794679420970841798L;
  //private final JButton testButton;
  //private final JTextArea textArea;
  
  public Frame() throws IOException {
    //=====================================================
    //TITLE
    //=====================================================
    
    JLabel introLabel = new JLabel();
    StringBuilder title = new StringBuilder();
    
    Path titlePath = Paths.get("konsultadeskapp/title.html");
    List<String> htmlTitle = Files.readAllLines(titlePath);
    htmlTitle.forEach(el -> title.append(el));

    introLabel.setText(title.toString());
    introLabel.setBackground(Color.WHITE);
    introLabel.setOpaque(true);
    introLabel.setVisible(true);
    introLabel.setFont(Font.decode("Courier New"));
    introLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    add(introLabel);
    
    //=====================================================
    //MAIN FRAME
    //=====================================================
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500,500);
    setVisible(true);
    ImageIcon icon = new ImageIcon("index.jpg");
    setIconImage(icon.getImage());
    //=====================================================
    
    /*super("konsulta");
    setLayout(new BorderLayout());
    
    testButton = new JButton("Test");
    testButton.addActionListener(e -> {testButton();});
    add(testButton, BorderLayout.PAGE_START);

    StringBuilder env = new StringBuilder();
    System.getenv().forEach((k, v) -> {
      if (k.contains("KONSULTA") && k.contains("URL")) {
        String db = k.substring(9, k.indexOf("_URL"));
        env.append(db + ":" + v + "\n");
      }
      if (k.contains("KONSULTA") && k.contains("VERSION")) super.setTitle("konsulta - version " + v);
    });

    textArea = new JTextArea();
    textArea.setText(env.toString());
    textArea.setBounds(0, 0, 500, 250);
    add(textArea);
        
    Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
    JLabel label = new JLabel();
    label.setText(env.toString());
    label.setVisible(true);
    label.setBorder(border);
    add(label, BorderLayout.CENTER);
    
    setLocationRelativeTo(null);
    setSize(500,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
     */
  }
  
  /*private void testButton() {
    String test = KonsultaTest.testSqlManagerWithH2InMemoryDb();
    textArea.setText(test);
  }*/
}
