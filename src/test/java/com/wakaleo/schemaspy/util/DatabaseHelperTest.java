/*
 * TestDatabaseHelperTest.java
 * JUnit based test
 *
 * Created on 16 May 2007, 10:41
 */

package com.wakaleo.schemaspy.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/**
 * @author john
 */
class DatabaseHelperTest {
  private static final String TEST_DB_URL = "jdbc:hsqldb:file:target/testdb;shutdown=true";

  @Test
  void testSetupDatabase() throws Exception {
    DatabaseHelper.setupDatabase("src/test/resources/sql/testdb.sql");

    try (Connection connection = DriverManager.getConnection(TEST_DB_URL, "SA", "")) {
      try (Statement statement = connection.createStatement();
          ResultSet rs = statement.executeQuery("select * from employee")) {
        assertTrue(rs.next(), "Expected employee table to contain at least one row");
      }

      try (Statement statement = connection.createStatement();
          ResultSet rs = statement.executeQuery("select * from item")) {
        assertTrue(rs.next(), "Expected item table to contain at least one row");
      }

      try (Statement statement = connection.createStatement();
          ResultSet rs = statement.executeQuery("select * from customer")) {
        assertTrue(rs.next(), "Expected customer table to contain at least one row");
      }

      try (Statement statement = connection.createStatement();
          ResultSet rs = statement.executeQuery("select * from salesorder")) {
        assertTrue(rs.next(), "Expected salesorder table to contain at least one row");
      }
    }
  }
}
