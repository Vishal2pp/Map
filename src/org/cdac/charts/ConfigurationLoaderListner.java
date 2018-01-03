package org.cdac.charts;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ConfigurationLoaderListner implements ServletContextListener, ServletContextAttributeListener {
	public ConfigurationLoaderListner() {

	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext context = servletContextEvent.getServletContext();
		String fileName = context.getInitParameter("PROPERTIES_DIRECTORY");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(fileName);

		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		AMChartsConfigurator amc = new AMChartsConfigurator();
		amc.setUploadDirectory(prop.getProperty("UPLOAD_DIRECTORY"));
		amc.setStaticDataDirectory(prop.getProperty("STATIC_DATA_DIRECTORY"));
		amc.setDatePattern(prop.getProperty("DATE_PATTERN"));
		amc.setDateCell(Integer.parseInt(prop.getProperty("DATE_CELL")));
		amc.setDateRow(Integer.parseInt(prop.getProperty("DATE_ROW")));
		amc.setTotalValueRow(Integer.parseInt(prop.getProperty("TOTAL_VALUES_ROW")));
		amc.setCbseSchoolIndex(Integer.parseInt(prop.getProperty("CBSE_SCHOOLS_INDEX")));
		amc.setCbseSchoolTrainedIndex(Integer.parseInt(prop.getProperty("CBSE_SCHOOLS_TRAINED_INDEX")));
		amc.setTeacherTrainedIndex(Integer.parseInt(prop.getProperty("TAECHER_TRAINED_INDEX")));
		amc.setStatesNameIndex(Integer.parseInt(prop.getProperty("STATES_NAME_INDEX")));
		amc.setStartRowOfStates(Integer.parseInt(prop.getProperty("START_ROW_OF_STATES")));
		amc.setLastRowOfStates(Integer.parseInt(prop.getProperty("LAST_ROW_OF_STATES")));
		amc.setYourMaxMemorySize(Integer.parseInt(prop.getProperty("yourMaxMemorySize")));
		amc.setFirstRowDataValidator(prop.getProperty("FIRST_ROW_DATA_VALIDATOR"));
		amc.setUsername(prop.getProperty("USER_NAME"));
		amc.setPassword(prop.getProperty("PASSWORD"));

		amc.setStateSchoolIndex(Integer.parseInt(prop.getProperty("STATE_SCHOOLS_INDEX")));
		amc.setStateSchoolTrainedIndex(Integer.parseInt(prop.getProperty("STATE_SCHOOLS_TRAINED_INDEX")));
		amc.setStateTeacherTrainedIndex(Integer.parseInt(prop.getProperty("STATE_TEACHER_TRAINED_INDEX")));
		
		prop.clear();

		context.setAttribute("configuration", amc);

	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

}
