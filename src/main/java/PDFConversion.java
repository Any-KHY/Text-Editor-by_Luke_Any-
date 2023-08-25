import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PDFConversion {

    public static void convertToPDF() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(pdfFilter);
        int result = fileChooser.showSaveDialog(TextEditor.mainFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String outputPath = selectedFile.getAbsolutePath();

            if (!selectedFile.getName().toLowerCase().endsWith(".pdf")) {
                outputPath += ".pdf";
            }
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                PDFont fontType = PDType1Font.HELVETICA; // default font style
                int fontSize = TextEditor.textArea.getTextArea().getFont().getSize();
                Color fontColor = TextEditor.textArea.getTextArea().getForeground();

                PDRectangle mediaBox = page.getMediaBox();
                float margin = fontSize / 2 + 30;
                float y = mediaBox.getHeight() - margin;
                float x = margin;
                float currentY = y;

                contentStreamSetting(contentStream, fontType, fontSize, fontColor, x, y);

                String[] lines = TextEditor.textArea.getTextArea().getText().split("\n");
                for (String line : lines) {
                    float remainingSpace = currentY - margin;
                    float textHeight = fontSize * 1.5f;

                    if (textHeight > remainingSpace) {
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage(); // Start a new page
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStreamSetting(contentStream, fontType, fontSize, fontColor, x, y);
                        currentY = y;
                    }
                    contentStream.showText(line);
                    contentStream.newLine();
                    currentY -= textHeight;
                }
                contentStream.endText();
                contentStream.close();

                document.save(outputPath);
                JOptionPane.showMessageDialog(TextEditor.mainFrame, "Text converted to PDF.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(TextEditor.mainFrame, "Error on saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void contentStreamSetting(PDPageContentStream contentStream, PDFont fontType,
                                             int fontSize, Color fontColor, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setFont(fontType, fontSize);
        contentStream.setNonStrokingColor(fontColor);
        contentStream.setLeading(fontSize * 1.5f);

    }


}
