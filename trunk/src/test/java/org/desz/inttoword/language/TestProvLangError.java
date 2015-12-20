package org.desz.inttoword.language;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

public class TestProvLangError {

	class BeanA {
		private String pr;

		/**
		 * @return the pr
		 */
		public String getPr() {
			return pr;
		}

		/**
		 * @param pr
		 *            the pr to set
		 */
		public void setPr(String pr) {
			this.pr = pr;
		}

		public BeanA() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	class BeanB {
		private String pr;

		/**
		 * @return the pr
		 */
		public String getPr() {
			return pr;
		}

		/**
		 * @param pr
		 *            the pr to set
		 */
		public void setPr(String pr) {
			this.pr = pr;
		}

		public BeanB() {
			super();
			// TODO Auto-generated constructor stub
		}
	}

	@Test
	public void test() {
		BeanA a = new BeanA();
		a.setPr("prA");
		BeanB b = new BeanB();
		BeanUtils.copyProperties(a, b, "");
		assertTrue(b.getPr().equals(a.getPr()));
	}

}
