package com.davcet.konsulta.konsultacommon.env;

public enum EnvVariables {
	KONSULTA_VERSION("KONSULTA_VERSION"),
  KONSULTA_TEST_H2_URL("KONSULTA_TEST_H2_URL"),
  KONSULTA_TEST_H2_USER("KONSULTA_TEST_H2_USER"),
  KONSULTA_TEST_H2_PWD("KONSULTA_TEST_H2_PWD");
	
	EnvVariables(String var) {
		this.var = var;
	}
	
	private final String var;
		
	public final String value() {
		return System.getenv(this.var);
	}
}