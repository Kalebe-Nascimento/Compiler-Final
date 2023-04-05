import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

public class GeradorCodigo {
  public GeradorCodigo(){
    try{
      FileWriter fstream = new FileWriter("codigo_gerado.txt",false);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write("");
      out.close();
    }catch(Exception e){
      System.err.println("Erro ao gravar arquivo: " + e.getMessage());
    }
  }

  public void printf(String text){
    String cod = new Date().getTime()+"";
    String value = text.replaceAll("\"", "");

    writeFile(
    "\n; printf(\""+value+"\")"
    +"\nsection .data\n"
    +"    format" + cod + " db \"" + value + "\",0\n"
    +"    len" + cod + " equ $-format" + cod + "\n"
    +"section .text\n"
    +"    global _start\n"
    +"_start:\n"
    +"    ; imprime a string " + cod + "\n"
    +"    mov eax, 4\n"
    +"    mov ebx, 1\n"
    +"    mov ecx, format" + cod + "\n"
    +"    mov edx, len" + cod + "\n"
    +"    int 0x80\n"
    +"    ; sai do programa\n"
    +"    mov eax, 1\n"
    +"    xor ebx, ebx\n"
    +"    int 0x80\n"
    );
  }

  public void scanf(String text){
    String cod = new Date().getTime()+"";
    String value = text.replaceAll("\"", "");

    writeFile(
    "\n; scanf(\""+value+"\")"
    +"\nsection .data\n"
    +"    format" + cod + " db \"" + value + "\",0\n"
    +"    len" + cod + " equ $-format" + cod + "\n"
    +"section .bss\n"
    +"    buffer resb 32 ; buffer de entrada para armazenar os valores lidos\n"
    +"section .text\n"
    +"    global _start\n"
    +"_start:\n"
    +"    ; lê o valor de entrada com base na string de formato " + cod + "\n"
    +"    mov eax, 3 ; número do sistema de chamada para ler entrada (sys_read)\n"
    +"    mov ebx, 0 ; descritor de arquivo padrão para entrada (stdin)\n"
    +"    mov ecx, buffer ; endereço do buffer de entrada\n"
    +"    mov edx, 32 ; tamanho máximo do buffer\n"
    +"    int 0x80 ; faz a chamada de sistema\n"
    +"    ; converte o valor lido para o tipo de dado apropriado\n"
    +"    ; ... código assembly para converter o valor lido para o tipo de dado apropriado ...\n"
    +"    ; apresenta os valores lidos\n"
    +"    ; ... código assembly para apresentar os valores lidos ...\n"
    +"    ; sai do programa\n"
    +"    mov eax, 1\n"
    +"    xor ebx, ebx\n"
    +"    int 0x80\n"
    );
  }

  public void writeFile(String text){
    try{
      FileWriter fstream = new FileWriter("codigo_gerado.txt",true);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write(text);
      out.close();
    }catch(Exception e){
      System.err.println("Erro ao gravar arquivo: " + e.getMessage());
    }
  }
}
