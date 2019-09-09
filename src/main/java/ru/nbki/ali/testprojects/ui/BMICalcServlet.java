package ru.nbki.ali.testprojects.ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.nbki.ali.testprojects.businesslogic.BMICalc;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;
import ru.nbki.ali.testprojects.dataaccess.DataLayer;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Servlet which serves as a mediator between
 * business logic layer and an index.jsp html page
 *
 * @author Arseniy Lee
 * @version 1.0
 */
@Singleton
public class BMICalcServlet extends HttpServlet {

    private final DataLayer dataLayer;

    private final BMICalc bmiCalc;

    @Inject
    public BMICalcServlet(DataLayer dataLayer, BMICalc bmiCalc) {
        this.dataLayer = dataLayer;
        this.bmiCalc = bmiCalc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("SAVED_DATA", getDataList());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String heightStr = req.getParameter("height");
        String weightStr = req.getParameter("weight");
        if (heightStr == null || weightStr == null) {
            resp.sendRedirect("error.jsp");
        } else {
            try {
                final float height = Float.parseFloat(heightStr);
                final float weight = Float.parseFloat(weightStr);
                final DataInputUnit dataUnit = new DataInputUnit(weight, height);

                final float bmi = bmiCalc.getBMI(dataUnit);    // Calculate BMI
                final String bmiCategory = bmiCalc.getCategory(bmi); // Calculate BMI category


                req.setAttribute("BMI", bmi);
                req.setAttribute("BMI_CATEGORY", bmiCategory);
                req.setAttribute("SAVED_DATA", getDataList());

                dataLayer.addElement(new DataStorageUnit(dataUnit, bmi, bmiCategory));// Save DataStorageUnit in the database
                resp.setStatus(HttpServletResponse.SC_OK);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendRedirect("error.jsp");
            }
        }
    }

    private List<DataStorageUnit> getDataList () {
        final Iterator<DataStorageUnit> iterator = dataLayer.getDataIterator(); // Set up an iterator
        final ArrayList<DataStorageUnit> data2DArray = new ArrayList<>();    // Represent saved data as an ArrayList of DataStorageUnit
        while (iterator.hasNext()) {
            DataStorageUnit item = new DataStorageUnit(iterator.next());
            data2DArray.add(item);
        }
        return data2DArray;
    }

}
