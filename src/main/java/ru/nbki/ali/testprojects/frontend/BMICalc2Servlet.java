package ru.nbki.ali.testprojects.frontend;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@link HttpServlet} that serves JavaScript code compiled from Elm source.
 * @author Arseniy Lee
 * @version 1.5
 */
@Singleton
public class BMICalc2Servlet extends HttpServlet {
    /**
     * Passes base url of the server it is running on to the BMICalc2.jsp.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("BASE_URL", getBaseUrl(req));
        req.getRequestDispatcher("BMICalc2.jsp").forward(req, resp);
    }

    /**
     *
     * @param request Input request
     * @return Provides base url based on the input request.
     */
    private static String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();
        return scheme + serverName + serverPort + contextPath;
    }
}
