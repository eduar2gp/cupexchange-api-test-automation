package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingDepositResponse {
    // This matches the array inside the JSON you shared
    private List<PendingDeposit> content;

    // Pageable metadata (optional, but good for validation)
    private int totalElements;
    private int totalPages;
    private boolean last;
    private int size;
    private int number;

    public List<PendingDeposit> getContent() {
        return content;
    }

    public void setContent(List<PendingDeposit> content) {
        this.content = content;
    }
}
