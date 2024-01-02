package net.chocomint.freeway_handler;

import net.chocomint.freeway_handler.utils.CoordinateWithSectionId;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class LineChartExample extends JFrame {

	public LineChartExample(String title) throws ParserConfigurationException, IOException, SAXException {
		super(title);

		List<CoordinateWithSectionId> list = FreewayHandler.COORDINATE_LIST;

		// Create a dataset
		XYSeries series = new XYSeries("");
		list.forEach(coordinate -> series.add(coordinate.coordinate().longitude(), coordinate.coordinate().latitude()));

		XYSeriesCollection dataset = new XYSeriesCollection(series);

		// Create the chart
		JFreeChart chart = ChartFactory.createScatterPlot(
				"Simple Line Chart",
				"X-Axis",
				"Y-Axis",
				dataset
		);

		// Customize the chart if needed (e.g., change colors, add legend, etc.)
		NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
		yAxis.setRange(22.4, 25.5); // Set the desired Y-axis range

		// Create a panel to display the chart
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(560, 370));
		setContentPane(chartPanel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LineChartExample example = null;
			try {
				example = new LineChartExample("Line Chart Example");
			} catch (ParserConfigurationException | SAXException | IOException ignored) {
			}
			assert example != null;
			example.setSize(500, 700);
			example.setLocationRelativeTo(null);
			example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			example.setVisible(true);
		});
	}
}
