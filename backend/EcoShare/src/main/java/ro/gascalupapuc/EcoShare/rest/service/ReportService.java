package ro.gascalupapuc.EcoShare.rest.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.gascalupapuc.EcoShare.model.*;
import ro.gascalupapuc.EcoShare.model.dto.ReportDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseReportAndroidDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseReportDTO;
import ro.gascalupapuc.EcoShare.model.dto.ResponseUserDTO;
import ro.gascalupapuc.EcoShare.rest.repository.CharacteristicsRepository;
import ro.gascalupapuc.EcoShare.rest.repository.OperatorRepository;
import ro.gascalupapuc.EcoShare.rest.repository.ReportRepository;
import ro.gascalupapuc.EcoShare.rest.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CharacteristicsService characteristicsService;
    private final OperatorRepository operatorRepository;

    private final CharacteristicsRepository characteristicsRepository;
    public ResponseReportDTO createReport(Integer idUser, ReportDTO reportDTO){
         User user = userRepository.findById(idUser).orElseThrow(); //TODO USER NOT FOUND

        var carac = reportDTO.getCharacteristics();

        characteristicsRepository.save(reportDTO.getCharacteristics());

        Report report = Report.builder()
                 .category(reportDTO.getCategory())
                 .description(reportDTO.getDescription())
                 .characteristics(reportDTO.getCharacteristics())
                 .createDate(LocalDateTime.now())
                 .isResolve(false)
                 .createByUser(user)
                 .photoBase46(reportDTO.getBitmap())
                 .isAnon(reportDTO.getIsAnon())
                 .lat(reportDTO.getLat())
                 .lon(reportDTO.getLon())
                 .build();
         carac.setReport(report);
         assignReport(report);

         return mapEntityToResponseDTO(report);
    }

    public ResponseUserDTO assignReport(Report report){

        List<Operator> listOfOp = operatorRepository.findAll();

        List<Operator> listOfOperator = listOfOp.stream()
                .filter(u -> u.getRole().equals(Role.OPERATOR))
                .collect(Collectors.toList());

        List<Report> listOfReport = listOfOperator.stream()
                .map(Operator::getListOfReports)
                .min(Comparator.comparing(List::size))
                .get();

        Operator operator =  listOfOperator.stream()
                .filter(u -> u.getListOfReports().containsAll(listOfReport))
                .findFirst()
                .get();

        listOfReport.add(report);
        operator.setListOfReports(listOfReport);

        reportRepository.save(report);

        return OperatorService.mapEntityToResponseDTO(operator);
    }

    public ResponseReportDTO resolveReport(Integer idReport) {
        List<Operator> listOfOp = operatorRepository.findAll();

        List<Operator> listOfOperator = listOfOp.stream()
                .filter(u -> u.getRole().equals(Role.OPERATOR))
                .collect(Collectors.toList());

        List<Report> listOfReport = listOfOperator.stream()
                .map(Operator::getListOfReports)
                .min(Comparator.comparing(List::size))
                .get();

        Operator operator =  listOfOperator.stream()
                .filter(u -> u.getListOfReports().containsAll(listOfReport))
                .findFirst()
                .get();


        Report report = reportRepository.findById(idReport).orElseThrow(); //TODO: Report not found
        report.setIsResolve(true);
        report.setResolveDate(LocalDateTime.now());
        reportRepository.save(report);
        operator.getListOfReports().remove(report);
        return mapEntityToResponseDTO(report);
    }

    public List<ResponseReportDTO> getReportsByOperator(Integer idOperator){
        Operator operator = operatorRepository.findById(idOperator).orElseThrow(); //TODO: operator not found

        return operator.getListOfReports().stream()
                .map(report -> mapEntityToResponseDTO(report))
                .collect(Collectors.toList());
    }

    public static ReportDTO mapEntityToDTO(Report report){
        return ReportDTO.builder()
                .id(report.getId())
                .category(report.getCategory())
                .description(report.getDescription())
                .createDate(report.getCreateDate())
                .resolveDate(report.getResolveDate())
                .isResolve(report.getIsResolve())
                .isAnon(report.getIsAnon())
                .lat(report.getLat())
                .lon(report.getLon())
                .build();
    }

    private ResponseReportDTO mapEntityToResponseDTO(Report report){
        ResponseReportDTO responseReportDTO = null;
        if(report.getIsAnon() == true){
            responseReportDTO = ResponseReportDTO.builder()
                    .id(report.getId())
                    .category(report.getCategory())
                    .description(report.getDescription())
                    .characteristicsDTO(characteristicsService.mapEntityToDTO(report.getCharacteristics()))
                    .createDate(report.getCreateDate())
                    .resolveDate(report.getResolveDate())
                    .isResolve(report.getIsResolve())
                    .base64ph(report.getPhotoBase46())
                    .isAnon(report.getIsAnon())
                    .lat(report.getLat())
                    .lon(report.getLon())
                    .build();
        } else if (report.getIsAnon() == false){
            responseReportDTO = ResponseReportDTO.builder()
                    .id(report.getId())
                    .category(report.getCategory())
                    .createByUserDTO(UserService.mapEntityToUserDTO(report.getCreateByUser()))
                    .description(report.getDescription())
                    .characteristicsDTO(characteristicsService.mapEntityToDTO(report.getCharacteristics()))
                    .createDate(report.getCreateDate())
                    .resolveDate(report.getResolveDate())
                    .isResolve(report.getIsResolve())
                    .base64ph(report.getPhotoBase46())
                    .isAnon(report.getIsAnon())
                    .build();
        }
        return responseReportDTO;
    }

    public List<ResponseReportDTO> getReportsByUser(Integer idUser) {
        User listOfUser = userRepository.findById(idUser).orElseThrow();//TODO: user not found

        return  listOfUser.getListOfReports().stream()
                .map(r -> mapEntityToResponseDTO(r))
                .collect(Collectors.toList());

    }

    public List<ResponseReportAndroidDTO> getReportsByAndroid(Integer idUser) {
        User listOfUser = userRepository.findById(idUser).orElseThrow();//TODO: user not found

        return  listOfUser.getListOfReports().stream()
                .map(r -> mapEntityToAndroidResponse(r))
                .collect(Collectors.toList());
    }

    private ResponseReportAndroidDTO mapEntityToAndroidResponse(Report report){
        return ResponseReportAndroidDTO.builder()
                .description(report.getDescription())
                .isResolve(report.getIsResolve())
                .build();
    }
}
