@Service
public class LicenseAnalyzerService {

    public List<DependencyLicenseInfo> analyzeLicenses(String manifestPath) {
        try {
            File inputDir = new File(manifestPath);
            if (!inputDir.exists() || !inputDir.isDirectory()) {
                throw new IllegalArgumentException("Invalid manifest folder path.");
            }

            // Configure the ORT analyzer
            AnalyzerConfiguration analyzerConfig = AnalyzerConfiguration.builder()
                    .allowDynamicVersions(true)
                    .skipExcluded(true)
                    .enabledPackageManagers(Set.of("Maven", "NPM", "PIP"))
                    .build();

            OrtResult ortResult = Analyzer.analyze(inputDir, analyzerConfig, new RepositoryConfiguration());

            List<DependencyLicenseInfo> licenses = new ArrayList<>();
            ortResult.getAnalyzer().getResult().getPackages().forEach(pkg -> {
                String name = pkg.getPkg().getId().getName();
                String version = pkg.getPkg().getId().getVersion();
                Set<String> licenseSet = pkg.getPkg().getDeclaredLicenses();

                for (String lic : licenseSet) {
                    licenses.add(new DependencyLicenseInfo(
                            name,
                            version,
                            lic,
                            "https://spdx.org/licenses/" + lic + ".html"
                    ));
                }
            });

            return licenses;

        } catch (Exception e) {
            throw new RuntimeException("License analysis failed", e);
        }
    }
}
