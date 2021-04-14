package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WChararter {
    private String word;
    private Long beginTime;
    private Long endTime;
    private int channelId;
    private int speechRate;
    private double emotionValue;
}