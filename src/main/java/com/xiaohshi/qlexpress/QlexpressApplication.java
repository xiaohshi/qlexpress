package com.xiaohshi.qlexpress;

import com.xiaohshi.qlexpress.loader.WorkflowLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class QlexpressApplication {

	public static void main(String[] args) {
		WorkflowLoader.init();
		SpringApplication.run(QlexpressApplication.class, args);
	}
}
