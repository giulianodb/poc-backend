package com.giulianodb.service.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.giulianodb.domain.Personne;
import com.giulianodb.service.PersonneService;

@StepScope
@Component
public class PersonneReaderBatch implements ItemReader<Personne>, InitializingBean {
  
  private BufferedReader br;
  
  private File file;
  
  @Autowired
  private PersonneService service;
//  
  @Value("${fileName}")
  private String fileName;
  
  @Override
  public Personne read () throws Exception {
    try {
      final String line;
      if ((line = br.readLine()) != null) {
    	  
        return service.fromFileLine(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  
    br.close();
//    file.delete();
    return null;
  }
  
  @Override
  public void afterPropertiesSet () throws Exception {
    file = new File(fileName);
    br = new BufferedReader(new FileReader(file));
  }
}