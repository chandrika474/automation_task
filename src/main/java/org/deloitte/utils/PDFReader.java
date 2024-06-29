package org.deloitte.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFReader {
    private static LoggerReport logger = LoggerReport.getLogger(PDFReader.class);

    public static String readPDFFile(String filePath) {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            if (!document.isEncrypted()) {
                PDFTextStripper pdfStripper = new PDFTextStripper();
                return pdfStripper.getText(document);
            } else {
                logger.error("The document is encrypted and cannot be read.");
            }
        } catch (IOException e) {
            logger.error("Exception occurred while reading the file.", e);
        }
        return null;
    }
}
