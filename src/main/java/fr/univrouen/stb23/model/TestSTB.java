package fr.univrouen.stb23.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class TestSTB {

	private Resource resource;

	public TestSTB() {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		resource = resourceLoader.getResource("classpath:xml/test0.xml");
	}

	public String loadFileXML() {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			sb.append("Error : ").append(e.getMessage());
		}
		return sb.toString();
	}
}
