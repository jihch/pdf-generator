package io.github.jihch.main;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class QuestionLoader {
	
	public List<Entry<String, Integer>> load(File f, String sheetName) {
		
		List<Entry<String, Integer>> list = new ArrayList<>();
		
		try (Workbook wb = new XSSFWorkbook(f)) {
			
			if (StringUtils.isNotBlank(sheetName)) {
				Sheet sheet = wb.getSheet(sheetName);
				list = load(sheet);
				
			} else {
				Iterator<Sheet> it = wb.sheetIterator();
				while (it.hasNext()) {
					List<Entry<String, Integer>> tmpList = load(it.next());
					list.addAll(tmpList);
				}
				
			}
			
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
			
		} 
		return list;
	}
	
	/**
	 * 加载一个Sheet
	 * @param sheet
	 * @return
	 */
	private List<Entry<String, Integer>> load(Sheet sheet) {
		List<Entry<String, Integer>> list = new ArrayList<>();
		int lastRowNum = sheet.getLastRowNum();
		if (lastRowNum == -1) {
			return list;
		}

		for (int i = 0; i <= lastRowNum; i++) {

			Row row = sheet.getRow(i);
			Cell qCell = row.getCell(0); // 题目
			Cell nCell = row.getCell(1); // 留白行数
			String q = qCell.getStringCellValue();
			int n = (int) nCell.getNumericCellValue();
			Entry<String, Integer> e = new AbstractMap.SimpleEntry<String, Integer>(q, n);
			list.add(e);
		}

		return list;
	}
	   
}
