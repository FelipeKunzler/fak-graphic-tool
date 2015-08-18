package fak.graphicTool;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class MainWindow {

	private JFrame frame;
	private JLabel lbImageViewer;

	private Picture picture;
	
	private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "bmp", "gif"};

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
		this.refreshPictureDisplay();

		JPanel panelGraphic = new JPanel();
		panelGraphic.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelGraphic.setBounds(10, 270, 215, 163);
		frame.getContentPane().add(panelGraphic);
		panelGraphic.setLayout(null);

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
		panelButtons.setBounds(10, 35, 215, 113);
		frame.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);

		JButton btnA = new JButton(Messages.getString("MainWindow.btnA.text"));
		btnA.setBounds(10, 11, 89, 23);
		panelButtons.add(btnA);

		JButton btnB = new JButton(Messages.getString("MainWindow.btnB.text"));
		btnB.setBounds(113, 11, 89, 23);
		panelButtons.add(btnB);

		JButton btnC = new JButton(Messages.getString("MainWindow.btnC.text"));
		btnC.setBounds(10, 44, 89, 23);
		panelButtons.add(btnC);

		JButton btnD = new JButton(Messages.getString("MainWindow.btnD.text"));
		btnD.setBounds(113, 44, 89, 23);
		panelButtons.add(btnD);

		JButton btnE = new JButton(Messages.getString("MainWindow.btnE.text"));
		btnE.setBounds(10, 78, 89, 23);
		panelButtons.add(btnE);

		JButton btnOriginal = new JButton(Messages.getString("MainWindow.btnF.text"));
		btnOriginal.setBounds(113, 78, 89, 23);
		panelButtons.add(btnOriginal);

		JPanel panelInformation = new JPanel();
		panelInformation.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelInformation.setBounds(10, 159, 215, 100);
		frame.getContentPane().add(panelInformation);
		panelInformation.setLayout(null);

		JLabel labelMean = new JLabel(Messages.getString("MainWindow.labelAverage.text"));
		labelMean.setBounds(24, 28, 83, 14);
		panelInformation.add(labelMean);

		JLabel labelMedian = new JLabel(Messages.getString("MainWindow.labelMedian.text"));
		labelMedian.setBounds(118, 28, 97, 14);
		panelInformation.add(labelMedian);

		JLabel labelRange = new JLabel(Messages.getString("MainWindow.labelRange.text"));
		labelRange.setBounds(118, 64, 85, 14);
		panelInformation.add(labelRange);

		JLabel labelMode = new JLabel(Messages.getString("MainWindow.labelMode.text"));
		labelMode.setBounds(24, 64, 83, 14);
		panelInformation.add(labelMode);

		this.lbImageViewer = new JLabel("");
		this.lbImageViewer.setBounds(235, 35, 398, 398);
		frame.getContentPane().add(this.lbImageViewer);
	}
	
	private void openPictureDialog() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter(Messages.getString("MainWindow.ImageFilterDialog.text"), ALLOWED_EXTENSIONS));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			picture = new Picture(fileChooser.getSelectedFile().getAbsolutePath());
			refreshPictureDisplay();
		}
	}

	private void refreshPictureDisplay() {
		ImageIcon stretchedImage = picture.getStretchedImage(this.lbImageViewer.getWidth(),
				this.lbImageViewer.getHeight());
		this.lbImageViewer.setIcon(stretchedImage);
	}

}
