package com.daimeng.api.cst.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daimeng.api.cst.entity.CstInf;

public interface CstInfRepository extends JpaRepository<CstInf, String>{

	public ArrayList<CstInf> findAll();
}
