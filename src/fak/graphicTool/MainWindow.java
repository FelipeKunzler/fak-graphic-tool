package fak.graphicTool;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

public class MainWindow {
	
	private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "bmp", "gif"};

	@UsesMessagesText(hasToolTip = true)
	private JButton btnA;
	@UsesMessagesText(hasToolTip = true)
	private JButton btnB;	
	@UsesMessagesText(hasToolTip = true)
	private JButton btnC;	
	@UsesMessagesText(hasToolTip = true)
	private JButton btnD;	
	@UsesMessagesText(hasToolTip = true)
	private JButton btnE;	
	@UsesMessagesText(hasToolTip = true)
	private JButton btnOriginal;		
	@UsesMessagesText
	private JLabel lbHistogramViewer;
	@UsesMessagesText
	private JLabel lbMeanUpperHalf;
	@UsesMessagesText
	private JLabel lbMedianLowerHalf;
	@UsesMessagesText
	private JLabel lbVariance;	
	@UsesMessagesText
	private JLabel lbMode;	
	@UsesMessagesText	
	private JLabel lbApplyEffects;
	@UsesMessagesText
	private JMenu mnFile;
	@UsesMessagesText
	private JMenu mnHelp;
	@UsesMessagesText
	private JMenu mnMorphology;
	@UsesMessagesText
	private JMenuItem mntmErosion;
	@UsesMessagesText
	private JMenuItem mntmDilation;
	@UsesMessagesText
	private JMenuItem mntmOpening;
	@UsesMessagesText
	private JMenuItem mntmClosing;
	@UsesMessagesText
	private JMenuItem mnLanguage;
	@UsesMessagesText
	private JMenuItem mntmAbout;
	@UsesMessagesText
	private JMenuItem mntmExit;
	@UsesMessagesText
	private JMenuItem mntmOpen;
	@UsesMessagesText
	private JRadioButtonMenuItem rdbMenuEn;
	@UsesMessagesText
	private JRadioButtonMenuItem rdbMenuPt;
	@UsesMessagesText
	private JMenu mnTools;
	@UsesMessagesText
	private JMenu mnRotate;
	@UsesMessagesText
	private JMenuItem mntmClockwise;
	@UsesMessagesText
	private JMenuItem mntmCounterclockwise;
	@UsesMessagesText
	private JMenuItem mnMirror;
	@UsesMessagesText
	private JMenuItem mntmResize;
	@UsesMessagesText
	private JMenuItem mntmMove;
	@UsesMessagesText
	private JMenuItem mntmThresholding;
	@UsesMessagesText
	private JMenuItem mntmHorizontally;
	@UsesMessagesText
	private JMenuItem mntmVertically;
	@UsesMessagesText
	private JMenuItem mntmRoberts;
	@UsesMessagesText
	private JMenuItem mntmSobel;
	@UsesMessagesText
	private JMenuItem mnBorderDetection;
	@UsesMessagesText
	private JCheckBox chckbxStretchImage;
	
	private JLabel lbImageViewer;
	private JLabel lbDimension;
	private JScrollPane scrollPanel;
	private JFrame frame;
	
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
		
		initializeComponents();
		
		BufferedImage defaultImage = null;
		try {
			defaultImage = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("lena.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.picture = new Picture(defaultImage);
		
		this.refreshPictureInfo();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeComponents() {
		
		frame = new JFrame();
		frame.setTitle(Messages.getString("MainWindow.frame.title"));
		frame.setResizable(false);
		frame.setBounds(100, 100, 644, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		try {
			Image icon = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("icon.png"));
			frame.setIconImage(icon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		this.lbApplyEffects = new JLabel(Messages.getString("MainWindow.lbApplyEffects.text"));
		lbApplyEffects.setHorizontalAlignment(SwingConstants.CENTER);
		lbApplyEffects.setHorizontalTextPosition(SwingConstants.CENTER);
		this.lbApplyEffects.setOpaque(true);
		this.lbApplyEffects.setBackground(this.frame.getBackground());
		this.lbApplyEffects.setBounds(30, 25, 82, 14);
		frame.getContentPane().add(this.lbApplyEffects);
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelButtons.setBounds(8, 32, 215, 114);
		frame.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		this.btnA = new JButton(Messages.getString("MainWindow.btnA.text"));
		this.btnA.setToolTipText(Messages.getString("MainWindow.btnA.toolTipText"));
		this.btnA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('a');
				refreshPictureInfo();
			}
		});
		this.btnA.setBounds(14, 11, 89, 23);
		panelButtons.add(this.btnA);
		
		this.btnB = new JButton(Messages.getString("MainWindow.btnB.text"));
		this.btnB.setToolTipText(Messages.getString("MainWindow.btnB.toolTipText"));
		this.btnB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('b');
				refreshPictureInfo();
			}
		});
		this.btnB.setBounds(113, 11, 89, 23);
		panelButtons.add(this.btnB);
		
		this.btnC = new JButton(Messages.getString("MainWindow.btnC.text"));
		this.btnC.setToolTipText(Messages.getString("MainWindow.btnC.toolTipText")); 
		this.btnC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('c');
				refreshPictureInfo();
			}
		});
		this.btnC.setBounds(14, 45, 89, 23);
		panelButtons.add(this.btnC);
		
		this.btnD = new JButton(Messages.getString("MainWindow.btnD.text"));
		this.btnD.setToolTipText(Messages.getString("MainWindow.btnD.toolTipText"));
		this.btnD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('d');
				refreshPictureInfo();
			}
		});
		this.btnD.setBounds(113, 45, 89, 23);
		panelButtons.add(this.btnD);
		
		this.btnE = new JButton(Messages.getString("MainWindow.btnE.text"));
		this.btnE.setToolTipText(Messages.getString("MainWindow.btnE.toolTipText"));
		this.btnE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.modifyImage('e');
				refreshPictureInfo();
			}
		});
		this.btnE.setBounds(14, 79, 89, 23);
		panelButtons.add(this.btnE);
		
		this.btnOriginal = new JButton(Messages.getString("MainWindow.btnOriginal.text"));
		this.btnOriginal.setToolTipText(Messages.getString("MainWindow.btnOriginal.toolTipText"));
		this.btnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.restoreOriginalImage();
				refreshPictureInfo();
			}
		});
		this.btnOriginal.setBounds(113, 79, 89, 23);
		panelButtons.add(this.btnOriginal);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 664, 21);
		frame.getContentPane().add(menuBar);
		
		this.mnFile = new JMenu(Messages.getString("MainWindow.mnFile.text"));
		menuBar.add(mnFile);
		
		this.mntmOpen = new JMenuItem(Messages.getString("MainWindow.mntmOpen.text"));
		this.mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openPictureDialog();
			}
		});
		
		this.mnFile.add(this.mntmOpen);
		
		JSeparator separator = new JSeparator();
		this.mnFile.add(separator);
		
		this.mntmExit = new JMenuItem(Messages.getString("MainWindow.mntmExit.text"));
		this.mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.mnFile.add(this.mntmExit);
		
		this.mnTools = new JMenu(Messages.getString("MainWindow.mnTools.text"));
		menuBar.add(this.mnTools);
		
		this.mnRotate = new JMenu(Messages.getString("MainWindow.mnRotate.text"));
		mnTools.add(this.mnRotate);
		
		this.mntmClockwise = new JMenuItem(Messages.getString("MainWindow.mntmClockwise.text"));
		this.mntmClockwise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picture.rotate();
				refreshPictureInfo();
			}
		});
		this.mnRotate.add(mntmClockwise);
		
		this.mntmCounterclockwise = new JMenuItem(Messages.getString("MainWindow.mntmCounterclockwise.text"));
		this.mntmCounterclockwise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picture.rotate(true);
				refreshPictureInfo();
				
			}
		});
		mnRotate.add(this.mntmCounterclockwise);
		
		this.mnMirror = new JMenu(Messages.getString("MainWindow.mnMirror.text"));
		mnTools.add(this.mnMirror);
		
		this.mntmHorizontally = new JMenuItem(Messages.getString("MainWindow.mntmHorizontally.text"));
		this.mntmHorizontally.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picture.mirror();
				refreshPictureInfo();
			}
		});
		this.mnMirror.add(mntmHorizontally);
		
		this.mntmVertically = new JMenuItem(Messages.getString("MainWindow.mntmVertically.text"));
		this.mntmVertically.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picture.mirror(true);
				refreshPictureInfo();
			}
		});
		this.mnMirror.add(mntmVertically);
		
		this.mntmResize = new JMenuItem(Messages.getString("MainWindow.mntmResize.text"));
		this.mntmResize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				onResizeClick();
			}
		});
		mnTools.add(this.mntmResize);
		
		this.mntmMove = new JMenuItem(Messages.getString("MainWindow.mntmMove.text"));
		this.mntmMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onMoveClick();
			}
		});
		this.mnTools.add(this.mntmMove);
		
		this.mntmThresholding = new JMenuItem(Messages.getString("MainWindow.mntmThresholding.text"));
		this.mntmThresholding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onThresholdingClick();
			}
		});
		this.mnTools.add(this.mntmThresholding);
		
		this.mnBorderDetection = new JMenu(Messages.getString("MainWindow.mnBorderDetection.text"));
		mnTools.add(this.mnBorderDetection);
		
		this.mntmRoberts = new JMenuItem(Messages.getString("MainWindow.mntmRoberts.text"));
		this.mntmRoberts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picture.detectBordersRoberts();
				refreshPictureInfo();
			}
		});
		this.mnBorderDetection.add(mntmRoberts);
		
		this.mntmSobel = new JMenuItem(Messages.getString("MainWindow.mntmSobel.text"));
		this.mntmSobel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				picture.detectBordersSobel();
				refreshPictureInfo();
			}
		});
		this.mnBorderDetection.add(mntmSobel);
		
		this.mnMorphology = new JMenu(Messages.getString("MainWindow.mnMorphology.text"));
		menuBar.add(this.mnMorphology);
		
		this.mntmErosion = new JMenuItem(Messages.getString("MainWindow.mntmErosion.text"));
		this.mntmErosion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				picture.erosion();
				refreshPictureInfo();
			}
		});
		this.mnMorphology.add(this.mntmErosion);
		
		this.mntmDilation = new JMenuItem(Messages.getString("MainWindow.mntmDilation.text"));
		this.mntmDilation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.dilation();
				refreshPictureInfo();
			}
		});
		this.mnMorphology.add(this.mntmDilation);
		
		this.mntmOpening = new JMenuItem(Messages.getString("MainWindow.mntmOpening.text"));
		this.mntmOpening.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.opening();
				refreshPictureInfo();
			}
		});
		this.mnMorphology.add(this.mntmOpening);
		
		this.mntmClosing = new JMenuItem(Messages.getString("MainWindow.mntmClosing.text"));
		this.mntmClosing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				picture.closing();
				refreshPictureInfo();
			}
		});
		this.mnMorphology.add(this.mntmClosing);
		
		this.mnHelp = new JMenu(Messages.getString("MainWindow.mnHelp.text"));
		menuBar.add(this.mnHelp);
		
		this.mnLanguage = new JMenu(Messages.getString("MainWindow.mnLanguage.text"));
		this.mnHelp.add(this.mnLanguage);
		
		this.rdbMenuEn = new JRadioButtonMenuItem(Messages.getString("MainWindow.rdbMenuEn.text"));
		this.rdbMenuEn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbMenuPt.setSelected(!rdbMenuPt.isSelected());
				Messages.setResourceBundle(Messages.BUNDLE_NAME_EN);
				refreshTexts();
			}
		});
		this.rdbMenuEn.setSelected(true);
		mnLanguage.add(this.rdbMenuEn);
		
		this.rdbMenuPt = new JRadioButtonMenuItem(Messages.getString("MainWindow.rdbMenuPt.text"));
		this.rdbMenuPt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbMenuEn.setSelected(!rdbMenuEn.isSelected());
				Messages.setResourceBundle(Messages.BUNDLE_NAME_PT);
				refreshTexts();
			}
		});
		mnLanguage.add(this.rdbMenuPt);
		
		this.mntmAbout = new JMenuItem(Messages.getString("MainWindow.mntmAbout.text"));
		this.mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, Messages.getString("MainWindow.dialogAbout.text"), 
						Messages.getString("MainWindow.mntmAbout.text"), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		this.mnHelp.add(this.mntmAbout);

		JPanel panelInformation = new JPanel();
		panelInformation.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInformation.setBounds(8, 151, 215, 108);
		frame.getContentPane().add(panelInformation);
		panelInformation.setLayout(null);

		this.lbMeanUpperHalf = new JLabel(Messages.getString("MainWindow.lbMeanUpperHalf.text"));
		this.lbMeanUpperHalf.setBounds(13, 11, 192, 14);
		panelInformation.add(this.lbMeanUpperHalf);

		this.lbMedianLowerHalf = new JLabel(Messages.getString("MainWindow.lbMedianLowerHalf.text"));
		this.lbMedianLowerHalf.setBounds(13, 36, 192, 14);
		panelInformation.add(this.lbMedianLowerHalf);

		this.lbVariance = new JLabel(Messages.getString("MainWindow.lbVariance.text"));
		this.lbVariance.setBounds(13, 86, 192, 14);
		panelInformation.add(this.lbVariance);

		this.lbMode = new JLabel(Messages.getString("MainWindow.lbMode.text"));
		this.lbMode.setBounds(13, 61, 192, 14);
		panelInformation.add(this.lbMode);
		
		this.lbHistogramViewer = new JLabel("");
		this.lbHistogramViewer.setBounds(0, 259, 230, 174);
		frame.getContentPane().add(this.lbHistogramViewer);
		
		this.scrollPanel = new JScrollPane();
		this.scrollPanel.setBounds(233, 32, 398, 398);
		frame.getContentPane().add(this.scrollPanel);
		
		this.lbImageViewer = new JLabel("");
		lbImageViewer.setHorizontalTextPosition(SwingConstants.CENTER);
		this.lbImageViewer.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPanel.setViewportView(this.lbImageViewer);
		
		this.chckbxStretchImage = new JCheckBox(Messages.getString("MainWindow.chckbxStretchImage.text"));
		this.chckbxStretchImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshPictureInfo();
			}
		});
		this.chckbxStretchImage.setBounds(233, 429, 174, 23);
		frame.getContentPane().add(this.chckbxStretchImage);
		
		this.lbDimension = new JLabel("");
		this.lbDimension.setHorizontalAlignment(SwingConstants.RIGHT);
		this.lbDimension.setBounds(463, 433, 165, 14);
		frame.getContentPane().add(this.lbDimension);
	}
	
	private void refreshTexts(){
		for (Field field: MainWindow.class.getDeclaredFields()) {						
			field.setAccessible(true);
			UsesMessagesText messageTextAnnotation = field.getAnnotation(UsesMessagesText.class);
			if (messageTextAnnotation != null){
				this.invokeMethod(field, "setText", "text");
				
				if (messageTextAnnotation.hasToolTip()){
					this.invokeMethod(field, "setToolTipText", "toolTipText");
				}
			}
		}
		
		refreshPictureInfo();
	}
	
	private void invokeMethod(Field field, String methodName, String partialKey){
		String key = MainWindow.class.getSimpleName() + "." + field.getName() + "." + partialKey;
		try {
			Method method = field.getType().getMethod(methodName, String.class);
			method.invoke(field.get(this), Messages.getString(key));
		} catch (Exception e){
			e.printStackTrace();
		}
		
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
		
		if (this.chckbxStretchImage.isSelected()){
			this.lbImageViewer.setIcon(
					this.picture.getStretchedImage(this.scrollPanel.getWidth() - 3, this.scrollPanel.getHeight() - 3));
		}
		else{
			this.lbImageViewer.setIcon(new ImageIcon(this.picture.getBufferedImage()));
		}
		
		this.refreshHistogram();
		
		BufferedImage bi = this.picture.getBufferedImage();
		this.lbDimension.setText(bi.getWidth() + " x " + bi.getHeight() + "px");
		this.lbMeanUpperHalf.setText(Messages.getString("MainWindow.lbMeanUpperHalf.text") + Integer.toString(this.picture.getMeanUpperHalf()));
		this.lbMedianLowerHalf.setText(Messages.getString("MainWindow.lbMedianLowerHalf.text") + Integer.toString(this.picture.getMedianLowerHalf()));
		this.lbMode.setText(Messages.getString("MainWindow.lbMode.text") + Integer.toString(this.picture.getMode()));
		this.lbVariance.setText(Messages.getString("MainWindow.lbVariance.text") + Integer.toString(this.picture.getVariance()));
	}
	
	private void onResizeClick(){
		String strScale = (String) JOptionPane.showInputDialog(
                frame, Messages.getString("MainWindow.dialogScale.text"), Messages.getString("MainWindow.mntmResize.text"), JOptionPane.QUESTION_MESSAGE,
                null, null, "1.0");
		
		if (strScale != null){
			
			double scale;
			try {
				scale = Double.parseDouble(strScale);
			}
			catch(Exception e) {
				scale = -1;
			}
			
			if (scale > 0){
				picture.resize(scale);
				refreshPictureInfo();
			}
			else {
				JOptionPane.showMessageDialog(frame,
						Messages.getString("MainWindow.dialogErrorScale.text"),
						Messages.getString("MainWindow.dialogErrorScale.title"),
					    JOptionPane.ERROR_MESSAGE);									
			}
		}
	}
	
	private void onMoveClick(){		
		
		JTextField tfX = new JTextField();
		tfX.setText("0");
		JTextField tfY = new JTextField();
		tfY.setText("0");
		
		Object[] options = {
			Messages.getString("MainWindow.dialogMoveX.text"), tfX,
			Messages.getString("MainWindow.dialogMoveY.text"), tfY
		};
		
		int option = JOptionPane.showOptionDialog(frame, options, Messages.getString("MainWindow.mntmMove.text"), 
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, new Object[]{"OK", "Cancel"}, options[1]);
		if (option == JOptionPane.OK_OPTION) {
		
			int x;
			int y;
			try {
				x = Integer.parseInt(tfX.getText());
				y = Integer.parseInt(tfY.getText());
			}
			catch(Exception e) {
				x = -1;
				y = -1;
			}
			
			if (x >= 0 && y >= 0){
				picture.move(x, y);
				refreshPictureInfo();
			}
			else {
				JOptionPane.showMessageDialog(frame,
						Messages.getString("MainWindow.dialogMoveError.text"),
						Messages.getString("MainWindow.mntmMove.text"),
					    JOptionPane.ERROR_MESSAGE);									
			}
	    }
	}
	
	private void onThresholdingClick(){
		String strThreshold = (String) JOptionPane.showInputDialog(
                frame, Messages.getString("MainWindow.dialogThreshold.text"), Messages.getString("MainWindow.mntmThresholding.text"), JOptionPane.QUESTION_MESSAGE,
                null, null, "125");
		
		if (strThreshold != null){
			
			int threshold;
			try {
				threshold = Integer.parseInt(strThreshold);
			}
			catch(Exception e) {
				threshold = -1;
			}
			
			if (threshold >= 0 && threshold <= 255){
				picture.thresholding(threshold);
				refreshPictureInfo();
			}
			else {
				JOptionPane.showMessageDialog(frame,
						Messages.getString("MainWindow.dialogErrorThreshold.text"),
						Messages.getString("MainWindow.dialogErrorThreshold.title"),
					    JOptionPane.ERROR_MESSAGE);									
			}
		}
	}
}
