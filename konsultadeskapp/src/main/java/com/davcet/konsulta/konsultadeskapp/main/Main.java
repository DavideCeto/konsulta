package com.davcet.konsulta.konsultadeskapp.main;

import java.io.IOException;

import com.davcet.konsulta.konsultadeskapp.frame.Frame;

public class Main {
  public static void main (String... args) {
   try {
    new Frame();
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
  }
  }
}
