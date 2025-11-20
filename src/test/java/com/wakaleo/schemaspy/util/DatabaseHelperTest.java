/*
 * TestDatabaseHelperTest.java
 * JUnit based test
 *
 * Created on 16 May 2007, 10:41
 */

package com.wakaleo.schemaspy.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;

/**
 * @author john
 */
class DatabaseHelperTest {
  @Test
  void testSetupDatabase() throws Exception {
    DatabaseHelper.setupDatabase("src/test/resources/sql/testdb.sql");

    Connection connection = DriverManager.getConnection("jdbc:derby:target/testdb");

    ResultSet rs = connection.createStatement().executeQuery("select * from employee");
    assertNotNull(rs);

    rs = connection.createStatement().executeQuery("select * from item");
    assertNotNull(rs);

    rs = connection.createStatement().executeQuery("select * from customer");
    assertNotNull(rs);

    rs = connection.createStatement().executeQuery("select * from salesorder");
    assertNotNull(rs);

    rs.close();
    connection.close();
  }
}
