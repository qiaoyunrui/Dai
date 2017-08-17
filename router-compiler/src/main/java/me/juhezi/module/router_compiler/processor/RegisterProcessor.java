package me.juhezi.module.router_compiler.processor;

import com.google.auto.service.AutoService;
import com.juhezi.module.router_annotation.annotation.Register;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by Juhezi on 2017/8/17.
 */
@AutoService(Processor.class)
public class RegisterProcessor extends AbstractProcessor {

    private Filer mFileUtils;
    private Elements mElementUtils;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFileUtils = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
        mMessager = processingEnvironment.getMessager();
    }

    /**
     * 返回支持的注解类型
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(Register.class.getCanonicalName());
        return annotationTypes;
    }

    /**
     * 返回支持的源码版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 核心部位
     * - 收集信息
     * - 生成代理类
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment
                .getElementsAnnotatedWith(Register.class);
        // 1.收集信息
        for (Element element : elements) {
            if (!checkAnnotationUseValid(element)) return false;
            TypeElement typeElement = (TypeElement) element;
            String className = typeElement.getQualifiedName().toString();   //获得类名
            System.out.println(className);
        }
        return false;
    }

    boolean checkAnnotationUseValid(Element element) {
        return element instanceof TypeElement;  //只接受类
    }

}