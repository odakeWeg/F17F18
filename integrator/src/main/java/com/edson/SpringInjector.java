package com.edson;

import javafx.application.Application;

import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class SpringInjector {
    //@TODO: inject spring and check if can be unified with App.java
    
    public static void main(String[] args) {
		//SingleFrameApplication.launch(DesktopApplicationGateway.class, args);
		//org.jdesktop.application.Application.launch(DesktopApplicationGateway.class, args);

		Application.launch(App.class);
	}

}
