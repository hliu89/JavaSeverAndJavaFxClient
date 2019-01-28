package com.hongqiao.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.hongqiao.dto.Text;
import com.hongqiao.service.TextService;
import com.hongqiao.service.TextServiceImpl;
import com.hongqiao.util.JsonUtil;

/**
 * Servlet implementation class TextServlet
 */
@WebServlet("/TextServlet")
public class TextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TextService textService=new TextServiceImpl();
    /**
     * Default constructor. 
     */
    public TextServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			// get method by method name
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			//get method access
			method.setAccessible(true);
			//invoke this method
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//search by key word
	protected void SearchByKeyWord(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyWord = request.getParameter("KeyWord");
		
	        List<Text> texts=textService.selectByKeyWord(keyWord);
	        PrintWriter writer = response.getWriter();  
	        try {  
	            writer.write(JsonUtil.TextListToJason(texts));  
	        } catch (JSONException e) {  
	            e.printStackTrace();  
	        }  
	        writer.flush();  
	        writer.close();  
	}
	
	//get text detail
	protected void GetDetailData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Text text=textService.selectById(id);
		if(text!=null) {
			request.setAttribute("text", text);
		}else {
			request.setAttribute("detailMessgae", "Operation error");
		}
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);
	}
	
	//get all text
	protected void GetAllText(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Text> texts = textService.selectAll();
		String alltexts = null;
		if (texts != null) {
			alltexts = JsonUtil.TextListToJason(texts);

		}
		PrintWriter writer = response.getWriter();
		try {
			writer.write(alltexts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
	}
	
	//update text
	protected void UpdateText(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String textJson = request.getParameter("updateText");
		List<Text> texts=JsonUtil.JsonToTextList(textJson);
		PrintWriter writer = response.getWriter();
		for(Text text: texts) {
			if(!textService.updateText(text)) {
				JSONObject error = new JSONObject();
				error.append("error", "update fail");
				try {
					writer.write(error.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				writer.flush();
				writer.close();
				return;
			}
		}
		texts = textService.selectAll();
		String alltexts = null;
		if (texts != null) {
			alltexts = JsonUtil.TextListToJason(texts);
		}
		
		try {
			writer.write(alltexts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
	}
	
	protected void DeleteText(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		PrintWriter writer = response.getWriter();
		if(!textService.deleteText(id)) {
			JSONObject error = new JSONObject();
			error.append("error", "delete fail");
			try {
				writer.write(error.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			writer.flush();
			writer.close();
			return;
		}
		
		List<Text> texts = textService.selectAll();
		String alltexts = null;
		if (texts != null) {
			alltexts = JsonUtil.TextListToJason(texts);
		}
		
		try {
			writer.write(alltexts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
	}
	



}
