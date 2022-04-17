package io.github.jihch.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PDFGenerator {

	@Value("${margin-left}")
	private Integer marginLeft;
	
	@Value("${height}")
	private Float height;
	
	@Value("${font-size}")
	private Float fontSize;
	
	@Value("${font-path}")
	private String fontPath;
	
	public PDDocument generate(List<Entry<String, Integer>> list) {
		
		PDDocument doc = new PDDocument();
		
		File fontFile = new File(fontPath);
		
		// 加载字体库
		PDFont font = null;
		
		try {
			font = PDType0Font.load(doc, fontFile);
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		int i = 0;
		
		do {

			// 创建新的一页
			PDPage page = new PDPage(PDRectangle.A4);

			doc.addPage(page);
			
			try (PDPageContentStream stream = new PDPageContentStream(doc, page)) {
				
				stream.setFont(font, fontSize);
	
				stream.beginText();
	
				stream.newLineAtOffset(marginLeft, fontSize);
	
				float remainHeight = height;
	
				for (; i < list.size() && remainHeight > 0; i++) {
	
					Entry<String, Integer> e = list.get(i);
	
					float contentHeight = e.getValue() * fontSize + fontSize;
	
					stream.newLineAtOffset(0, contentHeight);
	
					stream.showText(e.getKey());
	
					remainHeight -= contentHeight;
	
				}
	
				stream.endText();
	
			} catch (IOException e) {
				e.printStackTrace();
				
			}

		} while (i < list.size()); // 创建新的一页的条件是，还没有打印完集合中的题目

		return doc;
	}
}
