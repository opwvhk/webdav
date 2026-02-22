package opwvhk.webdav;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class JavaTimeAdapter {

	public static LocalDateTime parseLocalDateTime(String value) {
		return value != null ? LocalDateTime.parse(value) : null;
	}

	public static String printLocalDateTime(LocalDateTime value) {
		return value != null ? value.toString() : null;
	}

	public static LocalDate parseLocalDate(String value) {
		return value != null ? LocalDate.parse(value) : null;
	}

	public static String printLocalDate(LocalDate value) {
		return value != null ? value.toString() : null;
	}
}
