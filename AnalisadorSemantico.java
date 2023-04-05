import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AnalisadorSemantico {
  private Hashtable variables = new Hashtable();
  private Map<String, ArrayList<String[]>> functions = new HashMap<String, ArrayList<String[]>> ();

  public void createVar(Object type, Object id){
    variables.put(id, type);
  }

  public void verifyExistsVar(Object id) throws Exception {
    if(variables.get(id) == null){
      throwError("Erro: Variavel '" + id + "' nao declarada.");
    }
  }

  public void createFunction (Object name, Object parametersUnformatted) throws Exception{
    if(functions.get(name) != null){
      throwError("Erro: funcao '"+ name + "' ja foi declarada.");
    }
    String[] arrayParameters = parametersUnformatted.toString().length() > 1 ? parametersUnformatted.toString().split("\n") : new String[0];
    ArrayList parameters = new ArrayList();
    for(String p: arrayParameters){
      if(p.length() <= 1) continue;
      String[] parameter = p.split(" ");
      parameters.add(parameter);
    }
    
    functions.put(name.toString(), parameters);
  }

  public void existsFunction(Object name) throws Exception{
    if(functions.get(name) == null){
      throwError("Erro: funcao '"+ name + "' nao declarada anteriormente.");
    }
  }

  public void verifyParameters(Object name, Object pexec) throws Exception{
    String[] parametersExec = pexec.toString().length() > 1 ? pexec.toString().split(",") : new String[0];
    ArrayList<String[]> parameters = functions.get(name);

    if(parameters.size() != parametersExec.length){
      throwError("Erro: funcao '"+ name + "'" + " aceita somente "+ parameters.size() + " parametros.");
    }

    for(int i = 0; i < parameters.size(); i++){
      String type = parameters.get(i)[0];
      String pName = parameters.get(i)[1];
      Object value = parametersExec[i].trim();

      if(value.toString().contains("VARIAVEL")){
        String[] valueSplitted = value.toString().split(" ");
        if(valueSplitted[1].length() > 0 && variables.containsKey(valueSplitted[1])){
          String typeVar = variables.get(valueSplitted[1]).toString();
          if(type.compareTo(typeVar) != 0){
            throwError("Erro: Parametro '" + pName + "' aceita somente valores " + type + " ."); 
          }
          return;
        }
      }

      if(type.equals("INTEIRO") && (value.toString().contains(".") && !value.toString().contains(".0"))){
        throwError("Erro: Parametro '" + pName + "' aceita somente valores inteiros.");
      }
  
      if((type.equals("REAL") || type.equals("INTEIRO")) && (value.toString().contains("\"") || value.toString().contains("\'"))){
        throwError("Erro: Parametro '" + pName + "' somente aceita valores numericos.");
      }
  
      if(type.equals("STRING") && !value.toString().contains("\"")){
        throwError("Erro: Parametro '" + pName + "' somente aceita valores strings.");
      }
  
      if(type.equals("CARACTERE") && !value.toString().contains("\'")){
        throwError("Erro: Parametro '" + pName + "' somente aceita caracteres.");
      }
  
      if(type.equals("CARACTERE") && value.toString().length() > 3){
        System.out.println(value);
        throwError("Erro: Parametro '" + pName + "' somente aceita caracteres.");
      }
    }
  }

  public void throwError(String message) throws Exception {
    System.out.println(message);
    throw new Exception();
  }
  
  public void verifyAssignment(Object id, Object value) throws Exception {
    if(variables.get(id) == null){
      throwError("Erro: Variavel '" + id + "' nao declarada.");
    }

    String type = variables.get(id).toString();

    if(type.equals("INTEIRO") && (value.toString().contains(".") && !value.toString().contains(".0"))){
      throwError("Erro: Variavel '" + id + "' possui o tipo inteiro.");
    }

    if((type.equals("REAL") || type.equals("INTEIRO")) && (value.toString().contains("\"") || value.toString().contains("\'"))){
      throwError("Erro: Variavel '" + id + "' somente aceita valores numericos.");
    }

    if(type.equals("STRING") && !value.toString().contains("\"")){
      throwError("Erro: Variavel '" + id + "' somente aceita valores strings.");
    }

    if(type.equals("CARACTERE") && !value.toString().contains("\'")){
      throwError("Erro: Variavel '" + id + "' somente aceita caracteres.");
    }

    if(type.equals("CARACTERE") && value.toString().length() > 3){
      throwError("Erro: Variavel '" + id + "' somente aceita caracteres.");
    }
  }
}
