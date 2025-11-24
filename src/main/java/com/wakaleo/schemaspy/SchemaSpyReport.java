package com.wakaleo.schemaspy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

/**
 * The SchemaSpy Maven plugin report.
 *
 * <p>This plugin is designed to generate SchemaSpy report for a Maven website. SchemaSpy also may
 * need the Graphviz tool (https://www.graphviz.org/) in order to generate graphical representations
 * of the table/view relationships, so this needs to be installed on your machine.
 *
 * <p>The schemaspy goal invokes the SchemaSpy command-line tool. SchemaSpy generates a graphical
 * and HTML report describing a given relational database.
 *
 * @author John Smart
 * @author mprins
 */
@Mojo(name = "schemaspy", defaultPhase = LifecyclePhase.SITE)
public class SchemaSpyReport extends AbstractMavenReport {

  @Parameter(property = "vizjs", defaultValue = "true")
  protected boolean vizjs = false;

  /**
   * Whether to create the report only on the execution root of a multi-module project.
   *
   * @since 5.0.4
   */
  @Parameter(property = "runOnExecutionRoot", defaultValue = "false")
  protected boolean runOnExecutionRoot = false;

  /** The output directory for the intermediate report. */
  @Parameter(property = "targetDirectory", defaultValue = "${project.build.directory}")
  private File targetDirectory;

  /** The name of the database being analysed. */
  @Parameter(property = "database", required = true)
  private String database;

  /** The host address of the database being analysed. */
  @Parameter(property = "host")
  private String host;

  /** The port, required by some drivers. */
  @Parameter(property = "port")
  private String port;

  /** The type of database being analysed — defaults to ora. */
  @Parameter(property = "databaseType")
  private String databaseType;

  /** Connect to the database with this user id. */
  @Parameter(property = "user")
  private String user;

  /** Database schema to use — defaults to the specified user. */
  @Parameter(property = "schema")
  private String schema;

  /** Database password to use — defaults to none. */
  @Parameter(property = "password")
  private String password;

  /**
   * If specified, SchemaSpy will look for JDBC drivers on this path, rather than using the
   * application classpath. Useful if your database has a non-O/S driver not bundled with the
   * plugin.
   */
  @Parameter(property = "pathToDrivers")
  private String pathToDrivers;

  /**
   * Schema description. Displays the specified textual description on summary pages. If your
   * description includes an equals sign then escape it with a backslash. NOTE: This field doesn't
   * seem to be used by SchemaSpy in the current version.
   */
  @Parameter(property = "schemaDescription")
  private String schemaDescription;

  /**
   * Only include matching tables/views. This is a regular expression that's used to determine which
   * tables/views to include. For example: -i "(.*book.*)|(library.*)" includes only those
   * tables/views with 'book' in their names or that start with 'library'. You might want to use
   * "description" with this option to describe the subset of tables.
   */
  @Parameter(property = "includeTableNamesRegex")
  private String includeTableNamesRegex;

  /**
   * Exclude matching columns from relationship analysis to simplify the generated graphs. This is a
   * regular expression that's used to determine which columns to exclude. It must match table name,
   * followed by a dot, followed by column name. For example: -x "(book.isbn)|(borrower.address)"
   * Note that each column name regular expression must be surrounded by ()'s and separated from other
   * column names by a |.
   */
  @Parameter(property = "excludeColumnNamesRegex")
  private String excludeColumnNamesRegex;

  /**
   * Allow HTML In Comments. Any HTML embedded in comments normally gets encoded so that it's
   * rendered as text. This option allows it to be rendered as HTML.
   */
  @Parameter(property = "allowHtmlInComments")
  private Boolean allowHtmlInComments;

  /**
   * Comments Initially Displayed. Column comments are normally hidden by default. This option
   * displays them by default.
   *
   * @deprecated this seems to no longer be a supported option in SchemaSpy
   */
  @Deprecated
  @Parameter(property = "commentsInitiallyDisplayed")
  private Boolean commentsInitiallyDisplayed;

  /** Don't include implied foreign key relationships in the generated table details. */
  @Parameter(property = "noImplied")
  private Boolean noImplied;

  /** Only generate files needed for insertion/deletion of data (e.g. for scripts). */
  @Parameter(property = "noHtml")
  private Boolean noHtml;

  /** Detail of execution logging. */
  @Parameter(property = "logLevel")
  private String logLevel;

  /**
   * Some databases, like Derby, will crash if you use the old driver object to establish a
   * connection (eg "connection = driver.connect(...)"). In this case, set useDriverManager to true
   * to use the DriverManager.getConnection() method instead (eg "connection =
   * java.sql.DriverManager.getConnection(...)"). Other databases (e.g. MySQL) seem to only work with
   * the first method, so don't use this parameter unless you have to.
   */
  @Parameter(property = "useDriverManager")
  private Boolean useDriverManager;

  /** The CSS Stylesheet. Allows you to override the default SchemaSpyCSS stylesheet. */
  @Parameter(property = "cssStylesheet")
  private String cssStylesheet;

  /**
   * Single Sign-On. Don't require a user to be specified with -user to simplify configuration when
   * running in a single sign-on environment.
   */
  @Parameter(property = "singleSignOn")
  private Boolean singleSignOn;

  /**
   * Generate lower-quality diagrams. Various installations of Graphviz (depending on OS and/or
   * version) will default to generating either higher or lower quality images. That is, some might
   * not have the "lower quality" libraries and others might not have the "higher quality"
   * libraries.
   */
  @Parameter(property = "lowQuality")
  private Boolean lowQuality;

  /**
   * Generate higher-quality diagrams. Various installations of Graphviz (depending on OS and/or
   * version) will default to generating either higher or lower quality images. That is, some might
   * not have the "lower quality" libraries and others might not have the "higher quality"
   * libraries.
   */
  @Parameter(property = "highQuality")
  private Boolean highQuality;

  /**
   * Evaluate all schemas in a database. Generates a high-level index of the schemas evaluated and
   * allows for traversal of cross-schema foreign key relationships. Use with -schemaSpec
   * "schemaRegularExpression" to narrow-down the schemas to include.
   */
  @Parameter(property = "showAllSchemas")
  private Boolean showAllSchemas;

  /**
   * Evaluate specified schemas. Similar to -showAllSchemas, but explicitly specifies which schema
   * to evaluate without interrogating the database's metadata. Can be used with databases like
   * MySQL where a database isn't composed of multiple schemas.
   */
  @Parameter(property = "schemas")
  private String schemas;

  /** No schema required for this database (e.g. derby). */
  @Parameter(property = "noSchema")
  private Boolean noSchema;

  /** Don't query or display row counts. */
  @Parameter(property = "noRows")
  private Boolean noRows;

  /** Don't query or display row counts. */
  @Parameter(property = "noViews")
  private Boolean noViews;

  /**
   * Specifies additional properties to be used when connecting to the database. Specify the entries
   * directly, escaping the ='s with \= and separating each key\=value pair with a ;.
   *
   * <p>May also be a file name, useful for hiding connection properties from public logs.
   */
  @Parameter(property = "connprops")
  private String connprops;

  /**
   * Don't generate ads in reports.
   *
   * @deprecated will be removed
   */
  @Deprecated
  @Parameter(property = "noAds", defaultValue = "true")
  private Boolean noAds;

  /**
   * Don't generate sourceforge logos in reports.
   *
   * @deprecated will be removed
   */
  @Deprecated
  @Parameter(property = "noLogo", defaultValue = "true")
  private Boolean noLogo;

  @Parameter(property = "catalog", defaultValue = "%")
  private String catalog;

  /**
   * The SchemaSpy analyser that generates the actual report. Can be overridden for testing
   * purposes.
   */
  private MavenSchemaAnalyzer analyzer;

  protected void setSchemaAnalyzer(final MavenSchemaAnalyzer analyzer) {
    this.analyzer = analyzer;
  }

  /**
   * Convenience method used to build the schemaspy command line parameters.
   *
   * @param argList the current list of schemaspy parameter options.
   * @param parameter a new parameter to add
   * @param value the value for this parameter
   */
  private void addToArguments(
      final List<String> argList, final String parameter, final Boolean value) {
    if (value != null && value) {
      argList.add(parameter);
      argList.add(String.valueOf(true));
    }
  }

  /**
   * Convenience method used to build the schemaspy command line parameters.
   *
   * @param argList the current list of schemaspy parameter options.
   * @param parameter a new parameter to add
   * @param value the value for this parameter
   */
  private void addFlagToArguments(
      final List<String> argList, final String parameter, final Boolean value) {
    if (value != null && value) {
      argList.add(parameter);
    }
  }

  /**
   * Convenience method used to build the schemaspy command line parameters.
   *
   * @param argList the current list of schemaspy parameter options.
   * @param parameter a new parameter to add
   * @param value the value for this parameter
   */
  private void addToArguments(
      final List<String> argList, final String parameter, final String value) {
    if (value != null) {
      argList.add(parameter);
      argList.add(value);
    }
  }

  /**
   * Generate the Schemaspy report.
   *
   * @throws MavenReportException if schemaspy crashes
   * @param locale the language of the report — currently ignored.
   */
  @Override
  protected void executeReport(final Locale locale) throws MavenReportException {
    File outputDir;
    if (outputDirectory == null) {
      // targetDirectory should be set by the maven framework. This is just for unit testing
      // purposes
      if (targetDirectory == null) {
        targetDirectory = new File("target");
      }
      File siteDir = new File(targetDirectory, "site");
      outputDir = new File(siteDir, "schemaspy");
    } else {
      outputDir = new File(outputDirectory, "schemaspy");
    }
    outputDir.mkdirs();

    String schemaSpyDirectory = outputDir.getAbsolutePath();
    getLog().debug("SchemaSpy output directory: " + schemaSpyDirectory);

    List<String> argList = new ArrayList<>();

    addToArguments(argList, "-dp", pathToDrivers);
    addToArguments(argList, "-db", database);
    addToArguments(argList, "-host", host);
    addToArguments(argList, "-port", port);
    addToArguments(argList, "-t", databaseType);
    addToArguments(argList, "-u", user);
    addToArguments(argList, "-p", password);
    if (null != schema && schema.contains(",")) {
      addToArguments(argList, "-schemas", schema);
    } else {
      addToArguments(argList, "-s", schema);
    }
    addToArguments(argList, "-o", schemaSpyDirectory);
    addToArguments(argList, "-desc", schemaDescription);
    addToArguments(argList, "-i", includeTableNamesRegex);
    addToArguments(argList, "-x", excludeColumnNamesRegex);
    addFlagToArguments(argList, "-ahic", allowHtmlInComments);
    addFlagToArguments(argList, "-noimplied", noImplied);
    addFlagToArguments(argList, "-nohtml", noHtml);
    addToArguments(argList, "-loglevel", logLevel);
    addFlagToArguments(argList, "-norows", noRows);
    addFlagToArguments(argList, "-noviews", noViews);
    addFlagToArguments(argList, "-noschema", noSchema);
    addFlagToArguments(argList, "-all", showAllSchemas);
    addToArguments(argList, "-schemas", schemas);
    addToArguments(argList, "-useDriverManager", useDriverManager);
    addToArguments(argList, "-css", cssStylesheet);
    addFlagToArguments(argList, "-sso", singleSignOn);
    addFlagToArguments(argList, "-lq", lowQuality);
    addFlagToArguments(argList, "-hq", highQuality);
    addToArguments(argList, "-connprops", connprops);
    addFlagToArguments(argList, "-cid", commentsInitiallyDisplayed);
    addFlagToArguments(argList, "-noads", noAds);
    addFlagToArguments(argList, "-nologo", noLogo);
    addToArguments(argList, "-cat", catalog);
    addFlagToArguments(argList, "-vizjs", vizjs);
    if (getLog().isDebugEnabled()) {
      addFlagToArguments(argList, "-debug", true);
    }
    getLog().debug("SchemaSpy arguments: " + argList);

    try {
      if (analyzer == null) {
        analyzer = new MavenSchemaAnalyzer();
        analyzer.applyConfiguration(argList);
      }
      analyzer.analyze();
    } catch (Exception e) {
      throw new MavenReportException(e.getMessage(), e);
    }
  }

  @Override
  public boolean canGenerateReport() {
    return !runOnExecutionRoot || project.isExecutionRoot();
  }

  @Override
  public String getDescription(final Locale locale) {
    return "SchemaSpy database documentation";
  }

  @Override
  public String getName(final Locale locale) {
    return "SchemaSpy";
  }

    /**
     * @deprecated use {@link #getOutputPath()} instead.
     * @return the output path of the report
     */
  @Override
  @Deprecated (since = "4.0.0")
  public String getOutputName() {
    return "schemaspy/index";
  }

  @Override
  public String getOutputPath() {
    return "schemaspy/index";
  }

  /**
   * Always return {@code true} as we're using the report generated by SchemaSpy rather than
   * creating our own report.
   *
   * @return {@code true}
   */
  @Override
  public boolean isExternalReport() {
    return true;
  }
}
