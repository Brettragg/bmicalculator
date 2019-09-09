package ru.nbki.ali.testprojects.ui;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.nbki.ali.testprojects.businesslogic.BMICalc;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;
import ru.nbki.ali.testprojects.dataaccess.DataLayer;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        JsonObject jsonData = Json.createObjectBuilder()
                .add("SAVED_DATA", getDataJsonArray())
                .build();
        req.setAttribute("JSON_DATA", jsonData);
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
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                JsonObject jsonData = Json.createObjectBuilder()
                        .add("HEIGHT", height)
                        .add("WEIGHT", weight)
                        .add("BMI", bmi)
                        .add("BMI_CATEGORY", bmiCategory)
                        .add("SAVED_DATA", getDataJsonArray())
                        .build();
                /*try (PrintWriter out = resp.getWriter()) {
                    out.print(jsonData);
                    out.flush();
                }*/
                req.setAttribute("JSON_DATA", jsonData);

                dataLayer.addElement(new DataStorageUnit(dataUnit, bmi, bmiCategory));// Save DataStorageUnit in the database
                resp.setStatus(HttpServletResponse.SC_OK);
                req.getRequestDispatcher("index.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendRedirect("error.jsp");
            }
        }
    }
    private JsonArray getDataJsonArray() {
        final Iterator<DataStorageUnit> iterator = dataLayer.getDataIterator();
        JsonArrayBuilder arrayBuilder  = Json.createArrayBuilder();
        while (iterator.hasNext()) {
            DataStorageUnit item =  new DataStorageUnit(iterator.next());
            JsonObject bmiRecord = Json.createObjectBuilder()
                .add("HEIGHT", item.getHeight())
                .add("WEIGHT", item.getWeight())
                .add("BMI", item.getBmi())
                .add("BMI_CATEGORY", item.getCategory())
                .build();
            arrayBuilder.add(bmiRecord);
        }
        return arrayBuilder.build();
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
