package io.soo.springboot.core.api.controller.v1.request;

import io.soo.springboot.core.domain.ExampleData;

public record ExampleRequestDto(String data) {
    public ExampleData toExampleData() {
        return new ExampleData(data, data);
    }
}
