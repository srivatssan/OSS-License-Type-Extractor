package com.sri.licenseanalyzer.controller;

import com.sri.licenseanalyzer.model.ManifestRequest;
import com.sri.licenseanalyzer.model.DependencyLicenseInfo;
import com.sri.licenseanalyzer.service.LicenseAnalyzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicenseAnalyzerController {

    @Autowired
    private LicenseAnalyzerService service;

    @PostMapping
    public List<DependencyLicenseInfo> analyze(@RequestBody ManifestRequest request) {
        return service.analyze(request.getManifestPath());
    }
}
