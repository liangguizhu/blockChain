package com.servlet;

import com.dao.BlockChain;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FullChain extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BlockChain blockChain = BlockChain.getInstance();
        Map<String, Object> response = new HashMap<String,Object>();
        response.put("chain", blockChain.getChain());
        response.put("length", blockChain.getChain().size());
        JSONObject jsonResponse = new JSONObject(response);
        resp.setContentType("application/json");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(jsonResponse);
        printWriter.close();
    }
}
