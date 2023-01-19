package com.davcet.konsulta.konsultacommon.exception;

import java.util.Arrays;
import com.davcet.konsulta.konsultacommon.env.EnvVariables;

public final class ExceptionManager {
  private static final String kver = EnvVariables.GET.konsultaVersion();
  
  public static final void print(Exception e) {
    
    System.out.println("KONSULTA VERSION:" + kver);
    
    /** Print Stack Trace **/
    System.out.println(e.getMessage());

    Arrays.asList(e.getStackTrace()).forEach(System.out::println);
    
  }
  
  public static final String getStackTrace(Exception e) {
    
    StringBuilder sb = new StringBuilder();
    sb.append("KONSULTA VERSION:");
    sb.append(kver);
    sb.append("\n");
    
    /** Print Stack Trace **/
    sb.append("MESSAGE:");
    sb.append(e.getMessage());
    sb.append("\n");

    Arrays.asList(e.getStackTrace()).forEach(el -> {
      sb.append(el);
      sb.append("\n");
     });

    return sb.toString();
  }
}
