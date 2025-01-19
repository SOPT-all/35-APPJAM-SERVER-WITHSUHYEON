package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sopt.appjam.withsuhyeon.constant.Region;
import sopt.appjam.withsuhyeon.dto.constant.res.RegionsResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/enums")
public class ConstantController {

    @GetMapping("/regions")
    public ResponseEntity<RegionsResponse> getAllRegions() {
        return ResponseEntity.ok(Region.generateRegionsResponse());
    }
}
