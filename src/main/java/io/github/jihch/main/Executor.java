package io.github.jihch.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Executor {

	@Value("${target}")
	private String target;
	
	@Value("${question-size}")
	private Integer questionSize;
	
	@Autowired
	private Explorer explorer;
	
	@Value("${excel-db}")
	private String excelDB;
	
	@Value("${sheet-name}")
	private String sheetName;
	
	@Autowired
	private QuestionLoader loader;
	
	@Autowired
	private QuestionRandom random;
	
	@Autowired
	private PDFGenerator generator;
	
	public void execute() {
		
		File excel = new File(excelDB);
		
		List<Entry<String, Integer>> list = loader.load(excel, sheetName); 
				
		list = random.random(list, questionSize);
		
		PDDocument doc = generator.generate(list);
		
		// Saving the document
		try {
			
			doc.save(target);
			
			// Closing the document
			doc.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		explorer.show(target);
		
		System.out.println("PDF created");
	
	}
}
