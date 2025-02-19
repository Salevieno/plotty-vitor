package main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private static final String assetsPath = ".\\assets\\";
	private static final ImageIcon imgSwitchTheme = new ImageIcon(assetsPath + "switchTheme.png");
	private static final ImageIcon imgAddAngle1 = new ImageIcon(assetsPath + "addAngle1.png");
	private static final ImageIcon imgReduceAngle1 = new ImageIcon(assetsPath + "reduceAngle1.png");
	private static final ImageIcon imgAddAngle2 = new ImageIcon(assetsPath + "addAngle2.png");
	private static final ImageIcon imgReduceAngle2 = new ImageIcon(assetsPath + "reduceAngle2.png");


	private static final Dimension btnStdSize = new Dimension(40, 40) ;
	private static final Color btnStdColor = new Color(255, 255, 255) ;
	private static final Color darkTheme = new Color(50, 50, 80) ;
	private static final Color lightTheme = new Color(180, 220, 250) ;
	private static Color bgColor = darkTheme ;
	
	public HeaderPanel(Dimension size)
	{
        this.setBackground(bgColor);
        this.setPreferredSize(size);
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnAddAngle1 = createBtn(imgAddAngle1, btnStdSize);

        btnAddAngle1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.addAngle1();
			}
		});
		
        this.add(btnAddAngle1);

        JButton btnReduceAngle1 = createBtn(imgReduceAngle1, btnStdSize);

        btnReduceAngle1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.reduceAngle1();
			}
		});
		
        this.add(btnReduceAngle1);
        
        JButton btnAddAngle2 = createBtn(imgAddAngle2, btnStdSize);

        btnAddAngle2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.addAngle2();
			}
		});
		
        this.add(btnAddAngle2);

        JButton btnReduceAngle2 = createBtn(imgReduceAngle2, btnStdSize);

        btnReduceAngle2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.reduceAngle2();
			}
		});
		
        this.add(btnReduceAngle2);

        JButton btnTheme = createBtn(imgSwitchTheme, btnStdSize);

        btnTheme.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.switchTheme();
			}
		});
		
        this.add(btnTheme);

	}

	public static JButton createBtn(ImageIcon icon, Dimension size)
	{
		JButton button = new JButton();
		if (icon != null)
		{
			button.setIcon(icon);
			button.setContentAreaFilled(false);
		}
		button.setVerticalAlignment(0);
		button.setHorizontalAlignment(0);
		button.setPreferredSize(size);
		button.setBackground(btnStdColor);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return button;
	}

	public void switchTheme()
	{
		bgColor = bgColor == darkTheme ? lightTheme : darkTheme ;
		this.setBackground(bgColor);
	}
	
}
