package lk.ijse._2_back_end.controller;

import lk.ijse._2_back_end.dto.ReportDTO;
import lk.ijse._2_back_end.service.PdfService;
import lk.ijse._2_back_end.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/report")
@CrossOrigin
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final PdfService pdfService;

    // GET /api/v1/report dashboard data
    @GetMapping
    public ResponseEntity<ReportDTO> getReport() {
        return ResponseEntity.ok(reportService.generateReport());
    }

    // GET /api/v1/report/download PDF
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        ReportDTO report   = reportService.generateReport();
        byte[]    pdfBytes = pdfService.generateReportPdf(report);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=VIMS_Report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}