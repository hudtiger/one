package test;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.alibaba.fastjson.JSONObject;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import test.model.AutoCode;

public class testDynamic{

	public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
//		makeClass();
		new AutoCode();
		DynamicObject obj = new DynamicObject();
		obj.set("Id", 123).set("Name","Micheal").set("Id","3444");
		System.err.println(obj.toJSONString());
	}

	private static void makeClass() throws NotFoundException, CannotCompileException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		ClassPool pool = ClassPool.getDefault();
		pool.importPackage("io.netty.channel.ChannelFuture");
		pool.importPackage("io.netty.channel.ChannelHandlerContext");
		CtClass tmpClass = pool.makeClass("Point");   //创建新类
		CtClass[] interfaces = new CtClass[] {pool.get("test.Netty.IBizHandle"),pool.get("java.io.Serializable")}; //加载现有接口/类
		tmpClass.setInterfaces(interfaces);
		
		//创建字段
		CtField field01 = CtField.make("private int id;",tmpClass);  
		CtField field02 = CtField.make("private String name;", tmpClass);  
		tmpClass.addField(field01);  
		tmpClass.addField(field02);  
		//封装字段
		CtMethod method01 = CtMethod.make("public String getName(){return name;}", tmpClass);  
		CtMethod method02 = CtMethod.make("public void setName(String name){this.name = name;}", tmpClass);  
		tmpClass.addMethod(method01);  
		tmpClass.addMethod(method02);  
		
		//添加有参构造器  
		CtConstructor cons = new CtConstructor(null,tmpClass);  
		cons.setBody("{}");  
		tmpClass.addConstructor(cons);  
		//无参构造器  
		CtConstructor constructor = new CtConstructor(new CtClass[]{CtClass.intType,pool.get("java.lang.String")},tmpClass);  
		constructor.setBody("{this.id=id;this.name=name;}");  
		tmpClass.addConstructor(constructor);  
		
		//创建接口方法	
		CtMethod interfaceMethod = CtNewMethod.make("public ChannelFuture dealWith(ChannelHandlerContext ctx, Object msg){ System.out.println(\"dealWith method called:\"); return null; }",tmpClass);
		tmpClass.addMethod(interfaceMethod);
		//新建方法
		CtMethod sayHiMethod = CtNewMethod.make("public String say(String msg) { return \"Hi,\"+msg;}", tmpClass);
		tmpClass.addMethod(sayHiMethod);
		//修改已有方法
		CtMethod sayMethodRef = tmpClass.getDeclaredMethod("say");
		sayMethodRef.insertBefore(" System.out.println(\"say method called:\"+$1);");
	//	sayMethodRef.setBody("{ System.out.println(\"say method called:\"); }");
		tmpClass.writeFile("F:\\"); //写入字节码
		
		Class<?> cls = tmpClass.toClass();
		//Object obj = cls.newInstance();
		Constructor<?> constructorRef = cls.getConstructor(int.class,String.class);
		Object obj = constructorRef.newInstance(123,"construct");
		System.err.printf("%s\r\n",cls.getDeclaredMethod("say",String.class).invoke(obj, "Micheal"));
	//	cls.getDeclaredMethod("setName",String.class).invoke(obj,"modify");
		System.err.printf("%s\r\n",cls.getDeclaredMethod("getName").invoke(obj));
		System.err.println(JSONObject.toJSONString(obj));
	}
}

class DynamicObject{
	
	Object obj = null;
	Class<?> cls = null;
	public DynamicObject() throws CannotCompileException, InstantiationException, IllegalAccessException, NotFoundException, IOException {
		CtClass tmpClass = null; 
		ClassPool pool = null;
		pool = ClassPool.getDefault();
		pool.importPackage("java.util.Map");
		pool.importPackage("java.util.HashMap");
		pool.importPackage("java.lang.Object");
		pool.importPackage("lombok.Data");
		pool.insertClassPath(new ClassClassPath(this.getClass()));
		tmpClass = pool.makeClass("test.tmpClass");   //创建新类
		
		//添加类注解
		ClassFile ccFile = tmpClass.getClassFile();
        ConstPool constPool = ccFile.getConstPool();
		AnnotationsAttribute bodyAttr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation bodyAnnot = new Annotation("lombok.Data", constPool);
    //    bodyAnnot.addMemberValue("name", new StringMemberValue("HelloWoldService", constPool));
        bodyAttr.addAnnotation(bodyAnnot);
        ccFile.addAttribute(bodyAttr);

        //属性字段
//		CtClass mapClass = pool.get("java.util.HashMap");
//		CtField attrPool = new CtField(mapClass,"attrPool",tmpClass); 
        CtField attrId =  CtField.make("private String id;",tmpClass);  
        tmpClass.addField(attrId);
		CtField attrPool =  CtField.make("public Map attrPool;",tmpClass);  
		tmpClass.addField(attrPool);
		//添加有参构造器  
		CtConstructor cons = new CtConstructor(null,tmpClass);  
		cons.setBody("{this.attrPool = new HashMap();}");  
		tmpClass.addConstructor(cons);  
		//新建方法
		CtMethod getMethod = CtNewMethod.make("public Object get(String key) { return this.attrPool.get(key);}", tmpClass);
		CtMethod setMethod = CtNewMethod.make("public void set(String key,Object val) { this.attrPool.put(key, val); }", tmpClass);
		tmpClass.addMethod(getMethod);
		tmpClass.addMethod(setMethod);
		//新建对象
		cls = tmpClass.toClass();
		obj = cls.newInstance();
	}
	
	public DynamicObject set(String property,Object content) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		cls.getDeclaredMethod("set",String.class,Object.class).invoke(obj, property,content);
		return this;
	}
	
	public Object get(String property) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return cls.getDeclaredMethod("get",String.class).invoke(obj, property);
	}
	
	public String toJSONString() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, InvocationTargetException, NoSuchMethodException {
		return JSONObject.toJSONString(cls.getField("attrPool").get(obj));
	//	return JSONObject.toJSONString(cls.getDeclaredMethod("getAttrPool").invoke(obj));
	}
}