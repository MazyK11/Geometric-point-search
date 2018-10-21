package uloha1;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author mazurd
 */
// Algorithm class, where all the counting is happening
public class Algorithm {
    // enum variable
    protected enum position {INSIDE, OUTSIDE, BOUNDARY, IDENTICAL};

    //method, which counts the dot product
    public static double dotProd(double ux,double uy,double vx,double vy){
        return ux*vx + uy*vy;
    }
    // method for counting length of vectors
    public static double len(double ux,double uy){
        return sqrt(ux*ux + uy*uy);
    }
    // method, which counts angle between two vectors 
    public static double angle(double ux, double uy, double vx, double vy){
        double skalarsoucin = dotProd(ux,uy,vx,vy);
        double ulen = len(ux,uy);
        double vlen = len(vx,vy);
        return Math.acos(skalarsoucin/(ulen*vlen));
    }
    // method, which does determinant test
    public static int deter(int x1, int y1, int x2, int y2,
            double x, double y){
        double det = (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1);
        //if determinant is close to 0 (10) it takes it as boundary
        // this interval is really small - need to use decimal numbers, to get there
        if (det <= 100 && det >= -100 ) {
            if (x >= x2 && x <= x1 || x <= x2 && x >= x1){
                if (y >= y2 && y <= y1 || y <= y2 && y >= y1){
                     return 0;
                }
                return 3; 
            }
            return 3;
        }
        else if (det > 0){
            return 1;
        }
        else{
            return 2;
        }
    }
    // method, which calls specific algorithm for each polygon in polygon array
    public static void polygons(Point2D pt, Polygon[] polygons, LinkedList results,
            boolean alg) {
        if (alg) {
            for (int i = 0; i < polygons.length; i++) {
                results.add(windingalg(pt, (Polygon) polygons[i]));
            }
        } else {
            for (int i = 0; i < polygons.length; i++) {
                results.add(rayCrossingAlg(pt, (Polygon) polygons[i]));
            }
        }
    }
    // method, which uses winding algorithm to find out if point lies within
    // polygon or not 
    public static position windingalg(Point2D pt,Polygon p){
        // check if the point is identical with polygons vertex
        for (int i = 0;i< p.npoints;i++){
            if (pt.getX() == p.xpoints[i] && pt.getY() == p.ypoints[i]){
                return position.IDENTICAL;
            }
        }
        // check if the point lies on polygon boundary
        for (int i = 0;i<p.npoints;i++){
            if(deter(p.xpoints[i], p.ypoints[i],
                    p.xpoints[(i+1)%p.npoints], 
                    p.ypoints[(i+1)%p.npoints],pt.getX(),pt.getY()) == 0){
                 return position.BOUNDARY;
            }
        }
        
        double sumAngle = 0;
        final double eps = 0.01;
        //cycle through all points of polygon
        for (int i =0;i<p.npoints;i++){
            // first vector between polygon point and point sought
            double ux = p.xpoints[i] - pt.getX();
            double uy = p.ypoints[i] - pt.getY();
            // second vector between next polygon point and point sought
            double vx = p.xpoints[(i+1)%p.npoints] - pt.getX();
            double vy = p.ypoints[(i+1)%p.npoints] - pt.getY();
            
            // check if point i+i is left or right from line between sought point
            // and point i
            if (deter((int) pt.getX(),(int) pt.getY(),
                   p.xpoints[i],p.ypoints[i],
                   p.xpoints[(i+1)%p.npoints],p.ypoints[(i+1)%p.npoints]) == 1){
                // count angle between two vectors and deduct it to sumAngle
                sumAngle = sumAngle - angle(ux,uy,vx,vy);
            }
            else{
                // count angle between two vectors and add it up to sumAngle
                sumAngle = sumAngle + angle(ux,uy,vx,vy);
            }
        }
        // if the sum value is 2PI or close to 2PI
        if (abs(sumAngle) >= (2*Math.PI - eps)){
            return position.INSIDE;
        }
        else {
            //if not return outside
            return position.OUTSIDE;
        }
    }
    // method, which uses ray crossing algorithm to find out if point lies within
    // polygon or not 
    public static position rayCrossingAlg(Point2D pt, Polygon poly) {
         // check if the point is identical with polygons vertex
        for (int i = 0;i< poly.npoints;i++){
            if (pt.getX() == poly.xpoints[i] && pt.getY() == poly.ypoints[i]){
                return position.IDENTICAL;
            }
        }
        // check if the point lies on polygon boundary
        for (int i = 0;i<poly.npoints;i++){
            if(deter(poly.xpoints[i], poly.ypoints[i],
                    poly.xpoints[(i+1)%poly.npoints], 
                    poly.ypoints[(i+1)%poly.npoints],pt.getX(),pt.getY()) == 0){
                return position.BOUNDARY;
            }
        }
        int count = 0;
        // remember first values
        double xir = poly.xpoints[0] - pt.getX();
        double yir = poly.ypoints[0] - pt.getY();
        //cycle through all points of polygon
        for (int i = 1; i < poly.xpoints.length + 1; i++) {
            // next values from next polygon points
            double xiir = poly.xpoints[i % poly.xpoints.length] - pt.getX();
            double yiir = poly.ypoints[i % poly.xpoints.length] - pt.getY();

            // check if it is upper half
            if ((yiir > 0) && (yir <= 0) || (yir > 0) && (yiir <= 0)) {
                //find intersection
                double xm = (xiir * yir - xir * yiir) / (yiir - yir);
                // if the intersection is positive
                if (xm > 0) {
                    //count intersection
                    count++;
                }
            }
            //remember new values
            xir = xiir;
            yir = yiir;
        }
        // if count of intersection is even
        if (count % 2 == 0) {
            // return outside
            return position.OUTSIDE;
        } else {
            // if not return inside
            return position.INSIDE;
        }

    }
    // method which, makes polygon from generated points
    public static ArrayList<Integer> [] polygonmaker (ArrayList<Integer>pointsx,
            ArrayList<Integer>pointsy){
        boolean crossing = true;
        // cycle until something break through cycle
        while(true){
            //cycle through all points
            for(int m = 0;m<pointsx.size();m++){
                //cycle through all points
                for(int i =0;i<pointsx.size();i++){
                    // the case when i+1 point and m+1 point are actually the 
                    //first points of polygon
                    if (i+1 >= pointsx.size() && m+1 >= pointsx.size()){
                        //calling a method,which finds out if lines would 
                        //intersect or not
                        crossing  = intersect(pointsx.get(m),pointsy.get(m),
                                pointsx.get(0),pointsy.get(0),
                                pointsx.get(i),pointsy.get(i),
                                pointsx.get(0),pointsy.get(0));
                    }
                    // the case when i+1 point is actually the 
                    //first point of polygon
                    else if(i+1 >= pointsx.size()){
                        //calling a method,which finds out if lines would 
                        //intersect or not
                        crossing  = intersect(pointsx.get(m),pointsy.get(m),
                                pointsx.get(m+1),pointsy.get(m+1),
                                pointsx.get(i),pointsy.get(i),
                                pointsx.get(0),pointsy.get(0));
                    }
                    // the case when m+1 point is actually the 
                    //first point of polygon
                    else if (m+1 >= pointsx.size()){
                        //calling a method,which finds out if lines would 
                        //intersect or not
                        crossing  = intersect(pointsx.get(m),pointsy.get(m),
                                pointsx.get(0),pointsy.get(0),
                                pointsx.get(i),pointsy.get(i),
                                pointsx.get(i+1),pointsy.get(i+1));
                    }
                    else{
                        //calling a method,which finds out if lines would 
                        //intersect or not
                        crossing  = intersect(pointsx.get(m),pointsy.get(m),
                                pointsx.get(m+1),pointsy.get(m+1),
                                pointsx.get(i),pointsy.get(i),
                                pointsx.get(i+1),pointsy.get(i+1));
                    }
                    
                    // if polygon lines would intersect 
                    if (crossing == true){
                        // shuffle the lists of points
                        Collections.shuffle(pointsx);
                        Collections.shuffle(pointsy);
                        // jump back to first cycle
                        break;
                    }
                }
                // if polygon lines would intersect 
                if (crossing == true){
                    // jump back to first cycle
                    break;
                }
            }
            // if polygon lines would not intersect
            if(crossing == false){
                //return array of list of points
                ArrayList<Integer> [] a = new ArrayList [2];
                a[0] = pointsx;
                a[1] = pointsy;
                return a;
            }
        }  
    }
    
    //method, which checks if two lines intersect each other or not
    public static boolean intersect(int x1, int y1,int x2,int y2,
            int x3, int y3, int x4, int y4){
        //if end and start points are identical return false, because
        //lines are connected
        if (x1 == x3 && y1 == y3){
            return false;
        }
        else if (x1 == x4 && y1 == y4){
            return false;
        }
        else if(x2 == x3 && y2 == y3){
            return false;
        }
        else if(x2 == x4 && y2 == y4){
            return false;
        }
        return Line2D.linesIntersect(x1,y1,x2,y2,x3,y3,x4,y4);
    }
    //method, which generates random value 
    public static int random(int min, int max){
        int randomNum = ThreadLocalRandom.current().nextInt(min, max);
        return randomNum;
    }
    
    
    
}
