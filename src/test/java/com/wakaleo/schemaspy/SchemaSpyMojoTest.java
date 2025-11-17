package com.wakaleo.schemaspy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.wakaleo.schemaspy.util.DatabaseHelper;
import java.io.File;
import java.util.Locale;
import java.util.logging.Logger;

import org.apache.maven.api.plugin.testing.InjectMojo;
import org.apache.maven.api.plugin.testing.MojoTest;
import org.apache.maven.plugin.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * SchemaSpyReport unit tests Test POM files is kept in test/resources/unit directory.
 *
 * @author john
 */
@MojoTest
public class SchemaSpyMojoTest {
  @BeforeEach
  public void setUp(TestInfo testInfo) throws Exception {
    DatabaseHelper.setupDatabase("src/test/resources/sql/testdb.sql");
    Logger.getLogger("global").info("Starting :" + testInfo.getDisplayName());
  }

  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/test-plugin-config.xml")
  public void testCustomConfiguration(SchemaSpyReport mojo) throws Exception {

    Log log = mock(Log.class);

    mojo.setLog(log);

    //        File projectCopy = this.resources.getBasedir("unit");
    //        File testPom = new File(projectCopy,"test-plugin-config.xml");
    //        assumeTrue(testPom.exists() && testPom.isFile(),
    //                "POM file should exist as file.");

    //        SchemaSpyReport mojo = new SchemaSpyReport();
    //        mojo = (SchemaSpyReport) this.rule.configureMojo(mojo,
    // rule.extractPluginConfiguration("schemaspy-maven-plugin", testPom));
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/test/schemaspy/index.html");
    System.out.println("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }

  @Test
  @InjectMojo(goal = "schemaspy", pom = "classpath:/unit/test-plugin-config.xml")
  public void testFullConfiguration(SchemaSpyReport mojo) throws Exception {
    //        File projectCopy = this.resources.getBasedir("unit");
    //        File testPom = new File(projectCopy,"full-test-plugin-config.xml");
    //
    //        assumeTrue(testPom.exists() && testPom.isFile(),
    //                "POM file should exist as file.");
    //
    //        SchemaSpyReport mojo = new SchemaSpyReport();
    //        mojo = (SchemaSpyReport) this.rule.configureMojo(mojo,
    // rule.extractPluginConfiguration("schemaspy-maven-plugin", testPom));
    mojo.executeReport(Locale.getDefault());

    // check if the reports generated
    File generatedFile = new File("./target/reports/full-test/schemaspy/index.html");
    System.out.println("generatedFile = " + generatedFile.getAbsolutePath());
    assertTrue(generatedFile.exists());
  }
  //    @Test
  //    public void testConfigurationUsingJDBCUrl() throws Exception {
  //        File projectCopy = this.resources.getBasedir("unit");
  //        File testPom = new File(projectCopy,"jdbcurl-test-plugin-config.xml");
  //        assumeNotNull("POM file should not be null.", testPom);
  //        assumeTrue("POM file should exist as file.",
  //                testPom.exists() && testPom.isFile());
  //
  //        SchemaSpyReport mojo = (SchemaSpyReport) this.rule.lookupMojo("schemaspy",testPom);
  //        mojo.executeReport(Locale.getDefault());
  //
  //        // check if the reports generated
  //        File generatedFile = new File("./target/reports/jdbcurl-test/schemaspy/index.html");
  //        System.out.println("generatedFile = " + generatedFile.getAbsolutePath());
  //        assertTrue(generatedFile.exists());
  //    }

}
