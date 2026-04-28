package lk.ijse._2_back_end.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lk.ijse._2_back_end.dto.DashboardResponse;
import lk.ijse._2_back_end.entity.Claim;
import lk.ijse._2_back_end.entity.Payment;
import lk.ijse._2_back_end.entity.Policy;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PdfReportUtil {

    // ── Brand colours ────────────────────────────────────────────────────
    private static final BaseColor PRIMARY   = new BaseColor(37,  99, 235);   // blue
    private static final BaseColor SUCCESS   = new BaseColor(40, 167,  69);   // green
    private static final BaseColor WARNING   = new BaseColor(245, 158, 11);   // amber
    private static final BaseColor DANGER    = new BaseColor(220,  53,  69);  // red
    private static final BaseColor LIGHT_ROW = new BaseColor(245, 247, 250);
    private static final BaseColor HEADER_BG = new BaseColor(30,  41,  59);   // dark slate

    // ── Fonts ─────────────────────────────────────────────────────────────
    private static final Font TITLE_FONT    = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD,   BaseColor.WHITE);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.WHITE);
    private static final Font SECTION_FONT  = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD,   HEADER_BG);
    private static final Font COL_FONT      = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   BaseColor.WHITE);
    private static final Font CELL_FONT     = new Font(Font.FontFamily.HELVETICA, 9,  Font.NORMAL, HEADER_BG);
    private static final Font LABEL_FONT    = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   HEADER_BG);
    private static final Font VALUE_FONT    = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(55, 65, 81));
    private static final Font FOOTER_FONT   = new Font(Font.FontFamily.HELVETICA, 8,  Font.ITALIC, BaseColor.GRAY);

    private static final DateTimeFormatter DT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // ═════════════════════════════════════════════════════════════════════
    //  1. DASHBOARD / SUMMARY REPORT
    // ═════════════════════════════════════════════════════════════════════

    public byte[] generateDashboardReport(DashboardResponse r) throws DocumentException {
        Document doc = new Document(PageSize.A4, 40, 40, 60, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        addPageEvents(writer);
        doc.open();

        addHeader(doc, "VIMS – System Summary Report", "Full KPI overview  |  " + r.getGeneratedAt());
        doc.add(Chunk.NEWLINE);

        addSectionTitle(doc, "Policy Breakdown");
        addTwoColStats(doc, new String[][]{
                {"Total Policies",     str(r.getTotalPolicies())},
                {"Active",            str(r.getActivePolicies())},
                {"Pending Approval",  str(r.getPendingPolicies())},
                {"Suspended",         str(r.getSuspendedPolicies())},
                {"Expired",           str(r.getExpiredPolicies())},
                {"Rejected",          str(r.getRejectedPolicies())}
        });

        doc.add(Chunk.NEWLINE);
        addSectionTitle(doc, "Claims Breakdown");
        addTwoColStats(doc, new String[][]{
                {"Total Claims",    str(r.getTotalClaims())},
                {"Submitted",       str(r.getSubmittedClaims())},
                {"Under Review",    str(r.getUnderReviewClaims())},
                {"Approved",        str(r.getApprovedClaims())},
                {"Rejected",        str(r.getRejectedClaims())},
                {"Settled",         str(r.getSettledClaims())}
        });

        doc.add(Chunk.NEWLINE);
        addSectionTitle(doc, "Financial Summary");
        addTwoColStats(doc, new String[][]{
                {"Total Revenue Collected",  "LKR " + r.getTotalRevenue()},
                {"Total Pending Amount",     "LKR " + r.getTotalPendingAmount()},
                {"Total Penalty Collected",  "LKR " + r.getTotalPenaltyCollected()},
                {"Overdue Payments",         str(r.getOverduePayments())},
                {"Paid Payments Count",      str(r.getPaidPayments())}
        });

        doc.add(Chunk.NEWLINE);
        addSectionTitle(doc, "System Overview");
        addTwoColStats(doc, new String[][]{
                {"Total Customers",     str(r.getTotalCustomers())},
                {"Active Customers",    str(r.getActiveCustomers())},
                {"Suspended Customers", str(r.getSuspendedCustomers())},
                {"Total Vehicles",      str(r.getTotalVehicles())}
        });

        if (r.getPoliciesByType() != null && !r.getPoliciesByType().isEmpty()) {
            doc.add(Chunk.NEWLINE);
            addSectionTitle(doc, "Policies by Type");
            String[][] rows = r.getPoliciesByType().entrySet().stream()
                    .map(e -> new String[]{e.getKey().replace("_", " "), str(e.getValue())})
                    .toArray(String[][]::new);
            addTwoColStats(doc, rows);
        }

        doc.close();
        return out.toByteArray();
    }

    // ═════════════════════════════════════════════════════════════════════
    //  2. POLICIES REPORT
    // ═════════════════════════════════════════════════════════════════════

    public byte[] generatePoliciesReport(List<Policy> policies) throws DocumentException {
        Document doc = new Document(PageSize.A4.rotate(), 40, 40, 60, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        addPageEvents(writer);
        doc.open();

        addHeader(doc, "VIMS – Policies Report",
                "Total records: " + policies.size() + "  |  " + DashboardResponse.now());
        doc.add(Chunk.NEWLINE);

        float[] cols = {50, 90, 100, 120, 70, 80, 80, 100};
        PdfPTable table = baseTable(cols);
        addHeaderRow(table, new String[]{"#", "Policy No.", "Customer", "Vehicle", "Type", "Status", "Premium (LKR)", "Valid Until"});

        boolean alt = false;
        int i = 1;
        for (Policy p : policies) {
            BaseColor bg = alt ? LIGHT_ROW : BaseColor.WHITE;
            addRow(table, bg,
                    str(i++),
                    safe(p.getPolicyNumber()),
                    safe(p.getUser() != null ? p.getUser().getFullName() : "-"),
                    safe(p.getVehicle() != null ? p.getVehicle().getChassisNumber() : "-"),
                    safe(p.getPolicyType()),
                    safe(p.getStatus()),
                    safe(p.getPremiumAmount()),
                    p.getEndDate() != null ? p.getEndDate().toString() : "-"
            );
            alt = !alt;
        }
        doc.add(table);
        doc.close();
        return out.toByteArray();
    }

    // ═════════════════════════════════════════════════════════════════════
    //  3. CLAIMS REPORT
    // ═════════════════════════════════════════════════════════════════════

    public byte[] generateClaimsReport(List<Claim> claims) throws DocumentException {
        Document doc = new Document(PageSize.A4.rotate(), 40, 40, 60, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        addPageEvents(writer);
        doc.open();

        addHeader(doc, "VIMS – Claims Report",
                "Total records: " + claims.size() + "  |  " + DashboardResponse.now());
        doc.add(Chunk.NEWLINE);

        float[] cols = {40, 90, 100, 80, 80, 100, 90, 110};
        PdfPTable table = baseTable(cols);
        addHeaderRow(table, new String[]{"#", "Claim No.", "Customer", "Type", "Status", "Claimed (LKR)", "Date Filed", "Description"});

        boolean alt = false;
        int i = 1;
        for (Claim c : claims) {
            BaseColor bg = alt ? LIGHT_ROW : BaseColor.WHITE;
            addRow(table, bg,
                    str(i++),
                    safe(c.getClaimId()),
                    safe(c.getPolicy() != null && c.getPolicy().getUser() != null
                            ? c.getPolicy().getUser().getFullName() : "-"),
                    safe(c.getClaimId()),
                    safe(c.getClaimStatus()),
                    safe(c.getClaimAmount()),
                    c.getClaimDate() != null ? c.getClaimDate().toString() : "-",
                    truncate(safe(c.getDescription()), 40)
            );
            alt = !alt;
        }
        doc.add(table);
        doc.close();
        return out.toByteArray();
    }

    // ═════════════════════════════════════════════════════════════════════
    //  4. PAYMENTS REPORT
    // ═════════════════════════════════════════════════════════════════════

    public byte[] generatePaymentsReport(List<Payment> payments) throws DocumentException {
        Document doc = new Document(PageSize.A4.rotate(), 40, 40, 60, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        addPageEvents(writer);
        doc.open();

        addHeader(doc, "VIMS – Payments Report",
                "Total records: " + payments.size() + "  |  " + DashboardResponse.now());
        doc.add(Chunk.NEWLINE);

        float[] cols = {40, 90, 100, 80, 80, 80, 100, 100};
        PdfPTable table = baseTable(cols);
        addHeaderRow(table, new String[]{"#", "Payment Ref.", "Customer", "Status", "Amount (LKR)", "Penalty (LKR)", "Due Date", "Paid Date"});

        boolean alt = false;
        int i = 1;
        for (Payment p : payments) {
            BaseColor bg = alt ? LIGHT_ROW : BaseColor.WHITE;
            String user = (p.getPolicy() != null && p.getPolicy().getVehicle() != null)
                    ? p.getPolicy().getVehicle().getFuelType() : "-";
            addRow(table, bg,
                    str(i++),
                    safe(p.getPaymentStatus()),
                    user,
                    safe(p.getPaymentStatus()),
                    safe(p.getAmount()),
                    p.getAmount() != null ? p.getAmount().toString() : "0.00",
                    p.getDueDate() != null ? p.getDueDate().toString() : "-",
                    p.getPaidDate() != null ? p.getPaidDate().toString() : "-"
            );
            alt = !alt;
        }
        doc.add(table);
        doc.close();
        return out.toByteArray();
    }

    // ═════════════════════════════════════════════════════════════════════
    //  PRIVATE HELPERS
    // ═════════════════════════════════════════════════════════════════════

    private void addHeader(Document doc, String title, String subtitle) throws DocumentException {
        PdfPTable banner = new PdfPTable(1);
        banner.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(HEADER_BG);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(18);

        Paragraph p = new Paragraph();
        p.add(new Chunk(title + "\n", TITLE_FONT));
        p.add(new Chunk(subtitle, SUBTITLE_FONT));
        cell.addElement(p);
        banner.addCell(cell);
        doc.add(banner);
    }

    private void addSectionTitle(Document doc, String text) throws DocumentException {
        Paragraph p = new Paragraph(text, SECTION_FONT);
        p.setSpacingBefore(8);
        p.setSpacingAfter(4);
        doc.add(p);

        // underline
        LineSeparator line = new LineSeparator(1.5f, 100, PRIMARY, Element.ALIGN_LEFT, -2);
        doc.add(new Chunk(line));
        doc.add(Chunk.NEWLINE);
    }

    /** Two-column key/value stat table */
    private void addTwoColStats(Document doc, String[][] rows) throws DocumentException {
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(70);
        t.setHorizontalAlignment(Element.ALIGN_LEFT);
        t.setWidths(new float[]{3f, 2f});

        boolean alt = false;
        for (String[] row : rows) {
            BaseColor bg = alt ? LIGHT_ROW : BaseColor.WHITE;
            PdfPCell label = new PdfPCell(new Phrase(row[0], LABEL_FONT));
            PdfPCell value = new PdfPCell(new Phrase(row[1], VALUE_FONT));
            for (PdfPCell c : new PdfPCell[]{label, value}) {
                c.setBackgroundColor(bg);
                c.setBorderColor(new BaseColor(220, 220, 220));
                c.setPadding(6);
            }
            value.setHorizontalAlignment(Element.ALIGN_RIGHT);
            t.addCell(label);
            t.addCell(value);
            alt = !alt;
        }
        doc.add(t);
    }

    private PdfPTable baseTable(float[] widths) throws DocumentException {
        PdfPTable t = new PdfPTable(widths.length);
        t.setWidthPercentage(100);
        t.setWidths(widths);
        t.setSpacingBefore(6);
        return t;
    }

    private void addHeaderRow(PdfPTable table, String[] headers) {
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, COL_FONT));
            cell.setBackgroundColor(PRIMARY);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private void addRow(PdfPTable table, BaseColor bg, String... values) {
        for (String v : values) {
            PdfPCell cell = new PdfPCell(new Phrase(v == null ? "-" : v, CELL_FONT));
            cell.setBackgroundColor(bg);
            cell.setBorderColor(new BaseColor(220, 220, 220));
            cell.setPadding(6);
            table.addCell(cell);
        }
    }

    private void addPageEvents(PdfWriter writer) {
        writer.setPageEvent(new PdfPageEventHelper() {
            @Override
            public void onEndPage(PdfWriter w, Document d) {
                PdfContentByte cb = w.getDirectContent();
                Phrase footer = new Phrase(
                        "VIMS – Vehicle Insurance Management System  |  Page " + w.getPageNumber(),
                        FOOTER_FONT);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
                        (d.left() + d.right()) / 2, d.bottom() - 10, 0);
            }
        });
    }

    // ── tiny helpers ──────────────────────────────────────────────────────
    private String str(long v)   { return String.valueOf(v); }
    private String str(int v)    { return String.valueOf(v); }
    private String safe(Object o){ return o == null ? "-" : o.toString(); }
    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }
}