package lk.ijse._2_back_end.service;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lk.ijse._2_back_end.dto.ReportDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generateReportPdf(ReportDTO report) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (PdfDocument pdf = new PdfDocument(new PdfWriter(out));
             Document   doc = new Document(pdf)) {

            doc.add(new Paragraph("VIMS — Admin Report")
                    .setBold().setFontSize(18));
            doc.add(new Paragraph("─────────────────────────"));

            doc.add(new Paragraph("Total Policies   : " + report.getTotalPolicies()));
            doc.add(new Paragraph("Active Policies  : " + report.getActivePolicies()));
            doc.add(new Paragraph("Suspended        : " + report.getSuspendedPolicies()));
            doc.add(new Paragraph("Total Revenue    : LKR " + report.getTotalRevenue()));
            doc.add(new Paragraph("Pending Claims   : " + report.getPendingClaims()));
            doc.add(new Paragraph("Approved Claims  : " + report.getApprovedClaims()));
        }

        return out.toByteArray();
    }
}
