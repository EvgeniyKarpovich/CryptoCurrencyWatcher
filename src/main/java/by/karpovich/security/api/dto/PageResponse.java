package by.karpovich.security.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {

    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
        return new PageResponse<>(page.getContent(), metadata);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Metadata {
        private int page;
        private int size;
        private long totalElement;
    }
}
