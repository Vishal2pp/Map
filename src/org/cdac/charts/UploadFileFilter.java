package org.cdac.charts;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class UploadFileFilter implements Filter {

	private int yourMaxMemorySize;

	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		AMChartsConfigurator amc = (AMChartsConfigurator) context.getAttribute("configuration");
		yourMaxMemorySize = amc.getYourMaxMemorySize();
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> multiparts = null;
		try {
			multiparts = upload.parseRequest(request);
			if (multiparts.get(0).getSize() != 0) {
				if (multiparts.get(0).getSize() <= yourMaxMemorySize) {
					String name = multiparts.get(0).getName();
					String ext = getExtension(name);
					if (ext.equals("xlsx")) {
						session.setAttribute("filename", name);
						session.setAttribute("multiparts", multiparts);
						chain.doFilter(request, response);
					} else {

						session.setAttribute("message", "Sorry,File is invalid, allowed extension is .xlsx");
						session.setAttribute("status", "unsuccessful");
						response.sendRedirect("FileUpload.jsp");
					}
				} else {
					session.setAttribute("message", "Sorry, File is too big");
					session.setAttribute("status", "unsuccessful");
					response.sendRedirect("FileUpload.jsp");
				}

			} else {
				session.setAttribute("message", "Please choose a valid file.");
				session.setAttribute("status", "unsuccessful");
				response.sendRedirect("FileUpload.jsp");
			}

		} catch (Exception e) {
		}
	}

	public void destroy() {
	}

	private String getExtension(String name) {
		String ext = FilenameUtils.getExtension(name);
		return ext;
	}
}
