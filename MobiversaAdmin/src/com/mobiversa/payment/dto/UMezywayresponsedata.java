package com.mobiversa.payment.dto;

import java.util.ArrayList;

import com.mobiversa.payment.util.UMEzyway;

public class UMezywayresponsedata {

	public int size;
	public ArrayList<UMEzyway> umezywaylist;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<UMEzyway> getUmezywaylist() {
		return umezywaylist;
	}

	public void setUmezywaylist(ArrayList<UMEzyway> umezywaylist) {
		this.umezywaylist = umezywaylist;
	}

	public UMezywayresponsedata(int size, ArrayList<UMEzyway> umezywaylist) {
		this.size = size;
		this.umezywaylist = umezywaylist;
	}

	@Override
	public String toString() {
		return "UMezywayresponsedata [size=" + size + ", umezywaylist=" + umezywaylist + ", getSize()=" + getSize()
				+ ", getUmezywaylist()=" + getUmezywaylist() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
