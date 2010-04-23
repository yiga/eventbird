package com.bushytails.eventbird.util;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public class StoreManager {
	public static PersistenceManagerFactory getPMF() {
		if(pmf == null) {
			pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
		}
		return pmf;
	}
	
	private static PersistenceManagerFactory pmf;
}
