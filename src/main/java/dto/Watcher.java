package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Watcher {
    private String taskId;
    private String requestId;
    private String StatusText;
    private Long bizDuration;
    private Long solveTime;
    private Long statusCode;
    private List<WChararter> words;
}
