package com.davcet.konsulta.konsultadeskapp.frame;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.davcet.konsulta.konsultatest.*;

public class Frame extends JFrame {
  private final JButton testButton;
  private final JTextArea textArea;
  
  public Frame() {
    super("konsulta");
    
    setLayout(new BorderLayout());
    
    testButton = new JButton("Test");
    testButton.addActionListener(e -> {testButton();});
    add(testButton, BorderLayout.PAGE_START);

    StringBuilder env = new StringBuilder();
    System.getenv().forEach((k, v) -> {
      env.append(k + ":" + v + "\n");
    });

    textArea = new JTextArea();
    textArea.setText(env.toString());
    JScrollPane scroll = new JScrollPane(textArea);
    add(scroll, BorderLayout.CENTER);
    
    setLocationRelativeTo(null);
    setSize(500,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }
  
  private void testButton() {
    String test = KonsultaTest.testSqlManagerWithH2InMemoryDb();
    textArea.setText(test);
  }
}
