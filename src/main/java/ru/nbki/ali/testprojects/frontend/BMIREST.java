package ru.nbki.ali.testprojects.frontend;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.nbki.ali.testprojects.Helper;
import ru.nbki.ali.testprojects.businesslogic.IBMICalc;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;
import ru.nbki.ali.testprojects.dataaccess.IDataLayer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * RESTful web service that either requests empty Json body
 * and responds with a Json Array of previous BMI calculations
 * or requests height and weight and responds with resulting BMI,
 * BMI category and list of previous calculations.
 * @author Arseniy Lee
 * @version 1.5
 */
@Singleton
public class BMIREST extends HttpServlet {
    private final IDataLayer dataLayer;
    private final IBMICalc bmiCalc;

    @Inject

    public BMIREST(IDataLayer dataLayer, IBMICalc bmiCalc) {
        this.dataLayer = dataLayer;
        this.bmiCalc = bmiCalc;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        BufferedReader reader = req.getReader();
        DataInputUnit dataInputUnit = gson.fromJson(reader, DataInputUnit.class);
        ServletOutputStream out = resp.getOutputStream();

        if (dataInputUnit != null) { // Just send list if request body is empty
            float bmi = bmiCalc.getBMI(dataInputUnit);
            String bmiCategory = bmiCalc.getCategory(bmi);
            DataStorageUnit respData = new DataStorageUnit(dataInputUnit, bmi, bmiCategory);
            dataLayer.addElement(new DataStorageUnit(respData, bmi, bmiCategory));// Save DataStorageUnit in the database
            resp.setStatus(HttpServletResponse.SC_OK);

        }
        out.println(gson.toJson(getDataList()));
        out.flush();
    }

    private List<DataStorageUnit> getDataList() {
        return Helper.getList(dataLayer.getDataIterator());
    }

}
