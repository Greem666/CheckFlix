package com.maciej.checkflix.analytics.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PieChartReviewAnalysisDto extends AbstractReviewAnalysisDto {
    private ByteArrayResource inputStream;

    public PieChartReviewAnalysisDto(List<String> originalReviews, URI path) throws IOException {
        super(originalReviews);
        this.inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(path)));
    }
}
