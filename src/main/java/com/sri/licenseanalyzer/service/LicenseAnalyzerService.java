package com.sri.licenseanalyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.licenseanalyzer.model.DependencyLicenseInfo;
import org.springframework.stereotype.Service;
import com.sri.licenseanalyzer.config.OrtEnvironmentConfig;
import java.io.File;
import java.util.*;

@Service
public class LicenseAnalyzerService {

    public List<DependencyLicenseInfo> analyze(String manifestPath) {
        try {
            File input = new File(manifestPath);
            File outputDir = new File("ort-output");
            outputDir.mkdirs();

            ProcessBuilder pb = new ProcessBuilder(
                    "ort", "analyze",
                    "-i", input.getAbsolutePath(),
                    "-o", outputDir.getAbsolutePath(),
                    "--output-formats", "JSON"
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("ORT CLI failed. Check logs.");
            }

            File ortResultFile = new File(outputDir, "analyzer-result.json");
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(ortResultFile);

            List<DependencyLicenseInfo> result = new ArrayList<>();
            for (JsonNode pkg : root.path("packages")) {
                String name = pkg.path("package").path("id").asText();
                String version = pkg.path("package").path("version").asText();
                Set<String> licenses = new HashSet<>();
                pkg.path("package").path("declared_licenses").forEach(l -> licenses.add(l.asText()));

                for (String license : licenses) {
                    result.add(new DependencyLicenseInfo(
                            name,
                            version,
                            license,
                            "https://spdx.org/licenses/" + license + ".html"
                    ));
                }
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("License analysis failed", e);
        }
    }
}
