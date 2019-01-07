package bambooforest.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bambooforest.action.Action;



/**
 * Servlet implementation class APIController
 */
@WebServlet(
		urlPatterns = { 
				"/APIController", 
				"/api/*"
		}, 
		initParams = { 
				@WebInitParam(name = "property", value = "api.properties")
		})
public class APIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String,Object> commandMap = new HashMap<>();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public APIController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		String props = config.getInitParameter("property");
		String realFolder = "/property"; //properties������ ����� ����
		//�����ø����̼� ��Ʈ ���
		ServletContext context = config.getServletContext();
		//realFolder�� �����ø����̼� �ý��ۻ��� �����η� ����
		String realPath = context.getRealPath(realFolder) +"\\"+props;
							    
		//��ɾ�� ó��Ŭ������ ���������� ������ Properties��ü ����
		Properties pr = new Properties();
		FileInputStream f = null;
		try{
			//command.properties������ ������ �о��
			f = new FileInputStream(realPath); 
			//command.properties�� ������ Properties��ü pr�� ����
			pr.load(f);
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (f != null) try { f.close(); } catch(IOException ex) {}
		}
		//Set��ü�� iterator()�޼ҵ带 ����� Iterator��ü�� ��
		Iterator<?> keyIter = pr.keySet().iterator();
		//Iterator��ü�� ����� ��ɾ�� ó��Ŭ������ commandMap�� ����
		while( keyIter.hasNext() ) {
			String command = (String)keyIter.next();
			String className = pr.getProperty(command);
			try{
				Class<?> commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();
				commandMap.put(command, commandInstance);
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}catch (InstantiationException e) {
				e.printStackTrace();
			}catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = null;
		Action action=null;
		try {
			String command = request.getRequestURI();
			if(command.indexOf(request.getContextPath()) == 0) 
				command = command.substring(request.getContextPath().length());
			action = (Action)commandMap.get(command);  
			if(action == null)
				action = (Action)commandMap.get("/api/hello");
			result = action.process(request, response);
			System.out.println("�ѿ��� jsonString : " + result);
		}catch(Throwable e) {
			e.printStackTrace();
		}
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().println(result);
	}

}
