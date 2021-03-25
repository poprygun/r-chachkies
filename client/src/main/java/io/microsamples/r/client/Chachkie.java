package io.microsamples.r.client;

import lombok.Value;

import java.time.Instant;

@Value
class Chachkie {
    Double lat, lon;
    Instant when;
}
