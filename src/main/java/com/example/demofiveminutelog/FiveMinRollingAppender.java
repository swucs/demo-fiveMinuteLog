package com.example.demofiveminutelog;

import ch.qos.logback.core.rolling.RollingFileAppender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


@Slf4j
@Service
public class FiveMinRollingAppender<E> extends RollingFileAppender<E> {

	private LocalDateTime nextRollingOverTime = nextRollingOverTime();
	private static final int rollerOverTimeMinutes = 2;

	/**
	 * 0초로 설정하면 rolling이 안되는 경우가 있어서 매분 1초로 설정
	 */
	@Scheduled(cron = "1 0/1 * * * ?")
	public void tlo_log_start() {
		try {
			log.info("@Scheduled tlo_log_start : {}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			Logger tloLogger = LoggerFactory.getLogger("tloLogger");
			tloLogger.error(""); //tlo log 는 info 사용. error 는 파일 나누기 위한 trigger위해 사용.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private LocalDateTime nextRollingOverTime() {
		LocalDateTime now = LocalDateTime.now();
		return nextRollingOverTime(now);
	}

	private LocalDateTime nextRollingOverTime(LocalDateTime baseTime) {
        return baseTime.plusMinutes(rollerOverTimeMinutes).truncatedTo(ChronoUnit.MINUTES);
	}


	@Override
	public void rollover() {
		try {
			LocalDateTime now = LocalDateTime.now();

			log.info("rollover now : {}",  now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			log.info("nextRollingOverTime : {}",  nextRollingOverTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

			if (now.isAfter(nextRollingOverTime)) {
				nextRollingOverTime = nextRollingOverTime(nextRollingOverTime);
				super.rollover();
				log.info("super.rollover() called");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void writeOut(E event) throws IOException {
		log.info("event : {}", event.toString());
		if (!"[ERROR]".equals(event.toString().trim())) {
			super.writeOut(event);
		}
	}
}
