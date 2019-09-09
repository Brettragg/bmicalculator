package ru.nbki.ali.testprojects.ui;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import ru.nbki.ali.testprojects.Helper;
import ru.nbki.ali.testprojects.businesslogic.IBMICalc;
import ru.nbki.ali.testprojects.dataaccess.DataInputUnit;
import ru.nbki.ali.testprojects.dataaccess.DataStorageUnit;
import ru.nbki.ali.testprojects.dataaccess.IDataLayer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Arseniy Lee
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        ServletOutputStream out = resp.getOutputStream();
        resp.setStatus(HttpServletResponse.SC_OK);
        out.println(gson.toJson(getDataList()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        gson.toJson(req);

        BufferedReader reader = req.getReader();
        resp.setStatus(HttpServletResponse.SC_OK);
        DataInputUnit dataInputUnit = gson.fromJson(reader, DataInputUnit.class);
        float bmi = bmiCalc.getBMI(dataInputUnit);
        String bmiCategory = bmiCalc.getCategory(bmi);
        DataStorageUnit respData = new DataStorageUnit(dataInputUnit, bmi, bmiCategory);

        dataLayer.addElement(new DataStorageUnit(respData, bmi, bmiCategory));// Save DataStorageUnit in the database
        resp.setStatus(HttpServletResponse.SC_OK);
        ServletOutputStream out = resp.getOutputStream();
        out.println(gson.toJson(respData));
    }

    private List<DataStorageUnit> getDataList() {
        return Helper.getList(dataLayer.getDataIterator());
    }

}
