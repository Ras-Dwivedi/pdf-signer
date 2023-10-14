package com.crubn.ssi.signer;

import com.aspose.pdf.PKCS1;
import com.aspose.pdf.facades.PdfFileSignature;
//import com.spire.pdf.PdfDocument;
//import com.spire.pdf.graphics.PdfFont;
//import com.spire.pdf.graphics.PdfFontFamily;
//import com.spire.pdf.graphics.PdfFontStyle;
//import com.spire.pdf.graphics.PdfImage;
//import com.spire.pdf.security.GraphicMode;
//import com.spire.pdf.security.PdfCertificate;
//import com.spire.pdf.security.PdfCertificationFlags;
//import com.spire.pdf.security.PdfSignature;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PdfSign {
    public static void AddPdfFileSignature() {
        PdfFileSignature pdfSign = new PdfFileSignature();
        pdfSign.bindPdf("whitepaper.pdf");

        // Create a rectangle for signature location
        java.awt.Rectangle rect = new java.awt.Rectangle(10, 10, 200, 50);
        // Set signature appearance
//        pdfSign.setSignatureAppearance("ignou_logo.png");

        // Create any of the three signature types
//        PKCS1 signature = new PKCS1("cert/crubn.pfx", "1234"); // PKCS#1
          PKCS1 signature = new PKCS1("self-signed.pfx", "12345678"); // PKCS#1

        pdfSign.sign(1, "I'm document author", "vbnegi@ignou.ac.in", "New Delhi", true, rect,
                signature);
        // Save output PDF file
        pdfSign.save("DigitallySign.pdf");
    }


    public static void main(String[] args) {
        AddPdfFileSignature();

//        //Load a pdf document
//        PdfDocument doc = new PdfDocument();
//        doc.loadFromFile("C:\\Users\\Administrator\\Desktop\\Moon.pdf");
//
//        //Load the certificate
//        PdfCertificate cert = new PdfCertificate("F:\\pfx\\output\\Test.pfx", "111");
//
//        //Create a PdfSignature object and specify its position and size
//        PdfSignature signature = new PdfSignature(doc, doc.getPages().get(0), cert, "MySignature");
//        Rectangle2D rect = new Rectangle2D.Float();
//        rect.setFrame(new Point2D.Float((float) doc.getPages().get(0).getActualSize().getWidth() - 380, (float) doc.getPages().get(0).getActualSize().getHeight() - 120), new Dimension(250, 150));
//        signature.setBounds(rect);
//
//        //Set the graphics mode
//        signature.setGraphicMode(GraphicMode.Sign_Image_And_Sign_Detail);
//
//        //Set the signature content
//        signature.setNameLabel("Signer:");
//        signature.setName("Jessie");
//        signature.setContactInfoLabel("ContactInfo:");
//        signature.setContactInfo("xxxxxxxxx");
//        signature.setDateLabel("Date:");
//        signature.setDate(new java.util.Date());
//        signature.setLocationInfoLabel("Location:");
//        signature.setLocationInfo("Florida");
//        signature.setReasonLabel("Reason: ");
//        signature.setReason("The certificate of this document");
//        signature.setDistinguishedNameLabel("DN: ");
//        signature.setDistinguishedName(signature.getCertificate().get_IssuerName().getName());
//        signature.setSignImageSource(PdfImage.fromFile("C:\\Users\\Administrator\\Desktop\\cert.jpg"));
//
//        //Set the signature font
//        signature.setSignDetailsFont(new PdfFont(PdfFontFamily.Helvetica, 10f, PdfFontStyle.Bold));
//
//        //Set the document permission
//        signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
//        signature.setCertificated(true);
//
//        //Save to file
//        doc.saveToFile("AddSignature.pdf");
//        doc.close();
    }
}