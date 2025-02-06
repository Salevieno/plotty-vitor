package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

import graphics.Align;
import graphics.DrawPrimitives;

public class Plotty
{
	private final Point initialDrawingPoint = new Point(300, 300) ;
	
	private final double d = 4 ;
	private final double L = 12.2 ;
	private final double scale = 10 ;

	private double t1 ;
	private double t2 ;
	private double t3 ;
	private double t4 ;
	
	private Point2D.Double p1 ;
	private Point2D.Double p2 ;
	private Point2D.Double p3 ;
	private Point2D.Double p4 ;
	private Point2D.Double p5 ;
	
	private boolean calculoFuncionou = true ;

	
	public Plotty(double t1, double t2)
	{
		this.t1 = t1 ;
		this.t2 = t2 ;
		
		p1 = new Point2D.Double(0, 0) ;
		p2 = new Point2D.Double(d, 0) ;
//		p3 = new Point2D.Double(0, 0) ;
//		p4 = new Point2D.Double(0, 0) ;
		
		double[] angles = calcAngulos() ;
		
		if (angles != null)
		{
			t3 = angles[0] ;
			t4 = angles[1] ;
		}
		
		Point2D.Double[] points = calcPoints() ;
		p3 = points[0] ;
		p4 = points[1] ;
		p5 = points[2] ;
		
        calculoFuncionou = pointOk() ;
        if (!calculoFuncionou)
        {
        	logDetalhesCalc() ;
        }
		
	}

	public Plotty()
	{
		this(Math.toRadians(90), Math.toRadians(90)) ;		
	}
	
	public double[] calcAngulos()
	{

        calculoFuncionou = true ;
        double C = (d / L) - Math.cos(t1) - Math.cos(t2);
        double S = Math.sin(t2) - Math.sin(t1);

        double beta = Math.atan2(S, C); // atan2 handles quadrant

        double cosAlpha = C / (2 * Math.cos(beta));
        if (1 < Math.abs(cosAlpha))
        {
//            System.out.println("No solution: cosAlpha is out of range.") ;
            calculoFuncionou = false ;
            return null ;
        }
    	double alpha = Math.acos(cosAlpha) ;
    	
        t3 = alpha + beta;
        t4 = alpha - beta;
        
        t3 = Math.toDegrees(t3) % 360;
        t4 = Math.toDegrees(t4) % 360;

        if (t3 < 0)
        {
        	t3 += 360;
        }
        if (t4 < 0)
        {
        	t4 += 360;
        }

        t3 = Math.toRadians(t3) ;
        t4 = Math.toRadians(t4) ;
        
        return new double[] {t3, t4} ;
        
	}
	
	public Point2D.Double[] calcPoints()
	{
		p3 = new Point2D.Double(p1.x + L * Math.cos(t1), p1.y + L * Math.sin(t1)) ;
		p4 = new Point2D.Double(p2.x - L * Math.cos(t2), p2.y + L * Math.sin(t2)) ;
		p5 = new Point2D.Double(p3.x + L * Math.cos(t3), p3.y + L * Math.sin(t3)) ;
		
		if (linesIntersect(p1, p3, p2, p4))
		{
			System.out.println("lines intersect!");
		}
		
		return new Point2D.Double[] {p3, p4, p5} ;
	}
	
	public void updateAngles(double novoT1, double novoT2)
	{
		t1 = novoT1 ;
		t2 = novoT2 ;
		
		calcAngulos() ;
		calcPoints() ;
	}
	
	public boolean getCalculoFuncionou() { return calculoFuncionou ;}
	public boolean posicaoValida()
	{
		return !linesIntersect(p1, p3, p2, p4) && !linesIntersect(p3, p5, p2, p4) && !linesIntersect(p1, p3, p5, p4) ;
	}
	
	private Point2D.Double calcPontoGarraPelaEsquerda() { return new Point2D.Double(p3.x + L * Math.cos(t3), p3.y + L * Math.sin(t3)) ;}
	private Point2D.Double calcPontoGarraPelaDireita() { return new Point2D.Double(p4.x - L * Math.cos(t4), p4.y + L * Math.sin(t4)) ;}
	
	private boolean pointOk()
	{
		
		double tol = Math.pow(10, -9) ;
		Point2D.Double pontoGarra1 = calcPontoGarraPelaEsquerda() ;
		Point2D.Double pontoGarra2 = calcPontoGarraPelaDireita() ;
		double dx = Math.abs(pontoGarra1.x - pontoGarra2.x) ;
		double dy = Math.abs(pontoGarra1.y - pontoGarra2.y) ;
		
		return dx <= tol && dy <= tol ;
		
	}
	
	private void drawPoint(Point2D.Double pos, DrawPrimitives DP)
	{
		DP.drawCircle(toDrawing(pos), 5, Color.white, Color.white) ;
	}
	
	private void drawLine(Point2D.Double p1, Point2D.Double p2, DrawPrimitives DP)
	{
		DP.drawLine(toDrawing(p1), toDrawing(p2), Color.white) ;
	}
	
	private Point toDrawing(Point2D.Double point)
	{
		return new Point((int) (initialDrawingPoint.x + scale * point.x), (int) (initialDrawingPoint.y - scale * point.y)) ;
	}
	
	private Point offset(Point point, Point offset)
	{
		return new Point(point.x + offset.x, point.y + offset.y) ;
	}
	
	private Point offset(Point point)
	{
		return offset(point, new Point(5, 15)) ;
	}
	
	private double toDegree(double angle) { return Math.round(Math.toDegrees(angle)) ;}
	
	public void displayLabels(DrawPrimitives DP)
	{

		DP.drawText(toDrawing(p1), Align.topLeft, "p1", Color.white) ;
		DP.drawText(toDrawing(p2), Align.topLeft, "p2", Color.white) ;
		DP.drawText(toDrawing(p3), Align.topLeft, "p3", Color.white) ;
		DP.drawText(toDrawing(p4), Align.topLeft, "p4", Color.white) ;
		DP.drawText(toDrawing(p5), Align.topLeft, "p5", Color.white) ;
		
		DP.drawText(offset(toDrawing(p1)), Align.topLeft, "t1: " + toDegree(t1) + "°", Color.white) ;
		DP.drawText(offset(toDrawing(p2)), Align.topLeft, "t2: " + toDegree(t2) + "°", Color.white) ;
		DP.drawText(offset(toDrawing(p3)), Align.topLeft, "t3: " + toDegree(t3) + "°", Color.white) ;
		DP.drawText(offset(toDrawing(p4)), Align.topLeft, "t4: " + toDegree(t4) + "°", Color.white) ;

	}
	
	public void display(DrawPrimitives DP)
	{

		drawLine(p1, p3, DP) ;
		drawLine(p3, p5, DP) ;
		drawLine(p5, p4, DP) ;
		drawLine(p4, p2, DP) ;
		
		drawPoint(p1, DP) ;
		drawPoint(p2, DP) ;
		drawPoint(p3, DP) ;
		drawPoint(p4, DP) ;
		drawPoint(p5, DP) ;

		if (!calculoFuncionou)
		{
			DP.drawText(new Point(100, 100), Align.topLeft, "Cálculo não funcionou!", Color.red) ;
		}
		
	}
	
	private void logDetalhesCalc()
	{

        System.out.println("ponto da garra não foi calculado corretamente") ;
        System.out.println("t1, t2: " + Math.toDegrees(t1) + ", " + Math.toDegrees(t2)) ;
        System.out.println("t3, t4: " + Math.toDegrees(t3) + ", " + Math.toDegrees(t4)) ;
        System.out.println("cos(t1), sin(t2): " + Math.sin(t1) + ", " + Math.sin(t2)) ;
        System.out.println("sin(t3), sin(t4): " + Math.sin(t3) + ", " + Math.sin(t4)) ;
        System.out.println("cos(t1), cos(t2): " + Math.cos(t1) + ", " + Math.cos(t2)) ;
        System.out.println("cos(t3), cos(t4): " + Math.cos(t3) + ", " + Math.cos(t4)) ;
        System.out.println("eq. 1: " + (Math.cos(t1) + Math.cos(t3)) + " = " + (d / L - Math.cos(t2) - Math.cos(t4))) ;
        System.out.println("eq. 2: " + (Math.sin(t1) + Math.sin(t3)) + " = " + (Math.sin(t2) + Math.sin(t4))) ;
        System.out.println("Pela esquerda: " + calcPontoGarraPelaEsquerda()) ;
        System.out.println("Pela direita: " + calcPontoGarraPelaDireita()) ;
        
	}
	
	
	
	// Method to find the orientation of an ordered triplet (p, q, r).
    // 0 -> Collinear, 1 -> Clockwise, 2 -> Counterclockwise
    private static int orientation(Point2D.Double p, Point2D.Double q, Point2D.Double r)
    {
    	double  val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (Math.abs(val) < 1e-9) return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
    }

    // Check if point q lies on segment pr
	private static boolean onSegment(Point2D.Double p, Point2D.Double q, Point2D.Double r)
	{
        return q.x >= Math.min(p.x, r.x) && q.x <= Math.max(p.x, r.x) &&
               q.y >= Math.min(p.y, r.y) && q.y <= Math.max(p.y, r.y);
    }

    // Main method to check if two line segments intersect
    public static boolean linesIntersect(Point2D.Double p1, Point2D.Double q1, Point2D.Double p2, Point2D.Double q2)
    {
        // Find orientations for the four combinations
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        // General case
        if (o1 != o2 && o3 != o4) return true;

        // Special cases
        if (o1 == 0 && onSegment(p1, p2, q1)) return true; // p2 lies on p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true; // q2 lies on p1q1
        if (o3 == 0 && onSegment(p2, p1, q2)) return true; // p1 lies on p2q2
        if (o4 == 0 && onSegment(p2, q1, q2)) return true; // q1 lies on p2q2

        return false; // No intersection
    }

	public void addAngle1(double amount)
	{
		t1 += amount ;
		calcAngulos() ;
		calcPoints() ;
	}
	
	public void addAngle2(double amount)
	{
		t2 += amount ;
		calcAngulos() ;
		calcPoints() ;
	}

	public Point getInitialDrawingPoint() {
		return initialDrawingPoint;
	}

	public double getD() {
		return d;
	}

	public double getL() {
		return L;
	}

	public double getScale() {
		return scale;
	}

	public double getT1() {
		return t1;
	}

	public double getT2() {
		return t2;
	}

	public double getT3() {
		return t3;
	}

	public double getT4() {
		return t4;
	}

	public Point2D.Double getP1() {
		return p1;
	}

	public Point2D.Double getP2() {
		return p2;
	}

	public Point2D.Double getP3() {
		return p3;
	}

	public Point2D.Double getP4() {
		return p4;
	}

	public Point2D.Double getP5() {
		return p5;
	}
	
	
	
}
