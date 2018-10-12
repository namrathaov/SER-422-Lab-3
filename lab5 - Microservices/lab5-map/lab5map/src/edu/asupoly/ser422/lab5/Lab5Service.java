package edu.asupoly.ser422.lab5;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public  class Lab5Service {
	
	// Singleton pattern
	private static Lab5Service __theService = null;

	public static final Lab5Service getService() throws Exception {
		if (__theService == null) {
			__theService = new Lab5Service();
		}
		return __theService;
	}

	public final String mapToLetterGrade(double grade) {
		if (grade >= 98.0) return "A+";
		if (grade >= 93.0) return "A";
		if (grade >= 90.0) return "A-";
		if (grade >= 88.0) return "B+";
		if (grade >= 83.0) return "B";
		if (grade >= 80.0) return "B-";
		if (grade >= 77.0) return "C+";
		if (grade >= 70.0) return "C";
		if (grade >= 60.0) return "D";
		if (grade < 0.0) return "I";
		return "E";
	}
}
