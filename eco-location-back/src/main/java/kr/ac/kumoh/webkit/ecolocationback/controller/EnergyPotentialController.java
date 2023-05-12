package kr.ac.kumoh.webkit.ecolocationback.controller;

import kr.ac.kumoh.webkit.ecolocationback.dto.response.PotentialByRegionDto;
import kr.ac.kumoh.webkit.ecolocationback.entity.EnergyPotential;
import kr.ac.kumoh.webkit.ecolocationback.service.EnergyPotentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/energy-potential")
public class EnergyPotentialController {

    @Autowired
    private EnergyPotentialService energyPotentialService;

    // 잠재 발전량 데이터 전부 전송
    @GetMapping("/allEP")
    public List<EnergyPotential> getEnergyPotential(){
        return energyPotentialService.getAllEnergyPotential();
    }

    // 사용자가 원하는 기간을 지정하여 보내면 지정된 기간 사이의 잠재 발전량 데이터를 전부 전송한다.
    @GetMapping
    public List<EnergyPotential> getEPByForecastTimeBetween(
            @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime firstForecastTime,
            @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime secondForecastTime){
        return energyPotentialService.getEPByForecastTimeBetween(firstForecastTime, secondForecastTime);
    }

    // 발전원별 잠재량 표시
    @GetMapping("/source")
    public PotentialByRegionDto getEnergyPotentialBySourceAndYear(@RequestParam("sourceType") String sourceType,
                                                                  @RequestParam("year") int year){
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
        return energyPotentialService.getAllEnergyPotentialBySourceAndYear(sourceType,start,end);
    }
}
