package com.aeh.aoc2022;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.compiler.RuntimeTypeCheckOption;
import org.kie.dmn.core.impl.DMNRuntimeImpl;

public class App {
    
   public static void main( String[] args ) throws IOException
   {
      // run with "mvn exec:java"
      // set in pom.xml (e)xec-maven-plugin)
      String day = args[0]; // "01", "02", etc.
      String mode = args[1]; // "sample" or "full"
      String[] outputNodeNames = // single:"Part 1", multiple:"Output,Part 2"
         (String[])Arrays.asList(args[2].split(",")).stream().map(x -> x.toString().trim()).toArray(String[]::new);
      
      final KieServices ks = KieServices.Factory.get();
      ReleaseId releaseId = ks.newReleaseId("com.aeh.aoc2022", "dmn-project", "1.0-SNAPSHOT");
      KieContainer kieContainer = ks.newKieContainer(releaseId);
      DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
      ((DMNRuntimeImpl) dmnRuntime).setOption(new RuntimeTypeCheckOption(true));
      
      String namespace = "com.aeh.aoc2022.dmn";
      String modelName = "decisionmodel-" + day;
      final DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);

      final DMNContext context = dmnRuntime.newContext();
      
      String filename = "src/main/resources/"+ mode + "-" + day + ".txt";
      List<String> lines = Files.readAllLines(Paths.get(filename));

      context.set("InputLines", lines);  

      DMNResult dmnResult = dmnRuntime.evaluateByName (dmnModel, context, outputNodeNames);  

      System.out.println(LocalTime.now());
      if (mode.startsWith("sample")) {
         for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {  
            System.out.println(dr.getDecisionName());
            System.out.println(dr.getResult());
         }
      } else {
         for (String decision : outputNodeNames) {
            System.out.println(decision + ": " + dmnResult.getContext().get(decision));
         }
      }

   }
}