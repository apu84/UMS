package org.ums;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

import org.apache.commons.lang.WordUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BasicGenerator {

  public static void main(String[] args) {
    new BasicGenerator();
  }

  public BasicGenerator() {
    generateModel();
  }

  private void generateModel() {
    JSONObject template = getTemplateJson();
    JSONObject model = (JSONObject) template.get("model");
    if(model != null) {
      String immutableModelName = model.get("name").toString();
      String mutableModelName = String.format("%s%s", "Mutable", immutableModelName);
      generateImmutable(immutableModelName, mutableModelName, (JSONArray) model.get("fields"));
      generateMutable(immutableModelName, mutableModelName, (JSONArray) model.get("fields"));
      String idType = getIdType((JSONArray) model.get("fields"));
      generateContentManager(immutableModelName, mutableModelName, idType);
      generatePersistentModel(immutableModelName, mutableModelName, (JSONArray) model.get("fields"));
      generateDaoDecorator(immutableModelName, mutableModelName, idType);
    }

  }

  private void generateImmutable(String pImmutable, String pMutable, JSONArray pFields) {
    JavaInterfaceSource immutable = Roaster.create(JavaInterfaceSource.class);
    immutable.setPackage("org.ums.domain.model.immutable").setName(pImmutable);
    immutable.addInterface(Serializable.class);
    if(hasDateField(pFields)) {
      immutable.addImport("java.util.Date");
    }
    immutable.addImport("org.ums.domain.model.common.Identifier");
    immutable.addImport("org.ums.domain.model.common.EditType");
    immutable.addImport(String.format("org.ums.domain.model.mutable.%s", pMutable));

    immutable.addInterface(String.format("EditType<%s>", pMutable))
        .addInterface(org.ums.domain.model.common.LastModifier.class)
        .addInterface(String.format("Identifier<%s>", getIdType(pFields)));

    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(field.get(fieldName) instanceof String) {
          immutable.addMethod().setName(String.format("get%s", WordUtils.capitalize(fieldName)))
              .setReturnType(field.get(fieldName).toString());
        }
        else {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);
          if(!fieldValue.containsKey("idType")) {
            JSONObject referenceObject = (JSONObject) fieldValue.get("reference");
            String referenceName = WordUtils.capitalize(referenceObject.get("name").toString());

            if(referenceObject.containsKey("package")) {
              immutable.addImport(String.format("%s.%s", referenceObject.get("package").toString(), referenceName));
            }

            immutable.addMethod().setName(String.format("get%s", referenceName)).setReturnType(referenceName);

            if(fieldValue.containsKey("type") && fieldValue.get("type").toString().equalsIgnoreCase("Ref")) {
              immutable.addMethod().setName(String.format("get%sId", referenceName))
                  .setReturnType(getReferenceIdReturnType(referenceObject));
            }
          }
        }
      }
    }

    print(immutable.toString());
  }

  private void generateMutable(String pImmutable, String pMutable, JSONArray pFields) {
    JavaInterfaceSource mutable = Roaster.create(JavaInterfaceSource.class);
    mutable.setPackage("org.ums.domain.model.mutable").setName(String.format("Mutable%s", pImmutable));
    mutable.addImport("org.ums.domain.model.common.Editable");
    mutable.addImport("org.ums.domain.model.common.MutableIdentifier");
    mutable.addImport("org.ums.domain.model.mutable.MutableLastModifier");
    mutable.addImport(String.format("org.ums.domain.model.immutable.%s", pImmutable));
    if(hasDateField(pFields)) {
      mutable.addImport("java.util.Date");
    }

    mutable.addInterface(pImmutable).addInterface(String.format("Editable<%s>", getIdType(pFields)))
        .addInterface(String.format("MutableIdentifier<%s>", getIdType(pFields))).addInterface("MutableLastModifier");

    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(field.get(fieldName) instanceof String) {
          mutable.addMethod().setName(String.format("set%s", WordUtils.capitalize(fieldName))).setReturnTypeVoid()
              .addParameter(field.get(fieldName).toString(), String.format("p%s", WordUtils.capitalize(fieldName)));
        }
        else {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);
          if(!fieldValue.containsKey("idType")) {
            JSONObject referenceObject = (JSONObject) fieldValue.get("reference");
            String referenceName = WordUtils.capitalize(referenceObject.get("name").toString());

            if(referenceObject.containsKey("package")) {
              mutable.addImport(String.format("%s.%s", referenceObject.get("package").toString(), referenceName));
            }

            mutable.addMethod().setName(String.format("set%s", referenceName)).setReturnTypeVoid()
                .addParameter(referenceName, String.format("p%s", referenceName));

            if(fieldValue.containsKey("type") && fieldValue.get("type").toString().equalsIgnoreCase("Ref")) {
              mutable.addMethod().setName(String.format("set%sId", referenceName)).setReturnTypeVoid()
                  .addParameter(getReferenceIdReturnType(referenceObject), String.format("p%sId", referenceName));
            }
          }
        }
      }
    }

    print(mutable.toString());
  }

  private void generateContentManager(String pImmutable, String pMutable, String idType) {
    JavaInterfaceSource manager = Roaster.create(JavaInterfaceSource.class);
    manager.setPackage("org.ums.manager").setName(String.format("%sManager", pImmutable));
    manager.addImport("org.ums.manager.ContentManager");
    manager.addImport(String.format("org.ums.domain.model.immutable.%s", pImmutable));
    manager.addImport(String.format("org.ums.domain.model.mutable.%s", pMutable));

    manager.addInterface(String.format("ContentManager<%s, %s, %s>", pImmutable, pMutable, idType));

    print(manager.toString());
  }

  private void generatePersistentModel(String pImmutable, String pMutable, JSONArray pFields) {
    JavaClassSource model = Roaster.create(JavaClassSource.class);
    String modelName = String.format("Persistent%s", pImmutable);
    model.setPackage("org.ums.persistent.model").setName(modelName);
    model.addImport("org.springframework.context.ApplicationContext");
    model.addImport("org.ums.context.AppContext");
    model.addImport(String.format("org.ums.domain.model.immutable.%s", pImmutable));
    model.addImport(String.format("org.ums.domain.model.mutable.%s", pMutable));

    String managerName = String.format("%sManager", WordUtils.capitalize(pImmutable));
    model.addImport(String.format("org.ums.manager.%s", managerName));
    if(hasDateField(pFields)) {
      model.addImport("java.util.Date");
    }
    model.addInterface(pMutable);

    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(!(field.get(fieldName) instanceof String)) {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);
          if(!fieldValue.containsKey("idType")) {
            JSONObject referenceObject = (JSONObject) fieldValue.get("reference");
            String referenceName = WordUtils.capitalize(referenceObject.get("name").toString());

            if(referenceObject.containsKey("package")) {
              model.addImport(String.format("%s.%s", referenceObject.get("package").toString(), referenceName));
            }

            if(referenceObject.containsKey("manager")) {
              JSONObject managerObject = (JSONObject) referenceObject.get("manager");
              String referenceManagerName = "";
              if(managerObject.containsKey("name")) {
                referenceManagerName = managerObject.get("name").toString();
              }
              else {
                referenceManagerName = String.format("%sManager", referenceName);
              }

              if(managerObject.containsKey("package")) {
                model.addImport(String.format("%s.%s", managerObject.get("package").toString(), referenceManagerName));
              }

              model.addField().setName(String.format("s%s", referenceManagerName)).setType(referenceManagerName)
                  .setPrivate().setStatic(true);

            }
          }
        }
      }
    }

    model.addField().setName(String.format("s%s", managerName)).setType(managerName).setPrivate().setStatic(true);

    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(field.get(fieldName) instanceof String) {
          model.addField().setName(String.format("m%s", WordUtils.capitalize(fieldName)))
              .setType(field.get(fieldName).toString()).setPrivate();

          MethodSource getMethodSource =
              model.addMethod().setBody(String.format("return m%s;", WordUtils.capitalize(fieldName)))
                  .setName(String.format("get%s", WordUtils.capitalize(fieldName)))
                  .setReturnType(field.get(fieldName).toString()).setPublic();
          getMethodSource.addAnnotation().setName("Override");

          MethodSource setMethodSource =
              model.addMethod().setName(String.format("set%s", WordUtils.capitalize(fieldName))).setPublic();
          setMethodSource.addParameter(field.get(fieldName).toString(),
              String.format("p%s", WordUtils.capitalize(fieldName)));
          setMethodSource.setBody(String.format("this.m%s = p%s;", WordUtils.capitalize(fieldName),
              WordUtils.capitalize(fieldName)));
          setMethodSource.addAnnotation().setName("Override");

        }
        else {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);
          if(!fieldValue.containsKey("idType")) {
            JSONObject referenceObject = (JSONObject) fieldValue.get("reference");
            String referenceName = WordUtils.capitalize(referenceObject.get("name").toString());
            String referenceManager = String.format("s%sManager", referenceName);
            model.addField().setName(String.format("m%s", referenceName)).setType(referenceName).setPrivate();

            MethodSource getMethodSource =
                model.addMethod().setName(String.format("get%s", referenceName)).setReturnType(referenceName)
                    .setPublic();
            getMethodSource.addAnnotation().setName("Override");

            if(fieldValue.containsKey("type") && fieldValue.get("type").toString().equalsIgnoreCase("Ref")) {
              getMethodSource.setBody(String.format("return m%s == null? %s.get(m%sId): %s.validate(m%s);",
                  referenceName, referenceManager, referenceName, referenceManager, referenceName));
            }
            else {
              getMethodSource.setBody(String.format("return m%s;", referenceName));
            }

            MethodSource setMethodSource = model.addMethod().setName(String.format("set%s", referenceName)).setPublic();
            setMethodSource.addParameter(referenceName, String.format("p%s", referenceName));
            setMethodSource.setBody(String.format("this.m%s = p%s;", referenceName, referenceName));
            setMethodSource.addAnnotation().setName("Override");

            if(fieldValue.containsKey("type") && fieldValue.get("type").toString().equalsIgnoreCase("Ref")) {
              model.addField().setName(String.format("m%sId", WordUtils.capitalize(fieldName)))
                  .setType(getReferenceIdReturnType((JSONObject) fieldValue.get("reference"))).setPrivate();

              getMethodSource =
                  model.addMethod().setBody(String.format("return m%sId;", WordUtils.capitalize(fieldName)))
                      .setName(String.format("get%sId", WordUtils.capitalize(fieldName)))
                      .setReturnType(getReferenceIdReturnType((JSONObject) fieldValue.get("reference"))).setPublic();
              getMethodSource.addAnnotation().setName("Override");

              setMethodSource =
                  model.addMethod().setName(String.format("set%sId", WordUtils.capitalize(fieldName))).setPublic();
              setMethodSource.addParameter(getReferenceIdReturnType((JSONObject) fieldValue.get("reference")),
                  String.format("p%sId", WordUtils.capitalize(fieldName)));
              setMethodSource.setBody(String.format("this.m%sId = p%sId;", WordUtils.capitalize(fieldName),
                  WordUtils.capitalize(fieldName)));
              setMethodSource.addAnnotation().setName("Override");
            }
          }
          else {
            model.addField().setName(String.format("m%s", WordUtils.capitalize(fieldName)))
                .setType(fieldValue.get("type").toString()).setPrivate();

            MethodSource getMethodSource =
                model.addMethod().setBody(String.format("return m%s;", WordUtils.capitalize(fieldName)))
                    .setName(String.format("get%s", WordUtils.capitalize(fieldName)))
                    .setReturnType(fieldValue.get("type").toString()).setPublic();
            getMethodSource.addAnnotation().setName("Override");

            MethodSource setMethodSource =
                model.addMethod().setName(String.format("set%s", WordUtils.capitalize(fieldName))).setPublic();
            setMethodSource.addParameter(fieldValue.get("type").toString(),
                String.format("p%s", WordUtils.capitalize(fieldName)));
            setMethodSource.setBody(String.format("this.m%s = p%s;", WordUtils.capitalize(fieldName),
                WordUtils.capitalize(fieldName)));
            setMethodSource.addAnnotation().setName("Override");
          }
        }
      }
    }
    String modelManagerInstance = String.format("s%s", managerName);
    String lastModified = "lastModified";
    model.addField().setName(String.format("m%s", WordUtils.capitalize(lastModified))).setType("String").setPrivate();

    MethodSource getMethodSource =
        model.addMethod().setBody(String.format("return m%s;", WordUtils.capitalize(lastModified)))
            .setName(String.format("get%s", WordUtils.capitalize(lastModified))).setReturnType("String").setPublic();
    getMethodSource.addAnnotation().setName("Override");

    MethodSource setMethodSource =
        model.addMethod().setName(String.format("set%s", WordUtils.capitalize(lastModified))).setPublic();
    setMethodSource.addParameter("String", String.format("p%s", WordUtils.capitalize(lastModified)));
    setMethodSource.setBody(String.format("this.m%s = p%s;", WordUtils.capitalize(lastModified),
        WordUtils.capitalize(lastModified)));
    setMethodSource.addAnnotation().setName("Override");

    MethodSource methodSource = model.addMethod().setName("create");
    methodSource.setReturnType(getIdType(pFields));
    methodSource.setPublic();
    methodSource.setBody(String.format("return %s.create(this);", modelManagerInstance));
    methodSource.addAnnotation().setName("Override");

    methodSource = model.addMethod().setName("update");
    methodSource.setReturnTypeVoid();
    methodSource.setPublic();
    methodSource.setBody(String.format("%s.update(this);", modelManagerInstance));
    methodSource.addAnnotation().setName("Override");

    methodSource = model.addMethod().setName("edit");
    methodSource.setReturnType(pMutable);
    methodSource.setPublic();
    methodSource.setBody(String.format("return new Persistent%s(this);", pImmutable));
    methodSource.addAnnotation().setName("Override");

    methodSource = model.addMethod().setName("delete");
    methodSource.setReturnTypeVoid();
    methodSource.setPublic();
    methodSource.setBody(String.format("%s.delete(this);", modelManagerInstance));
    methodSource.addAnnotation().setName("Override");

    methodSource = model.addMethod().setName(modelName);
    methodSource.setConstructor(true).setBody("");
    methodSource.setPublic();

    methodSource = model.addMethod().setName(modelName);
    methodSource.setConstructor(true);
    methodSource.addParameter(pMutable, String.format("p%s", pImmutable));
    methodSource.setBody(getConstructorBody(pFields, String.format("p%s", pImmutable)));
    methodSource.setPublic();

    model.addMethod().setStatic(true).setName("staticBlock").setBody(generateStaticBlock(model, pFields, managerName));
    print(model.toString().replace("void staticBlock()", ""));
  }

  private void generateDaoDecorator(String pImmutable, String pMutable, String idType) {
    JavaClassSource decorator = Roaster.create(JavaClassSource.class);
    decorator.setPackage("org.ums.decorator").setName(String.format("%sDaoDecorator", pImmutable));
    decorator.addImport(String.format("org.ums.manager.%sManager", pImmutable));
    decorator.addImport("org.ums.decorator.ContentDaoDecorator");
    decorator.addImport(String.format("org.ums.domain.model.immutable.%s", pImmutable));
    decorator.addImport(String.format("org.ums.domain.model.mutable.%s", pMutable));
    decorator.setSuperType(String.format("ContentDaoDecorator<%s, %s, %s, %sManager>", pImmutable, pMutable, idType,
        pImmutable));
    decorator.addInterface(String.format("%sManager", pImmutable));

    print(decorator.toString());
  }

  private String getConstructorBody(JSONArray pFields, String pImmutable) {
    StringBuilder builder = new StringBuilder();
    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(field.get(fieldName) instanceof String) {
          builder.append(String.format("set%s(%s.get%s());\r\n", WordUtils.capitalize(fieldName), pImmutable,
              WordUtils.capitalize(fieldName)));
        }
        else {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);
          if(fieldValue.containsKey("idType")) {
            builder.append(String.format("set%s(%s.get%s());\r\n", WordUtils.capitalize(fieldName), pImmutable,
                WordUtils.capitalize(fieldName)));
          }
          else {
            JSONObject referenceObject = (JSONObject) fieldValue.get("reference");
            String referenceName = WordUtils.capitalize(referenceObject.get("name").toString());
            builder.append(String.format("set%s(%s.get%s());\r\n", referenceName, pImmutable, referenceName));

            if(fieldValue.containsKey("type") && fieldValue.get("type").toString().equalsIgnoreCase("Ref")) {
              builder.append(String.format("set%sId(%s.get%sId());\r\n", referenceName, pImmutable, referenceName));
            }
          }
        }
      }
    }
    builder.append(String.format("setLastModified(%s.getLastModified());", pImmutable));
    return builder.toString();
  }

  private String generateStaticBlock(JavaClassSource model, JSONArray pFields, String modelManager) {
    StringBuilder builder = new StringBuilder();
    builder.append("ApplicationContext applicationContext = AppContext.getApplicationContext();");
    builder.append("\r\n");

    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(!(field.get(fieldName) instanceof String)) {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);

          if(fieldValue.containsKey("type") && fieldValue.get("type").toString().equalsIgnoreCase("Ref")) {
            JSONObject referenceObject = (JSONObject) fieldValue.get("reference");
            String referenceName = WordUtils.capitalize(referenceObject.get("name").toString());

            if(referenceObject.containsKey("package")) {
              model.addImport(String.format("%s.%s", referenceObject.get("package").toString(), referenceName));
            }

            if(referenceObject.containsKey("manager")) {
              JSONObject managerObject = (JSONObject) referenceObject.get("manager");
              String managerName = "";
              if(managerObject.containsKey("name")) {
                managerName = managerObject.get("name").toString();
              }
              else {
                managerName = String.format("%sManager", referenceName);
              }
              builder.append(String.format("s%s = applicationContext.getBean(\"%s\", %s.class);\r\n", managerName,
                  WordUtils.uncapitalize(managerName), managerName));

            }
          }

        }
      }
    }
    builder.append(String.format("s%s = applicationContext.getBean(\"%s\", %s.class);", modelManager,
        WordUtils.uncapitalize(modelManager), modelManager));
    return builder.toString();
  }

  private String getReferenceIdReturnType(JSONObject referenceObject) {
    if(referenceObject.containsKey("id")) {
      JSONObject idObject = (JSONObject) referenceObject.get("id");
      return idObject.get("type").toString();
    }
    return "String";
  }

  private File getTemplate() {
    ClassLoader classLoader = getClass().getClassLoader();
    return new File(classLoader.getResource("template.json").getFile());
  }

  private JSONObject getTemplateJson() {
    JSONParser parser = new JSONParser();

    try {
      return (JSONObject) parser.parse(new FileReader(getTemplate()));
    } catch(Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  private void print(String pContent) {
    System.out.println("-------------------------------------------------------------------------");
    System.out.println(Roaster.format(pContent));
    System.out.println("-------------------------------------------------------------------------");
  }

  private boolean hasDateField(JSONArray pFields) {
    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(field.get(fieldName) instanceof String) {
          String type = field.get(fieldName).toString();
          if(type.equalsIgnoreCase("Date")) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private String getIdType(JSONArray pFields) {
    for(int i = 0; i < pFields.size(); i++) {
      JSONObject field = (JSONObject) pFields.get(i);

      for(Object fieldNameObject : field.keySet()) {
        String fieldName = (String) fieldNameObject;

        if(!(field.get(fieldName) instanceof String)) {
          JSONObject fieldValue = (JSONObject) field.get(fieldName);
          if(fieldValue.containsKey("idType")) {
            return fieldValue.get("type").toString();
          }
        }
      }
    }
    return null;
  }
}
