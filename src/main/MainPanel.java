package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import graphics.DrawPrimitives;


public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private static DrawPrimitives DP = new DrawPrimitives();
	private static final Color darkTheme = new Color(10, 10, 20) ;
	private static final Color lightTheme = new Color(230, 230, 250) ;
	private static Color bgColor = darkTheme ;
	
	private Plotty plotty = new Plotty() ;
	
	public MainPanel(Dimension size)
	{
		this.setPreferredSize(size);
		this.setBackground(bgColor);
	}
	
	public void run()
	{
		
		List<Double> listT1 = new ArrayList<>() ;
		List<Double> listT2 = new ArrayList<>() ;
		List<Double> listT3 = new ArrayList<>() ;
		List<Double> listT4 = new ArrayList<>() ;
		List<Double> listP3x = new ArrayList<>() ;
		List<Double> listP3y = new ArrayList<>() ;
		List<Double> listP4x = new ArrayList<>() ;
		List<Double> listP4y = new ArrayList<>() ;
		List<Double> listP5x = new ArrayList<>() ;
		List<Double> listP5y = new ArrayList<>() ;
		

		int qtdPointsI = 61 ;
		int qtdPointsJ = 61 ;
		for (int i = 0; i <= qtdPointsI - 1 ; i += 1)
		{
			for (int j = 0; j <= qtdPointsJ - 1 ; j += 1)
			{
				double angle1 = Math.toRadians(0.0 + 6*i) ;
				double angle2 = Math.toRadians(0.0 + 6*j) ;
				plotty.updateAngles(angle1, angle2) ;
				if (plotty.getCalculoFuncionou() && plotty.posicaoValida())
				{
					listT1.add(angle1) ;
					listT2.add(angle2) ;
					listT3.add(plotty.getT3()) ;
					listT4.add(plotty.getT4()) ;
					plotty.calcPoints() ;
					listP3x.add(plotty.getP3().x) ;
					listP3y.add(plotty.getP3().y) ;
					listP4x.add(plotty.getP4().x) ;
					listP4y.add(plotty.getP4().y) ;
					listP5x.add(plotty.getP5().x) ;
					listP5y.add(plotty.getP5().y) ;
				}
			}
		}
		
//		for (int i = 0; i <= listT1.size() - 1 ; i += 1)
//		{
//			System.out.println(listT1.get(i) + "," + listT2.get(i));
//		}
//		System.out.println("\n\n\n");
//		for (int i = 0; i <= listP5x.size() - 1 ; i += 1)
//		{
//			System.out.println(listP5x.get(i) + "," + listP5y.get(i));
//		}
		saveToFile(listP5x, listP5y, "pointpos") ;
		saveToFile(listT1, listT2, "angles") ;
		saveToFile(listT3, listT4, "t3t4") ;
		saveToFile(listP3x, listP3y, "p3") ;
		saveToFile(listP4x, listP4y, "p4") ;
		

		plotty.display(DP) ;
//		plotty.displayLabels(DP) ;
	}

	private void saveToFile(List<Double> list1, List<Double> list2, String fileName)
	{
		FileWriter writer = null;
		
		try
		{
			writer = new FileWriter(fileName + ".txt");
			for (int i = 0; i <= list1.size() - 1 ; i += 1)
			{
				writer.write(String.valueOf(list1.get(i)) + "," + String.valueOf(list2.get(i)));
				writer.write(System.lineSeparator()) ;
			}
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void switchTheme()
	{
		bgColor = bgColor == darkTheme ? lightTheme : darkTheme ;
		this.setBackground(bgColor);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		DP.setGraphics((Graphics2D) g) ;
		run() ;
	}

	public void addAngle1()
	{
		plotty.addAngle1(Math.toRadians(3)) ;
		repaint() ;
	}

	public void reduceAngle1()
	{
		plotty.addAngle1(-Math.toRadians(3)) ;
		repaint() ;
	}

	public void addAngle2()
	{
		plotty.addAngle2(Math.toRadians(3)) ;
		repaint() ;
	}

	public void reduceAngle2()
	{
		plotty.addAngle2(-Math.toRadians(3)) ;
		repaint() ;
	}

}
