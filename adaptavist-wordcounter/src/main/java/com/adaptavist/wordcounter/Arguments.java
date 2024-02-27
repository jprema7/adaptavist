package com.adaptavist.wordcounter;

public record Arguments(String inputFile, String outputFile){

    public static Arguments parse(String[] args) {
        if (args.length < 1 || args.length > 2) {
            throw new IllegalArgumentException("Usage: java Application <input file> <optional: output file>");
        }
        return new Arguments(args[0], args.length == 2 ? args[1] : null);
    }
}
