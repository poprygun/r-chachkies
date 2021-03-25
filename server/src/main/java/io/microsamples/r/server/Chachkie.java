package io.microsamples.r.server;

import lombok.Data;

import java.time.Instant;

@Data
class Chachkie {
    Double lat, lon;
    Instant when;
}
