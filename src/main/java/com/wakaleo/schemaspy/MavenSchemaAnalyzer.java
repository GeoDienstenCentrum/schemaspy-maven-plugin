package com.wakaleo.schemaspy;

import org.schemaspy.LayoutFolder;
import org.schemaspy.SchemaAnalyzer;
import org.schemaspy.cli.CommandLineArgumentParser;
import org.schemaspy.cli.CommandLineArguments;
import org.schemaspy.connection.ScNullChecked;
import org.schemaspy.connection.ScSimple;
import org.schemaspy.connection.SqlConnection;
import org.schemaspy.input.dbms.ConnectionConfig;
import org.schemaspy.input.dbms.ConnectionURLBuilder;
import org.schemaspy.input.dbms.DriverFromConfig;
import org.schemaspy.input.dbms.service.DatabaseServiceFactory;
import org.schemaspy.input.dbms.service.SqlService;
import org.schemaspy.output.OutputProducer;
import org.schemaspy.output.xml.dom.XmlProducerUsingDOM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Wrapper around the {@link SchemaAnalyzer} to hide the initialization details from the {@link SchemaSpyReport}
 * maven mojo.
 */
public class MavenSchemaAnalyzer {
    private SchemaAnalyzer analyzer;

    private CommandLineArguments cliArgs;

    /**
     * Adds the schemaspy plugin configuration properties. This is necessary before calling {@link #analyze()}.
     * @param argList a list of property-value pairs.
     */
    public void applyConfiguration(List<String> argList) {
        CommandLineArgumentParser parser = new CommandLineArgumentParser(argList.toArray(new String[0]));
        cliArgs = parser.commandLineArguments();
    }

    /**
     * Executes the schemaspy analyzer process.
     */
    public void analyze() throws SQLException, IOException {
        cliArgs = Objects.requireNonNull(cliArgs, "The field 'commandLineArguments' need to reference an instance. Call 'applyConfiguration(...) to initiate command line arguments");
        ConnectionConfig connectionConfig = cliArgs.getConnectionConfig();
        SqlConnection connection = new ScSimple(connectionConfig, new ConnectionURLBuilder(connectionConfig), new DriverFromConfig(connectionConfig));
        SqlService sqlService = new SqlService();
        DatabaseServiceFactory databaseServiceFactory = new DatabaseServiceFactory(sqlService);
        OutputProducer outputProducer = new XmlProducerUsingDOM();
        LayoutFolder layoutFolder = new LayoutFolder(SchemaAnalyzer.class.getClassLoader());
        analyzer = new SchemaAnalyzer(sqlService, databaseServiceFactory, cliArgs, outputProducer, layoutFolder, connection);
        analyzer.analyze();
    }
}
