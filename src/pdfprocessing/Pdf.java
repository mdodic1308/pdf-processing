/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfprocessing;

import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author RSMXD001
 */
public class Pdf {

    private final PdfReader reader;
    public Pdf(File listOfPDF) throws IOException{
        reader = new PdfReader(listOfPDF.getAbsolutePath());
    }
}
