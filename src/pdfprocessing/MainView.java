/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfprocessing;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.itextpdf.text.Rectangle;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;


/**
 *
 * @author RSMXD001
 */
public class MainView extends javax.swing.JFrame {

    private final JFileChooser openFileChooser;
    private File fileDir;
    private File[] listOfPDFs;

    
    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
        openFileChooser = new JFileChooser();
        //openFileChooser.setCurrentDirectory(new File("c:\\"));
      //  openFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        openFileChooser.setAcceptAllFileFilterUsed(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnOpenFeeder = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnOpenFeeder.setText("Find folder with pdf-s");
        btnOpenFeeder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenFeederActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel3.setText("<html>Instructions: Click on button \"Find folder with pdf-s\" and choose whatever file from folder with pdf-s. Program will create \"output.csv\" and \"listOfEncypedFiles.txt\" files in choosen folder</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(btnOpenFeeder, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOpenFeeder)
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenFeederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenFeederActionPerformed

        jLabel2.setVisible(false);
        int returnValue = openFileChooser.showOpenDialog(this);
        if (returnValue==JFileChooser.APPROVE_OPTION){        
            fileDir=openFileChooser.getCurrentDirectory();
            jLabel1.setText("Choosen folder: "+fileDir.getAbsolutePath());
            if (fileDir.isDirectory()) {
                listOfPDFs = fileDir.listFiles(new FilenameFilter()
            {
                // use anonymous inner class 
                @Override
                public boolean accept(File dir, String name)
                {
                    return name.toLowerCase().endsWith(".pdf");
                }
            });
             
                String csvPath=fileDir.getAbsolutePath()+"/output.csv";
            //    String csvPath=fileDir.getAbsolutePath()+"/output.tab";
                File file = new File(csvPath); 
                FileWriter outputfile = null;
                try {
                    // create FileWriter object with file as parameter
                    outputfile = new FileWriter(file);
                    // create CSVWriter object filewriter object as parameter 
                    CSVWriter writer = new CSVWriter(outputfile); 
                    // adding header to csv 
                    String[] header = { "Date1", "Date2", "Title", "Author Name","text" }; 
                
                    writer.writeNext(header); 
                    PrintWriter encryptedFiles = new PrintWriter(new FileOutputStream(fileDir.getAbsolutePath()+"/listOfEncryptedFiles.txt"));
                    for (File listOfPDF : listOfPDFs) {
                        String text;
                        String date="";
                        PdfReader reader = new PdfReader(listOfPDF.getAbsolutePath());
                        //    PrintWriter headerAndFooter = new PrintWriter(new FileOutputStream(fileDir.getAbsolutePath()+"/header"+i+".txt"));
                        
                        Rectangle rect = new Rectangle(25, 40, 550, 650);
                        Rectangle rectHeader=new Rectangle(1, 650, 600, 800);
                        RenderFilter filter = new RegionTextRenderFilter(rect);
                        RenderFilter filterHeader = new RegionTextRenderFilter(rectHeader);
                        TextExtractionStrategy strategy;
                        TextExtractionStrategy strategyHeader;
                        StringBuilder text2=new StringBuilder();
                        int j=1;
                        strategyHeader = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filterHeader);
                        //headerAndFooter.println(PdfTextExtractor.getTextFromPage(reader, j, strategyHeader));
                        String date2=PdfTextExtractor.getTextFromPage(reader, j, strategyHeader);
                        for (j = 1; j <= reader.getNumberOfPages(); j++) {
                            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                            text2.append(PdfTextExtractor.getTextFromPage(reader, j, strategy));
                            //out.println(PdfTextExtractor.getTextFromPage(reader, j, strategy));
                            
                        }       reader.close();
                        PDDocument document;
                        try {
                            document = PDDocument.load(listOfPDF);
                            if (!document.isEncrypted()) {
                                PDDocumentInformation info = document.getDocumentInformation();
//                              System.out.println( "Page Count=" + document.getNumberOfPages() );
//                              System.out.println( "Title=" + info.getTitle() );
//                              System.out.println( "Author=" + info.getAuthor() );
//                              System.out.println( "Subject=" + info.getSubject() );
//                              System.out.println( "Keywords=" + info.getKeywords() );
//                              System.out.println( "Creator=" + info.getCreator() );
//                              System.out.println( "Producer=" + info.getProducer() );
//                              System.out.println( "Creation Date=" + info.getCreationDate().getTime());
//                              System.out.println( "Modification Date=" + info.getModificationDate());
//                              System.out.println( "Trapped=" + info.getTrapped() );

                                PDFTextStripper stripper = new PDFTextStripper();
                                text = stripper.getText(document);
                                //text=text2.toString();
                                String [] data=new String[5];
                                date=findDate(date2);
                                data[0]=date;
                                data[1]=info.getCreationDate().getTime().toString();
                                data[2]=info.getTitle();
                                data[3]=info.getAuthor();
                                data[4]="\""+followCVSformat(textFilter(text))+"\"";
                                writer.writeNext(data);
                            } else {
                                encryptedFiles.write(listOfPDF.getName() + "\n");
                            }
                            document.close();
                        }catch (IOException ex) { 
                            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }     
                    // closing writer connection
                    writer.close();
                    encryptedFiles.close();
                    jLabel2.setVisible(true);
                    jLabel2.setText("It is done!");
                } catch (IOException ex) {
                    Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                    jLabel2.setText("Unsuccessfull!");
                }
            } else {
                jLabel1.setText("The selected file is not a folder. Try again!");
            }
        }
        else {
            jLabel1.setText("No file chosen!");
        }
    }//GEN-LAST:event_btnOpenFeederActionPerformed

    private static String textFilter(String text) {
        StringBuilder sb=new StringBuilder();
        String[] pom = text.split("\r\n|\r|\n");
        String before="";
        for (String p: pom ){
            if (p.length()>60) sb.append(p+"\n");
            else if (before.length()>60) sb.append(p+"\n");
            before=p;
        }
        return sb.toString();
    }
    
    
    
    private static String followCVSformat(String value) {

        String result = value;
//        if (result.contains("\"")) {
//            result = result.replace("\"", "\"\"");
//        }
//        if (result.contains(",")) {
//            result = result.replace(",", " ");
//        }
//        if (result.contains("\n")) {
//            result = result.replace("\n", "");
//        }
//        if (result.contains("\r")) {
//            result = result.replace("\r", " ");
//        }
//        if (result.contains("\r\n")) {
//            result = result.replace("\r\n", " ");
//        }
//        if (result.contains("%n")) {
//            result = result.replace("%n", " ");
//        }
//        
        return result;

    }
    
    
    public String findDate(String text){

        StringBuilder sb=new StringBuilder();
        for (int i=0; i<text.length();i++) {
            if (Character.isDigit(text.charAt(i))) sb.append(text.charAt(i));
            else sb.append(" ");
        }
        String pom=sb.toString();

        String year="";
        int pos=pom.lastIndexOf("20"); 
        
            
            // find all occurrences forward
            int i=-1;
            while (true) {
                i=pom.indexOf("20",i+1);
                if ( i!=-1 && Character.isDigit(pom.charAt(i+2)) && Character.isDigit(pom.charAt(i+3)) && pom.length()>i+3) year="20"+pom.charAt(i+2)+pom.charAt(i+3);
                if (i==-1) break;
            }
        String month="";
        text=text.toLowerCase();
        if (text.contains("january")) month="January";
        if (text.contains("february")) month="February";
        if (text.contains("march")) month="March";
        if (text.contains("april")) month="April";
        if (text.contains("may")) month="May";
        if (text.contains("june")) month="June";
        if (text.contains("july")) month="July";
        if (text.contains("august")) month="August";
        if (text.contains("september")) month="September";
        if (text.contains("october")) month="October";
        if (text.contains("november")) month="November";
        if (text.contains("december")) month="December";
        
        return month+", "+year;
    }
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
                
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOpenFeeder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
