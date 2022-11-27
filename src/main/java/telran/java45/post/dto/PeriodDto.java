package telran.java45.post.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PeriodDto {
	LocalDate dateFrom;
	LocalDate dateTo;
}
