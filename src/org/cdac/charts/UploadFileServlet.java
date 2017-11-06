package org.cdac.charts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

@WebServlet(urlPatterns = "/UploadFile")
public class UploadFileServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String UPLOAD_DIRECTORY;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		AMChartsConfigurator amc = (AMChartsConfigurator) context.getAttribute("configuration");
		UPLOAD_DIRECTORY = amc.getUploadDirectory();
		uploadAndParse(request, response);
	}

	private void uploadAndParse(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		ServletContext context = getServletContext();
		AMChartsConfigurator amc = (AMChartsConfigurator) context.getAttribute("configuration");
		try {
			writeMultipartFileToDisk(session);
			String name = (String) session.getAttribute("filename");
			XLSXtoJSONConverter converter = new XLSXtoJSONConverter(amc);
			boolean isValidExcelSheet = converter.readAndCheck(UPLOAD_DIRECTORY + File.separator + name);
			if (isValidExcelSheet) {
				converter.convert();
				session.setAttribute("message",
						"Excel File uploaded Successfully.Please click show map to see the Map.");
				session.setAttribute("status", "successful");
				response.sendRedirect("FileUpload.jsp");
			} else {
				session.setAttribute("message", "Sorry, Excel File is invalid.");
				session.setAttribute("status", "unsuccessful");
				response.sendRedirect("FileUpload.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", "Sorry, Excel File is not in a proper Format.");
			session.setAttribute("status", "unsuccessful");
			response.sendRedirect("FileUpload.jsp");
		}
	}

	public void writeMultipartFileToDisk(HttpSession session) throws FileUploadException, Exception {
		List<FileItem> multiparts = (List<FileItem>) session.getAttribute("multiparts");
		String name = (String) session.getAttribute("filename");
		File file = new File(UPLOAD_DIRECTORY + File.separator + name);
		for (FileItem item : multiparts) {
			if (!item.isFormField()) {
				item.write(file);
			}
		}
	}

}
