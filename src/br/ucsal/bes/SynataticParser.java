package br.ucsal.bes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SynataticParser {

    static private Integer currentLine = 1;
    static public void incrementFileLine(){
        currentLine++;
    }

    static private Map<String, SymbolEntry> symbolTable;
    static public SymbolEntry addToOrGetFromSymbolTable(String lexeme, Token token){
        String truncatedLexeme = truncateLexeme(lexeme, 35);
        SymbolEntry symbolEntry = symbolTable.get(truncatedLexeme);

        if(symbolEntry == null){

            Integer newIndex = symbolTable.size() + 1;
            Integer lengthBeforeTruncate = lexeme.length();
            Integer lengthAfterTruncate = truncatedLexeme.length();
            symbolEntry = new SymbolEntry(
                    truncatedLexeme,
                    token,
                    newIndex,
                    lengthBeforeTruncate,
                    lengthAfterTruncate
            );

            symbolTable.put(lexeme, symbolEntry);
        }

        symbolEntry.addLineNumber(currentLine, 5);
        return symbolEntry;
    }

    private static String truncateLexeme(String lexeme, Integer maxLength){
        if(lexeme.length() > maxLength){
            return lexeme.substring(0, maxLength);
        }

        return lexeme;
    }

    private File sourceFile;
    private List<LexEntry> lexReport;
    private final List<String> REPORT_HEADER = Arrays.asList(
            "Equipe 2",
            "Nomes: Filipe Fróes, Filipe Roger, Juan Ribeiro, Matheus Amorim",
            "Telefones: 71 9 {935-5276, 9211-6178, 8102-0852, 9275-3627}",
            "Emails: {filipe.froes, matheus.amorim, juan.pereira, filipe.santos}@ucsal.edu.br",
            "",""
    );


    public SynataticParser(File sourceFile){
        this.sourceFile = sourceFile;
        this.lexReport = new ArrayList<>();
        this.symbolTable = new LinkedHashMap<>();
    }

    public void runLexicalAnalyzer() throws IOException {
        Path filePath = Paths.get(sourceFile.getPath());
        String sourceText = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);

        LexicalAnalyzer lexAnalyzer = new LexicalAnalyzer(sourceText);

        do{
            LexEntry lexEntry = lexAnalyzer.analyze();
            if(lexEntry != null) lexReport.add(lexEntry);
        }while(!lexAnalyzer.isFinished());
    }

    public File generateLexicalReport() throws IOException {
        Path lexicalReport = this.makeReportFromSource(".LEX");

        List<String> reportTitle = Arrays.asList("Relatório da Análise Léxica","","");
        List<String> reportLines = this.lexReport.stream()
                .map(lp -> lp.toString())
                .collect(Collectors.toList());
        List<String> footer = Arrays.asList("","Total de registros: " + reportLines.size());

        return this.writeReport(lexicalReport, reportTitle, reportLines, footer);
    }

    public File generateSymbolReport() throws IOException {
        Path lexicalReport = this.makeReportFromSource(".TAB");

        List<String> reportTitle = Arrays.asList("Relatório da Tabela de Símbolos","","");
        List<String> reportLines = symbolTable.values().stream()
                .map(lp -> lp.toString())
                .collect(Collectors.toList());
        List<String> footer = Arrays.asList("","Total de registros: " + reportLines.size());

        return this.writeReport(lexicalReport, reportTitle, reportLines, footer);
    }

    private File writeReport(Path file, List<String> ...lineChunks) throws IOException{
        List<String> lines = new ArrayList<>(REPORT_HEADER);

        for (List<String>lineChunk: lineChunks){
            lines.addAll(lineChunk);
        }

        Files.write(file, lines, Charset.forName("UTF-8"));

        return file.toFile();
    }

    private Path makeReportFromSource(String ext){
        String absolutePath = sourceFile.getAbsolutePath();
        String toSavePath = absolutePath.substring(0, absolutePath.lastIndexOf('.')) + ext;
        return Paths.get(toSavePath);
    }

}
