package fak.graphicTool;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class MainWindow {
	
	private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "bmp", "gif"};

	private JFrame frame;
	private JLabel lbImageViewer;
	private JLabel lbHistogramViewer;
	private JLabel lbMeanUpperHalf;
	private JLabel lbMedianLowerHalf;
	private JLabel lbVariance;
	private JLabel lbMode;

	private Picture picture;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();

		this.picture = new Picture("res/lena.png");
		this.refreshPictureInfo();

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 664, 21);
		frame.getContentPane().add(menuBar);

		JMenu mnFile = new JMenu(Messages.getString("MainWindow.mnFile.text"));
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem(Messages.getString("MainWindow.mntmOpen.text"));
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openPictureDialog();
			}
		});

		mnFile.add(mntmOpen);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem(Messages.getString("MainWindow.mntmExit.text"));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu(Messages.getString("MainWindow.mnHelp.text"));
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem(Messages.getString("MainWindow.mntmAbout.text"));
		mnHelp.add(mntmAbout);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(Messages.getString("MainWindow.frame.title"));
		frame.setResizable(false);
		frame.setBounds(100, 100, 648, 473);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panelButtons = new JPanel();
		panelButtons.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelButtons.setBounds(10, 35, 215, 121);
		frame.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);

		JButton btnA = new JButton(Messages.getString("MainWindow.btnA.text"));
		btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('a');
				refreshPictureInfo();
			}
		});
		btnA.setBounds(10, 11, 89, 23);
		panelButtons.add(btnA);

		JButton btnB = new JButton(Messages.getString("MainWindow.btnB.text"));
		btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('b');
				refreshPictureInfo();
			}
		});
		btnB.setBounds(113, 11, 89, 23);
		panelButtons.add(btnB);

		JButton btnC = new JButton(Messages.getString("MainWindow.btnC.text"));
		btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('c');
				refreshPictureInfo();
			}
		});
		btnC.setBounds(10, 44, 89, 23);
		panelButtons.add(btnC);

		JButton btnD = new JButton(Messages.getString("MainWindow.btnD.text"));
		btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('d');
				refreshPictureInfo();
			}
		});
		btnD.setBounds(113, 44, 89, 23);
		panelButtons.add(btnD);

		JButton btnE = new JButton(Messages.getString("MainWindow.btnE.text"));
		btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('e');
				refreshPictureInfo();
			}
		});
		btnE.setBounds(10, 78, 89, 23);
		panelButtons.add(btnE);

		JButton btnOriginal = new JButton(Messages.getString("MainWindow.btnOriginal.text"));
		btnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.restoreOriginalImage();
				refreshPictureInfo();
			}
		});
		btnOriginal.setBounds(113, 78, 89, 23);
		panelButtons.add(btnOriginal);

		JPanel panelInformation = new JPanel();
		panelInformation.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInformation.setBounds(10, 159, 215, 114);
		frame.getContentPane().add(panelInformation);
		panelInformation.setLayout(null);

		this.lbMeanUpperHalf = new JLabel(Messages.getString("MainWindow.lbMeanUpperHalf.text"));
		this.lbMeanUpperHalf.setBounds(13, 11, 179, 14);
		panelInformation.add(this.lbMeanUpperHalf);

		this.lbMedianLowerHalf = new JLabel(Messages.getString("MainWindow.lbMedianLowerHalf.text"));
		this.lbMedianLowerHalf.setBounds(13, 36, 168, 14);
		panelInformation.add(this.lbMedianLowerHalf);

		this.lbVariance = new JLabel(Messages.getString("MainWindow.lbVariance.text"));
		this.lbVariance.setBounds(13, 86, 85, 14);
		panelInformation.add(this.lbVariance);

		this.lbMode = new JLabel(Messages.getString("MainWindow.lbMode.text"));
		this.lbMode.setBounds(13, 61, 83, 14);
		panelInformation.add(this.lbMode);

		this.lbImageViewer = new JLabel("");
		this.lbImageViewer.setBounds(235, 35, 398, 398);
		frame.getContentPane().add(this.lbImageViewer);
		
		this.lbHistogramViewer = new JLabel("");
		this.lbHistogramViewer.setBounds(3, 271, 229, 165);
		frame.getContentPane().add(this.lbHistogramViewer);
	}
	
	private void refreshHistogram(){
		
		int[] histogram = this.picture.getHistogramValues();
		
		final XYSeries series = new XYSeries("");
		for(int i = 0; i < histogram.length; i++) {
			series.add(i+2, histogram[i]);
		}
		
        final XYSeriesCollection dataset = new XYSeriesCollection(series);

        PlotOrientation orientation = PlotOrientation.VERTICAL; 
        JFreeChart chart = ChartFactory.createHistogram(
        		"", // Title
        		"", // X label
        		"", // Y label
                dataset, // Dataset
                orientation, // Orientation
                false, // Legend
                false, // Tooltips
                false); // Urls
        
        chart.setBackgroundPaint(this.frame.getBackground());
        
        XYPlot plot = (XYPlot) chart.getPlot();
        
        ValueAxis range = plot.getRangeAxis();
        range.setVisible(false);      
        
        ValueAxis domain = plot.getDomainAxis();
        domain.setVisible(false);    
        
        DeviationRenderer renderer = new DeviationRenderer(true, false);
        plot.setRenderer(renderer);
        
        plot.setAxisOffset(RectangleInsets.ZERO_INSETS);
        
        int viewerHeight = this.lbHistogramViewer.getHeight();
        int viewerWidth = this.lbHistogramViewer.getWidth();
        BufferedImage bi = chart.createBufferedImage(viewerWidth, viewerHeight);
        
        this.lbHistogramViewer.setIcon(new ImageIcon(bi.getScaledInstance(viewerWidth, viewerHeight, Image.SCALE_SMOOTH)));
	}
	
	private void openPictureDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter(Messages.getString("MainWindow.ImageFilterDialog.text"), ALLOWED_EXTENSIONS));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.picture = new Picture(fileChooser.getSelectedFile().getAbsolutePath());
			refreshPictureInfo();
		}
	}

	private void refreshPictureInfo() {
		ImageIcon stretchedImage = this.picture.getStretchedImage(this.lbImageViewer.getWidth(),
				this.lbImageViewer.getHeight());
		this.lbImageViewer.setIcon(stretchedImage);
		this.refreshHistogram();
		
		this.lbMeanUpperHalf.setText(Messages.getString("MainWindow.lbMeanUpperHalf.text") + Integer.toString(this.picture.getMeanUpperHalf()));
		this.lbMedianLowerHalf.setText(Messages.getString("MainWindow.lbMedianLowerHalf.text") + Integer.toString(this.picture.getMedianLowerHalf()));
		this.lbMode.setText(Messages.getString("MainWindow.lbMode.text") + Integer.toString(this.picture.getMode()));
		this.lbVariance.setText(Messages.getString("MainWindow.lbVariance.text") + Integer.toString(this.picture.getVariance()));
	}
}
