package uloha1;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mazurd
 */
// class representing GUI
public class GUI extends javax.swing.JFrame {
    // variables
    protected GenerateWorker wo;
    protected JFileChooser choose;
    protected BufferedReader br;
    protected boolean loadOrGenerate;
    
    // constructor
    public GUI() {
        initComponents();
        wo = new GenerateWorker();
        choose = new JFileChooser();
        //setting File filter for txt files
        choose.setFileFilter(new FileNameExtensionFilter("","txt"));
        CB.setSelectedIndex(0);
        loadOrGenerate = false;
    }
    
    // class representing new thread 
    public class GenerateWorker extends SwingWorker<Void,Void>{
        @Override
        protected Void doInBackground() throws Exception {
            if (loadOrGenerate == true){
                // generate points and make polygon from them
                drawPanel1.reseter();
                drawPanel1.generate();
                ArrayList<Integer> [] a = Algorithm.polygonmaker(drawPanel1.pointsx,
                        drawPanel1.pointsy);
                drawPanel1.pointsx = a[0];
                drawPanel1.pointsy = a[1];
                drawPanel1.makePolygonFrompPoints();
            }
            else{
                try{
                    // load a file
                    br = new BufferedReader(new FileReader(choose.getSelectedFile()));
                    // first row is number of polygons
                    drawPanel1.polygons = new Polygon [Integer.parseInt(br.readLine())];
                    String str;
                    int v = 0;
                    // reading a lines
                    while ((str = br.readLine()) != null){
                        // fill x with first line
                        int [] x = split(str);
                        str = br.readLine();
                        // fill y with second line
                        int [] y = split(str);
                        // make polygon from 2 lines
                        drawPanel1.polygons[v] = new Polygon(x,y,x.length);
                        v++;
                    }
                    // clsoe the file
                    br.close();
                    // message for user
                    message.setText("File choosen");
                }
                catch(IOException e){
                    // message for user
                    message.setText("Failed to load the file");
                }
            }
            return null;
        }

        @Override
         protected void done(){
            //repaint the canvas
            drawPanel1.repaint();
         }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        drawPanel1 = new uloha1.DrawPanel();
        jPanel1 = new javax.swing.JPanel();
        Insert = new javax.swing.JLabel();
        Labelx = new javax.swing.JLabel();
        x = new javax.swing.JTextField();
        Labely = new javax.swing.JLabel();
        y = new javax.swing.JTextField();
        Button = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        polygongen = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        predef = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        loadbut = new javax.swing.JButton();
        message = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        CB = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout drawPanel1Layout = new javax.swing.GroupLayout(drawPanel1);
        drawPanel1.setLayout(drawPanel1Layout);
        drawPanel1Layout.setHorizontalGroup(
            drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawPanel1Layout.setVerticalGroup(
            drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );

        jPanel1.setLayout(new java.awt.GridBagLayout());

        Insert.setText("Insert point coordinates:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 54, 0, 0);
        jPanel1.add(Insert, gridBagConstraints);

        Labelx.setText("coordinate x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 0);
        jPanel1.add(Labelx, gridBagConstraints);

        x.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 10, 0, 0);
        jPanel1.add(x, gridBagConstraints);

        Labely.setText("coordinate y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 18, 0, 0);
        jPanel1.add(Labely, gridBagConstraints);

        y.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 4, 0, 0);
        jPanel1.add(y, gridBagConstraints);

        Button.setText("Draw point");
        Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 62;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 18, 0, 213);
        jPanel1.add(Button, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        polygongen.setText("Generate a random polygon");
        polygongen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polygongenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 47, 0, 0);
        jPanel2.add(polygongen, gridBagConstraints);

        jLabel1.setText("or");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 4, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        predef.setText("Use predefined polygons");
        predef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                predefActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        jPanel2.add(predef, gridBagConstraints);

        jLabel2.setText("or");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(25, 10, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        loadbut.setText("Load a file...");
        loadbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadbutActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 78;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 4, 0, 0);
        jPanel2.add(loadbut, gridBagConstraints);

        message.setText("No file choosen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 39;
        gridBagConstraints.ipady = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 92);
        jPanel2.add(message, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        CB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Winding algorithm", "Ray crossing algorithm" }));
        CB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 187;
        gridBagConstraints.ipady = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 44, 0, 482);
        jPanel3.add(CB, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(drawPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // method, which creates point from given x and y coordinates when draw point
    // button is pressed
    private void ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonActionPerformed
        this.drawPanel1.point = new Point2D.Double(Double.parseDouble(x.getText()),
                Double.parseDouble(y.getText()));
        // execute new thread
        this.drawPanel1.worker.execute();
    }//GEN-LAST:event_ButtonActionPerformed
    // method, which executes new thread for generating polygon when "generate
    // a random polygon" button is pressed
    private void polygongenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polygongenActionPerformed
        this.drawPanel1.polygons = new Polygon[1];
        this.drawPanel1.point = new Point2D.Double(-10.0,-10.0);
        this.drawPanel1.pointsx.clear();
        this.drawPanel1.pointsy.clear();
        loadOrGenerate = true;
        wo.execute();
        wo = new GenerateWorker();
        
    }//GEN-LAST:event_polygongenActionPerformed
    //method, which makes predefined polygons, when "Use predefined polygons" 
    // button is pressed
    private void predefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_predefActionPerformed
        this.drawPanel1.reseter();
        this.drawPanel1.polygons = new Polygon [2];
        this.drawPanel1.point = new Point2D.Double(-10.0,-10.0);
        this.drawPanel1.polygons[0] = new Polygon(new int[]{10,110,150,110,50},new int [] {10,10,100,200,200},5);
        this.drawPanel1.polygons[1] = new Polygon(new int[]{150,170,215,80,110},new int [] {100,80,150,250,200},5);
        this.drawPanel1.repaint();
    }//GEN-LAST:event_predefActionPerformed
    // method, which makes polygon(s) from .txt file, when user choose a right .txt
    // file using "Load a file.." button
    private void loadbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadbutActionPerformed
        int returnvalue = choose.showOpenDialog(this);
        if (returnvalue == JFileChooser.APPROVE_OPTION){
            // reset values
            this.drawPanel1.reseter();
            this.drawPanel1.point = new Point2D.Double(-10.0,-10.0);
            loadOrGenerate = false;
            wo.execute();
            wo = new GenerateWorker();
        }
        else{   
            // message for user
            message.setText("No file choosen");
        }
    }//GEN-LAST:event_loadbutActionPerformed
    // method, which splits lines to single strings and parses them to integer
    public int []split(String str){
        String [] items;
        items = str.split(",");
        int x [] = new int[items.length];
        //parse strings
        x = parse(items,x);
        return x;
    }
    // method, which parses String to integer
    public int[] parse(String []items,int []p){
        for(int i = 0;i < items.length;i++){
            p[i] = Integer.parseInt(items[i]);   
        }
        return p;
    }
    // method which, sets choosen algorithm, when combobox value is changed
    private void CBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CBActionPerformed
        String a = (String) CB.getSelectedItem();
        if (a == "Winding algorithm")
            this.drawPanel1.alg = true;
        else
            this.drawPanel1.alg = false;
    }//GEN-LAST:event_CBActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GUI cp = new GUI();
                // set title
                cp.setTitle("Geometric point search");
                cp.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button;
    private javax.swing.JComboBox<String> CB;
    private javax.swing.JLabel Insert;
    private javax.swing.JLabel Labelx;
    private javax.swing.JLabel Labely;
    private uloha1.DrawPanel drawPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton loadbut;
    private javax.swing.JLabel message;
    private javax.swing.JToggleButton polygongen;
    private javax.swing.JButton predef;
    private javax.swing.JTextField x;
    private javax.swing.JTextField y;
    // End of variables declaration//GEN-END:variables
}
