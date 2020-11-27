package com.maciej.checkflix.mapper.justwatch;

import com.maciej.checkflix.domain.justwatch.Scoring;
import com.maciej.checkflix.domain.justwatch.ScoringDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScoringMapper {

    public Scoring mapToScoring(ScoringDto scoringDto) {
        return new Scoring(
                scoringDto.getProviderType(),
                scoringDto.getValue()
        );
    }

    public ScoringDto mapToScoringDto(Scoring scoring) {
        return new ScoringDto(
                scoring.getProviderType(),
                scoring.getValue()
        );
    }

    public List<Scoring> mapToScoringList(List<ScoringDto> scoringDtoList) {
        return scoringDtoList.stream()
                .map(this::mapToScoring)
                .collect(Collectors.toList());
    }

    public List<ScoringDto> mapToScoringDtoList(List<Scoring> scoringList) {
        return scoringList.stream()
                .map(this::mapToScoringDto)
                .collect(Collectors.toList());
    }
}
