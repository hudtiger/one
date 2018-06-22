package test.Dyanmic;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;


@SupportedAnnotationTypes("test.Dyanmic.CodeComplier")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CodeProcessor extends AbstractProcessor {
	
	private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
    	System.err.println("init...");
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    	StringBuilder builder = new StringBuilder()
                .append("package com.usl.generated;\n\n")
                .append("public class GeneratedClass {\n\n")
                .append("\tpublic String getMessage() {\n") 
                .append("\t\treturn \"");

    	System.err.println("ok");
        for (Element element : roundEnv.getElementsAnnotatedWith(CodeComplier.class)) {
            String objectType = element.getSimpleName().toString();


            builder.append(objectType).append(" says hello!\\n");
        }


        builder.append("\";\n")
                .append("\t}\n")
                .append("}\n"); 


        try { 
            JavaFileObject source = processingEnv.getFiler().createSourceFile("GeneratedClass");


            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        	System.err.println(e);
        }


        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(CodeComplier.class.getCanonicalName());
        return annotataions;
    }
}
