package br.ucsal.bes;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {


        String filePath = "";

        for (int i = 0; i < args.length; i++){
            if(args[i].equals("-f") && (i + 1) < args.length){
                filePath = args[i + 1];
                break;
            }
        }

        if(filePath.isEmpty()){
            System.err.println("Faltando argumento obrigatório: f");
            System.err.println("Exemplo: -f caminho/para/arquivo");
            System.exit(0);
        }


        File sourceFile = new File(filePath  + ".181");
        String fileName = sourceFile.getName();
        String absolutePath = sourceFile.getAbsolutePath();

        if(!sourceFile.exists()){
            System.err.println("Arquivo "+ fileName +" não encontrado no caminho " + absolutePath);
            System.exit(0);
        }

        SynataticParser synataticParser = new SynataticParser(sourceFile);

        try {
            synataticParser.runLexicalAnalyzer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File lexicalReport = synataticParser.generateLexicalReport();
            File symbolReport = synataticParser.generateSymbolReport();

            System.out.println("Relatório de Análise Léxica salvo com sucesso em: " + lexicalReport.getAbsolutePath());
            System.out.println("Relatório de Tabela de Símbolos salvo com sucesso em: " + symbolReport.getAbsolutePath());
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Falha ao escrever arquivos em disco: ");
            e.printStackTrace();
        }
    }
}
