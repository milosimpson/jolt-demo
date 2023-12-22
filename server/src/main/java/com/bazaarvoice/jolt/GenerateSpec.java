package com.bazaarvoice.jolt;

import com.bazaarvoice.jolt.helper.Data;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@WebServlet("/generate")
public class GenerateSpec extends HttpServlet {

    public static final String UNDERSCORE = "_";

    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("===========/generate POST Request ===========");
        System.out.println("input-> " + req.getParameter("input"));

        String inputPayload;
        try {
            inputPayload = req.getParameter("input");
        } catch (Exception e) {
            response.getWriter().println("Could not url-decode the inputs.\n");
            return;
        }
        Object input;
        Writer writer = new StringWriter();
        Configuration configuration = new Configuration();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            input = JsonUtils.jsonToObject(inputPayload);
            if (input instanceof LinkedHashMap) {
                linkedHashMap = (LinkedHashMap) input;
            } else if (input instanceof ArrayList){
                linkedHashMap = (LinkedHashMap) ((ArrayList)input).get(0);
            }
            Object firstObjectKey = linkedHashMap.keySet().toArray()[0];
            String sample = firstObjectKey.toString().split(UNDERSCORE)[0];
            Data data = new Data(sample);
            configuration.setDirectoryForTemplateLoading(new File(GenerateSpec.class.getResource("/").toURI()));
            Template template = configuration.getTemplate("spec_template.ftl");

            template.process(data, writer);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Could not parse the 'input' JSON.\n");
            return;
        }

        String result = writer.toString();
        System.out.println("Java GenerateSpec result=> "+ result);

        // Add PR details in the response
        response.getWriter().println(result);
    }

}
