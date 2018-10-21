package uloha1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.SwingWorker;
/**
 *
 * @author mazurd
 */
// Drawing class
public class DrawPanel extends javax.swing.JPanel {
    // variables
    protected Polygon [] polygons;
    protected LinkedList results;
    protected Point2D point;
    protected ArrayList<Integer> pointsx;
    protected ArrayList<Integer> pointsy;
    protected SwingWorker worker;
    protected int counter;
    protected boolean alg;
    
    // constructor
    public DrawPanel() {
        alg = true;
        polygons = new Polygon [2];
        point = new Point2D.Double(-10.0,-10.0);
        pointsx = new  ArrayList<>();
        pointsy = new  ArrayList<>();
        results = new LinkedList();
        worker = new Worker();
        counter = 0;
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        initComponents();
    }
    // method for resetting values
    public void reseter(){
        results.clear();
        worker = new Worker();
        counter = 0;
    }
    
    // method, which makes polygon from points
    public void makePolygonFrompPoints(){
        int x []= new int[pointsx.size()];
        int y []= new int[pointsy.size()];
        for (int i = 0;i<pointsx.size();i++){
                x[i] = pointsx.get(i);
                y[i] = pointsy.get(i);
            }
       polygons [0]= new Polygon(x,y,pointsx.size());
    }
    
    // method, where all the drawing is happening
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gfx = (Graphics2D)g;
        
        // draw polygons if exists
        if(polygons[0] != null){
            for (int i =0;i<polygons.length;i++){     
                gfx.drawPolygon(polygons[i]);
            }
        }
        //cycle through all results
        for (int i = 0;i<results.size();i++){
            gfx.setColor(Color.GREEN);
            if (results.get(i) == Algorithm.position.INSIDE){
                //fillpolygon with color
                gfx.fillPolygon(polygons[i]);
                gfx.setColor(Color.BLACK);
                //boundary with different color
                gfx.drawPolygon(polygons[i]);
                gfx.drawString("Result: Inside",2,getHeight()-5);
            }
            else if (results.get(i) == Algorithm.position.BOUNDARY){
                //if boundary, draw boundary with thicker line
                gfx.setStroke(new BasicStroke(3));
                gfx.drawPolygon(polygons[i]);
                gfx.setColor(Color.BLACK);
                gfx.drawString("Result: Boundry",2,getHeight()-5);
            }
            else if (results.get(i) == Algorithm.position.IDENTICAL){
                //if Identical point, draw boundary with thicker line
                gfx.setStroke(new BasicStroke(3));
                gfx.drawPolygon(polygons[i]);
                gfx.setColor(Color.BLACK);
                gfx.drawString("Result: Identical point",2,getHeight()-5);
            }
            else{
                //if outside, draw just lines of polygon
                counter ++;
                gfx.setStroke(new BasicStroke(1));
                gfx.setColor(Color.BLACK);
                gfx.drawPolygon(polygons[i]);
                if (counter == results.size()){
                    gfx.drawString("Result: Outside",2,getHeight()-5);
                }
            }
        }
        //draw a point as a cross
        gfx.setStroke(new BasicStroke(2));
        gfx.setColor(Color.RED);
        gfx.drawLine((int) point.getX()-5,(int) point.getY()-5,(int) point.getX()+5,(int) point.getY()+5);
        gfx.drawLine((int) point.getX()-5,(int) point.getY()+5,(int) point.getX()+5,(int) point.getY()-5);
    }
    // method, which generates two random values, 
    // which represent x and y of a point
    public void generate(){
        for (int i = 0;i<Algorithm.random(4,30);i++){
            int x = Algorithm.random(0,getWidth());
            int y = Algorithm.random(0,getHeight());
            pointsx.add(x);
            pointsy.add(y);
        }
    }
    // class representing new thread 
    public class Worker extends SwingWorker<Void,Void>{
        @Override
        protected Void doInBackground() throws Exception {
            // reset values
            reseter();
            // calling a method, which calls specific algorithm
            // for each polygon in polygon array
            Algorithm.polygons(point,polygons,results,alg);
            return null;
        }

        @Override
         protected void done(){
             //repaint the canvas
             repaint();
         }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // method, which gets coordinates of mouse cursor when clicked
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // create new point from coordinates
        point = new Point2D.Double(evt.getX(),evt.getY());
        // execute new thread 
        worker.execute();
        
        
    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
