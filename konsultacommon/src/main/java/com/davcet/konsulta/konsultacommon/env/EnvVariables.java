package com.davcet.konsulta.konsultacommon.env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum EnvVariables {
  GET;
  
  public List<String> dbNames(){
    Map<String, String> env = System.getenv();
    List<String> dbNames = new ArrayList<>();
    for (String k : env.keySet()) {
      if (k.contains("KONSULTA") && k.contains("URL")) {
        String db = k.substring(9, k.indexOf("_URL"));
        dbNames.add(db);
      }
    }
    
    return dbNames;
  }

  public String konsultaVersion(){
    Map<String, String> env = System.getenv();
    for (String k : env.keySet()) {
      if (k.contains("KONSULTA") && k.contains("VERSION")) {
        return env.get(k);
      }
    }
    return "no version retrieved";
  }

  public String dbUrl(String dbName){
    return System.getenv("KONSULTA_" + dbName + "_URL");
  }
  
  public String dbUser(String dbName){
    return System.getenv("KONSULTA_" + dbName + "_USER");
  }
  
  public String dbPwd(String dbName){
    return System.getenv("KONSULTA_" + dbName + "_PWD");
  }

}