package org.bigdata.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class FileCheckTask {

	//스케쥴링 선언
	@Scheduled(cron = "0 * * * * *")
	public void checkFiles()throws Exception {
		log.warn("run");
		log.warn("======================");
	}
}
