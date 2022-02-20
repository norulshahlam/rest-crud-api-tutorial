package com.shah.restcrudapitutorial.model.request;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class EmployeePatchPatchTest {

	@Test
	void testObjectMethod() {
		EmployeePatch object = new EmployeePatch();
		object.equals(new EmployeePatch());
		object.hashCode();
		object.toString();
		assertThat(object).isNotNull();
	}
	
	@Test
	void testAll() {
		EmployeePatch object = new EmployeePatch();
		object.equals(EmployeePatch.builder().build());
		assertThat(object).isNotNull();
	}

}
