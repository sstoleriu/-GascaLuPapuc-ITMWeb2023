package ro.gascalupapuc.EcoShare.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.gascalupapuc.EcoShare.model.dto.ReportDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseReportAndroidDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseReportDTO;
import ro.gascalupapuc.EcoShare.rest.service.ReportService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/{idUser}")
    ResponseEntity<ResponseReportDTO> createReport(@PathVariable Integer idUser, @RequestBody ReportDTO reportDTO){
        return ResponseEntity.ok(reportService.createReport(idUser,reportDTO));
    }

    @GetMapping("/resolve/{idReport}")
    ResponseEntity<ResponseReportDTO> resolveReport(@PathVariable Integer idReport){
        return ResponseEntity.ok(reportService.resolveReport(idReport));
    }

    @GetMapping("/allReports/{idOperator}")
    ResponseEntity<List<ResponseReportDTO>> getReportsByOperator(@PathVariable Integer idOperator){
        return ResponseEntity.ok(reportService.getReportsByOperator(idOperator));
    }

    @GetMapping("/allReports/user/{idUser}")
    ResponseEntity<List<ResponseReportDTO>> getReportsByUser(@PathVariable Integer idUser){
        return ResponseEntity.ok(reportService.getReportsByUser(idUser));
    }

    @GetMapping("/allReports/android/user/{idUser}")
    ResponseEntity<List<ResponseReportAndroidDTO>> getReportsByAndroid(@PathVariable Integer idUser){
        return ResponseEntity.ok(reportService.getReportsByAndroid(idUser));
    }
}
