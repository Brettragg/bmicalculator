package ru.nbki.ali.testprojects.configurations;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import ru.nbki.ali.testprojects.businesslogic.IBMICalc;
import ru.nbki.ali.testprojects.businesslogic.WHO_BMICalc;
import ru.nbki.ali.testprojects.dataaccess.DBDataLayer;
import ru.nbki.ali.testprojects.dataaccess.IDataLayer;
import ru.nbki.ali.testprojects.dbc.IDBConnector;
import ru.nbki.ali.testprojects.dbc.SimpleConnector;
import ru.nbki.ali.testprojects.ui.BMICalcServlet;
import ru.nbki.ali.testprojects.ui.BMIREST;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import static com.google.inject.jndi.JndiIntegration.fromJndi;

/**
 *
 * @author Arseniy Lee
 * @version 1.1
 */
public class BMICalcServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("").with(BMICalcServlet.class);
                serve("/BMICalc").with(BMICalcServlet.class);
                serve("/BMIREST").with(BMIREST.class);
                bind(IBMICalc.class).to(WHO_BMICalc.class);
                bind(IDataLayer.class).to(DBDataLayer.class);
                bind(IDBConnector.class).to (SimpleConnector.class);
                bind(Context.class).to(InitialContext.class);
                bind(DataSource.class).toProvider(fromJndi(DataSource.class, "java:/comp/env/jdbc/db"));
            }

        });
    }

}
