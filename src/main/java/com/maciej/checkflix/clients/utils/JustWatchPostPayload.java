package com.maciej.checkflix.clients.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JustWatchPostPayload {
        private String age_certifications;
        private String content_types;
        private String presentation_types;
        private String providers;
        private String genres;
        private String languages;
        private String release_year_from;
        private String release_year_until;
        private String monetization_types;
        private String min_price;
        private String max_price;
        private String nationwide_cinema_releases_only;
        private String scoring_filter_types;
        private String cinema_release;
        private String query;
        private String page;
        private String page_size;
        private String timeline_type;
        private String person_id;
}
