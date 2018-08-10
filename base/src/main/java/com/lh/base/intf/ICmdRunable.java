package com.lh.base.intf;

import java.io.IOException;

public interface ICmdRunable {

	
	String getCmdName();
	
	void run(String[] args) throws IOException;
	
	
}
