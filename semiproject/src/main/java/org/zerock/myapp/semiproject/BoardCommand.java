package org.zerock.myapp.semiproject;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BoardCommand {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws IOException ;
	
}//BoardCommand Interface
