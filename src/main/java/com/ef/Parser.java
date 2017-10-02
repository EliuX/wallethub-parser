package com.ef;


import com.ef.config.ParserModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Note: The name <code>Parser</code> was infered as a restriction,
 * otherwise it would be named <code>WallethubParserApp</code>
 */
public class Parser {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new ParserModule()
        );
    }
}
