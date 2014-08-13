/*
 * 
 */
package Charts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.*;
import org.jfree.data.time.Second;



// TODO: Auto-generated Javadoc
/**
 * The Class ChartGeneration.
 */
public class ChartGeneration extends ApplicationFrame
{
		
		/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
		
		/** The Seconds. */
		int[] Seconds;
		
		/** The Minutes. */
		int[] Minutes;
		
		/** The Hours. */
		int[] Hours;
		
		/** The Days. */
		int[] Days;
		
		/** The Months. */
		int[] Months;
		
		/** The Years. */
		int[] Years;
		
		/** The RSS mem arr. */
		Double[] RSSMemArr;
		
		/** The VSZ mem arr. */
		Double[] VSZMemArr;
		
		/** The cpu. */
		Double[] CPU;
		
		/** The os. */
		String OS;
		
		/** The Image name. */
		String ImageName;
		
		
		
		
        /**
		 * Gets the image name.
		 * 
		 * @return the image name
		 */
        public String getImageName() {
			return ImageName;
		}

		/**
		 * Instantiates a new chart generation.
		 * 
		 * @param name
		 *            the name
		 * @param _Seconds
		 *            the _ seconds
		 * @param _Minutes
		 *            the _ minutes
		 * @param _Hours
		 *            the _ hours
		 * @param _Days
		 *            the _ days
		 * @param _Months
		 *            the _ months
		 * @param _Years
		 *            the _ years
		 * @param _CPU
		 *            the cpu
		 * @param chartName
		 *            the chart name
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		public ChartGeneration(String name, int _Seconds[], int[] _Minutes, int[] _Hours, int[] _Days, int[] _Months, int[] _Years, Double[] _CPU, String chartName) throws IOException
        {
                super(name);
                Seconds = _Seconds;
                Minutes = _Minutes;
                Hours= _Hours;
                Days = _Days;
                Months = _Months;
                Years = _Years;
                CPU = _CPU;
                XYDataset xydataset = createDatasetCPU();
                JFreeChart jfreechart = createChart(xydataset, "CPU");
                ChartPanel chartpanel = new ChartPanel(jfreechart, false);
                chartpanel.setPreferredSize(new Dimension(1000, 1000));
                chartpanel.setMouseZoomable(true, false);
                setContentPane(chartpanel);
                
                createImageFromChart(jfreechart, chartName + "CPU");
        }

		/**
		 * Creates the image from chart.
		 * 
		 * @param jfreechart
		 *            the jfreechart
		 * @param imageName
		 *            the image name
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
		private void createImageFromChart(JFreeChart jfreechart, String imageName)
				throws IOException 
		{
			Image image = jfreechart.createBufferedImage(500, 500);
			BufferedImage bi = (BufferedImage) image;
			File f = new File(imageName + ".png");
			ImageIO.write(bi, "png", f);
			ImageName = imageName + ".png";
		}

		
        /**
		 * Instantiates a new chart generation.
		 * 
		 * @param name
		 *            the name
		 * @param _Seconds
		 *            the _ seconds
		 * @param _Minutes
		 *            the _ minutes
		 * @param _Hours
		 *            the _ hours
		 * @param _Days
		 *            the _ days
		 * @param _Months
		 *            the _ months
		 * @param _Years
		 *            the _ years
		 * @param _VSZ
		 *            the vsz
		 * @param _RSS
		 *            the rss
		 * @param chartName
		 *            the chart name
		 * @throws IOException
		 *             Signals that an I/O exception has occurred.
		 */
        public ChartGeneration(String name, int _Seconds[], int[] _Minutes, int[] _Hours, int[] _Days, int[] _Months, int[] _Years, Double[] _VSZ, Double[] _RSS, String chartName) throws IOException
        {
                super(name);
                Seconds = _Seconds;
                Minutes = _Minutes;
                Hours= _Hours;
                Days = _Days;
                Months = _Months;
                Years = _Years;
                RSSMemArr = _RSS;
                VSZMemArr = _VSZ;
                XYDataset xydataset = createDatasetMem();
                JFreeChart jfreechart = createChart(xydataset, "Memory");
                ChartPanel chartpanel = new ChartPanel(jfreechart, false);
                chartpanel.setPreferredSize(new Dimension(1000, 1000));
                chartpanel.setMouseZoomable(true, false);

                setContentPane(chartpanel);
                
                createImageFromChart(jfreechart, chartName + "MEMORY");

                
        }
        

        /**
		 * Creates the chart.
		 * 
		 * @param xydataset
		 *            the xydataset
		 * @param CPU_MEM
		 *            the cpu mem
		 * @return the j free chart
		 */
        private JFreeChart createChart(XYDataset xydataset, String CPU_MEM)
        {
                JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Monitoring Results", "DateTime", CPU_MEM, xydataset, true, true, false);
                jfreechart.setBackgroundPaint(Color.white);
                XYPlot xyplot = (XYPlot)jfreechart.getPlot();
                xyplot.setBackgroundPaint(Color.lightGray);
                xyplot.setDomainGridlinePaint(Color.white);
                xyplot.setRangeGridlinePaint(Color.white);
                xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
                xyplot.setDomainCrosshairVisible(true);
                xyplot.setRangeCrosshairVisible(true);
                org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
                if (xyitemrenderer instanceof XYLineAndShapeRenderer)
                {
                        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
                        xylineandshaperenderer.setBaseShapesVisible(true);
                        xylineandshaperenderer.setBaseShapesFilled(true);
                        xylineandshaperenderer.setBaseItemLabelsVisible(true);
                }
                PeriodAxis periodaxis = new PeriodAxis("Date");
                periodaxis.setTimeZone(TimeZone.getTimeZone("GMT"));
                periodaxis.setAutoRangeTimePeriodClass(org.jfree.data.time.Second.class);
                periodaxis.setMajorTickTimePeriodClass(org.jfree.data.time.Second.class);
                PeriodAxisLabelInfo aperiodaxislabelinfo[] = new PeriodAxisLabelInfo[2];
                aperiodaxislabelinfo[0] = new PeriodAxisLabelInfo(org.jfree.data.time.Second.class, new SimpleDateFormat("HH:mm:ss"), new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1, 14), Color.blue, false, new BasicStroke(0.0F), Color.lightGray);
                aperiodaxislabelinfo[1] = new PeriodAxisLabelInfo(org.jfree.data.time.Day.class, new SimpleDateFormat("dd-MM-yyyy"),new RectangleInsets(2D, 2D, 2D, 2D), new Font("SansSerif", 1, 14), Color.blue, false, new BasicStroke(0.0F), Color.lightGray);
                periodaxis.setLabelInfo(aperiodaxislabelinfo);
                xyplot.setDomainAxis(periodaxis);
                return jfreechart;
        }
        
        // TODO: To pass the array size of the file - number of lines in the file

        /**
		 * Creates the dataset mem.
		 * 
		 * @return the XY dataset
		 */
        private XYDataset createDatasetMem()
        {
        	TimeSeriesCollection timeseriescollection;
        		if (RSSMemArr == null)
        		{
                    TimeSeries timeseries = new TimeSeries("RSS");
    				for (int i = 0 ; i < Seconds.length ; i ++)
    				{
    					// TODO: Replace "180+i" with VSZ & "129 +i+2" with RSS 
    					// TODO: To separate between HP-UX & other OS..
    					timeseries.add(new Second(Seconds[i],Minutes[i],Hours[i],Days[i],Months[i], Years[i]), VSZMemArr[i]);
    				}
    		
                    timeseriescollection = new TimeSeriesCollection();
                    timeseriescollection.addSeries(timeseries);
                   // timeseriescollection.addSeries(timeseries1);
                    timeseriescollection.setXPosition(TimePeriodAnchor.MIDDLE);
        		}
        		else 
        		{
	                TimeSeries timeseries = new TimeSeries("RSS");
	                TimeSeries timeseries1 = new TimeSeries("VSZ");
					for (int i = 0 ; i < Seconds.length || i < RSSMemArr.length; i ++)
					{
						// TODO: Replace "180+i" with VSZ & "129 +i+2" with RSS 
						// TODO: To separate between HP-UX & other OS..
						timeseries.add(new Second(Seconds[i],Minutes[i],Hours[i],Days[i],Months[i], Years[i]), RSSMemArr[i]);
						timeseries1.add(new Second(Seconds[i],Minutes[i],Hours[i],Days[i],Months[i], Years[i]),VSZMemArr[i]);
					}
			
	                timeseriescollection = new TimeSeriesCollection();
	                timeseriescollection.addSeries(timeseries);
	                timeseriescollection.addSeries(timeseries1);
	                timeseriescollection.setXPosition(TimePeriodAnchor.MIDDLE);
        		}
                return timeseriescollection;	
        }

        /**
		 * Creates the dataset cpu.
		 * 
		 * @return the XY dataset
		 */
        private XYDataset createDatasetCPU()
        {
                TimeSeries timeseries = new TimeSeries("%CPU");
               // TimeSeries timeseries1 = new TimeSeries("RSS & VSZ");
				for (int i = 0 ; i < Seconds.length ; i ++)
				{
					// TODO: Replace "180+i" with VSZ & "129 +i+2" with RSS 
					// TODO: To separate between HP-UX & other OS..
					timeseries.add(new Second(Seconds[i],Minutes[i],Hours[i],Days[i],Months[i], Years[i]), CPU[i]);
					//timeseries1.add(new Second(Seconds[i],Minutes[i],Hours[i],Days[i],Months[i], Years[i]),129 +i+2);
				}
		
                TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
                timeseriescollection.addSeries(timeseries);
               // timeseriescollection.addSeries(timeseries1);
                timeseriescollection.setXPosition(TimePeriodAnchor.MIDDLE);
                return timeseriescollection;	
        }
/*        public JPanel createDemoPanel()
        {
        	if (CPU == null)
        	{
                JFreeChart jfreechart = createChart(createDatasetCPU(),"CPU");
                return new ChartPanel(jfreechart);
        	}
        	else
        	{
        		JFreeChart jfreechart = createChart(createDatasetMem(), "Memory");
                return new ChartPanel(jfreechart);
        	}
        }*/

/*        public static void main(String args[])
        {
        		ChartGeneration periodaxisdemo1 = new ChartGeneration("Period Axis Demo");
                periodaxisdemo1.pack();
                RefineryUtilities.centerFrameOnScreen(periodaxisdemo1);
                periodaxisdemo1.setVisible(true);
        }*/
}

