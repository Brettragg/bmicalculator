package ru.nbki.ali.testprojects.frontend;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.nbki.ali.testprojects.Helper;
import ru.nbki.ali.testprojects.businesslogic.IBMICalc;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;
import ru.nbki.ali.testprojects.dataaccess.IDataLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A Servlet which serves as a mediator between
 * business logic layer and an BMICalc1.jsp html page
 *
 * @author Arseniy Lee
 * @version 1.0
 */
@Singleton
public class BMICalcServlet extends HttpServlet {

    private final IDataLayer dataLayer;

    private final IBMICalc bmiCalc;

    @Inject
    public BMICalcServlet(IDataLayer dataLayer, IBMICalc bmiCalc) {
        this.dataLayer = dataLayer;
        this.bmiCalc = bmiCalc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("SAVED_DATA", getDataList());
        req.getRequestDispatcher("BMICalc1.jsp").forward(req, resp);
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
                req.getRequestDispatcher("BMICalc1.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendRedirect("error.jsp");
            }
        }
    }

    private List<DataStorageUnit> getDataList() {
        return Helper.getList(dataLayer.getDataIterator());
    }

}
